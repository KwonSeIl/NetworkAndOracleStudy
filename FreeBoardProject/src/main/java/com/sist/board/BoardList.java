package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//////////////////////////// ����Ŭ ���� => ������ �б�
		BoardDAO dao=new BoardDAO();
		ArrayList<BoardVO> list=dao.boardListData();
		//////////////////////////////////////////////////
		
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
		 out.println("<table class=table>");
		 out.println("<tr>");
		 out.println("<td>");
		 out.println("<a href=\"BoardInsert\" class=\"btn btn-sm btn-primary\">����</a>");
		 out.println("</td>");
		 out.println("</tr>");
		 out.println("</table>");
		 out.println("<table class=table>");
		 out.println("<tr class=success>");
		 out.println("<th class=text-center width=10%>��ȣ</th>");
		 out.println("<th class=text-center width=45%>����</th>");
		 out.println("<th class=text-center width=15%>�̸�</th>");
		 out.println("<th class=text-center width=20%>�ۼ���</th>");
		 out.println("<th class=text-center width=10%>��ȸ��</th>");
		 out.println("</tr>");
		 ////////////////////////////////////// Data ��� ��ġ
		 for(BoardVO vo:list)
		 {
			 
			 out.println("<tr>");
			 out.println("<td class=text-center width=10%>"+vo.getNo()+"</td>");
			 out.println("<td width=45%><a href=BoardDetail?no="+vo.getNo()+">"+vo.getSubject()+"</a></td>");
			 out.println("<td class=text-center width=15%>"+vo.getName()+"</td>");
			 out.println("<td class=text-center width=20%>"+vo.getDbday()+"</td>");
			 out.println("<td class=text-center width=10%>"+vo.getHit()+"</td>");
			 out.println("</tr>");
			 
		 }
		 //////////////////////////////////////
		 out.println("</table>");
		 out.println("</div>");
		 out.println("</div>");
		 out.println("</body>");
		 out.println("</html>");
		 
		 
	}

}