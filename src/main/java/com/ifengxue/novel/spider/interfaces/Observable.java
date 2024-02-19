package com.ifengxue.novel.spider.interfaces;

/**
 * ���۲���
 * @author LiuKeFeng
 * @date   2016��9��26��
 */
public interface Observable {
	public void registerObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notifyObservers();
	public void notifyObservers(Object arg);
}
