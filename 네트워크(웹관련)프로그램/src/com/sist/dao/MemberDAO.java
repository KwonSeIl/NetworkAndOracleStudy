package com.sist.dao;
import java.sql.*;
import java.util.*;
public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	//����̹� ���
	public MemberDAO()
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
			conn=DriverManager.getConnection(URL,"hr","happy"); //����Ŭ => conn hr/happy
		}catch(Exception ex) {}
	}
	// ����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			//exit
		}catch(Exception ex) {}
	}
	// ���
	public String isLogin(String id, String pwd)
	{
		String result="";
		try
		{
			//����
			getConnection();
			// SQL ���� ����
			String sql="SELECT COUNT(*) FROM member WHERE id='"+id+"'";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			if(count==0)
			{
				result="NOID";
			}
			else
			{
				sql="SELECT pwd,name FROM member WHERE id='"+id+"'";
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				String name=rs.getString(2);
				
				if(db_pwd.equals(pwd)) //�α���
				{
					result=name;
				}
				else
				{
					result="NOPWD";
				}
			}
			// ����Ŭ ����
			// ����� �ޱ�
			
		}catch(Exception ex) 
		{
			ex.printStackTrace(); //���� Ȯ��
		}
		finally
		{
			disConnection(); //����
		}
		return result;
		
	}

}
