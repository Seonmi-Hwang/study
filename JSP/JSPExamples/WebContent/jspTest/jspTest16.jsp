<h1>Directive Example</h1>
<%@ page contentType="text/html;charset=EUC-KR"%>
<%
	String name = "include";
%>
<html>
<body>

<%@ include file="jspTest17.jsp"%>

<B>include</B> �������� Body �κ��Դϴ�.

<%@ include file="jspTest18.jsp"%>

</body>
</html>