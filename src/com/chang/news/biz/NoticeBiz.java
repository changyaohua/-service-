package com.chang.news.biz;

import java.util.List;

import com.chang.news.bean.NoticeBean;


/**
 * ���ڶ��û���Ϣ���в�����ҵ��㣬��Ҫ���ڵ�����ʵ����Ķ�Ӧ�ķ���
 * 
 * @since 2015��8��20��
 * @author ��ҫ��
 * @version v1.0
 *
 */
public interface NoticeBiz {

	public boolean insertNewsData(List<NoticeBean> noticeList);

	public List<NoticeBean> fetchAllNotice();
	
}
