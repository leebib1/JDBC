package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.employee.model.dto.Employee;
import com.employee.model.service.EmpService;
import com.employee.view.MainView;

public class EmpController {
	private EmpService service=new EmpService();
	private MainView view=new MainView();
	
	
	//mainmenu 호출
	public void mainMenu() {
		view.mainMenu();
	}
	
	//전체 사원을 조회하는 기능
	public void selectEmpAll() {
		List<Employee> employees=service.selectEmpAll();
		view.selectEmpAll(employees);
	}

	//사원조회 SubMenu(1.부서, 2.직책, 3.이름, 4. 급여(크고작고->선택))
	public void selectEmpKeyword() {
		int choice=view.selectSubMenu();
		String data="";
		if(choice<=3) data=view.selectData();
		List<Employee> employee=service.selectEmployee(choice,data);
		view.selectEmpAll(employee);
	}
	
	
	//사원 등록
	public void insertEmployee() {
		String id=view.inputEmpId();
		if (service.searchEmpId(id) == 0) {//동일한 아이디가 없을 때만 데이터를 입력 받는다.
			Employee employee = view.insertEmployee();
			int result = service.insertEmployee(id,employee);
			view.updateResult(result);
		}else view.updateResult(10);
	}

	//사원수정(직책, 부서, 급여, 전화번호, 이메일)
	public void updateEmployee() {
		String id=view.inputEmpId();
		if(service.searchEmpId(id)==1) {//ID가 존재하는 경우에만 수정 가능
			Employee employee=view.updateEmployee();
			int result=service.updateEmployee(id,employee);
			view.updateResult(result);
		}else view.updateResult(10);
	}

	//사원 삭제 ->삭제 기준 값(id)필요
	public void deleteEmployee() {
		String id=view.inputEmpId();
		if(service.searchEmpId(id)==1) {//id가 존재하는 경우에만 삭제 가능
			int result=service.deleteEmployee(id);
			view.updateResult(result);
		}else view.updateResult(10);
	}

	//부서관리 submenu(1.등록, 2.수정,3.삭제)
	public void department() {
		int choice=view.tableUpdate();
		int result=service.department(choice);
		view.updateResult(result);
	}

	//submenu(1.등록, 2.부서수정,3.삭제)
	public void job() {
		
	}
	
	

	
}
