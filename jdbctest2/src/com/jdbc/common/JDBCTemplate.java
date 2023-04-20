package com.jdbc.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	//공통적으로 사용하는 기능들을 모아서 static 메소드로 구현한 뒤 사용한다.
	
	//Connection 객체를 생성해주는 기능을 제공
	public static Connection getConnection() {
		Connection conn=null;
		//파일 연동을 위해 절대경로를 가져온다.
		String path=JDBCTemplate.class.getResource("/driver.properties").getPath();
		System.out.println(path); 
		///D:/JDBC/jdbctest2/bin(<<까지 불러낸 절대경로)/gerResourc()에 적은 경로
		//->/D:/JDBC/jdbctest2/bin/driver.propertices
		
		try {
			Properties driver=new Properties();
			//코드가 여러 사람에게 공유 될 때 DB에 대한 정보를 은닉하기 위해 사용한다.
			driver.load(new FileReader(path));
			Class.forName(driver.getProperty("drivername"));
			conn=DriverManager.getConnection(driver.getProperty("url"),driver.getProperty("user"),driver.getProperty("pw"));
			conn.setAutoCommit(false);	
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}return conn;
	}
	
	//connection, Statement, ResultSet 객체를 닫아주는 기능 제공
	public static void close(Connection conn) {
		try {
			if(conn!=null&&!conn.isClosed()) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt!=null&&!stmt.isClosed()) stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs!=null&&!rs.isClosed()) rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//트랜젝션을 처리하는 기능을 제공
	public static void commit(Connection conn) {
		try {
			if(conn!=null&&!conn.isClosed()) conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn!=null&&!conn.isClosed()) conn.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
