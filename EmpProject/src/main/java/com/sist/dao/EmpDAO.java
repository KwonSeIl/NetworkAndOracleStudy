package com.sist.dao;
// ����Ŭ �����ϴ� �κ�
import java.util.*;
import java.sql.*;
public class EmpDAO {
	private Connection conn; //����Ŭ ����
	private PreparedStatement ps; //SQL������ ���� -> ����� �޴´�
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE"; //����Ŭ �ּ� (thin => ���Ḹ �����ش�)
	// ����̹� ���
	public EmpDAO()
	{
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
			// conn hr/happy
		}catch(Exception ex) {}
	}
	// ����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close(); //OutputStream, BufferedReader
			if(conn!=null) conn.close(); //Socket
		}catch(Exception ex) {}
	}
	// ��� => ������ SELECT column_list
	public ArrayList<EmpVO> empListData()
	{
		ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			//1. ����Ŭ ����
			getConnection();
			//2. sql ���� ����
			String sql="SELECT empno,ename,job,hiredate,deptno "
					+ "FROM emp";
			//3. sql������ ����
			ps=conn.prepareStatement(sql);
			//4. ���� ����� �޴´�
			ResultSet rs=ps.executeQuery();
			//5. ������� ArrayList�� ��´�
			while(rs.next()) // ������ ��� �� ���ٿ� Ŀ���� �̵�
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setDeptno(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace(); //���� ���
		}
		finally
		{
			disConnection(); //����Ŭ �ݱ�
		}
		return list;
	}
	// ��� => �󼼺��� SELECT *

}
