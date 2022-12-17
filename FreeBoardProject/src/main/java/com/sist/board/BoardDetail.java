package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
@WebServlet("/BoardDetail")
public class BoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
    // ������ ���ؼ� �ڵ����� ȣ��Ǵ� �޼ҵ� 
	// �����  ��û�� ������ ���� ȣ�� 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. ����ڰ� ������ �����͸� �޴´� ?no=10
		String no=request.getParameter("no");
		// 2. ��ȯ �ڵ� => �ڹٸ� �����ϰ� � ������ �������� �������� ���� ����
		// ���������� ������ �� �ִ� ���: html: text/html,xml: text/xml,json: text/plain
		response.setContentType("text/html;charset=UTF-8"); //HTML �ȿ� �ѱ��� ����
		// 3. ����� ���������� �о �� �ְ� �޸� ��ġ ����
		PrintWriter out=response.getWriter();
		// 4. ����Ŭ���� �����͸� ������ �´�
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.boardDetailData(Integer.parseInt(no));
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
		out.println("<th width=20% class=text-center>��ȣ</th>");
		out.println("<td width=30% class=text-center>"+vo.getNo()+"</td>");
		out.println("<th width=20% class=text-center>�ۼ���</th>");
		out.println("<td width=30% class=text-center>"+vo.getRegdate()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=20% class=text-center>�̸�</th>");
		out.println("<td width=30% class=text-center>"+vo.getName()+"</td>");
		out.println("<th width=20% class=text-center>��ȸ��</th>");
		out.println("<td width=30% class=text-center>"+vo.getHit()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=20% class=text-center>����</th>");
		out.println("<td colspan=3 class=text-center>"+vo.getSubject()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=4 class=text-left valign=top height=200><pre>"+vo.getContent()+"</pre></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=4 class=text-right>");
		out.println("<a href=# class=\"btn btn-xs btn-danger\">����</a>");
		out.println("<a href=BoardDelete?no="+vo.getNo()+" class=\"btn btn-xs btn-warning\">����</a>");
		out.println("<a href=BoardList class=\"btn btn-xs btn-info\">���</a>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
