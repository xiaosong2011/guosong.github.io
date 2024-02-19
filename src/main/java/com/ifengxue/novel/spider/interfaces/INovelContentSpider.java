package com.ifengxue.novel.spider.interfaces;

import com.ifengxue.novel.spider.entity.Content;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;

/**
 * @author LiuKeFeng
 * @date   2016��9��19��
 */
public interface INovelContentSpider {
	public Content getContent(String url) throws NovelSpiderException;
}
