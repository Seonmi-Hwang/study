<%@ page contentType="text/html;charset=EUC-KR"%>
<%
   	request.setCharacterEncoding("euc-kr");
	String name = "Dongduk";
%>
<html>
 <body>
 <h1>Forward Tag Example</h1>
   Forward Tag�� ������ �Ǳ� ���� ������ �Դϴ�.
<%
	request.setAttribute("name", name);  // name2�� request��ü�� ����
%> 
   <jsp:forward page="jspTest22.jsp" />
 </body>
</html>
