<h1>Directive Example</h1>
<%@ page contentType="text/html;charset=EUC-KR"%>
<%
	String name = "include";
%>
<html>
<body>

<%@ include file="jspTest17.jsp"%>

<B>include</B> 지시자의 Body 부분입니다.

<%@ include file="jspTest18.jsp"%>

</body>
</html>