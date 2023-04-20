package com.jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jdbc.model.vo.Member;

public class BasicJdbcTest {

	public static void main(String[] args) {
		//jdbc를 이용해서 오라클과 연동
		//1.오라클에서 제공하는 ojdbc.jar 파일을 버전에 맞춰서 다운로드
		//2.이클립스에서 프로젝트를 생성하고 생성된 프로젝트 라이브러리에 다운받은 jar파일을 추가한다.
		//설정을 못하고 프로젝트를 생성했다면 build path>Configure build path 들어가서 라이브러리 추가
		
		//프로젝트(애플리케이션)에서 DB에 접속
		//1.jar파일이 제공하는 클래스가 있는지 확인. 고정된 문장이기 때문에 외워서 사용한다.
		//->에러가 발생하면 jar 파일이 제대로 등록되어있는지 확인한다.
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//예외처리 필수
			
			//2.DriverManager 클래스를 이용해서 접속하는 객체를 불러온다.
			//DriverManager 클래스가 제공하는 getConnection() 이라는 static메소드를 이용해서 Connection 객체를 불러온다.
			//->Connection 객체를 반환한다
			
			//getConnection이 받는 3개의 매개변수
			//1번째 인수 : 접속할 DB의 ip주소(localhost,도메인네임 포함), 버전정보, 포트번호 >String
			//->접속할 DBMS별로 문자열 패턴이 지정되어있다.
			//oracle패턴 : jdbc:oracle:thin:@ip주소:포트번호:버전
			//2번째 인수 : DB 접속 계정명 >String
			//3번째 인수 : DB 접속 비밀번호 >String
			
			conn=DriverManager.getConnection(
					"jdbc:oracle:thin"
					+ ":@localhost:1521:xe","student","student");
			System.out.println("DB접속 확인 완료");
			
			//3.접속된 DB에서 SQL문을 실행하고 결과를 가져온다.
			//sql문을 실행하기 위해서 실행할 객체가 필요하다.
			//Statement, PreparedStatement : 문자열로 작성한 sql구문을 연결된 DB에서 실행하는 객체
			//sql문을 실행하려면 Statement의 멤버메소드인 executeQuery(), executeUpdate()를 이용
			//SELECT : executeQuery("쿼리문") 실행 -> ResultSet 객체 반환
			//INSERT, UPDATE, DELETE : executeUpdate("쿼리문") 실행 ->int 반환
			
			//1.쿼리문 작성
			//member table에 있는 id가 admin인 사원 조회
			//String sql="SELECT * FROM MEMBER WHERE MEMBER_ID='admin'";
			//->자바에서 문자열안에 작성하는 쿼리문은 세미콜론을 넣지 않는다. ->작성하면 에러 발생
			
			//전체 회원 불러오기
			String sql="SELECT * FROM MEMBER";
			
			//2.Statement 객체 불러오기
			//Connection 클래스가 제공하는 멤버메소드 creatStatement() 이용 ->예외처리 필요한 메소드
			stmt=conn.createStatement();
			
			//3.쿼리문 실행
			//Statement가 제공하는 executeQuery()실행 후 ResultSet객체 반환
			//->해당 select문을 사용했을때 resultset을 그대로 반환한다
			rs=stmt.executeQuery(sql);
			System.out.println(rs);
			
			//4.ResultSet 이용
			//반환된 SELECT문의 실행결과는 ResultSet 객체가 제공하는 메소드를 이용해서 컬럽별 값을 가져온다.
			//next() : 데이터의 row를 지정해서 가져온다. iterator와 동일한 방법으로 다음 Row 데이터를 가져온다. ->반환형 boolean
			//iterator에 사용했을 때와 마찬가지로 한 번 불러오면 재사용할 수 없다.
			//get자료형("컬럼명"||인덱스번호) : 자료형은 String,Int,Date을 자주 사용한다.
			//getString : char, varchar2, nchar, nvarchar2 자료형을 가져올 때
			//getInt/getDouble : number 자료형을 가져올때
			//getDate/getTimeStamp : Date,TimeStamp 자료형 가져올 때
			//rs.next(); //첫번째 row 지칭
//			String member_Id=rs.getString("member_id");
//			String member_Pwd=rs.getString("member_pwd");
//			int age=rs.getInt("age");
//			Date enrollDate=rs.getDate("enroll_date");
//			
//			System.out.println(member_Id+" "+member_Pwd+" "+age+" "+enrollDate);
			//commit하지 않으면 오류 발생
//			rs.next(); //admin만 불러왔을 땐 다음 row가 없다. 출력메소드로 찍어보면 false를 반환한다.
//			member_Id=rs.getString("member_id");
//			member_Pwd=rs.getString("member_pwd");
//			age=rs.getInt("age");
//			enrollDate=rs.getDate("enroll_date");
//			
//			System.out.println(member_Id+" "+member_Pwd+" "+age+" "+enrollDate);
			
			//반복되는 로직을 반복문 사용
//			while(rs.next()){
//				//rs.next();
//				String member_Id=rs.getString("member_id");
//				String member_Pwd=rs.getString("member_pwd");
//				int age=rs.getInt("age");
//				Date enrollDate=rs.getDate("enroll_date");
//				
//				System.out.println(member_Id+" "+member_Pwd+" "+age+" "+enrollDate);
//			}
			
			//DB에서 다수의 ROW를 가져왔을 때 자바에서는 클래스로 묶어서 관리할 수 있다. ->컬럼별로 다양한 타입을 사용하고 있기 때문에
			//클래스 타입을 가진 리스트로 데이터 불러오기
			ArrayList<Member> members=new ArrayList();
			
			while(rs.next()){
				members.add(new Member(rs.getString("member_id"),rs.getString("member_pwd"),rs.getString("member_name"),rs.getString("gender")
						,rs.getInt("age"),rs.getString("email"),rs.getString("phone"),rs.getString("address"),rs.getString("hobby")
						,rs.getDate("enroll_date")));
			}
			members.forEach((m)->System.out.println(m));
			
			//DML구문 실행
			//insert, update, delete
			//트랜잭션 처리를 해줘야한다. -->기본적으로 connection에서 관리한다. 
			//1.sql문 작성 ->	리터럴 형태에 맞춰서 작성해야 한다.
			//문자열 작성 중 엔터치면 이클립스가 자동으로 문자열을 이어준다.
			//쿼리문은 한 번에 하나의 쿼리문만 작성한다. 여러 개의 쿼리문을 한번에 작성해서 실행할 수 없다.
			sql="INSERT INTO MEMBER VALUES('inhoru','inhoru','최인호','M',26,'inhoru@inhoru.com','01012341455','금천구',"
					+ "'영화감상,애니감상,코딩',SYSDATE)";
			int result=stmt.executeUpdate(sql);
			System.out.println(result); //1개의 행이 업데이트 됐기 때문에 1을 반환 받았다.
			
			conn.setAutoCommit(false); //트랜잭션을 개발자가 직접 처리하게 함
			//위 구문 없이 dml구문 사용 후 commit하려고 하면 이미 커밋을 실행했다는 예외가 발생한다.
			//트랜잭션 구문 처리
			if(result>0) conn.commit();
			else conn.rollback();
			
			System.out.println(result);
			
			//5.생성한 객체는 반드시 반환해줘야한다.
			//Connection, Statement, ResultSet
			//close()메소드로 반환
			//반환은 생성의 역순으로 한다.
			//close는 반드시 실행되야 하므로 finally안에 작성해주고 finally에 작성하기 위해 해당 변수들은 try문 밖에 선언한다.
//			rs.close();
//			stmt.close();
//			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}
