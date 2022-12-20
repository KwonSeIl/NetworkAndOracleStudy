package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;
// 데이터베이스 연동 자바 프로그램 => 285page: 핵심 
public class StudentDAO {
	// 연결
	private Connection conn;
	// 송수신 SQL => 결과값 
	private CallableStatement cs;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	public StudentDAO()
	{
		// 드라이버 등록
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	// 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	// 해제
	public void disConnection()
	{
		try
		{
			if(cs!=null) cs.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 기능 설정
	// 1. 목록 출력
	/*
	 * 	CREATE OR REPLACE PROCEDURE studentListData(
    		pResult OUT SYS_REFCURSOR
		)
		IS
		BEGIN
		    OPEN pResult FOR
		        SELECT * FROM student;
		END;
		/
	 */
	public ArrayList<StudentVO> studentListData()
	{
		ArrayList<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			// 1. 연결
			getConnection();
			// 2. SQL 문장 전송
			String sql="{CALL studentListData(?)}";
			// 3. 오라클로 전송
			cs=conn.prepareCall(sql);
			// 4. ?에 값을 채운다
			cs.registerOutParameter(1, OracleTypes.CURSOR); //오라클 데이터형과 동일해야 한다.
			//5. 실행 요청
			cs.executeQuery();
			// 6. 임시로 저장된 메모리에서 데이터를 가지고 온다
			ResultSet rs=(ResultSet)cs.getObject(1);
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace(); // 오류처리
		}
		finally
		{
			disConnection(); // 닫기
		}
		return list;
	}
	
	// 2. 데이터 추가
	/*
	 *	CREATE OR REPLACE PROCEDURE studentInsert(
	    pName IN student.name%TYPE, -- IN 생략 가능. 생략되어 있으면 IN 변수 
	    pKor IN student.kor%TYPE,
	    pEng IN student.eng%TYPE,
	    pMath IN student.math%TYPE
		)
		IS
		    pMax NUMBER:=0;
		BEGIN
		    SELECT NVL(MAX(hakbun)+1,1) INTO pMax
		    FROM student;
		    
		    INSERT INTO student VALUES(pMax,pName,pKor,pEng,pMath);
		    COMMIT;
		END;
		/ 
	 */
	public void studentInsert(StudentVO vo)
	{
		try
		{
			// 1. 연결
			getConnection();
			// 2. SQL 문장 전송
			String sql="{CALL studentInsert(?,?,?,?)}";
			// 3. 프로시저 호출
			cs=conn.prepareCall(sql);
			// 4. ?에 값을 채운다
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			
			// 5. 실행 요청
			cs.executeQuery(); //무조건 executeQuery()를 사용한다 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			// 닫기
			disConnection();
		}
	}
	
	// 3. 데이터 수정
	/*
	 * 	CREATE OR REPLACE PROCEDURE studentUpdate(
		    pHakbun IN student.hakbun%TYPE,
		    pName IN student.name%TYPE,
		    pKor IN student.kor%TYPE,
		    pEng IN student.eng%TYPE,
		    pMath IN student.math%TYPE
		)
		IS
		BEGIN
		    UPDATE student SET
		    name=pName,kor=pKor,eng=pEng,math=pMath
		    WHERE hakbun=pHakbun;
		    COMMIT;
		END;
		/
	 */
	
	// 4. 데이터 삭제
	/*
	 * 	CREATE OR REPLACE PROCEDURE studentDelete(
		    pHakbun IN student.hakbun%TYPE
		)
		IS
		BEGIN
		    DELETE FROM student
		    WHERE hakbun=pHakbun;
		    COMMIT;
		END;
		/
	 */
	
	// 5. 데이터 검색
	/*
	 * CREATE OR REPLACE PROCEDURE studentDetailData(
		    pHakbun student.hakbun%TYPE,
		    pName OUT student.name%TYPE, --OUT: 매개변수 값을 받아온다
		    pKor OUT student.kor%TYPE,
		    pEng OUT student.eng%TYPE,
		    pMath OUT student.math%TYPE,
		    pTotal OUT NUMBER,
		    pAvg OUT NUMBER
		)
		IS
		BEGIN
		    SELECT name,kor,eng,math,(kor+eng+math),ROUND((kor+eng+math)/3) INTO pName,pKor,pEng,pMath,pTotal,pAvg
		    FROM student
		    WHERE hakbun=pHakbun;
		    
		    DBMS_OUTPUT.PUT_LINE(pName);
		    DBMS_OUTPUT.PUT_LINE(pKor);
		    DBMS_OUTPUT.PUT_LINE(pEng);
		    DBMS_OUTPUT.PUT_LINE(pMath);
		    DBMS_OUTPUT.PUT_LINE(pTotal);
		    DBMS_OUTPUT.PUT_LINE(pAvg);
		END;
		/
	 */
}
