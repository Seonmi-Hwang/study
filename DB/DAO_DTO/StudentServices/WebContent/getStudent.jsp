<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="service.*" %>
<%@ page import="service.dto.StudentDTO" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<body>
<%
	StudentService studentService = new StudentServiceImpl();
	StudentDTO student = studentService.getStudent(stu.getStuName());
	if (student != null ) {
		out.print("�й�: " + student.getStuNo()+ "<br>");
		out.print("�̸�: " + student.getStuName()+ "<br>");
		out.print("�г�: " + student.getYear()+ "<br>");
		out.print("�а�: " + student.getDept() + "<br>");
		out.print("��ȭ��ȣ: " + student.getStuPhoneNo()+ "<br>");
		out.print("��������: " + student.getProfName()+ "<br>");
	}
%>
<br>
<a href="index.jsp">Go back</a>
</body>
</html>