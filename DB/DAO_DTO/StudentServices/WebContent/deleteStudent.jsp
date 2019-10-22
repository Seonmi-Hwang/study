<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="service.*" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>학생 정보 삭제</title>
</head>

<body>
<%
	StudentService studentService = new StudentServiceImpl();
	int result = studentService.deleteStudent(stu.getStuNo());
	System.out.println(result + "개의 학생정보가 삭제되었습니다.");
	response.sendRedirect("index.jsp");
%>
</body>
</html>