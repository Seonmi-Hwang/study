<%@ page contentType="text/html;charset=EUC-KR"%>
<%@ page import="dbp.login.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   User user = (User)request.getAttribute("userInfo");
%>
�α��� ����!
<table>
  <tr><td>ID</td><td><%=request.getParameter("id")%></td></tr> 
  <tr><td>Password</td><td><%=request.getParameter("password")%></td></tr> 
  <tr><td>�̸�</td><td><%=user.getName()%></td></tr>    <!-- User ��ü�� �� �ʵ带 �о� ��� -->
  <tr><td>����</td><td>${userInfo.age}</td></tr> <!-- EL(Expression Language, ǥ�����) ��� -->
  <tr><td>��ȭ��ȣ</td><td>${userInfo.phone}</td></tr>
</table>
