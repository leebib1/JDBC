package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteJdbcTest {

	public static void main(String[] args) {
		Connection conn=null;
		Statement stmt=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
			
			stmt=conn.createStatement();
			
			String sql="DELETE FROM DEPARTMENT WHERE DEPT_ID='D0'";
			int result=stmt.executeUpdate(sql);
			
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
