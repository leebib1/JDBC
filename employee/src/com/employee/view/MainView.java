package com.employee.view;

import java.util.List;
import java.util.Scanner;

import com.employee.controller.EmpController;
import com.employee.model.dto.Employee;

public class MainView {
	private EmpController controller=new EmpController();
	Scanner sc=new Scanner(System.in);
	
	
	public void mainMenu() {
		int order=0;
		while(true) {
			System.out.println("==============사원 관리 프로그램==============");
			System.out.println("1. 전체 사원 조회");
			System.out.println("2. 사원 조회");
			System.out.println("3. 사원등록");
			System.out.println("4. 사원 수정(직책, 부서, 급여, 전화번호, 이메일)");
			System.out.println("5. 사원 삭제");
			System.out.println("6. 부서 관리");
			System.out.println("7. 직책 관리");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호 입력 : ");
			order=sc.nextInt();
			
			switch(order) {
			case 1 : controller.selectEmpAll(); break;
			case 2 : controller.selectEmpKeyword(); break;
			case 3 : controller.addEmployee(); break;
			case 4 : controller.updateEmployee(); break;
			case 5 : controller.deleteEmployee(); break;
			case 6 : controller.department(); break;
			case 7 : controller.job(); break;
			case 0 : System.out.println("프로그램을 종료합니다."); return;
			default : System.out.println("메뉴에 해당하는 번호를 입력하세요.");
			}
		}
	}
	
	public void selectEmpAll(List<Employee> employees) {
		System.out.println("==========사원 조회============");
		employees.forEach(e->System.out.println(e));
		System.out.println("==================================");
	}
}
