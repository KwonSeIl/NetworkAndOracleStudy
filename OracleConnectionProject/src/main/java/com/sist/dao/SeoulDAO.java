package com.sist.dao;
import java.util.*;
import java.sql.*;
public class SeoulDAO {
	// ���� ��ü
	private Connection conn;
	// SQL ���� ����, ����� �б�
	private PreparedStatement ps;
	// ����Ŭ �ּ�, ������, ��й�ȣ, ����̹��� ==> final ==> xml,properties => WEB-INF
	//	=========================> ��� ( \\,/)
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final String DRIVER="oracle.jdbc.driver.OracleDriver";
	private final String USER="hr";
	private final String PWD="happy";
	
	// ����̹� ���
	public SeoulDAO()
	{
		// �ѹ��� ����� ���� => ��ü ���� �� �� ���� ȣ��: �ڵ� �α���, ID ���� 
		try
		{
			// ��� ��� => Class.forName(), Properties �̿�
			Class.forName(DRIVER); //DriverManager�� �޸𸮸� �Ҵ� 
			// ��Ű����.Ŭ�������� ��� -> �޸� �Ҵ��� ��û
			// => MyBatis, Spring, Spring-Boot: Ŭ���� ������ => ��� => �̱���
			// ������ => new�� ������� �ʴ´�. �޸� �Ҵ� => ������̼�
			/*
			 * 	@Repository
			 * 	class A
			 * 
			 * 	class B
			 * 	{
			 * 		@Autowired
			 * 		A a;
			 * 	}
			 *  �����ӿ�ũ ==> Ŭ���� �м� 
			 */
		}catch(Exception ex) {}
	}
	// ����Ŭ ���� ==> ������Ʈ: DBCP - �������� �ּҸ� �� ����ϴ� �� ==> ORM
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,USER,PWD);
		}catch(Exception ex) {}
	}
	// ����Ŭ �ݱ�
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// ��� ����
	// VO, DAO ==> ���̺� �Ѱ��� ���� ���
	public ArrayList<SeoulVO> seoulListData(int type)
	{
		String[] table_name= {"","seoul_location","seoul_nature","seoul_shop"};
		ArrayList<SeoulVO> list=new ArrayList<SeoulVO>();
		try
		{
			// 1. ����
			getConnection();
			// 2. SQL ���� ����
			//String sql="SELECT /*+INDEX_ASC(seoul_location sl_no_ok)*/ no,title FROM "+table_name[type]
			//								  seoul_nature sn_no_pk
			//								  seoul_shop ss_no_pk
			String sql="SELECT no,title FROM "+table_name[type]
					+" ORDER BY no ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SeoulVO vo=new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
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
	// DAO, DAO => Service
	
}
