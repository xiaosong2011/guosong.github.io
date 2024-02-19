package com.ifengxue.novel.spider.impl.book;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifengxue.novel.spider.entity.BxwxBook;
import com.ifengxue.novel.spider.enums.NovelSiteEnum;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;
import com.ifengxue.novel.spider.impl.AbstractNovelSpiderV2;
import com.ifengxue.novel.spider.interfaces.INovelBookSpider;
import com.ifengxue.util.common.CollectionTool;

/**
 * ������ѧС˵�б�ҳ����
 * @author LiuKeFeng
 * @date   2016��10��2��
 */
public class BxwxBookSpider extends AbstractNovelSpiderV2 implements INovelBookSpider<BxwxBook> {

	@Override
	public boolean verify(Object obj) {
		return true;
	}

	@Override
	public List<BxwxBook> getsBook(String url) throws NovelSpiderException {
		String html = super.crawl(url, "GBK");
		parseDoc = Jsoup.parse(html);
		parseDoc.setBaseUri("http://www." + NovelSiteEnum.BiXiaWenXue.getComment());
		Elements grids = parseDoc.getElementsByClass("grid");
		if (!CollectionTool.isNotNullAndEmpty(grids)) throw new NovelSpiderException("û���ҵ�ƥ��class=grid��Ԫ��");
		Elements trs = grids.get(0).getElementsByTag("tr");
		if (!CollectionTool.isNotNullAndEmpty(trs) || trs.size() == 1) throw new NovelSpiderException("û���ҵ�ƥ��tag=tr��Ԫ�أ�����Ԫ�س���ֻ��1");
		List<BxwxBook> books = new ArrayList<>();
		for (int i = 1, size = trs.size(); i < size; i++) {		//��һ��Ԫ��Ϊ���⣬����
			BxwxBook book = parseElementAndCreateBook(trs, i);
			books.add(book);
		}
		return books;
	}

	/** ����TrԪ�ز�����Bookʵ�� */
	private BxwxBook parseElementAndCreateBook(Elements trs, int i) {
		BxwxBook book = new BxwxBook();
		Elements tds = trs.get(i).getElementsByTag("td");
		assert tds.size() == 6 : "���Ȳ�Ϊ6�����ڶ�����";
		Element booknameTag = tds.get(0).getElementsByTag("a").first();
		book.setBookname(booknameTag.text());
		book.setUrl(booknameTag.absUrl("href"));
		book.setUrl(book.getUrl().replace("binfo", "b").replace(".htm", ""));	//�滻С˵��ҳ��Ϊ�½�ҳ
		Element lastUpdateTag = tds.get(1).getElementsByTag("a").first();
		book.setLastUpdateChapter(lastUpdateTag == null ? null : lastUpdateTag.text());
		book.setLastUpdateChapterUrl(lastUpdateTag == null ? null : lastUpdateTag.absUrl("href"));	//ò�����URL��С˵�½��б�ҳURL��һ���ġ���
		book.setAuthor(tds.get(2).text());
		book.setSize(tds.get(3).text());
		book.setLastUpdateTime(tds.get(4).text());
		book.setStatus(tds.get(5).text());
		return book;
	}

	@Override
	public boolean hasNextPage() {
		return parseDoc.select("#pagelink .next").first() != null;	//ֻҪ����һҳ���Ԫ���ڣ�˵����ûץȡ�����	
	}
}
