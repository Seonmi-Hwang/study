<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="service.*" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�л� ���� ����</title>
</head>

<body>
<%
	StudentService studentService = new StudentServiceImpl();
	int result = studentService.deleteStudent(stu.getStuNo());
	System.out.println(result + "���� �л������� �����Ǿ����ϴ�.");
	response.sendRedirect("index.jsp");
%>
</body>
</html>