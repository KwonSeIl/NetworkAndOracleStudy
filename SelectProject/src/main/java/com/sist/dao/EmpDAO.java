package com.sist.dao;
// ����Ŭ �����ϴ� Ŭ����
/*
 *	~DAO => table�� �Ѱ� ���� �����ͺ��̽� ����
 *  ~VO, ~DTO => �����͸� ��Ƽ� ������ ����(������,�ڹٷ�)
 *  ~Manager => �ܺο��� �����͸� �о�´�(ũ�Ѹ�,JSON..)
 *  ~Service => DAO �������� ��� �ѹ��� ó��
 */
import java.util.*; //ArrayList
import java.sql.*;
/*
 * 	C/S => network(����Ŭ ����) SeverSocket 
 * 			DAO(Ŭ���̾�Ʈ) Scoket, BufferedReader,OutputStream ==> TCP
 * 	1. Connection(interface): ������ �� ���
 *  2. Statement(interface): SQL������ ���� -> ������� �޾ƿ� �� ���
 *  	Statement: SQL����� �����Ͱ��� ���ÿ� ����
 *  	PreparedStatement: SQL������ ���� ����, ���߿� ���� ä���ش�
 *  	CallableStatement: PL/SQL -> ����Ŭ �Լ�(���ν���)�� ȣ��
 *  3. ResultSet(interface): ������� �޸𸮿� ������ �� ���
 *  	------------------
 *  	   id	sex	   name	====> row ������ �б�
 *  	------------------
 *  			BOF
 *  		1	 2		3
 *  	------------------
 *  	   aaa	 man   hong | ==> next()
 *  		=> ���ڿ�: getString(1)
 *  		=> ����: getInt(2)
 *  		=> �Ǽ�: getDouble(3)
 *  		=> ��¥: getDate(4)
 *  	------------------
 *  	   bbb	woman  shim
 *  	------------------
 *  	   ccc	 man   park | ==> previous()
 *  	------------------
 *  			EOF
 *  	------------------
 *  	| Ŀ����ġ
 *  
 *  	2�� �ڹ� / 3�� �ڹ� / 4�� �ڹ�
 *  	------	 -----	  ------
 *  	DB ����	 JSP ����		Spring ���� => 1~8��, 11~12��, 15�� (base)
 *  	------------------------------
 *  	 1. ������ ����				| Spring���� ������ ���� 8�� ���
 *  	 2. �˰���
 */
public class EmpDAO {
	   //���ᰴü ����� : �� ����� 1���� ���
	   private Connection conn;
	   //SQL�ۼ��Ű�ü �����
	   private PreparedStatement ps;
	   //����Ŭ �ּ� ����
	   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	   //�̱��� ���� --> �޸� 1���� ���� ��(static���� �����) ����
	   private static EmpDAO dao; //static ��� �� �޸𸮸� 1���� �����ϰ� ��
	   //�޸� ���� ���� ���� : ������� �ʴ� �޸𸮰� ���� �ϴ� ��
	   
	   //1. ����̹� ��� : Class.forName("") --> ����̹��� ����Ʈ������������ ������� �־� Ŭ���� ����ϸ� �� (1���� ����ϸ� ��)
	   // ������ : �ѹ� ����, �ʱ�ȭ ���� ����
	   public EmpDAO()
	   {
	      try
	      {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         //Ŭ������ ��� ������ �а�, �޸� �Ҵ�, �������, �޼ҵ带 ������ �� ��� --> ���÷���(������)
	         //��Ű����.Ŭ������
	         //��Ű����.Ŭ������ �Ͽ� ��� --> �޸� �Ҵ� ��û
	      }catch(Exception ex) {}
	   }
	   //1-1. �̱��� ���� --> newInstance(), getInstance()
	   public static EmpDAO newInstance()
	   {
	      if(dao==null)
	         dao=new EmpDAO();
	      return dao;
	   }
	   //2. ���� (����)
	   public void getConnection()
	   {
	      try
	      {
	         conn=DriverManager.getConnection(URL,"hr","happy");
	         //conn hr/happy ��ɰ� ����
	      }catch(Exception ex) {}
	   }
	   //3. ���� (�ݱ�)
	   public void DisConnection()
	   {
	      try
	      {
	         if(ps!=null) ps.close(); //��� ����
	         if(conn!=null) conn.close();
	         //exit() ��ɰ� ����
	      }catch(Exception ex) {}
	   }
	   //4. ��� : SQL
	   //������� ��� Ŭ���̾�Ʈ�� ����
	   public ArrayList<EmpVO> empListData()
	   {
	      ArrayList<EmpVO> list=new ArrayList<EmpVO>(); //���� EmpVO�� �ް� list�� �����ִ� ��
	      try
	      {
	         //1. ����
	         getConnection();
	         //2. SQL����
	         String sql="SELECT empno,ename,job,mgr,hiredate,sal,comm,emp.deptno,dname,loc,grade "
	                  +"FROM emp,dept,salgrade "
	                  +"WHERE emp.deptno=dept.deptno "
	                  +"AND sal BETWEEN losal AND hisal";
	         //3. ����Ŭ ����
	         ps=conn.prepareStatement(sql);
	         //4. ���� �� ����� �ޱ�
	         ResultSet rs=ps.executeQuery(); //record ������ ������ ������ : while 1�� ���࿡ 1�� �б�
	         //5. ������� ArrayList�� ÷��
	         while(rs.next())
	         {
	            EmpVO vo=new EmpVO();
	            vo.setEmpno(rs.getInt(1));
	            vo.setEname(rs.getString(2));
	            vo.setJob(rs.getString(3));
	            vo.setMgr(rs.getInt(4));
	            vo.setHiredate(rs.getDate(5));
	            vo.setSal(rs.getInt(6));
	            vo.setComm(rs.getInt(7));
	            vo.setDeptno(rs.getInt(8));
	            vo.getDvo().setDname(rs.getString(9));
	            vo.getDvo().setLoc(rs.getString(10));
	            vo.getSvo().setGrade(rs.getInt(11));
	            list.add(vo);
	            //VO : ���̺� ���� ���� ����. ����/��������/�� --> Ŭ������ �����ؼ� ��������
	         }
	         rs.close();
	      }catch(Exception ex)
	      {
	         //���� Ȯ��
	         System.out.println(ex.getMessage());
	      }
	      finally
	      {
	         //����
	         DisConnection();
	      }
	      return list;
	      
	      
	   }
	   //�˻� : LIKE
	   public ArrayList<EmpVO> empFindData(String ename)
	   {
	      ArrayList<EmpVO> list=new ArrayList<EmpVO>();
	      try
	      {
	         //1. ����
	         getConnection();
	         //2. SQL ����
	         String sql="SELECT empno,ename,job,hiredate,sal "
	               +"FROM emp "
	               +"WHERE ename LIKE '%'||?||'%'";  //�����ϱ�. ����Ŭ�� �ٸ���
	         ps=conn.prepareStatement(sql);
	         ps.setString(1, ename);
	         ResultSet rs=ps.executeQuery();
	         while(rs.next())
	         {
	            EmpVO vo=new EmpVO();
	            vo.setEmpno(rs.getInt(1));
	            vo.setEname(rs.getString(2));
	            vo.setJob(rs.getString(3));
	            vo.setHiredate(rs.getDate(4));
	            vo.setSal(rs.getInt(5));
	            list.add(vo);
	         }
	         rs.close();
	      }catch(Exception ex)
	      {
	         //���� Ȯ��
	         System.out.println(ex.getMessage());
	      }
	      finally
	      {
	         //����
	         DisConnection();
	      }
	      return list;
	   }
	   // SubQuery
	   // �Ѹ��� ���� ��������
	   public EmpVO empSubQueryData(String ename)
	   {
		   EmpVO vo=new EmpVO();
		   try
		   {
			   // 1. ����
			   getConnection();
			   // 2. SQL ���� 
			   String sql="SELECT empno,ename,job,hiredate,sal,"
					   +"(SELECT dname FROM dept WHERE deptno=emp.deptno) dname, "
					   +"(SELECT dname FROM dept WHERE deptno=emp.deptno) loc "
					   +"FROM emp "
					   +"WHERE ename=?";
			   // 3. ����Ŭ ����
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, ename); //? ���� ���� �ڵ�
			   //ps.setInt(1,10) ==> 10
			   // 4. ������� �о �޸𸮿� ����
			   ResultSet rs=ps.executeQuery();
			   // 5. EmpVO�� ���� ����
			   rs.next();
			   vo.setEmpno(rs.getInt(1));
			   vo.setEname(rs.getString(2));
			   vo.setJob(rs.getString(3));
			   vo.setHiredate(rs.getDate(4));
			   vo.setSal(rs.getInt(5));
			   vo.getDvo().setDname(rs.getString(6));
			   vo.getDvo().setLoc(rs.getString(7));
			   rs.close();
		   }catch(Exception ex) 
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   DisConnection();
		   }
		   return vo;
	   }
	   //�ζ��κ� ==> FROM(SELECT~)
	   public ArrayList<EmpVO> empInlineView(int num)
	   {
		   ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		   try
		   {
			   // 1. ����
			   getConnection();
			   // 2. SQL ���� ���� => �޿� ������� ���� 5�� �о�´�
			   //					1    2	   3      4     5    6
			   String sql="SELECT empno,ename,job,hiredate,sal,rownum "
					   +"FROM (SELECT * FROM emp ORDER BY sal DESC) "
					   +"WHERE rownum<=?";
			   // 3. ����Ŭ ����
			   ps=conn.prepareStatement(sql);
			   // 4. ���� �� ����� �޸𸮿� ����
			   ps.setInt(1, num);
			   ResultSet rs=ps.executeQuery();
			   // 5. �޸𸮿� ����� ���� ArrayList�� ����
			   while(rs.next())
			   {
				   EmpVO vo=new EmpVO();
				   vo.setEmpno(rs.getInt(1));
				   vo.setEname(rs.getString(2));
				   vo.setJob(rs.getString(3));
				   vo.setHiredate(rs.getDate(4));
				   vo.setSal(rs.getInt(5));
				   list.add(vo);
			   }
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace(); //���� Ȯ��
		   }
		   finally
		   {
			   DisConnection(); //����Ŭ ����
		   }
		   return list;
	   }
	}