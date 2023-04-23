package com.employee.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.employee.model.dto.Employee;

public class EmpDao {
	private Properties sql=new Properties();
	{
		try{
			String path=EmpDao.class.getResource("/sql/employee/emp_sql.properties").getPath();
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Employee> selectEmpAll(Connection conn) {
		Statement stmt=null;
		ResultSet rs=null;
		List<Employee> employees=new ArrayList();
		String sql=this.sql.getProperty("selectAllEmp");
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				employees.add(getEmployee(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//사원조회 SubMenu(1.부서, 2.직책, 3.이름, 4. 급여(크고작고->선택))
	//서브메뉴에 따라 받은 데이터와 일치하는 사원 조회
	public List<Employee> selectEmployee(Connection conn, int choice, String data) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Employee> employees=new ArrayList();
		String sql=this.sql.getProperty("selectWhereEmp");
		try {
			
			if(choice==4) {
				sql=this.sql.getProperty("salaryAsc");
			}else if(choice==5) {
				sql=this.sql.getProperty("salaryDes");
			}
			
			switch(choice) {
			case 1 : sql=sql.replaceAll("#1","dept_title"); break;
			case 2 : sql=sql.replaceAll("#1","job_code"); break;
			case 3 : sql=sql.replaceAll("#1","emp_name"); break;
			}
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+data+"%");
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				employees.add(getEmployee(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//입력받은 사원을 DB에 추가하기
	public int insertEmployee(Connection conn, String id, Employee employee) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=this.sql.getProperty("insertEmp");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getEmpNo());
			pstmt.setString(4, employee.getEmail());
			pstmt.setString(5, employee.getPhone());
			pstmt.setString(6, employee.getDeptCode());
			pstmt.setString(7, employee.getJobCode());
			pstmt.setString(8, employee.getSalLevel());
			pstmt.setInt(9, employee.getSalary());
			pstmt.setDouble(10, employee.getBonus());
			pstmt.setString(11, employee.getManagerId());
			pstmt.setDate(12, employee.getHireDate());
			pstmt.setDate(13, employee.getEntDate());
			pstmt.setString(14, String.valueOf(employee.getEntYN()));
			
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Employee getEmployee(ResultSet rs) throws SQLException {
		Employee e=new Employee();
		e.setEmpId(rs.getString("emp_id"));
		e.setEmpName(rs.getString("emp_name"));
		e.setEmpNo(rs.getString("emp_no"));
		e.setEmail(rs.getString("email"));
		e.setPhone(rs.getString("phone"));
		e.setDeptCode(rs.getString("dept_code"));
		e.setJobCode(rs.getString("job_code"));
		e.setSalLevel(rs.getString("sal_level"));
		e.setSalary(rs.getInt("salary"));
		e.setBonus(rs.getDouble("bonus"));
		e.setManagerId(rs.getString("manager_id"));
		e.setHireDate(rs.getDate("hire_date"));
		e.setEntDate(rs.getDate("ent_date"));
		e.setEntYN(rs.getString("ent_yn").charAt(0));
		return e;
	}

	//추가, 삭제할 id가 존재하는지 확인하는 메소드
	public int searchEmpId(Connection conn, String id) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=this.sql.getProperty("searchEmpId");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			//입력 받은 ID가 존재하면 1을 반환
			if(rs.next()) return 1;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteEmployee(Connection conn, String id) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=this.sql.getProperty("deleteEmp");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//부서 테이블을 관리하는 메소드(1.등록, 2.수정,3.삭제)
	public int department(Connection conn, int choice) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql="";
		try {
			switch(choice) {
			case 1: sql="INSERT INTO DEPARTMENT VALUES(?,?,?)"; break;
			case 2: sql="UPDATE DEPARTMENT SET DEPT_ID=?, DEPT_TITLE=? LOCATION_ID=? WHERE DEPT_ID=?"; break;
			case 3: sql="DELETE FROM DEPARTMENT WHERE DEPT_ID=?"; break;
			}
			pstmt=conn.prepareStatement(sql);
			if(choice==3) {
				
			}
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	

	public int updateEmployee(Connection conn, String id, Employee employee) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=this.sql.getProperty("updateEmp");
		try {
			pstmt=conn.prepareStatement(sql);
			//UPDATE EMPLOYEE SET JOB_CODE=?, DEPT_CODE=?, SALARY=?, PHONE=?, EMAIL=? WHERE EMP_ID=?
			pstmt.setString(1, employee.getJobCode());
			pstmt.setString(2, employee.getDeptCode());
			pstmt.setInt(3, employee.getSalary());
			pstmt.setString(4, employee.getPhone());
			pstmt.setString(5, employee.getEmail());
			pstmt.setString(6, id);
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}




	
	

}
