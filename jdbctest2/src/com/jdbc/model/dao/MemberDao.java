package com.jdbc.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jdbc.common.JDBCTemplate;
import com.jdbc.model.dto.MemberDto;
import static com.jdbc.common.JDBCTemplate.*;

public class MemberDao {
	//SQL문을 저장하고 있는 파일을 멤버변수로 불러와서 사용
	private Properties sql=new Properties();
	{
		try {
			String path=MemberDao.class.getResource("/sql/member/member_sql.properties").getPath();
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<MemberDto> selectAllMember(Connection conn){
		//Statement는 쿼리문을 작성 시 불러오기 때문에 따로 분리한다.
		Statement stmt=null;
		ResultSet rs=null;
		//String sql="SELECT * FROM MEMBER";
		String sql=this.sql.getProperty("selectAllMember");
		List<MemberDto> members=new ArrayList();
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				members.add(getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		return members;
	}
	
	private MemberDto getMember(ResultSet rs) throws SQLException {
		MemberDto m=new MemberDto();
		m.setMemberId(rs.getString("member_id"));
		m.setMemberPwd(rs.getString("member_pwd"));
		m.setMemberName(rs.getString("member_name"));
		m.setGender(rs.getString("gender").charAt(0));
		m.setAge(rs.getInt("age"));
		m.setEmail(rs.getString("email"));
		m.setPhone(rs.getString("phone"));
		m.setAddress(rs.getString("address"));
		m.setHobby(rs.getString("hobby").split(","));
		m.setEnrollDate(rs.getDate("enroll_date"));
		return m;
	}
	
	public MemberDto selectMemberById(Connection conn, String id) {
		//sql구문에 변수를 대입할 때 자료형에 맞춰서 대입할 수 있는 PreparedStatement 이용
		//PreparedStatemnet는 Statement를 상속 받은 인터페이스
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		MemberDto m=null;
		//PreparedStatement 객체를 이용해서 쿼리문을 실행하려면 외부의 값을 받아서 쿼리문을 작성할 때 Statement와 다르게 작성한다.
		// Statement : "SELECT * FROM MEMBER WHERE MEMBER_ID='"+변수명+"'";
		//PreparedStatemnet : "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		//->외부값을 물음표(위치홀더)로 표시해서 쿼리문을 한 번에 작성한다. 자료형 표시도 구분하지 않음.
		
		//String sql="SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("selectMemberById");
		try {
			//PreparedStatement는 conn.preparedStatement() 메소드를 제공
			//인수로 쿼리문을 대입한다.
			pstmt=conn.prepareStatement(sql);
			//->생성할 때부터 쿼리문을 대입
			
			//? 표시에 값을 대입할 때는 set자료형(위치값, 대입값) 메소드 사용
			//각 ?표시에는 왼쪽부터 인덱스 번호가 부여된다. 해당 문장은 쿼리문이기 때문에 인덱스 번호의 시작은 1부터 한다.
			//위치홀더를 표시한 값이 존재하면 반드시 값을 대입해줘야한다. 대입하지 않으면 예외 발생.
			//ORA-17041: 인덱스에서 누락된 IN 또는 OUT 매개변수:: 1->해당 인덱스 자리의 매개변수에 값이 없다.
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery(); //->위에서 다 작성해서 pstmt가 쿼리문을 갖고 있기 때문에 그냥 실행하면 된다.
			if(rs.next()) m=getMember(rs);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return m;
		
	}
	
	public List<MemberDto> selectMemberByName(Connection conn, String name) {
		PreparedStatement pstmt=null;
		List<MemberDto> members=new ArrayList();
		//String sql="SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE ?";
		String sql=this.sql.getProperty("seelctMemberByName");
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			//set자료형에 해당하는 리터럴 타입을 위치에 대입하기 때문에 작성 시 주의해야한다.
			//"SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE '%'||?||'%'"; 로 작성해서 name만 집어넣어도 가능하다.
			pstmt.setString(1, "%"+name+"%");
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				members.add(getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return members;
	}

	public int insertMember(Connection conn, MemberDto m) {
		PreparedStatement pstmt=null;
		int result=0;
		//String sql="INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
		String sql=this.sql.getProperty("insertMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPwd());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, String.valueOf(m.getGender())); //자료형에 맞춰서 형변환
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, String.join(",",m.getMemberId()));
			
			result=pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateMember(Connection conn, MemberDto m, String id) {
		PreparedStatement pstmt=null;
		int result=0;
		//String sql="UPDATE MEMBER SET MEMBER_NAME=?, AGE=?, EMAIL=?, ADDRESS=? WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("updateMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberName());
			pstmt.setInt(2, m.getAge());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, id);
			
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		PreparedStatement pstmt=null;
		int result=0;
		//String sql="DELETE FROM MEMBER WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("deleteMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	
	
}
