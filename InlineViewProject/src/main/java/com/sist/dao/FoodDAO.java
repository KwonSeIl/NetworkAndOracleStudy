package com.sist.dao;
import java.util.*;
import java.sql.*;
//View => ����(����¡,�α����)
public class FoodDAO {
   private Connection conn;
   private PreparedStatement ps;
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   //1. Driver ��� => ������(�� ���� ����, �ʱ�ȭ)
   public FoodDAO()
   {
      try
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      }catch(Exception ex) {} 
   }
   //2. ����
   public void getConnection()
   {
      try
      {
         conn=DriverManager.getConnection(URL,"hr","happy");
      }catch(Exception ex) {}
   }
   //3. ����
   public void disConnection()
   {
      try
      {
         if(ps!=null) ps.close();
         if(conn!=null) conn.close();
      }catch(Exception ex) {}
   }
   //4. ���
   //4-1. ����¡��� => �ζ��κ� ==> �̹��� (5��)
   public ArrayList<FoodVO> foodListData(int page) //�Ű����� => ����ڷκ��� �޴� ��(��û��)
   {
      ArrayList<FoodVO> list=new ArrayList<FoodVO>();
      /*
      try
      {
         //1.����
         getConnection();
         //2. SQL ����
         String sql="SELECT fno,name,poster "
                  +"FROM food_location "
                  +"ORDER BY fno ASC";
         //3. ����Ŭ ����
         ps=conn.prepareStatement(sql);
         //4. �����û=>�����
         ResultSet rs=ps.executeQuery();
         
         int i=0; //20���� �����ִ� ����
         int j=0; //while�� Ƚ��
         final int rowSize=20; 
         int pagecnt=(page*rowSize)-rowSize; // ������ġ => ArrayList�� ���� ����
         //while, for => �ݺ��� => ���ۺ��� ������ ����
         while(rs.next())
         {
            if(i<rowSize && j>=pagecnt)
            {
               FoodVO vo=new FoodVO();
               vo.setFno(rs.getInt(1));;
               vo.setName(rs.getString(2));
               String poster=rs.getString(3);
               poster=poster.substring(0,poster.indexOf("^"));
               vo.setPoster(poster);
               list.add(vo);
               i++;
            }
            j++;
         }
         //5. ����� �����͸� ArrayList�� �̵� ==> ���������� ��û�� �� ���� ����� ������ ����
      }catch(Exception ex)
      {
         ex.printStackTrace();
      }
      finally
      {
         disConnection();
      }*/
      // �ζ��κ� => ���� 5��, ������(�ش���ġ)
      // ����Ǵ� ��ġ
      try
      {
    	  // 1. ����
    	  getConnection();
    	  // 2. SQL ���� ����
    	  String sql="SELECT fno,name,poster,num "
    			  +"FROM (SELECT fno,name,poster,rownum as num "
    			  +"FROM (SELECT fno,name,poster "
    			  +"FROM food_location ORDER BY fno ASC)) "
    			  +"WHERE num BETWEEN ? AND ?";
    	  // 3.����Ŭ ����
    	  ps=conn.prepareStatement(sql);
    	  // 4. ?�� ���� ä���
    	  int rowSize=20;
    	  int start=(rowSize*page)-(rowSize-1);
    	  // 1page ==> 20-19 = 1 ~ 20
    	  // 2page ==> 40-19 = 21 ~ 40
    	  int end=rowSize*page;
    	  
    	  ps.setInt(1, start);
    	  ps.setInt(2, end);
    	  
    	  // 5. ����� �ޱ�
    	  ResultSet rs=ps.executeQuery();
    	  //6. ArrayList�� ÷��
    	  while(rs.next())
    	  {
    		  FoodVO vo=new FoodVO();
    		  vo.setFno(rs.getInt(1));
    		  vo.setName(rs.getString(2));
    		  String poster=rs.getString(3);
    		  poster=poster.substring(0,poster.indexOf("^"));
    		  vo.setPoster(poster);
    		  
    		  list.add(vo);
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
   public int foodTotalPage()
   {
      int total=0;
      try
      {
         //1. ����
         getConnection();
         //2. SQL���� 
         String sql="SELECT CEIL(COUNT(*)/20.0) FROM food_location "; //�������� ���ϱ�
         //3. ����Ŭ ����
         ps=conn.prepareStatement(sql);
         //4. ���� ��û => �����
         ResultSet rs=ps.executeQuery();
         //5. Ŀ�� ��ġ ���� => ���� ��µ� �޸� ��ġ�� Ŀ�� �̵�
         rs.next();
         total=rs.getInt(1);
         rs.close();
         
      }catch(Exception ex)
      {
         ex.printStackTrace();
      }
      finally
      {
         disConnection();
      }
      return total;
   }
   //4-2. �˻� => LIKE
   //4-3. �󼼺��� => �ּ� �ڸ���
   public FoodVO foodDetailData(int fno)
   {
	   FoodVO vo=new FoodVO();
	   try
	   {
		   //1. ����
		   getConnection();
		   //2. SQL ���� ����
		   String sql="SELECT fno,name,tel,score,type,time,parking,menu,poster "
				   +"FROM (SELECT * FROM food_location) "
				   +"WHERE fno="+fno;
		   //3. ����Ŭ ����
		   ps=conn.prepareStatement(sql);
		   //4. ����� ����
		   ResultSet rs=ps.executeQuery();
		   //5. VO�� ����
		   rs.next(); // ����� ������ �б�
		   vo.setFno(rs.getInt(1));
		   vo.setName(rs.getString(2));
		   vo.setTel(rs.getString(3));
		   vo.setScore(rs.getDouble(4));
		   vo.setType(rs.getString(5));
		   vo.setTime(rs.getString(6));
		   vo.setParking(rs.getString(7));
		   vo.setMenu(rs.getString(8));
		   vo.setPoster(rs.getString(9));
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return vo;
   }
   public static void main(String[] args) {
      FoodDAO dao=new FoodDAO();
      Scanner scan=new Scanner(System.in);
      int totalpage=dao.foodTotalPage();
      System.out.print("1~"+totalpage+" ������ ������ �Է�:");
      int page=scan.nextInt();
      
      ArrayList<FoodVO> list=dao.foodListData(page);
      
      System.out.println("========== ����� ��� ==========");
      for(FoodVO vo:list)
      {
         System.out.println(vo.getFno()+" "+vo.getName());
      }
   }
}

