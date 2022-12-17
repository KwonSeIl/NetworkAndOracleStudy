package com.sist.dao;
// ����Ŭ ����
import java.util.*;
import java.sql.*;
public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
    // ����̹� ��� 
    public BoardDAO()
    {
    	try
    	{
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    	}catch(Exception ex){}
    }
    
    // ���� 
    public void getConnection()
    {
    	try
    	{
    		conn=DriverManager.getConnection(URL,"hr","happy");
    	}catch(Exception ex) {}
    }
    // ����
    public void disConnection()
    {
    	try
    	{
    		// ���� ���� ==> ��Ʈ������ 
    		if(ps!=null) ps.close();
    		if(conn!=null) conn.close();
    	}catch(Exception ex) {}
    }
    // ��� ==> CURD ���α׷� 
    // ==> ����¡ (X) ==> �̹���(����=>���)
    // 1. ��� ��� ==> SELECT (ORDER BY)
    public ArrayList<BoardVO> boardListData()
    {
    	ArrayList<BoardVO> list=new ArrayList<BoardVO>();
    	try
    	{
    		// 1. ����
    		getConnection(); // �޼ҵ�� �Ѱ��� ��� (����) , �ݺ����� ���� 
    		// 2. SQL���� ����
    		String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY/MM/DD'),hit "
    				  +"FROM freeboard "
    				  +"ORDER BY no DESC";
    		// 3. ����Ŭ ������ ���� 
    		ps=conn.prepareStatement(sql);
    		// 4. ����� �ޱ� 
    		ResultSet rs=ps.executeQuery();
    		// 5. ������� ArrayList�� ��´� 
    		// ����Ŭ�� ����� �����ʹ� �ڵ� ������ �� ���°� �ƴϴ� 
    		while(rs.next())// �޸𸮿� ��µ� ù��°��ġ�� Ŀ���� �̵��Ѵ� 
    		{
    			BoardVO vo=new BoardVO();
    			vo.setNo(rs.getInt(1));
    			vo.setSubject(rs.getString(2));
    			vo.setName(rs.getString(3));
    			vo.setDbday(rs.getString(4));
    			vo.setHit(rs.getInt(5));
    			list.add(vo);
    		}
    		rs.close();
    	}catch(Exception ex)
    	{
    		ex.printStackTrace(); // ���� Ȯ�� 
    	}
    	finally
    	{
    		disConnection(); // ����Ŭ ���� ����
    	}
    	return list;
    }
    // 2. �Խù� �߰� ==> INSERT
    public void boardInsert(BoardVO vo)
    {
    	try
    	{
    		//1. ����
    		getConnection();
    		//2. SQL���� 
    		String sql="INSERT INTO freeboard(no,name,subject,content,pwd) VALUES("
    				  +"(SELECT NVL(MAX(no)+1,1) FROM freeboard),?,?,?,?)";
    		//3. ����Ŭ�� ����
    		ps=conn.prepareStatement(sql);
    		//4. �������� ?�� ���� ä��� 
    		ps.setString(1, vo.getName());
    		ps.setString(2, vo.getSubject());
    		ps.setString(3, vo.getContent());
    		ps.setString(4, vo.getPwd());
    		
    		// 5. ���� ��� 
    		ps.executeUpdate();
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		disConnection();
    	}
    }
    // 3. �󼼺���  ==> SELECT (WHERE)
    public BoardVO boardDetailData(int no)// no�� �Խù� ��ȣ => ����ڷκ��� �޾Ƽ� �����͸� �Ѱ��ش� (Primary Key)
    {
    	 BoardVO vo=new BoardVO();
    	 try
    	 {
    		 // 1. ���� 
    		 getConnection();
    		 // 2. ������ SQL���� ���� 
    		 String sql="UPDATE freeboard SET "
    				   +"hit=hit+1 "
    				   +"WHERE no="+no;
    		 // 3. ���� 
    		 ps=conn.prepareStatement(sql);
    		 // 4. ���� 
    		 ps.executeUpdate(); // ���� => commit()
    		 
    		 // 5. ������ SQL������ ���� 
    		 sql="SELECT no,name,subject,content,regdate,hit "
    			+"FROM freeboard "
    			+"WHERE no="+no;
    		 // 6. ����Ŭ�� ����
    		 ps=conn.prepareStatement(sql);
    		 // 7. ������ ����� �б�
    		 ResultSet rs=ps.executeQuery();
    		 // 8. Ŀ���� ��ġ �̵� 
    		 rs.next();
    		 // 9. VO�� ���� ä��� 
    		 vo.setNo(rs.getInt(1));
    		 vo.setName(rs.getString(2));
    		 vo.setSubject(rs.getString(3));
    		 vo.setContent(rs.getString(4));
    		 vo.setRegdate(rs.getDate(5));
    		 vo.setHit(rs.getInt(6));
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
    //------------------------------------
    // 4. ����  ==> UPDATE
    // 5. ����  ==> DELETE
    public void boardDelete(int no)
    {
    	try
    	{
    		// 1. ����
    		getConnection();
    		// 2. SQL ����
    		String sql="DELETE FROM freeboard "
    				+"WHERE no="+no;
    		// 3. SQL ���� ����
    		ps=conn.prepareStatement(sql);
    		// 4. ���� ��û
    		ps.executeUpdate(); //commit()�� ���ԵǾ� �ִ�.
    		
    	}catch(Exception ex) 
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		disConnection();
    	}
    }
    //----------------------------------- ���� ���� Ȯ�� (��й�ȣ ��)
    // 6. �˻�  ==> SELECT (LIKE)
}