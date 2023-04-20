package com.jdbc.member.controller;

import java.util.List;

import com.jdbc.member.model.dao.MemberDao;
import com.jdbc.member.model.dto.MemberDto;
import com.jdbc.view.MainView;

public class MemberController {
	private MemberDao dao=new MemberDao();
	//private MainView view=new MainView(); ->서로 생성하면 오류 발생함
	
	public void mainMenu() {
		new MainView().mainMenu();
	}
	
	//전체 회원 조회
	public void selectAllMember() {
		//DB의 student 계정에 있는 MEMBER테이블의 모든 데이터를 가져와서 출력
		//1. DB에서 테이블의 모든 데이터 가져오기 ->DAO 클래스 이용
		List<MemberDto> members=dao.selectAllMember();
		
		//2. 가져온 데이터 출력 ->View 클래스 이용
		new MainView().printMembers(members);
		
	}
	
	//id로 회원 조회
	public void selectMemberById() {
		//DB의 테이블에서 사용자가 작성한 아이디와 일치한 데이터 가져오는 기능
		//1. 사용자로부터 조회할 아이디를 작성받음 ->View
		String inputId=new MainView().inputID();
		//2. 작성한 아이디와 일치하는 아이디를 DB에서 조회
		MemberDto m=dao.selectMemberById(inputId);
		//3. 조회한 회원 출력
		new MainView().printMember(m);
		
	}
	
	//이름으로 회원 조회
	public void selectMemberByName() {
		String inputName=new MainView().inputData();
		List<MemberDto> members=dao.selectMemberByName(inputName);
		new MainView().printMembers(members);
	}
	
	//입력받은 회원 추가
	public void insertMember() {
		MemberDto member=new MainView().addMember();
		int result=dao.insertMember(member);
		new MainView().updateMember(result);
	}
	
	//입력 받은 아이디의 회원정보 수정
	public void updateMember() {
		selectAllMember();
		String id=new MainView().inputID();
		if(dao.selectMemberById(id)!=null) { //아이다와 일치하는 값을 찾은 경우
			MemberDto update=new MainView().updateMemberData();
			int result=dao.updateMemberData(id, update);
			new MainView().updateMember(result);
		}else{//해당 아이디가 없는 경우 실패했습니다 화면을 출력하도록 0을 반환
			new MainView().updateMember(10);
		}
	}
	
	//회원 아이디로 해당 정보를 삭제하는 메소드
	public void deleteMember() {
		selectAllMember();
		String id=new MainView().inputID();
		if(dao.selectMemberById(id)!=null) {
			int result=dao.deleteMember(id);
			new MainView().deleteMember(result);
		}else new MainView().deleteMember(0);
	}
}
