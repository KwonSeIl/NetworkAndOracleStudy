<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.util.*,com.sist.dao.*"%>
<%
	request.setCharacterEncoding("EUC-KR");
	String dong=request.getParameter("dong");
	ArrayList<ZipcodeVO> list=null;
	ZipcodeDAO dao=new ZipcodeDAO();
	int count=0;
	if(dong!=null) //사용자가 동을 입력했을 때
	{
		list=new ArrayList<ZipcodeVO>();
		list=dao.postFind(dong);
		count=dao.postCount(dong);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>우편번호 검색</h1>
		<table border=0 width=600>
			<tr>
				<td>
					<form method=post action=find.jsp>
					입력:<input type=text size=20 name=dong>
					<input type=submit value="검색">
					</form>
				</td>
			</tr>
		</table>
		<table border=1 bordercolor=black width=600>
			<tr>
				<th width=20%>우편번호</th>
				<th width=80%>주소</th>
			</tr>
			<tr>
				<td colspan="2" align="right">
					검색결과:<%=count %>건
				</td>
			<%
			if(list!=null)
			{
				for(ZipcodeVO vo:list)
				{
			%>
					<tr>
						<td width=20%><%=vo.getZipcode() %></td>
						<td width=80%><%=vo.getAddress() %></td>
			<% 		
				}
			}
			%>
		</table>
	</center>
</body>
</html>