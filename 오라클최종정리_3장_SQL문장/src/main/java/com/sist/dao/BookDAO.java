package com.sist.dao;
/*
 * 	book, customer, orders => VO는 따로 제작
 * 	---------------------- BookDAO
 * 	emp, dept, salgrade => VO는 따로 제작
 * 	------------------- EmpDAO
 */
import java.util.*; //데이터를 모아서 넘겨줄 때 ArrayList
/*
 * 	ROW 한개 (ROW -> BookVO)
 * 	ROW 여러개 => ArrayList
 */
import java.sql.*;
/*
 * 	1. 드라이버 등록 Class.forName()
 * 	2. 연결 DriverManager.getConnection(URL,"user","pwd")
 *  3. SQL 문장 제작
 *  4. 오라클 전송 conn.preparedStatement(sql)
 *  5. 결과값 읽기
 *  	결과값 있는 경우 (SELECT) => executeQuery
 *  	결과값 없는 경우 (INSERT, UPDATE, DELETE) => executeUpdate() ==> commit을 포함하고 있다
 *  	저장되는 메모리: ResultSet
 *  				ResultSet rs=ps.executeQuery()
 *  	
 *  	=> SELECT empno,ename,job,sal,deptno FROM emp;
 *  	ResultSet의 구조
 *  	-------------------------------------------
 *  		empno	ename	job	  sal	hiredate
 *  	-------------------------------------------
 *  		  1	    '1'	    '1'   1.1   '22/10/10' | cursor 이동: next() => 처음부터 다음줄로 이동 후 데이터 읽기
 *  		  2 	'2'		'2'   2.2	'22/11/12' | cursor 이동: previous() => 마지막부터 이전줄로 이동 후 데이터 읽기
 *  	-------------------------------------------|(cursor)
 *  	While(rs.next()) 의 결과
 *  		1	'1'	    '1'   1.1   '22/10/10'
 *  		rs.getInt(1)
 *  		rs.getString(2)
 *  		rs.getString(3)
 *  		rs.getDouble(4)
 *  		rs.getDate(5)
 *  		==> 데이터형이 틀리면 오류 발생(내부 변환 실패)
 *  6. 메모리 닫기: rs.close()
 *  7. ps.close()
 *  8. conn.close()
 *  연결, 닫기 => 반복(메소드) ==> 한가지 기능을 만든다, 반복의 제거
 *  						-------------   -------
 *  						=> 수정, 재사용 : 객체지향의 기본 목적 
 */
public class BookDAO {
	private Connection conn; // 오라클 연결 객체
	private PreparedStatement ps; //송수신 객체
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//1. 드라이버 등록
	public BookDAO()
	{
		/*
		 * 	생성자 => 객체 저장할 때 호출되는 메소드
		 * 	---- 1) 여러개를 만들 수 있다: 오버로딩
		 * 		 2) 리턴형이 없다
		 * 		 3) 클래스명과 동일
		 * 		 4) 주로 역할: 멤버변수에 대한 초기화, 한 번만 수행, 연결, 드라이버
		 * 			예)자동 로그인, 쿠키 실행
		 */
		try
		{
			Class.forName("oracle.jdbc.dirver.OracleDriver"); //대소문자
			// 클래스 정보를 읽어서 제어할 때 주로 사용
			// 메모리 할당(new 대신 사용), 메소드 호출, 변수의 초기값 대입.. 리플렉션(스프링, 마이바티스)
			// new 연산자를 이용하면 결합성이 높은 프로그램이 돈다. -> 사용하지 말라고 권장, 결합성 낮은 프로그램 = 리플렉션 이용
			// 스프링: new를 사용하지 않는다. 변수값을 지정하지 않는다 
			// 결합성 => 영향(오류 -> 다른 클래스 에러) => 웹(스프링) => 자바
		}catch(Exception cf) {}
	}
	//2. 오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//3. 오라클 연결 종료
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 4. 기능 수행
	// 4-1.[질의 3-1] 모든 도서의 이름과 가격을 검색하시오 BookVO => 도서 정보 1개 
	// => 자바와 오라클 연동(70%) / HTML 출력(30%) ==> 웹프로그래머
	/*
	 * ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL 문장 제작
			//3. 오라클로 전송
			//4. 결과값을 가지고 온다
			//5. ArrayList값을 추가
		}catch(Exception ex)
		{
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	 */
	public ArrayList<BookVO> book3_1()
	{
		ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL 문장 제작
			String sql="SELECT bookname,price FROM book";
			//3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값을 가지고 온다
			ResultSet rs=ps.executeQuery();
			//5. ArrayList값을 추가
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
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	}
	// 모든 도서의 도서번호, 도서이름, 출판사, 가격 검색
	public ArrayList<BookVO> book3_2()
	{
		ArrayList<BookVO> list=new ArrayList<BookVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL 문장 제작
			/*String sql="SELECT bookid,bookname,publisher,price "
					+"FROM book";*/
			String sql="SELECT * FROM book";
			//3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값을 가지고 온다
			ResultSet rs=ps.executeQuery();
			//5. ArrayList값을 추가
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
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	}
	// 도서 테이블에 있는 모든 출판사를 검색하시오
	// 컬럼이 여러개 => VO
	// 컬럼이 한개 => 해당 데이터형을 받아온다 
	/*
	 * 	price => int형. ArrayList 사용 시 ArrayList<Integer> => Wrapper클래스 사용
	 * 	<클래스형(일반데이터형은 사용할 수 없다)>
	 */
	public ArrayList<String> book3_3()
	{
		ArrayList<String> list=new ArrayList<String>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL 문장 제작
			String sql="SELECT DISTINCT publisher FROM book";
			//3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값을 가지고 온다
			ResultSet rs=ps.executeQuery();
			//5. ArrayList값을 추가
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			rs.close();
		}catch(Exception ex)
		{
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	}
	// 조인이 걸렸을 때 처리하는 과정 => 고객과 고객의 주문에 관한 데이터 모부 보이기
	public ArrayList<CustomerVO> book3_21()
	{
		ArrayList<CustomerVO> list=new ArrayList<CustomerVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL 문장 제작
			// SQL 명령어가 올바르게 종료되지 않았습니다 ==> 공백 조절
			// 내부변환 안됨 ==> 데이터형 틀림
			// IN OUT ==> ?에 값을 지정하지 않는 경우
			// NULL ==> URL이 틀린 경우
			String sql="SELECT name,address,phone,bookid,saleprice,orderdate "
					+"FROM customer,orders "
					+"WHERE customer.custid=orders.custid "
					+"ORDER BY customer.custid";
			/*
			 * 	자바에서 오라클로부터 데이터를 읽기
			 * 	 1. 일반 SQL문장 (table,view)
			 * 	 2. JOIN
			 * 	 3. SubQuery
			 * 	 4. 인라인뷰 => 페이지 
			 */
			//3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			//4. 결과값을 가지고 온다
			ResultSet rs=ps.executeQuery();
			//5. ArrayList값을 추가
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
			//오류 확인
			ex.printStackTrace();
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	}
	

}
