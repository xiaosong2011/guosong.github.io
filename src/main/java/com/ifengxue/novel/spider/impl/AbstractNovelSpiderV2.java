package com.ifengxue.novel.spider.impl;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

import com.ifengxue.novel.spider.exceptions.CrawlException;
import com.ifengxue.novel.spider.exceptions.NovelSpiderException;
import com.ifengxue.novel.spider.interfaces.INovelSpider;
import com.ifengxue.novel.spider.util.NovelSpiderUtil;

/**
 * �ڶ��汾�������һ�汾Ƶ����URL���в���Ҫ�Ľ�����ƴ�ӵȲ���
 * @author LiuKeFeng
 * @date   2016��10��2��
 */
public abstract class AbstractNovelSpiderV2 implements INovelSpider {
	protected String domain;
	protected Document parseDoc;
	/**
	 * ��ȡ��ҳ����
	 * @param url ������URL
	 * @param charset ��ҳ�����ʽ
	 */
	@Override
	public String crawl(String url, String charset) throws NovelSpiderException {
		if (!url.startsWith("http")) url = "http://" + url;
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(RequestConfig.custom()
				.setConnectionRequestTimeout(2_000)
				.setConnectTimeout(10_000)
				.setSocketTimeout(10_000)
				.build());
		NovelSpiderUtil.setDefaultNovelSpiderHeader(httpGet);
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				CloseableHttpResponse response = httpClient.execute(httpGet)) {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else {
				throw new CrawlException("ץȡʧ�ܣ�HTTP״̬�룺" + statusLine.getStatusCode());
			}
		} catch (IOException e) {
			throw new NovelSpiderException("ץȡʧ�ܣ�ʧ��ԭ��" + e.getMessage(), e);
		}
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	public Document getParseDoc() {
		return parseDoc;
	}

	public void setParseDoc(Document parseDoc) {
		this.parseDoc = parseDoc;
	}
	
}
