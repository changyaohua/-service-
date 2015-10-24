package com.chang.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chang.news.bean.NoticeBean;
import com.chang.news.biz.NoticeBiz;
import com.chang.news.biz.NoticeBizImpl;


/**
 * �������ڴӽ�����ҳ�������ݼ��ص�mysql���ݿ���
 * 
 * @author ��ҫ��
 * @version v1.0
 *
 */
public class InitSchoolNews {
	
	private static String urlPath = "http://www.nuc.edu.cn/ejlb.jsp?urltype=tree.TreeTempUrl&wbtreeid=1106";
	private String urlNextPath = "http://www.nuc.edu.cn/ejlb.jsp";
	private String urlContentPath = "http://www.nuc.edu.cn";
	private String currUrlPath;
	
	private static String sqlTableName = "hongtai_school_news";
	
	private static Set<NoticeBean> noticeSet ;
	
	/**
	 * �����б���ѧУ԰������ҳ�б�Ľ���
	 * 
	 * @param url  У԰���ŵ�url
	 * @return    ��װ����������ҳ��Ϣ��set����
	 */
	public Set<NoticeBean> getDataFromWeb(String url) {
		currUrlPath = url;
		Document doc = null;
		Set<NoticeBean> tempList = new LinkedHashSet<NoticeBean>();
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Document content = Jsoup.parse(doc.toString());
		Elements elements = content.getElementsByClass("c1161");
		NoticeBean notice;
		for (Element element : elements) {
			notice = new NoticeBean();
			notice.setTitle(element.text());
			String time = element.parent().nextElementSibling().getElementsByClass("timestyle1161").text();
			notice.setTime(time);
			String contentUrl = urlContentPath
					+ element.attr("href");
			notice.setUrl(contentUrl);
			tempList.add(notice);
		}
		return tempList;
	}
	
	public void onLoad() {
		while (true) {
			String nextUrl = getNextUrlPath(currUrlPath);
			currUrlPath = urlNextPath + nextUrl;
			Set<NoticeBean> mlist = getDataFromWeb(currUrlPath);
			if (mlist == null || nextUrl == null) {
				break;
			} else {
				noticeSet.addAll(mlist);
			}
		}

				
	}

public String getNextUrlPath(String url) {
	Document doc = null;
	String nextUrlPath = null;
	Document content = null;
	try {
		doc = Jsoup.connect(url).get();
		content = Jsoup.parse(doc.toString());
	} catch (Exception e) {
		return null;
	}
	Elements elements = content.getElementsByClass("Next");
	for (Element element : elements) {
		if (element.text().equals("��ҳ")) {
			nextUrlPath = element.attr("href");
			break;
		}
	}
	return nextUrlPath;
}

private String getIndexFromString(String str) {
	String index = null;
	Pattern p = Pattern.compile("(\\d+)");
	Matcher m = p.matcher(str);
	if (m.find()) {
		index = m.group();
	}
	return index;
}
	
	public static void main(String[] args) {
		InitSchoolNews initSchoolNews = new InitSchoolNews();
		NoticeBiz noticeBiz = new NoticeBizImpl();
		noticeSet = initSchoolNews.getDataFromWeb(urlPath);
		initSchoolNews.onLoad();
		int count = 0;
		List<NoticeBean> noticeList = new ArrayList<NoticeBean>();
		noticeList.addAll(noticeSet);
		Collections.reverse(noticeList);
		for (NoticeBean bean : noticeList) {
			System.out.println(bean);
			count++;
		}
		System.out.println("���ƣ�" + count);
		boolean isSuccess = noticeBiz.insertNewsData(noticeList,sqlTableName);
		if (isSuccess) {
			System.out.println("��ʼ�����ݳɹ���");
		} else {
			System.out.println("��ʼ������ʧ�ܣ�");
		}
	}

}
