package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateJdbcTest {

	public static void main(String[] args) {
		Connection conn=null;
		Statement stmt=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
			
			stmt=conn.createStatement();
			
			String sql="UPDATE DEPARTMENT SET DEPT_TITLE='학생부',LOCATION_ID='L3' WHERE DEPT_ID='D0'";
			int result=stmt.executeUpdate(sql); //나중에 데이터를 반환하는 경우에는 해당 변수는 전부 try~catch 외부에 선언해줘야한다.
			
			if(result>0) conn.commit();
			else conn.rollback();
			
			System.out.println(result);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
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
