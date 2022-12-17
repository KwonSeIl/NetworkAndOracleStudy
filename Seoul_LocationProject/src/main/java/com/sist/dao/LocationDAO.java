package com.sist.dao;
//오라클 연결
import java.util.*;// ArrayList ==> 데이터 묶어준다
/*
 *       LocationVO
 *       ------------------------
 *          ====> 오라클에 저장 (한 개 명소에 대한 내용 저장) ==> new LoactionVO()
 *       ------------------------
 *  *       ====> 오라클에 저장 (한 개 명소에 대한 내용 저장)
 *       ------------------------
 *          ====> 오라클에 저장 (한 개 명소에 대한 내용 저장)
 *       ------------------------
 * 
 * 
 */
import java.sql.*; //오라클 연결 => 송수신(SQL 전송, 데이터 수신)
public class LocationDAO {
	
   // 데이터베이스 연결 객체
   private Connection conn;
   // 데이터베시으 송수신이 가능
   private PreparedStatement ps;
   // SQL 문장=> 전송, 데이터 받기
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   // 드라이버 등록
   public LocationDAO()
   {
      try
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      }catch(Exception ex) {}
   }
   // 연결
   public void getConnection()
   {
      try
      {
         conn=DriverManager.getConnection(URL,"hr","happy");
      }catch(Exception ex) {}
   }
   // 닫기
   public void disConnection()
   {
      try
      {
         if(ps!=null) ps.close();
         if(conn!=null) conn.close();
      }catch(Exception ex) {}
   }
   //-----------------------------------------모든 DAO 동일
   // 기능 (SQL 문장)
   public ArrayList<LocationVO> locationListData()
   {
      ArrayList<LocationVO> list=new ArrayList<LocationVO>();
      try
      {
         // 1. 연결
         getConnection();
         // 2. SQL 문장
         String sql="SELECT no,title,poster p,msg,rownum "
                  +"FROM seoul_location "
                  +"WHERE no BETWEEN 1 AND 20";
                  //WHERE no BETWEEN 1 AND 20
               
         // 3. 오라클 전송 ==========>
         ps=conn.prepareStatement(sql);
         // 4. 결과값 읽기
         ResultSet rs=ps.executeQuery(); //rs에 결과값 저장
         // 5. 결과값을 ArrayList에 담는다 ==> 브라우저에서 실행
         while(rs.next())
         {
            LocationVO vo=new LocationVO();
            vo.setNo(rs.getInt("no"));
            vo.setTitle(rs.getString("title"));
            vo.setPoster(rs.getString("p"));
            vo.setMsg(rs.getString("msg"));
            list.add(vo); //추출된 데이터를 모아둔다
         }
         rs.close(); //==========>
      }catch(Exception ex)
      {
         //오류 확인
         ex.printStackTrace();
      }
      finally
      {
         //닫기
         disConnection();
      }
      return list;
   }
}