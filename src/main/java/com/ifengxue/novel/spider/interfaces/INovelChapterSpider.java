package com.ifengxue.novel.spider.interfaces;

import java.util.List;

import com.ifengxue.novel.spider.entity.Chapter;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;

/**
 * @author LiuKeFeng
 * @date   2016��9��17��
 */
public interface INovelChapterSpider extends INovelSpider {
	/**
	 * ��ԭ���ӣ� http://www.aaa.com/xiaoshuo/bbb.html ��ôֻ��Ҫ��xiaoshuo/bbb.html����
	 * @param url Ҫ��ȡ�½��б�Ķ�url
	 * @return
	 */
	public List<Chapter> getChapters(String url) throws NovelSpiderException ;
}
