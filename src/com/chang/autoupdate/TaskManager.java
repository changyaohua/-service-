package com.chang.autoupdate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TaskManager implements ServletContextListener {

	/**
	 * ÿ���ӵĺ�����
	 */
	public static final long PERIOD_MINUTE = 1000*60;

	Timer timer;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		timer = new Timer("��ʱ������������", true);
		timer.schedule(new BackUpUpadteData(), 0, PERIOD_MINUTE*20);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (timer != null) {
			timer.cancel();
		}
	}

	class BackUpUpadteData extends TimerTask {
		private boolean isRunning = false;  //�ж϶�ʱ�����Ƿ�ִ�����
		private boolean isWorkTime = false; //�жϵ�ǰʱ���Ƿ�Ϊ����ʱ��
		
		private int currHour;  //��ǰʱ���Сʱ��ֵ
		@Override
		public void run() {
			Calendar cal = Calendar.getInstance();
			currHour = cal.get(Calendar.HOUR_OF_DAY);
			if (currHour >= 7 && currHour <=23) {
				isWorkTime = true;
			}
			if (isWorkTime) {
				if (!isRunning) {
					isRunning = true;
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
					System.out.println("��ʼִ�и�������..."); // ��ʼ����
					
					// working add what you want to do
					AutoUpdateNoticeOperation.update();
					
					System.out.println("ִ�и����������..."); // �������
					isRunning = false;
				} else {
					System.out.println("��һ������ִ�л�δ����..."); // ��һ������ִ�л�δ����
				}
			} else {
				System.out.println("���ڹ���ʱ�䣬δ���и�������...");
			}
			
		}

	}

}
