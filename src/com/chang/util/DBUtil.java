package com.chang.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ��ȡ���ݿ������ʵ���͹ر������ݿ����ӵ������Դ��ͨ����
 * 
 * @since 2015��8��20��
 * @author ��ҫ��
 * @version v1.0
 *
 */
public class DBUtil {
	/**
	 * ������mysql���ݿ�������ӵ�ʵ��������ķ���
	 * 
	 * @return ���ӵ�ʵ��������
	 * @throws Exception
	 *             �����ݿ�������ӷ������쳣
	 */
	public Connection getConnection() throws Exception {
		Connection conn = null;
		String className = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "root";
		Class.forName(className).newInstance();
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	/**
	 * �ر������ݿ����ӵ������Դ�ķ���
	 * 
	 * @param connection
	 *            ���ݿ�����Ӷ���
	 * @param preparedStatement
	 *            ���ݿ��preparedStatement����
	 * @param resultSet
	 *            ���ݿ��resultSet����
	 * @throws Exception
	 *             �ر����ݿ���쳣
	 */
	public void closeDBSourse(Connection connection,
			PreparedStatement preparedStatement, ResultSet resultSet)
			throws Exception {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}
