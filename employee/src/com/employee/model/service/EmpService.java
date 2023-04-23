package com.employee.model.service;

import java.sql.Connection;
import java.util.List;

import com.employee.model.dao.EmpDao;
import com.employee.model.dto.Employee;

import static com.employee.common.JDBCTemplate.*;

public class EmpService {
	private EmpDao dao=new EmpDao();

	public List<Employee> selectEmpAll() {
		Connection conn=getConnection();
		List<Employee> employees=dao.selectEmpAll(conn);
		close(conn);
		return employees;
		
	}

	public List<Employee> selectEmployee(int choice, String data) {
		Connection conn=getConnection();
		List<Employee> employees=dao.selectEmployee(conn,choice,data);
		close(conn);
		return employees;
	}

	public int insertEmployee(String id, Employee employee) {
		Connection conn=getConnection();
		int result=dao.insertEmployee(conn, id,employee);
		close(conn);
		return result;
	}

	public int searchEmpId(String id) {
		Connection conn=getConnection();
		int result=dao.searchEmpId(conn,id);
		close(conn);
		return result;
		
	}

	public int deleteEmployee(String id) {
		Connection conn=getConnection();
		int result=dao.deleteEmployee(conn, id);
		close(conn);
		return result;
	}

	public int updateEmployee(String id, Employee employee) {
		Connection conn=getConnection();
		int result=dao.updateEmployee(conn,id,employee);
		close(conn);
		return result;
	}

	public int department(int choice) {
		Connection conn=getConnection();
		int result=dao.department(conn, choice);
		close(conn);
		return result;
	}

	
	

}
