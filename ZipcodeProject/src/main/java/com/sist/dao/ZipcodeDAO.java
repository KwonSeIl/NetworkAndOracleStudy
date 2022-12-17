package com.sist.dao;
import java.util.*;
import java.sql.*;
/*
 * 	ȸ�������� �Ϻ� ==> ���̵�üũ, �����ȣ �˻�
 * 	--------------------------------
 */
public class ZipcodeDAO {
	private Connection conn; //����Ŭ ���� => �������̽�
	private PreparedStatement ps; //�ۼ��� => SQL ���� ���� -> ���� �� ����� �ޱ�
	// ����Ŭ �ּ� ����
	// jbdc:��ü��:����̹�����:@����Ŭ(IP):PORT:�����ͺ��̽���
	//jdbc:oracle:thin:@localhost:1521:XE
	// jdbc:mysql://localhost:3306?mydb
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//����̹� ����
	public ZipcodeDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class.forName ==> Ŭ������ ������ �о�´�
			// 1) �޸� �Ҵ� ���� 2) �޼ҵ� ȣ�� ���� 3) ������ �ʱⰪ ���� ����
			// ���÷���: Ŭ������ ������ �о ó���ϴ� ���� ==> �ַ� ���(������, ���̹�Ƽ��)
		}catch(Exception ex) {}
	}
	// ���� ==> �˻�
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
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	// ��� 2��
	// 1. �����ȣ �˻� ==> ���� / ���θ�(���̺귯�� => �������� ����)
	public ArrayList<ZipcodeVO> postFind(String dong)
	{
		ArrayList<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
					+"FROM zipcode "
					+"WHERE dong LIKE '%'||?||'%'";
			// 3. SQL ������ ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			// 4. ?�� ���� ä���
			ps.setString(1, dong);
			// 5. ���� �Ŀ� ������� ������ �´�
			ResultSet rs=ps.executeQuery(); // �� �پ� �о�´� 
			while(rs.next())
			{
				ZipcodeVO vo=new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			// ���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			// ����
			disConnection();
		}
		return list;
	}
	// 2. �˻� ����Ȯ��
	public int postCount(String dong)
	{
		int count=0;
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			String sql="SELECT count(*) "
					+"FROM zipcode "
					+"WHERE dong LIKE '%'||?||'%'";
			// 3. ����Ŭ ����
			ps=conn.prepareStatement(sql);
			// 4. ?�� ���� ä���
			ps.setString(1, dong);
			// 5. ���� �� ������� ������ �´�
			ResultSet rs=ps.executeQuery();
			// 6. count�� ����
			rs.next();
			count=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			// ���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			// ����
			disConnection();
		}
		return count;
	}
	

}
