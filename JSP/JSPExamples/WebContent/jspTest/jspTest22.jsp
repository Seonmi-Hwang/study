<%@ page contentType="text/html;charset=EUC-KR"%>
<%
   String id = request.getParameter("id");
   String password = request.getParameter("password");
   String name = (String)request.getAttribute("name");    // type ��ȯ �ʿ�
%>
����� ���̵�� <b><%=id%></b>�̰�<br>
�н������ <b><%=password%></b>�Դϴ�. (����� request parameters) <br>
�̸��� <b><%=name%></b> (jspTest21���� �����ǰ� ���޵� ������)
