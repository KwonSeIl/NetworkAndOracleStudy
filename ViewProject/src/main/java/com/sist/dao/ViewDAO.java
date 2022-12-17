package com.sist.dao;
import java.util.*;
import java.sql.*;
public class ViewDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// Driver ��� => Properties => Class.forName() �̿�
	public ViewDAO()
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
		}catch(Exception ex) {}
	}
	// ����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// ��ü ��� ==> ���̺� 4�� ����
	public void empListData()
	{
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT e1.empno,e1.ename,e2.ename as manager,e1.job,e1.hiredate,e1.sal,dname,loc,grade "
					+"FROM emp e1 JOIN dept "
					+"ON e1.deptno=dept.deptno "
					+"JOIN salgrade "
					+"ON e1.sal BETWEEN losal AND hisal "
					+"LEFT OUTER JOIN emp e2 "
					+"ON e1.mgr=e2.empno";
			// 3. SQL ���� ����
			ps=conn.prepareStatement(sql);
			// 4. ���� �� ������ ����
			ResultSet rs=ps.executeQuery();
			// 5. ����� �����͸� ���
			while(rs.next())
			{
				System.out.println(
						rs.getInt(1)+" "
						+rs.getString(2)+" "
						+rs.getString(3)+" "
						+rs.getString(4)+" "
						+rs.getDate(5).toString()+" "
						+rs.getInt(6)+" "
						+rs.getString(7)+" "
						+rs.getString(8)+" "
						+rs.getInt(9)
						);
				
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace(); //���� Ȯ��
		}
		finally
		{
			// ����
			disConnection();
		}
	}
	// �󼼺��� 
	public void empDetailData(int empno)
	{
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT e1.empno,e1.ename,e2.ename as manager,e1.job,e1.hiredate,e1.sal,dname,loc,grade "
					+"FROM emp e1 JOIN dept "
					+"ON e1.deptno=dept.deptno "
					+"JOIN salgrade "
					+"ON e1.sal BETWEEN losal AND hisal "
					+"LEFT OUTER JOIN emp e2 "
					+"ON e1.mgr=e2.empno "
					+"WHERE e1.empno="+empno;
			// 3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			// 4. ������ �б�
			ResultSet rs=ps.executeQuery();
			rs.next();
			System.out.println(
					rs.getInt(1)+" "
					+rs.getString(2)+" "
					+rs.getString(3)+" "
					+rs.getString(4)+" "
					+rs.getDate(5).toString()+" "
					+rs.getInt(6)+" "
					+rs.getString(7)+" "
					+rs.getString(8)+" "
					+rs.getInt(9)
					);
			rs.close();
			// row�� 1���� ��� => rs.next() �ѹ��� ���
			// row�� �������� ��� => while(rs.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	
	}
	// VIEW �̿��ؼ� ��ü ��� 
	public void viewListData()
	{
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT * FROM empDeptGrade_1";
			// 3. SQL ���� ����
			ps=conn.prepareStatement(sql);
			// 4. ���� �� ������ ����
			ResultSet rs=ps.executeQuery();
			// 5. ����� �����͸� ���
			while(rs.next())
			{
				System.out.println(
						rs.getInt(1)+" "
						+rs.getString(2)+" "
						+rs.getString(3)+" "
						+rs.getString(4)+" "
						+rs.getDate(5).toString()+" "
						+rs.getInt(6)+" "
						+rs.getInt(7)+" "
						+rs.getString(8)+" "
						+rs.getString(9)+" "
						+rs.getInt(10)
						);
				
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace(); //���� Ȯ��
		}
		finally
		{
			// ����
			disConnection();
		}

	}
	// �� ==> �ϴ� ����: �������α׷��� ���۽� ������ SQL ������ �ܼ��ϰ� ���� �� �ִ�
	public void viewDetailData(int empno)
	{
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT * FROM empDeptGrade_1 "
					+"WHERE empno="+empno;
			// 3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			// 4. ������ �б�
			ResultSet rs=ps.executeQuery();
			rs.next();
			System.out.println(
					rs.getInt(1)+" "
					+rs.getString(2)+" "
					+rs.getString(3)+" "
					+rs.getString(4)+" "
					+rs.getDate(5).toString()+" "
					+rs.getInt(6)+" "
					+rs.getInt(7)+" "
					+rs.getString(8)+" "
					+rs.getString(9)+" "
					+rs.getInt(10)
					);
			rs.close();
			// row�� 1���� ��� => rs.next() �ѹ��� ���
			// row�� �������� ��� => while(rs.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	// VIEW �̿��ؼ� �󼼺��� 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ViewDAO dao=new ViewDAO();
		dao.empListData();
		System.out.println("==================================");
		dao.viewListData();
		System.out.println("==================================");
		dao.empDetailData(7788);
		System.out.println("==================================");
		dao.viewDetailData(7788);
		
	}

}
