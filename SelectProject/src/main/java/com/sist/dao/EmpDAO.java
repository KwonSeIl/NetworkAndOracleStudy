package com.sist.dao;
// 오라클 연결하는 클래스
/*
 *	~DAO => table당 한개 만들어서 데이터베이스 연결
 *  ~VO, ~DTO => 데이터를 모아서 전송할 목적(브라우저,자바로)
 *  ~Manager => 외부에서 데이터를 읽어온다(크롤링,JSON..)
 *  ~Service => DAO 여러개를 묶어서 한번에 처리
 */
import java.util.*; //ArrayList
import java.sql.*;
/*
 * 	C/S => network(오라클 서버) SeverSocket 
 * 			DAO(클라이언트) Scoket, BufferedReader,OutputStream ==> TCP
 * 	1. Connection(interface): 연결할 때 사용
 *  2. Statement(interface): SQL문장을 전송 -> 결과값을 받아올 때 사용
 *  	Statement: SQL문장과 데이터값을 동시에 전송
 *  	PreparedStatement: SQL문장을 먼저 전송, 나중에 값을 채워준다
 *  	CallableStatement: PL/SQL -> 오라클 함수(프로시저)를 호출
 *  3. ResultSet(interface): 결과값을 메모리에 저장할 때 사용
 *  	------------------
 *  	   id	sex	   name	====> row 단위로 읽기
 *  	------------------
 *  			BOF
 *  		1	 2		3
 *  	------------------
 *  	   aaa	 man   hong | ==> next()
 *  		=> 문자열: getString(1)
 *  		=> 정수: getInt(2)
 *  		=> 실수: getDouble(3)
 *  		=> 날짜: getDate(4)
 *  	------------------
 *  	   bbb	woman  shim
 *  	------------------
 *  	   ccc	 man   park | ==> previous()
 *  	------------------
 *  			EOF
 *  	------------------
 *  	| 커서위치
 *  
 *  	2차 자바 / 3차 자바 / 4차 자바
 *  	------	 -----	  ------
 *  	DB 관련	 JSP 관련		Spring 관련 => 1~8장, 11~12장, 15장 (base)
 *  	------------------------------
 *  	 1. 디자인 패턴				| Spring에서 디자인 패턴 8개 사용
 *  	 2. 알고리즘
 */
public class EmpDAO {
	   //연결객체 만들기 : 한 사람당 1개만 사용
	   private Connection conn;
	   //SQL송수신객체 만들기
	   private PreparedStatement ps;
	   //오라클 주소 설정
	   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	   //싱글턴 패턴 --> 메모리 1개만 생성 후(static으로 만들기) 재사용
	   private static EmpDAO dao; //static 사용 시 메모리를 1개만 생성하게 됨
	   //메모리 누수 현상 방지 : 사용하지 않는 메모리가 없게 하는 것
	   
	   //1. 드라이버 등록 : Class.forName("") --> 드라이버가 소프트웨어형식으로 만들어져 있어 클래스 등록하면 됨 (1번만 등록하면 됨)
	   // 생성자 : 한번 수행, 초기화 역할 수행
	   public EmpDAO()
	   {
	      try
	      {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         //클래스의 모든 정보를 읽고, 메모리 할당, 멤버변수, 메소드를 제어할 때 사용 --> 리플렉션(스프링)
	         //패키지명.클래스명
	         //패키지명.클래스명 하여 등록 --> 메모리 할당 요청
	      }catch(Exception ex) {}
	   }
	   //1-1. 싱글턴 패턴 --> newInstance(), getInstance()
	   public static EmpDAO newInstance()
	   {
	      if(dao==null)
	         dao=new EmpDAO();
	      return dao;
	   }
	   //2. 연결 (열기)
	   public void getConnection()
	   {
	      try
	      {
	         conn=DriverManager.getConnection(URL,"hr","happy");
	         //conn hr/happy 기능과 동일
	      }catch(Exception ex) {}
	   }
	   //3. 해제 (닫기)
	   public void DisConnection()
	   {
	      try
	      {
	         if(ps!=null) ps.close(); //통신 종료
	         if(conn!=null) conn.close();
	         //exit() 기능과 동일
	      }catch(Exception ex) {}
	   }
	   //4. 기능 : SQL
	   //사원정보 묶어서 클라이언트로 전송
	   public ArrayList<EmpVO> empListData()
	   {
	      ArrayList<EmpVO> list=new ArrayList<EmpVO>(); //값은 EmpVO가 받고 list로 묶어주는 것
	      try
	      {
	         //1. 연결
	         getConnection();
	         //2. SQL문장
	         String sql="SELECT empno,ename,job,mgr,hiredate,sal,comm,emp.deptno,dname,loc,grade "
	                  +"FROM emp,dept,salgrade "
	                  +"WHERE emp.deptno=dept.deptno "
	                  +"AND sal BETWEEN losal AND hisal";
	         //3. 오라클 전송
	         ps=conn.prepareStatement(sql);
	         //4. 실행 후 결과값 받기
	         ResultSet rs=ps.executeQuery(); //record 단위로 데이터 가져옴 : while 1번 수행에 1줄 읽기
	         //5. 결과값을 ArrayList에 첨부
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
	            //VO : 테이블 마다 따로 제작. 조인/서브쿼리/뷰 --> 클래스를 포함해서 가져오기
	         }
	         rs.close();
	      }catch(Exception ex)
	      {
	         //에러 확인
	         System.out.println(ex.getMessage());
	      }
	      finally
	      {
	         //해제
	         DisConnection();
	      }
	      return list;
	      
	      
	   }
	   //검색 : LIKE
	   public ArrayList<EmpVO> empFindData(String ename)
	   {
	      ArrayList<EmpVO> list=new ArrayList<EmpVO>();
	      try
	      {
	         //1. 연결
	         getConnection();
	         //2. SQL 문장
	         String sql="SELECT empno,ename,job,hiredate,sal "
	               +"FROM emp "
	               +"WHERE ename LIKE '%'||?||'%'";  //주의하기. 오라클과 다르다
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
	         //오류 확인
	         System.out.println(ex.getMessage());
	      }
	      finally
	      {
	         //해제
	         DisConnection();
	      }
	      return list;
	   }
	   // SubQuery
	   // 한명의 정보 가져오기
	   public EmpVO empSubQueryData(String ename)
	   {
		   EmpVO vo=new EmpVO();
		   try
		   {
			   // 1. 연결
			   getConnection();
			   // 2. SQL 문장 
			   String sql="SELECT empno,ename,job,hiredate,sal,"
					   +"(SELECT dname FROM dept WHERE deptno=emp.deptno) dname, "
					   +"(SELECT dname FROM dept WHERE deptno=emp.deptno) loc "
					   +"FROM emp "
					   +"WHERE ename=?";
			   // 3. 오라클 전송
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, ename); //? 있을 때만 코딩
			   //ps.setInt(1,10) ==> 10
			   // 4. 결과값을 읽어서 메모리에 저장
			   ResultSet rs=ps.executeQuery();
			   // 5. EmpVO에 값을 대입
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
	   //인라인뷰 ==> FROM(SELECT~)
	   public ArrayList<EmpVO> empInlineView(int num)
	   {
		   ArrayList<EmpVO> list=new ArrayList<EmpVO>();
		   try
		   {
			   // 1. 연결
			   getConnection();
			   // 2. SQL 문장 제작 => 급여 순서대로 상위 5명만 읽어온다
			   //					1    2	   3      4     5    6
			   String sql="SELECT empno,ename,job,hiredate,sal,rownum "
					   +"FROM (SELECT * FROM emp ORDER BY sal DESC) "
					   +"WHERE rownum<=?";
			   // 3. 오라클 전송
			   ps=conn.prepareStatement(sql);
			   // 4. 실행 후 결과값 메모리에 저장
			   ps.setInt(1, num);
			   ResultSet rs=ps.executeQuery();
			   // 5. 메모리에 저장된 값을 ArrayList에 대입
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
			   ex.printStackTrace(); //오류 확인
		   }
		   finally
		   {
			   DisConnection(); //오라클 해제
		   }
		   return list;
	   }
	}