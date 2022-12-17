package com.sist.dao;
//����Ŭ ����
import java.util.*;// ArrayList ==> ������ �����ش�
/*
 *       LocationVO
 *       ------------------------
 *          ====> ����Ŭ�� ���� (�� �� ��ҿ� ���� ���� ����) ==> new LoactionVO()
 *       ------------------------
 *  *       ====> ����Ŭ�� ���� (�� �� ��ҿ� ���� ���� ����)
 *       ------------------------
 *          ====> ����Ŭ�� ���� (�� �� ��ҿ� ���� ���� ����)
 *       ------------------------
 * 
 * 
 */
import java.sql.*; //����Ŭ ���� => �ۼ���(SQL ����, ������ ����)
public class LocationDAO {
	
   // �����ͺ��̽� ���� ��ü
   private Connection conn;
   // �����ͺ����� �ۼ����� ����
   private PreparedStatement ps;
   // SQL ����=> ����, ������ �ޱ�
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   // ����̹� ���
   public LocationDAO()
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
   // �ݱ�
   public void disConnection()
   {
      try
      {
         if(ps!=null) ps.close();
         if(conn!=null) conn.close();
      }catch(Exception ex) {}
   }
   //-----------------------------------------��� DAO ����
   // ��� (SQL ����)
   public ArrayList<LocationVO> locationListData()
   {
      ArrayList<LocationVO> list=new ArrayList<LocationVO>();
      try
      {
         // 1. ����
         getConnection();
         // 2. SQL ����
         String sql="SELECT no,title,poster p,msg,rownum "
                  +"FROM seoul_location "
                  +"WHERE no BETWEEN 1 AND 20";
                  //WHERE no BETWEEN 1 AND 20
               
         // 3. ����Ŭ ���� ==========>
         ps=conn.prepareStatement(sql);
         // 4. ����� �б�
         ResultSet rs=ps.executeQuery(); //rs�� ����� ����
         // 5. ������� ArrayList�� ��´� ==> ���������� ����
         while(rs.next())
         {
            LocationVO vo=new LocationVO();
            vo.setNo(rs.getInt("no"));
            vo.setTitle(rs.getString("title"));
            vo.setPoster(rs.getString("p"));
            vo.setMsg(rs.getString("msg"));
            list.add(vo); //����� �����͸� ��Ƶд�
         }
         rs.close(); //==========>
      }catch(Exception ex)
      {
         //���� Ȯ��
         ex.printStackTrace();
      }
      finally
      {
         //�ݱ�
         disConnection();
      }
      return list;
   }
}