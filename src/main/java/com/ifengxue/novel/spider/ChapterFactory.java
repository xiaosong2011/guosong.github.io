package com.ifengxue.novel.spider;

import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.impl.chapter.BxwxNovelChapterSpider;
import com.ifengxue.novel.spider.impl.chapter.DefaultNovelChapterSpider;
import com.ifengxue.novel.spider.interfaces.INovelChapterSpider;

/**
 * ��Ӧƽ̨���½�ץȡ������
 * @author LiuKeFeng
 * @date   2016��9��27��
 */
public final class ChapterFactory {
	private ChapterFactory() {}
	
	/**
	 * ��ȡʵ����INovelChapterSpider�ӿڵ���
	 * @param novelSiteEnum
	 * @return
	 */
	public static INovelChapterSpider getChapterSpider(NovelSiteEnum novelSiteEnum) {
		switch (novelSiteEnum) {
		case BiQuGe : return new DefaultNovelChapterSpider(novelSiteEnum.getComment());
		case BiXiaWenXue : return new BxwxNovelChapterSpider(novelSiteEnum.getComment());
		case DingDianXiaoShuo : return new DefaultNovelChapterSpider(novelSiteEnum.getComment());
		default : throw new RuntimeException("��δ֧�ֵ�С˵վ�㡣");
		}
	}
}
