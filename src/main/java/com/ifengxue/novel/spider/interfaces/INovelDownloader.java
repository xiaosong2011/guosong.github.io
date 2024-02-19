package com.ifengxue.novel.spider.interfaces;

import java.util.List;

import com.ifengxue.novel.spider.entity.Chapter;
import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.exceptions.NovelDownloadException;

/**
 * С˵�½�������
 * @author LiuKeFeng
 * @date   2016��9��27��
 */
public interface INovelDownloader {
	/** ����ʧ��ʱ���ԵĴ��� */
	public static final int TRY_TIME = 3;
	/**
	 * @param path	�����ļ���·��
	 * @param novelSiteEnum		
	 * @param chapters Ҫ���ص��½�
	 * @throws Exception
	 */
	public void download(String path, NovelSiteEnum novelSiteEnum, List<Chapter> chapters) throws NovelDownloadException;
}
