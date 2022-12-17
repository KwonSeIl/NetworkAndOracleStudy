package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		 out.println("<html>");
		 out.println("<head>");
		 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		 out.println("<style>");
		 out.println(".container{margin-top:50px}");
		 out.println(".row{margin:0px auto;width:900px}");
		 out.println("h1{text-align:center}");
		 out.println("</style>");
		 out.println("</head>");
		 out.println("<body>");
		 out.println("<div class=container>");
		 out.println("<h1>�����Խ���</h1>");
		 out.println("<div class=row>");
		 out.println("<form method=post action=BoardInsert>");
		 out.println("<table class=table>");
		 out.println("<tr>");
		 out.println("<th align=right width=15%>�̸�</th>");
		 out.println("<td width=85%><input type=text name=name size=15></td>");
		 out.println("</tr>");
		 out.println("<tr>");
		 out.println("<th align=right width=15%>����</th>");
		 out.println("<td width=85%><input type=text name=subject size=45></td>");
		 out.println("</tr>");
		 out.println("<tr>");
		 out.println("<th align=right width=15%>����</th>");
		 out.println("<td width=85%><textarea rows=10 cols=50 name=content></textarea></td>");
		 out.println("</tr>");
		 out.println("<tr>");
		 out.println("<th align=right width=15%>��й�ȣ</th>");
		 out.println("<td width=85%><input type=password name=pwd size=10></textarea></td>");
		 out.println("</tr>");
		 out.println("<tr>");
		 out.println("<td colspan=2 align=center>");
		 out.println("<input type=submit value=�۾���>");
		 out.println("<input type=button value=��� onclick=\"javascript:history.back()\">");
		 out.println("</td>");
		 out.println("</tr>");
		 out.println("</table>");
		 out.println("</form>");
		 out.println("</div>");
		 out.println("</body>");
		 out.println("</html>");
	}
	//ó��
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // request :������� ��û �����͸� �޴´� 
		 // response : ��û ó���Ŀ� ����ڿ��� HTML�� ���� 
		 // �ѱ� ��ȯ 
		 try
		 {
			  // ����ڰ� ������ �����͸� ������ �ִ� Ŭ���� 
			 request.setCharacterEncoding("UTF-8"); // ���ڵ� 
			 // �ڹ�   =====>  ������  =======> �ڹ�
			 //       ���ڵ�            ���ڵ�
			 //       byte[]           String  ==> ASC
		 }catch(Exception ex) {}
		 
		 String name=request.getParameter("name");
		 String subject=request.getParameter("subject");
		 String content=request.getParameter("content");
		 String pwd=request.getParameter("pwd");
		 
		 /*System.out.println("�̸�:"+name);
		 System.out.println("����:"+subject);
		 System.out.println("����:"+content);
		 System.out.println("���:"+pwd);*/
		 BoardVO vo=new BoardVO();
		 vo.setName(name);
		 vo.setSubject(subject);
		 vo.setContent(content);
		 vo.setPwd(pwd);
		 
		 // DAO�� ���� ==> DAO���� ����Ŭ ���� 
		 BoardDAO dao=new BoardDAO();
		 dao.boardInsert(vo);
		 
		 // �̵� => BoardList
		 response.sendRedirect("BoardList");
	}
	

}
