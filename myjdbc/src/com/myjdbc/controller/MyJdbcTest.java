package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.myjdbc.medel.vo.Department;

public class MyJdbcTest {

	public static void main(String[] args) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn=DriverManager.getConnection("jdbc:oracle:thin"
					+ ":@localhost:1521:xe","BS","BS");
			
			stmt=conn.createStatement();
			
			String sql="SELECT * FROM DEPARTMENT";
			rs=stmt.executeQuery(sql);
			
			List<Department> department=new ArrayList();
			while(rs.next()) {
				department.add(new Department(rs.getString("dept_id"),rs.getString("dept_title"),rs.getString("location_id")));
				//department.add(new Department(rs.getString(1),rs.getString(2),rs.getString(3)));
				//->각 컬럼이 인덱스 번호를 갖고 있기 때문에 인덱스 번호로도 불러올 수 있지만 해당 컬럼을 명시해주지 않기 때문에 가독성이 떨어진다.
			}
			department.forEach((d)->System.out.println(d));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null)
				try {
					if(conn!=null) conn.close();
					if(stmt!=null) stmt.close();
					if(rs!=null) rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
	}

}
