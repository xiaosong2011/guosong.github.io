package com.ifengxue.novel.spider.impl.download;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ifengxue.novel.spider.ContentFactory;
import com.ifengxue.novel.spider.entity.Chapter;
import com.ifengxue.novel.spider.entity.Content;
import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.exceptions.NovelDownloadException;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;
import com.ifengxue.novel.spider.interfaces.INovelContentSpider;
import com.ifengxue.novel.spider.interfaces.INovelDownloader;
import com.ifengxue.util.common.CollectionTool;
import com.ifengxue.util.common.CommTool;

/**
 * @author LiuKeFeng
 * @date   2016��9��27��
 */
public class DefaultNovelDownloader implements INovelDownloader {

	@Override
	public void download(String path, NovelSiteEnum novelSiteEnum, List<Chapter> chapters)
			throws NovelDownloadException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(path, "GBK");
			assert !CollectionTool.isNotNullAndEmpty(chapters) : "�½��б�ӦΪnull��Ϊsize=0";
			if (CollectionTool.isNotNullAndEmpty(chapters)) {
				for (Chapter chapter : chapters) {
					Content content = null;
					try {
						content = tryAndDownload(novelSiteEnum, chapter.getHref());
					} catch (NovelSpiderException e) {
						//���Զ������ʧ����
						content = new Content();
						content.setTitle(chapter.getText());
						content.setContent("�ܱ�Ǹ��" + e.getMessage());
					}
					out.println(content.getTitle());
					out.println(content.getContent());
					CommTool.sleep(1_000);	//���ز�Ҫ̫Ƶ��
				}
			}
		} catch (IOException e) {
			throw new NovelDownloadException(e);
		} finally {
			out.close();
		}
	}
	
	/**
	 * ��γ�������
	 * @param novelSiteEnum
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public Content tryAndDownload(NovelSiteEnum novelSiteEnum, String url) throws NovelSpiderException {
		INovelContentSpider contentSpider = ContentFactory.getContentSpider(novelSiteEnum);
		Content content = null;
		for (int i = 1; i <= TRY_TIME; i++) {
			try {
				content = contentSpider.getContent(url);
				break;	//���سɹ�
			} catch (NovelSpiderException e) {
				// ����
			}
		}
		if (content != null) {
			return content;
		} else {
			throw new NovelSpiderException("����" + TRY_TIME + "�����ؾ�ʧ���ˣ�");
		}
	}
}
