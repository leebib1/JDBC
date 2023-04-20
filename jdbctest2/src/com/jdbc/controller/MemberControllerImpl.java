package com.jdbc.controller;

import java.util.List;

import com.jdbc.common.MemberController;
import com.jdbc.model.dto.MemberDto;
import com.jdbc.model.service.MemberService;
import com.jdbc.view.MainView;

public class MemberControllerImpl implements MemberController{

	//private MemberDao dao=new MemberDao();
	private MemberService service=new MemberService();
	
	@Override
	public void mainMenu() {
		new MainView().mainMenu();
		
	}
	
	@Override
	public void selectAllMember() {
		List<MemberDto> members=service.selectAllMember();
		new MainView().printMembers(members);
	}
	
	@Override
	public void selectMemberById() {
		String id=new MainView().inputID();
		MemberDto m=service.selectMemberById(id);
		new MainView().printMember(m);
	}

	@Override
	public void selectMemberByName() {
		String name=new MainView().inputData();
		List<MemberDto> members=service.selectMemberByName(name);
		new MainView().printMembers(members);
	}

	@Override
	public void insertMember() {
		MemberDto m=new MainView().addMember();
		int result=service.insertMember(m);
		new MainView().updateMember(result);
	}

	@Override
	public void updateMember() {
		String id=new MainView().inputID();
		if(service.selectMemberById(id)==null) new MainView().updateMember(10);
		else {
			MemberDto m=new MainView().updateMemberData();
			int result=service.updateMember(id, m);
			new MainView().updateMember(result);
		}
	}

	@Override
	public void deleteMember() {
		String id=new MainView().inputID();
		int result=service.deleteMember(id);
		new MainView().deleteMember(result);
		
	}

	
}
