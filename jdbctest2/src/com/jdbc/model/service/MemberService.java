package com.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import com.jdbc.common.JDBCTemplate;
import com.jdbc.model.dao.MemberDao;
import com.jdbc.model.dto.MemberDto;

import oracle.sql.converter.JdbcCharacterConverters;
//static 메소드명을 임포트해주면 앞에 테이블명을 작성하지 않고도 사용할 수 있다.
//import static com.jdbc.common.JDBCTemplate.getConnection;
import static com.jdbc.common.JDBCTemplate.*; //*을 사용하면 해당 테이블의 static메소드를 전부 import

public class MemberService {
	
	private MemberDao dao=new MemberDao();
	
	//1. DB에 연결하는 Connection 객체를 관리
	//2. 객체를 관리하기 때문에 트랜잭션처리도 해당 클래스에서 한다.
	//3. service에 해당하는 Dao 클래스를 호출해서 연결 DB에서 쿼리문을 실행시키는 기능을 한다.
	
	public List<MemberDto> selectAllMember(){
		//Connection conn=JDBCTemplate.getConnection();
		Connection conn=getConnection(); //static import했기 때문에 테이블명 없이 메소드 호출 가능
		List<MemberDto> members=dao.selectAllMember(conn);
		//JDBCTemplate.close(conn);
		close(conn);
		return members;
	}
	
	public MemberDto selectMemberById(String id) {
		Connection conn=getConnection();
		MemberDto m=dao.selectMemberById(conn, id);
		close(conn);
		return m;
	}
	
	public List<MemberDto> selectMemberByName(String name) {
		Connection conn=getConnection();
		List<MemberDto> members=dao.selectMemberByName(conn, name);
		close(conn);
		return members;
	}

	public int insertMember(MemberDto m) {
		Connection conn=getConnection();
		int result=dao.insertMember(conn,m);
		//트랜잭션 처리
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updateMember(String id, MemberDto m) {
		Connection conn=getConnection();
		int result=dao.updateMember(conn, m, id);
		if(result==1) commit(conn);
		else rollback(conn);
		close(conn);
		return 0;
	}

	public int deleteMember(String id) {
		Connection conn=getConnection();
		int result=dao.deleteMember(conn, id);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}


	
	
}
