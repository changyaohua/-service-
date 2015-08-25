package com.chang.news.dao;

import java.util.List;

import com.chang.news.bean.NoticeBean;


/**
 * 
 * @since 2015��8��20��
 * @author ��ҫ��
 * @version v1.0
 *
 */
public interface NoticeDao {

	public boolean insertNewsData(List<NoticeBean> noticeList) throws Exception;

	public List<NoticeBean> fetchAllNotice() throws Exception;

	public List<NoticeBean> fetchNoticeByPageNO(int pageNo) throws Exception;

	public int fetchNoticeRows() throws Exception;

	public NoticeBean fetchFirstNotice() throws Exception;

}
