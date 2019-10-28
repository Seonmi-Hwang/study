<%@ page contentType="text/html; charset=euc-kr" %>
<h1>If-else Example</h1>
<%!   String msg;    %>
<%
   request.setCharacterEncoding("euc-kr");
   String name = request.getParameter("name");
   String color = request.getParameter("color");
  
   if (color.equals("blue")) {  msg = "파란색";   }     
   else if (color.equals("red")) {  msg = "붉은색"; }
   else if (color.equals("orange")){  msg = "오렌지색"; }
   else {  color = "white";  msg = "기타색";  }
%>
<body bgcolor=<%=color%>>
<%=name%>님이 좋아하는 색깔은 <%=msg%>입니다.
</body>