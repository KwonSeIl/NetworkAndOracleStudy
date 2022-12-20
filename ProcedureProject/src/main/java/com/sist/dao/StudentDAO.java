package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;
// �����ͺ��̽� ���� �ڹ� ���α׷� => 285page: �ٽ� 
public class StudentDAO {
	// ����
	private Connection conn;
	// �ۼ��� SQL => ����� 
	private CallableStatement cs;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	public StudentDAO()
	{
		// ����̹� ���
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	// ����
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	// ����
	public void disConnection()
	{
		try
		{
			if(cs!=null) cs.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// ��� ����
	// 1. ��� ���
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
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="{CALL studentListData(?)}";
			// 3. ����Ŭ�� ����
			cs=conn.prepareCall(sql);
			// 4. ?�� ���� ä���
			cs.registerOutParameter(1, OracleTypes.CURSOR); //����Ŭ ���������� �����ؾ� �Ѵ�.
			//5. ���� ��û
			cs.executeQuery();
			// 6. �ӽ÷� ����� �޸𸮿��� �����͸� ������ �´�
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
			ex.printStackTrace(); // ����ó��
		}
		finally
		{
			disConnection(); // �ݱ�
		}
		return list;
	}
	
	// 2. ������ �߰�
	/*
	 *	CREATE OR REPLACE PROCEDURE studentInsert(
	    pName IN student.name%TYPE, -- IN ���� ����. �����Ǿ� ������ IN ���� 
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
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="{CALL studentInsert(?,?,?,?)}";
			// 3. ���ν��� ȣ��
			cs=conn.prepareCall(sql);
			// 4. ?�� ���� ä���
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			
			// 5. ���� ��û
			cs.executeQuery(); //������ executeQuery()�� ����Ѵ� 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			// �ݱ�
			disConnection();
		}
	}
	
	// 3. ������ ����
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
	
	// 4. ������ ����
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
	
	// 5. ������ �˻�
	/*
	 * CREATE OR REPLACE PROCEDURE studentDetailData(
		    pHakbun student.hakbun%TYPE,
		    pName OUT student.name%TYPE, --OUT: �Ű����� ���� �޾ƿ´�
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
