<%@ page contentType="text/html;charset=EUC-KR"%>
<%
    String name = request.getParameter("name"); // ����ڰ� browser���� �Է��� ��
	String name2 = (String)request.getAttribute("name2");    // jspTest19���� ������ ��. type ��ȯ �ʿ�
%>
include ActionTag�� Top�Դϴ�.<p>
name: <%=name%> (����� request parameter) <br>
Dept. of Computer Science<br>
<br>
name2: <%=name2%> (jspTest19���� �����ǰ� ���޵� ������) <br><br>

<hr>
