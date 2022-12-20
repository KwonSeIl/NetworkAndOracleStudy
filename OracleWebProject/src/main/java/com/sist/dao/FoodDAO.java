package com.sist.dao;
import java.util.*;
import java.sql.*;
public class FoodDAO {
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
		public FoodDAO()
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
		// ���
		// 1. ��� ��� ==> inlineview, index
		public ArrayList<FoodVO> foodListData(int page)
		{
			ArrayList<FoodVO> list=new ArrayList<FoodVO>();
			try
			{
				// 1. ���� PK_FOOD_LOCATION
				getConnection();
				// 2. SQL ���� ����
				String sql="SELECT fno,name,poster,score,num "
						+"FROM (SELECT fno,name,poster,score,rownum as num "
						+"FROM (SELECT /*+ INDEX_ASC(food_location pk_food_location)*/ fno,name,poster,score "
						+"FROM food_location)) "
						+"WHERE num BETWEEN ? AND ?";
				// 3. ����Ŭ�� ����
				ps=conn.prepareStatement(sql);
				// 4. ?�� ���� ä���
				int rowSize=20;
				int start=(rowSize*page)-(rowSize-1); // rownum=1������ �����ϱ� ������ -1
				int end=rowSize*page;
				/*
				 * 
				 * 	1page => 1 ~ 20 ==> start=1, end=20
				 *  2page => 21 ~ 40 ==> start=21, end=40
				 *  3page => 41 ~ 60  ==> start=41, end=60
				 */
				ps.setInt(1, start);
				ps.setInt(2, end);
				
				// 4. ���� ��û
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					FoodVO vo=new FoodVO();
					vo.setFno(rs.getInt(1));
					vo.setName(rs.getString(2));
					vo.setPoster(rs.getString(3));
					vo.setScore(rs.getDouble(4));
					// vo = ������ ���� ������ ������ �ִ�(�Ѱ��� ����) 
					list.add(vo); // list �ȿ� �������� 20���� �߰� 
				}
				rs.close();
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

}
