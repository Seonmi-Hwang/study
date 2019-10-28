<%@ page contentType="text/html;charset=EUC-KR"%>
<%
   String id = request.getParameter("id");
   String password = request.getParameter("password");
   String name = (String)request.getAttribute("name");    // type 변환 필요
%>
당신의 아이디는 <b><%=id%></b>이고<br>
패스워드는 <b><%=password%></b>입니다. (사용자 request parameters) <br>
이름은 <b><%=name%></b> (jspTest21에서 생성되고 전달된 데이터)
