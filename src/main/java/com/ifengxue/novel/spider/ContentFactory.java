package com.ifengxue.novel.spider;

import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.impl.content.DefaultNovelContentSpider;
import com.ifengxue.novel.spider.interfaces.INovelContentSpider;

/**
 * @author LiuKeFeng
 * @date   2016��9��27��
 */
public final class ContentFactory {
	private ContentFactory() {}
	
	/**
	 * ��ȡʵ����INovelContentSpider�ӿڵ���
	 * @param novelSiteEnum
	 * @return
	 */
	public static INovelContentSpider getContentSpider(NovelSiteEnum novelSiteEnum) {
		switch (novelSiteEnum) {
		case BiQuGe : return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		case BiXiaWenXue : return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		case DingDianXiaoShuo : return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		default : throw new RuntimeException("��δ֧�ֵ�С˵վ�㡣");
		}
	}
}
