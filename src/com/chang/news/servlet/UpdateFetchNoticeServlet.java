package com.chang.news.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.chang.news.initNewsData;
import com.chang.news.bean.NoticeBean;
import com.chang.news.biz.NoticeBiz;
import com.chang.news.biz.NoticeBizImpl;

/**
 * Servlet implementation class FirstFetchNoticeServlet
 */
@WebServlet("/UpdateFetchNoticeServlet")
public class UpdateFetchNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFetchNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeBiz noticeBiz = new NoticeBizImpl();
		String urlPath = "http://xsc.nuc.edu.cn/xwzx/zytz.htm";
		Set<NoticeBean> noticeSet = new initNewsData().getDataFromWeb(urlPath);
		List<NoticeBean> newNoticeList = new ArrayList<NoticeBean>();
		newNoticeList.addAll(noticeSet);
		NoticeBean oldFirstNoticeBean = noticeBiz.fetchFirstNotice();
		List<NoticeBean> newAddNotice = null;
		if (newNoticeList.get(0).equals(oldFirstNoticeBean)) {
			//����û�и������ݵĻ�����������
			System.out.println("δ��������");
		} else {
			newAddNotice = new ArrayList<NoticeBean>();
			for (NoticeBean noticeBean : newNoticeList) {
				if (noticeBean.equals(oldFirstNoticeBean)) {
					break;
				} else {
					newAddNotice.add(noticeBean);
				}
			}
			String sqlTableName = (String)request.getAttribute("sqlTableName");
			List<NoticeBean> noticeBeanList = noticeBiz.fetchNoticeByPageNO(1,sqlTableName);
			
	        //���¸��µ�д�����ݿ���
	        Collections.reverse(newAddNotice);
	        boolean isSuccess = noticeBiz.insertNewsData(newAddNotice,sqlTableName);
			if (isSuccess) {
				System.out.println("�������ݳɹ���");
			} else {
				System.out.println("��������ʧ������ʧ�ܣ�");
			}
			
			//���¸��µ����ݷ��ؿͻ���
			JSONArray jsonArray = JSONArray.fromObject(noticeBeanList);
			System.out.println(jsonArray.toString());
			response.getOutputStream().write(jsonArray.toString().getBytes("UTF-8"));  
	        response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
