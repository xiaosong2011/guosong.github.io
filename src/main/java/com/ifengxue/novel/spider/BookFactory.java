package com.ifengxue.novel.spider;

import com.ifengxue.novel.spider.entity.BxwxBook;
import com.ifengxue.novel.spider.impl.book.BxwxBookSpider;
import com.ifengxue.novel.spider.interfaces.INovelBookSpider;

/**
 * ���б����湤��
 * @author LiuKeFeng
 * @date   2016��10��2��
 */
public final class BookFactory {
	private BookFactory() {}
	/**
	 * ��ȡһ��������ѧվ���С˵����
	 * @return
	 */
	public static INovelBookSpider<BxwxBook> getBxwxBookSpider() {
		return new BxwxBookSpider();
	}
}
