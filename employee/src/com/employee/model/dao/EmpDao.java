package com.employee.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

//	(String empId, String empName, String empNo, String email, String phone, String deptCode,
//			String jobCode, String salLevel, int salary, double bonus, String managerId, Date hireDate, Date entDate,
//			char entYN)
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

}
