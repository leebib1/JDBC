package com.jdbc.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.member.model.dto.MemberDto;

public class MemberDao {
	
	public List<MemberDto> selectAllMember(){
		//DB에 접속해서 데이블에 있는 전체 데이터 가져오는 기능
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="SELECT * FROM MEMBER";
		List<MemberDto> members=new ArrayList();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			//다수값인 경우 while문으로 처리. 0~1개인 경우 if문으로 처리
			while(rs.next()){
//				MemberDto member=new MemberDto();
//				member.setMemberId(rs.getString("member_id"));
//				member.setMemberPwd(rs.getString("member_pwd"));
//				member.setMemberName(rs.getString("member_name"));
//				member.setAge(rs.getInt("age"));
//				member.setGender(rs.getString("gender").charAt(0));
//				member.setPhone(rs.getString("phone"));
//				member.setEmail(rs.getString("email"));
//				member.setAddress(rs.getString("address"));
//				member.setHobby(rs.getString("hobby").split(","));
//				member.setEnrollDate(rs.getDate("enroll_date"));
				
				//변수를 따로 선언할 필요 없이 리스트에 rs로 불러온 객체를 바로 저장
				members.add(getMember(rs));
				
			}
			
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return members;
	}
	
	public MemberDto selectMemberById(String id) {
		//테이블에 데이터가 많아질수록 전체 데이터를 가져오는 것보다 DB에서 해당하는 객체만 찾아 가져오는 게 효율이 좋을 수 있기 때문에 해당 값만 불러온다.
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		MemberDto m=null;
		String sql="SELECT * FROM MEMBER WHERE MEMBER_ID='"+id+"'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				m=getMember(rs);
//				m=new MemberDto();
//				m.setMemberId(rs.getString("member_id"));
//				m.setMemberPwd(rs.getString("member_pwd"));
//				m.setMemberName(rs.getString("member_name"));
//				m.setAge(rs.getInt("age"));
//				m.setGender(rs.getString("gender").charAt(0));
//				m.setPhone(rs.getString("phone"));
//				m.setEmail(rs.getString("email"));
//				m.setAddress(rs.getString("address"));
//				m.setHobby(rs.getString("hobby").split(","));
//				m.setEnrollDate(rs.getDate("enroll_date"));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	//반복되는 resultset 구문 작성하지 않을 수 있게 메소드마다 달라지는 resultset값만 매개변수로 받아서 해당 구문 처리한다.
	private MemberDto getMember(ResultSet rs) throws SQLException {
		MemberDto m=new MemberDto();
		m.setMemberId(rs.getString("member_id"));
		m.setMemberPwd(rs.getString("member_pwd"));
		m.setMemberName(rs.getString("member_name"));
		m.setAge(rs.getInt("age"));
		m.setGender(rs.getString("gender").charAt(0));
		m.setPhone(rs.getString("phone"));
		m.setEmail(rs.getString("email"));
		m.setAddress(rs.getString("address"));
		m.setHobby(rs.getString("hobby").split(","));
		m.setEnrollDate(rs.getDate("enroll_date"));
		return m;
	}
	
	//이름과 일치하는 정보 불러오기
	public List<MemberDto> selectMemberByName(String name) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<MemberDto> members=new ArrayList();
		String sql="SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE '%"+name+"%'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				members.add(getMember(rs));
			}
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return members;
	}
	
	//입력받은 회원을 DB에 업데이트
	public int insertMember(MemberDto member) {
		Connection conn=null;
		Statement stmt=null;
		int result=0;
		String sql="INSERT INTO MEMBER VALUES('"+member.getMemberId()+"','"+member.getMemberPwd()+"','"+member.getMemberName()+
				"','"+member.getGender()+"',"+member.getAge()+",'"+member.getEmail()+"','"+member.getPhone()+
				"','"+member.getAddress()+"','"+String.join(",",member.getHobby())+"','"+member.getEnrollDate()+"')";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
			
			if(result>0) conn.commit();
			else conn.rollback();
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//선택한 회원의 정보(이름, 나이, 이메일, 주소)를 수정하는 메소드
	public int updateMemberData(String id, MemberDto member) {
		Connection conn=null;
		Statement stmt=null;
		int result=0;
		//쿼리문 작성 시, 띄어쓰기에 주의한다. 쿼리문 오류가 발생할 수 있다.
		String sql="UPDATE MEMBER SET MEMBER_NAME='"+member.getMemberName()+"', AGE="+member.getAge()+", EMAIL='"+member.getEmail()+
				"', ADDRESS='"+member.getAddress()+"' WHERE MEMBER_ID='"+id+"'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
			
			if(result>0) conn.commit();
			else conn.rollback();
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public int deleteMember(String id) {
		Connection conn=null;
		Statement stmt=null;
		int result=0;
		//쿼리문 작성 시, 띄어쓰기에 주의한다. 쿼리문 오류가 발생할 수 있다.
		String sql="DELETE FROM MEMBER WHERE MEMBER_ID='"+id+"'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
			conn.setAutoCommit(false);
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
			
			if(result>0) conn.commit();
			else conn.rollback();
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
