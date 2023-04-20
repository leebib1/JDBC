package com.jdbc.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.jdbc.common.MemberController;
import com.jdbc.controller.MemberControllerImpl;
import com.jdbc.model.dto.MemberDto;

public class MainView {
	private MemberController controller=new MemberControllerImpl();
	//저장소가 별개로 존재하기 떼문에 new 생성자를 사용해서 문제 없다.
	
	public void mainMenu() {
		Scanner sc=new Scanner(System.in);
		while(true) {
			System.out.println("=====jdbc 회원관리 프로그램=====");
			System.out.println("1. 전체 회원 조회");
			System.out.println("2. 아이디로 회원 조회");
			System.out.println("3. 이름으로 회원 조회");
			System.out.println("4. 회원 등록");
			System.out.println("5. 회원 수정(이름, 나이, 이메일, 주소)");
			System.out.println("6. 회원 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 입력 : ");
			int cho=sc.nextInt();
			switch(cho) {
				case 1 : controller.selectAllMember(); break;
				case 2 : controller.selectMemberById();  break;
				case 3 : controller.selectMemberByName(); break;
				case 4 : controller.insertMember(); break;
				case 5 : controller.updateMember(); break;
				case 6 : controller.deleteMember(); break;
				case 0 : System.out.println("프로그램을 종료합니다."); return;
				default : System.out.println("0~5 사이의 메뉴를 입력하세요.");
			}
			
		}
	}
	
	//전체 회원 결과들을 출력
	public void printMembers(List<MemberDto> members) {
		System.out.println("============조회된 결과==========");
		if(members.size()!=0)members.forEach(m->System.out.println(m));
		else System.out.println("조회된 결과가 없습니다.");
		System.out.println("===========================");
	}
	
	//조회된 회원 결과를 출력
	public void printMember(MemberDto m) {
		System.out.println("============조회된 결과==========");
		if(m!=null) System.out.println(m);
		else System.out.println("정보와 일치하는 결과가 없습니다.");
		System.out.println("===========================");
	}
	
	//ID를 입력 받는 화면
	public String inputID() {
		Scanner sc=new Scanner(System.in);
		System.out.println("=========ID로 회원 조회==========");
		System.out.print("검색할 ID : ");
		return sc.nextLine();

	}
	
	public String inputData() {
		Scanner sc=new Scanner(System.in);
		System.out.println("=========이름으로 회원 조회==========");
		System.out.print("검색할 이름 : ");
		return sc.nextLine();

	}
	
	//회원 등록 정보를 받는 메소드
	public MemberDto addMember() {
		Scanner sc=new Scanner(System.in);
		System.out.println("============회원 추가===========");
		System.out.print("아이디 : ");
		String id=sc.nextLine();
		System.out.print("비밀번호 : ");
		String pwd=sc.nextLine();
		System.out.print("이름 : ");
		String name=sc.nextLine();
		System.out.print("성별(M/F) : ");
		char gender=sc.next().charAt(0);
		System.out.print("나이 : ");
		int age=sc.nextInt();
		sc.nextLine();
		System.out.print("이메일 : ");
		String email=sc.nextLine();
		System.out.print("전화번호 : ");
		String phone=sc.nextLine();
		System.out.print("주소 : ");
		String address=sc.nextLine();
		System.out.print("취미(,) : ");
		String[] hobby=sc.nextLine().split(",");
		//입사일은 입력 날짜로 들어가게 처리
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date enrollDate = new Date(System.currentTimeMillis());
		
		MemberDto member=new MemberDto(id, pwd, name, gender, age, email, phone, address, hobby, enrollDate);
		
		return member;
	}
	
	//회원 업데이트 성공여부를 알리는 메소드
	public void updateMember(int result) {
		if(result==1) System.out.println(result+"행 업데이트 되었습니다.");
		else if(result==10) System.out.println("해당 ID가 없습니다.");
		else System.out.println("회원 업데이트 실패했습니다.");
	}
	
	//회원 정보를 수정(이름, 나이, 이메일, 주소)할 데이터를 받는 화면
	public MemberDto updateMemberData() {
		Scanner sc=new Scanner(System.in);
		System.out.println("========회원 정보 수정==========");
		System.out.print("변경할 이름 : ");
		String name=sc.nextLine();
		System.out.print("변경할 나이 : ");
		int age=sc.nextInt();
		sc.nextLine();
		System.out.print("변경할 이메일 : ");
		String email=sc.nextLine();
		System.out.print("변경할 주소 : ");
		String address=sc.nextLine();
		
		MemberDto update=new MemberDto(name, age, email, address);
		return update;
		
	}
	
	public void deleteMember(int result) {
		if(result==1) System.out.println(result+"행 삭제 되었습니다.");
		else System.out.println("회원 삭제 실패했습니다.");
	}
}
