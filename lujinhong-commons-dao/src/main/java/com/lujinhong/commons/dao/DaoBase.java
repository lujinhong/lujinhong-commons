package com.lujinhong.commons.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoBase implements Dao {

	@Override
	public Connection getConnection() throws DaoException {
	        try {
	        	//注册JDBC驱动程序
	        	Class.forName("com.mysql.jdbc.Driver");  
	        	
				//打开一个数据库连接
				String URL = "jdbc:mysql://1.2.3.4:3306/filter_conf";
				String USERNAME = "lujinhong";
				String PASSWORD = "lujinhong";

	        	Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD); 
				return conn;

	        	
	           //return dataSource.getConnection();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new DaoException();
	        }
	}
	
	protected void closeDbObject(ResultSet rs, Statement stmt, Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
