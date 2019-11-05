<%@ page contentType="text/html;charset=EUC-KR"%>
<%@ page import="dbp.login.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   User user = (User)request.getAttribute("userInfo");
%>
로그인 성공!
<table>
  <tr><td>ID</td><td><%=request.getParameter("id")%></td></tr> 
  <tr><td>Password</td><td><%=request.getParameter("password")%></td></tr> 
  <tr><td>이름</td><td><%=user.getName()%></td></tr>    <!-- User 객체의 각 필드를 읽어 출력 -->
  <tr><td>나이</td><td>${userInfo.age}</td></tr> <!-- EL(Expression Language, 표현언어) 사용 -->
  <tr><td>전화번호</td><td>${userInfo.phone}</td></tr>
</table>
