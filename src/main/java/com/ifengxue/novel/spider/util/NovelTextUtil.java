package com.ifengxue.novel.spider.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import com.ifengxue.util.common.FileUtil;

/**
 * С˵�ı�����
 * @author LiuKeFeng
 * @date   2016��9��30��
 */
public final class NovelTextUtil {
	private NovelTextUtil() {}
	/** �ϲ�����ı��ļ���һ���ı��ļ� */
	public static void multiFileMerge(String path, String mergeName) {
		File file = new File(path);
		File[] files = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		if (files == null || files.length == 0) {
			FileUtil.appendContent(mergeName, "", "GBK");
			return;
		}
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String[] o1ChapterRange = getChapterRange(o1.getName());
				String[] o2ChapterRange = getChapterRange(o2.getName());
				assert o1ChapterRange.length == 2 : "С˵�½�����" + o1.getName() + "��ʽ����ȷ��";
				assert o2ChapterRange.length == 2 : "С˵�½�����" + o2.getName() + "��ʽ����ȷ��";
				int o1StartChpater = Integer.parseInt(o1ChapterRange[0]);
				int o2StartChapter = Integer.parseInt(o2ChapterRange[0]);
				return o1StartChpater - o2StartChapter;
			}
		});
		
		//����ɾ��Ŀ���ļ�:����ʷ����
		FileUtil.deleteFile(mergeName);
		for (File sourceFile : files) {
			FileUtil.appendFile(sourceFile.getAbsolutePath(), mergeName, "GBK", true);
		}
	}
	
	/**
	 * ���ļ���Ϊ10-100.txt�򷵻�����Ϊ["10","100"]
	 * @param name
	 * @return
	 */
	private static String[] getChapterRange(String name) {
		name = name.substring(0, name.lastIndexOf('.'));
		String[] chapterRange = name.split("\\-");
		return chapterRange;
	}
}
