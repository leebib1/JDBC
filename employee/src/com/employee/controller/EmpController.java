package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.employee.model.dto.Employee;
import com.employee.model.service.EmpService;
import com.employee.view.MainView;

public class EmpController {
	private EmpService service=new EmpService();
	
	
	//mainmenu 호출
	public void mainMenu() {
		new MainView().mainMenu();
	}
	
	//전체 사원을 조회하는 기능
	public void selectEmpAll() {
		List<Employee> employees=service.selectEmpAll();
		new MainView().selectEmpAll(employees);
	}

	//사원조회 SubMenu(1.부서, 2.직책, 3.이름, 4. 급여(크고작고->선택))
	public void selectEmpKeyword() {
		
	}
	
	//사원 등록
	public void addEmployee() {
		
	}

	//사원수정(직책, 부서, 급여, 전화번호, 이메일)
	public void updateEmployee() {
		
	}

	//사원 삭제 ->삭제 기준 값(id)필요
	public void deleteEmployee() {
		
	}

	//부서관리 submenu(1.등록, 2.수정,3.삭제)
	public void department() {
		
	}

	//submenu(1.등록, 2.부서수정,3.삭제)
	public void job() {
		
	}
	
	

	
}
