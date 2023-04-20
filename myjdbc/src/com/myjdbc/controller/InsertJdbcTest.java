package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertJdbcTest {

	public static void main(String[] args) {
		//D0 강사부 L2 추가
		Connection conn=null;
		Statement stmt=null;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
			
			
			stmt=conn.createStatement();
			
			String sql="INSERT INTO DEPARTMENT VALUES('D0','강사부','L2')";
			int result=stmt.executeUpdate(sql);
			//int result=stmt.executeUpdate("INSERT INTO DEPARTMENT VALUES('D0','강사부','L2')");
			//->위 코드처럼 작성해도 상관없다.
			
			if(result>0) conn.commit();
			else conn.rollback();
			
			System.out.println(result);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null)
				try {
					if(conn!=null) conn.close();
					if(stmt!=null) stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		

	}

}
