<%@ page contentType="text/html;charset=EUC-KR"%>
<%
   request.setCharacterEncoding("euc-kr");
   String name = "Dong-duk"; // local ����
   String name2 = "Dongduk2"; // local ����
%>
<html>
<body>
<h1>Include Tag Example</h1>

<% request.setAttribute("name2", name2); %>

<jsp:include page= "jspTest20.jsp"/>

include ActionTag�� Body�Դϴ�.
</body>
</html>