package com.ifengxue.novel.spider.impl.download;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ifengxue.novel.spider.ChapterFactory;
import com.ifengxue.novel.spider.entity.Chapter;
import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;
import com.ifengxue.novel.spider.interfaces.INovelChapterSpider;
import com.ifengxue.novel.spider.interfaces.INovelDownloader;
import com.ifengxue.novel.spider.interfaces.Observer;
import com.ifengxue.novel.spider.util.NovelSpiderUtil;
import com.ifengxue.novel.spider.util.NovelTextUtil;
import com.ifengxue.util.common.CollectionTool;
import com.ifengxue.util.common.FileUtil;

/**
 * @author LiuKeFeng
 * @date   2016��10��1��
 */
public class NovelDownloaderObserver implements Observer {
	private static ExecutorService service = Executors.newCachedThreadPool();
	/** Ҫ���ص�С˵���½��б�ҳ�� */
	protected String url;
	/** ȥ��������֮������url */
	protected String relativeUrl; 
	protected NovelSiteEnum novelSiteEnum;
	protected int taskAllocCount;
	protected int hasFinishedTaskCount;
	/**
	 * ������С˵վ���½��б�URL
	 * @param url
	 */
	public NovelDownloaderObserver(String url) {
		this.url = url;
	}
	public void process() {
		try {
			novelSiteEnum = NovelSpiderUtil.parseUrl(url);
			relativeUrl = NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url);
			INovelChapterSpider chapterSpider = ChapterFactory.getChapterSpider(novelSiteEnum);
			List<Chapter> chapters = chapterSpider.getChapters(relativeUrl);
			if (CollectionTool.isNotNullAndEmpty(chapters)) {
				Map<String, List<Chapter>> downloadTaskAlloc = NovelSpiderUtil.downloadTaskAlloc(chapters);
				taskAllocCount = downloadTaskAlloc.size();
				Set<String> taskSet = downloadTaskAlloc.keySet();
				INovelDownloader downloader = new DefaultNovelDownloader();
				
				String path =  NovelSpiderUtil.getNovelSavePath(url);
				//���������ڵ�·��
				FileUtil.mkdirs(path);
				FileUtil.mkdirs(path + NovelSpiderUtil.MERGE_PATH_AND_FILENAME.substring(0, NovelSpiderUtil.MERGE_PATH_AND_FILENAME.lastIndexOf('/')));
				for (String task : taskSet) {
					NovelDownloaderObservable observable = new NovelDownloaderObservable(downloader,
									path + "/" + task + ".txt",
									novelSiteEnum, downloadTaskAlloc.get(task));
					observable.registerObserver(this);
					service.execute(observable);
				}
			}
		} catch (NovelSpiderException e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	@Override
	public void update(Object arg) {
		if (++hasFinishedTaskCount >= taskAllocCount) {
			String savePath = NovelSpiderUtil.getNovelSavePath(url);
			NovelTextUtil.multiFileMerge(savePath, savePath + NovelSpiderUtil.MERGE_PATH_AND_FILENAME);
			service.shutdownNow();	// �ر��̳߳�
		}
	}
}
