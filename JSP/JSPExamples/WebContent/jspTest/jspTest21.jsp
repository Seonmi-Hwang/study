<%@ page contentType="text/html;charset=EUC-KR"%>
<%
   	request.setCharacterEncoding("euc-kr");
	String name = "Dongduk";
%>
<html>
 <body>
 <h1>Forward Tag Example</h1>
   Forward Tag의 포워딩 되기 전의 페이지 입니다.
<%
	request.setAttribute("name", name);  // name2를 request객체에 저장
%> 
   <jsp:forward page="jspTest22.jsp" />
 </body>
</html>
