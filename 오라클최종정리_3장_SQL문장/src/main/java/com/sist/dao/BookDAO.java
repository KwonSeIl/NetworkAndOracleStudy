package com.sist.dao;
/*
 * 	book, customer, orders => VO�� ���� ����
 * 	---------------------- BookDAO
 * 	emp, dept, salgrade => VO�� ���� ����
 * 	------------------- EmpDAO
 */
import java.util.*; //�����͸� ��Ƽ� �Ѱ��� �� ArrayList
/*
 * 	ROW �Ѱ� (ROW -> BookVO)
 * 	ROW ������ => ArrayList
 */
import java.sql.*;
/*
 * 	1. ����̹� ��� Class.forName()
 * 	2. ���� DriverManager.getConnection(URL,"user","pwd")
 *  3. SQL ���� ����
 *  4. ����Ŭ ���� conn.preparedStatement(sql)
 *  5. ����� �б�
 *  	����� �ִ� ��� (SELECT) => executeQuery
 *  	����� ���� ��� (INSERT, UPDATE, DELETE) => executeUpdate() ==> commit�� �����ϰ� �ִ�
 *  	����Ǵ� �޸�: ResultSet
 *  				ResultSet rs=ps.executeQuery()
 *  	
 *  	=> SELECT empno,ename,job,sal,deptno FROM emp;
 *  	ResultSet�� ����
 *  	-------------------------------------------
 *  		empno	ename	job	  sal	hiredate
 *  	-------------------------------------------
 *  		  1	    '1'	    '1'   1.1   '22/10/10' | cursor �̵�: next() => ó������ �����ٷ� �̵� �� ������ �б�
 *  		  2 	'2'		'2'   2.2	'22/11/12' | cursor �̵�: previous() => ���������� �����ٷ� �̵� �� ������ �б�
 *  	-------------------------------------------|(cursor)
 *  	While(rs.next()) �� ���
 *  		1	'1'	    '1'   1.1   '22/10/10'
 *  		rs.getInt(1)
 *  		rs.getString(2)
 *  		rs.getString(3)
 *  		rs.getDouble(4)
 *  		rs.getDate(5)
 *  		==> ���������� Ʋ���� ���� �߻�(���� ��ȯ ����)
 *  6. �޸� �ݱ�: rs.close()
 *  7. ps.close()
 *  8. conn.close()
 *  ����, �ݱ� => �ݺ�(�޼ҵ�) ==> �Ѱ��� ����� �����, �ݺ��� ����
 *  						-------------   -------
 *  						=> ����, ���� : ��ü������ �⺻ ���� 
 */
public class BookDAO {
	private Connection conn; // ����Ŭ ���� ��ü
	private PreparedStatement ps; //�ۼ��� ��ü
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//1. ����̹� ���
	public BookDAO()
	{
		/*
		 * 	������ => ��ü ������ �� ȣ��Ǵ� �޼ҵ�
		 * 	---- 1) �������� ���� �� �ִ�: �����ε�
		 * 		 2) �������� ����
		 * 		 3) Ŭ������� ����
		 * 		 4) �ַ� ����: ��������� ���� �ʱ�ȭ, �� ���� ����, ����, ����̹�
		 * 			��)�ڵ� �α���, ��Ű ����
		 */
		try
		{
			Class.forName("oracle.jdbc.dirver.OracleDriver"); //��ҹ���
			// Ŭ���� ������ �о ������ �� �ַ� ���
			// �޸� �Ҵ�(new ��� ���), �޼ҵ� ȣ��, ������ �ʱⰪ ����.. ���÷���(������, ���̹�Ƽ��)
			// new �����ڸ� �̿��ϸ� ���ռ��� ���� ���α׷��� ����. -> ������� ����� ����, ���ռ� ���� ���α׷� = ���÷��� �̿�
			// ������: new�� ������� �ʴ´�. �������� �������� �ʴ´� 
			// ���ռ� => ����(���� -> �ٸ� Ŭ���� ����) => ��(������) => �ڹ�
		}catch(Exception cf) {}
	}
	//2. ����Ŭ ����
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//3. ����Ŭ ���� ����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 4. ��� ����
	// 4-1.[���� 3-1] ��� ������ �̸��� ������ �˻��Ͻÿ� BookVO => ���� ���� 1�� 
	// => �ڹٿ� ����Ŭ ����(70%) / HTML ���(30%) ==> �����α׷���
	/*
	 * ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. ����
			getConnection();
			//2. SQL ���� ����
			//3. ����Ŭ�� ����
			//4. ������� ������ �´�
			//5. ArrayList���� �߰�
		}catch(Exception ex)
		{
			//���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			//����
			disConnection();
		}
		return list;
	 */
	public ArrayList<BookVO> book3_1()
	{
		ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. ����
			getConnection();
			//2. SQL ���� ����
			String sql="SELECT bookname,price FROM book";
			//3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			//4. ������� ������ �´�
			ResultSet rs=ps.executeQuery();
			//5. ArrayList���� �߰�
			while(rs.next())
			{
				BookVO vo=new BookVO();
				vo.setBookname(rs.getString(1));
				vo.setPrice(rs.getInt(2));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			//���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			//����
			disConnection();
		}
		return list;
	}
	// ��� ������ ������ȣ, �����̸�, ���ǻ�, ���� �˻�
	public ArrayList<BookVO> book3_2()
	{
		ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. ����
			getConnection();
			//2. SQL ���� ����
			/*String sql="SELECT bookid,bookname,publisher,price "
					+"FROM book";*/
			String sql="SELECT * FROM book";
			//3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			//4. ������� ������ �´�
			ResultSet rs=ps.executeQuery();
			//5. ArrayList���� �߰�
			while(rs.next())
			{
				BookVO vo=new BookVO();
				vo.setBookid(rs.getInt(1));
				vo.setBookname(rs.getString(2));
				vo.setPublisher(rs.getString(3));
				vo.setPrice(rs.getInt(4));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			//���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			//����
			disConnection();
		}
		return list;
	}
	// ���� ���̺� �ִ� ��� ���ǻ縦 �˻��Ͻÿ�
	// �÷��� ������ => VO
	// �÷��� �Ѱ� => �ش� ���������� �޾ƿ´� 
	/*
	 * 	price => int��. ArrayList ��� �� ArrayList<Integer> => WrapperŬ���� ���
	 * 	<Ŭ������(�Ϲݵ��������� ����� �� ����)>
	 */
	public ArrayList<String> book3_3()
	{
		ArrayList<String> list=new ArrayList<String>();
		try
		{
			//1. ����
			getConnection();
			//2. SQL ���� ����
			String sql="SELECT DISTINCT publisher FROM book";
			//3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			//4. ������� ������ �´�
			ResultSet rs=ps.executeQuery();
			//5. ArrayList���� �߰�
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			rs.close();
		}catch(Exception ex)
		{
			//���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			//����
			disConnection();
		}
		return list;
	}
	// ������ �ɷ��� �� ó���ϴ� ���� => ���� ���� �ֹ��� ���� ������ ��� ���̱�
	public ArrayList<CustomerVO> book3_21()
	{
		ArrayList<CustomerVO> list=new ArrayList<CustomerVO>();
		try
		{
			//1. ����
			getConnection();
			//2. SQL ���� ����
			// SQL ��ɾ �ùٸ��� ������� �ʾҽ��ϴ� ==> ���� ����
			// ���κ�ȯ �ȵ� ==> �������� Ʋ��
			// IN OUT ==> ?�� ���� �������� �ʴ� ���
			// NULL ==> URL�� Ʋ�� ���
			String sql="SELECT name,address,phone,bookid,saleprice,orderdate "
					+"FROM customer,orders "
					+"WHERE customer.custid=orders.custid "
					+"ORDER BY customer.custid";
			/*
			 * 	�ڹٿ��� ����Ŭ�κ��� �����͸� �б�
			 * 	 1. �Ϲ� SQL���� (table,view)
			 * 	 2. JOIN
			 * 	 3. SubQuery
			 * 	 4. �ζ��κ� => ������ 
			 */
			//3. ����Ŭ�� ����
			ps=conn.prepareStatement(sql);
			//4. ������� ������ �´�
			ResultSet rs=ps.executeQuery();
			//5. ArrayList���� �߰�
			while(rs.next())
			{
				CustomerVO vo=new CustomerVO();
				vo.setName(rs.getString(1));
				vo.setAddress(rs.getString(2));
				vo.setPhone(rs.getString(3));
				vo.getOvo().setBookid(rs.getInt(4));
				vo.getOvo().setSaleprice(rs.getInt(5));
				vo.getOvo().setOrderdate(rs.getDate(6));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			//���� Ȯ��
			ex.printStackTrace();
		}
		finally
		{
			//����
			disConnection();
		}
		return list;
	}
	

}
