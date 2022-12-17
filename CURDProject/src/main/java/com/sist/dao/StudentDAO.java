package com.sist.dao;
import java.util.*;
import java.sql.*;
public class StudentDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	// ����̹� ���: �ѹ��� ����, ���� �ʱ�ȭ ... ������ ���
	// �ڵ��α���, ��Ű �б�..
	//������ �ڹٴ� ��������� ����. �����ͺ��̽� ���� => �׻� ����
	public StudentDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}

	}
	// ����Ŭ ����
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	// ����Ŭ ���� ����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// ���
	// 1. �߰�
	public void studentInsert(StudentVO vo)
	{
		try
		{
			// 1. ����
			getConnection();
			// 2. sQL���� ����
			String sql="INSERT INTO student VALUES((SELECT NVL(MAX(hakbun)+1,1) FROM student),?,?,?,?)";
			// 3. SQL������ ������
			ps=conn.prepareStatement(sql);
			// 4. ���� ���� ?�� ���� ä���
			ps.setString(1, vo.getName()); //setString �̿��ϸ� �ڵ����� 'ȫ�浿' ó�� ''�� �ٿ��ش�
			ps.setInt(2, vo.getKor());
			ps.setInt(3, vo.getEng());
			ps.setInt(4, vo.getMath());
			// INSERT�� ���� ==> �ڹٴ� AUTOCOMMIT
			ps.executeUpdate(); //INSERT, UPDATE, DELETE => executeUpdate() �̿�
			// SELECT => executeQuery() �̿�
			//executeUpdate() vs executeQuery() ������: COMMIT(AUTO), COMMIT ���� ==> Ʈ�����
			
			
		}catch(Exception ex)
		{
			ex.printStackTrace(); //���� Ȯ��
		}
		finally
		{
			// �ݱ�
			disConnection();
		}
	}
	// 2. ������ �б�
	public ArrayList<StudentVO> studentListData()
	{
		ArrayList<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL���� ����
			String sql="SELECT hakbun,name,kor,eng,math,(kor+eng+math) total," 
					+"ROUND((kor+eng+math)/3,2) avg "
					+"FROM student";
			// sql���� ����
			ps=conn.prepareStatement(sql);
			// ���� �� �����͸� ��û
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				list.add(vo);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	// 3. ����
	// 4. ����

}
