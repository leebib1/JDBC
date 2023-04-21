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

}
