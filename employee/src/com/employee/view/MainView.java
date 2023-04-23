package com.employee.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.employee.controller.EmpController;
import com.employee.model.dto.Employee;

public class MainView {
	Scanner sc=new Scanner(System.in);
	
	
	public void mainMenu() {
		EmpController controller=new EmpController();
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
			case 3 : controller.insertEmployee(); break;
			case 4 : controller.updateEmployee(); break;
			case 5 : controller.deleteEmployee(); break;
			case 6 : controller.department(); break;
			case 7 : controller.job(); break;
			case 0 : System.out.println("프로그램을 종료합니다."); return;
			default : System.out.println("메뉴에 해당하는 번호를 입력하세요.");
			}
		}
	}
	
	//리스트에 있는 사원 데이터 조회
	public void selectEmpAll(List<Employee> employees) {
		System.out.println("==========사원 조회============");
		if(employees.size()>0)employees.forEach(e->System.out.println(e));
		else System.out.println("조회된 사원이 없습니다."); //조회된 사원이 없는 경우 안내 메세지 출력
		System.out.println("==================================");
	}

	//사원조회 SubMenu(1.부서, 2.직책, 3.이름, 4. 급여(크고작고->선택))
	public int selectSubMenu() {
		int choice=0;
		while(true) {
			System.out.println("==========조회할 항목 선택============");
			System.out.println("1. 부서");
			System.out.println("2. 직책");
			System.out.println("3. 이름");
			System.out.println("4. 급여 오름차순");
			System.out.println("5. 급여 내림차순");
			System.out.print("번호 선택 : ");
			choice=sc.nextInt();
			
			switch(choice) {
			case 1,2,3,4,5 : return choice;
			default : System.out.println("번호를 다시 선택하세요.");
			}
		}
		
	}

	//selectSubMenu에서 1~3번을 선택했을 때 검색할 데이터 입력
	public String selectData() {
		sc.nextLine();
		System.out.println("검색할 내용 입력 : ");
		String data=sc.nextLine();
		return data;
	}
	
	//삭제, 수정에 필요한 ID 입력
	public String inputEmpId() {
		System.out.println("============변경할 사원 ID 검색=============");
		System.out.print("사원 ID : ");
		return sc.nextLine();
	}
	
	//사원 등록 정보 받기
	public Employee insertEmployee() {
		Employee e=new Employee();
		//이미 입력 받은 ID 제외
		System.out.println("=============추가할 사원 정보 입력=============");
		System.out.print("이름 : ");
		e.setEmpName(sc.nextLine());
		System.out.print("주민번호(-포함) : ");
		e.setEmpNo(sc.nextLine());
		System.out.print("이메일 : ");
		e.setEmail(sc.nextLine());
		System.out.print("핸드폰(-없이) : ");
		e.setPhone(sc.nextLine());
		System.out.print("부서코드 : ");
		e.setDeptCode(sc.nextLine());
		System.out.print("직책코드 : ");
		e.setJobCode(sc.nextLine());
		System.out.print("급여레벨 : ");
		e.setSalLevel(sc.nextLine());
		System.out.print("급여 : ");
		e.setSalary(sc.nextInt());
		System.out.print("보너스 : ");
		e.setBonus(sc.nextDouble());
		sc.nextLine();
		System.out.print("매니저 ID : ");
		e.setManagerId(sc.nextLine());
		
		//입사는 입력한 날짜, 퇴사일은 null, 재직여부는 디폴트값'N'
		//입사일은 입력 날짜로 들어가게 처리
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		e.setHireDate(new Date(System.currentTimeMillis()));
		e.setEntYN('N');
		return e;
	}

	public void updateResult(int result) {
		if(result==1) System.out.println("업데이트 되었습니다.");
		else if(result==10) System.out.println("일치하는 ID가 없습니다.");
		else System.out.println("업데이트 실패하였습니다."); //ID는 존재하지만 수정, 삭제 실패한 경우
	}
	
	//수정할 정보(직책, 부서, 급여, 전화번호, 이메일) 입력
	public Employee updateEmployee() {
		Employee e=new Employee();
		System.out.println("=============변경할 사원 정보 입력=============");
		System.out.print("직책코드 : ");
		e.setJobCode(sc.nextLine());
		System.out.print("부서코드 : ");
		e.setDeptCode(sc.nextLine());
		System.out.print("급여 : ");
		e.setSalary(sc.nextInt());
		sc.nextLine();
		System.out.print("이메일 : ");
		e.setEmail(sc.nextLine());
		System.out.print("핸드폰(-없이) : ");
		e.setPhone(sc.nextLine());
		
		return e;
	}
	
	//부서, 직책 관리 서브 메뉴(1.등록, 2.수정,3.삭제)
	public int tableUpdate() {
		int choice=0;
		while(true) {
			System.out.println("=======실행할 항목 선택=========");
			System.out.println("1. 등록");
			System.out.println("2. 수정");
			System.out.println("3. 삭제");
			System.out.print("번호 선택 : ");
			choice=sc.nextInt();
			
			switch(choice) {
			case 1,2,3 : return choice;
			default : System.out.println("번호를 다시 선택하세요.");
			}
		}
	}
	
}
