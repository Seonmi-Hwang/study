<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="service.*, service.dto.*" %>
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
//	StudentDTO stu = new StudentDTO(); // student�� ������ �ϳ��� ���� �۾� -> useBean(javaBean) ������� ��ü -> �����δ� �Ⱦ�����
//	stu.setStuNo(request.getParameter("stuNo"));
//	stu.setStuName(request.getParameter("stuName"));
//	stu.setPwd(request.getParameter("pwd"));
//	stu.setStuPhoneNo(request.getParameter("stuPhoneNo"));
//	stu.setYear(request.getParameter("year"));
//	stu.setProfName(request.getParameter("profName"));
//	stu.setDept(request.getParameter("dept"));

	// (MVC����) controller ��� ������ ���� �� ��
	int result = studentService.insertStudent(stu);
	System.out.println(result + "���� �л������� �߰��Ǿ����ϴ�.");
	response.sendRedirect("index.jsp");
%>

</body>
</html>