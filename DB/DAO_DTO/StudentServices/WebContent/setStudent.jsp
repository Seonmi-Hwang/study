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
//	StudentDTO stu = new StudentDTO(); // student의 정보를 하나로 묶는 작업 -> useBean(javaBean) 사용으로 대체 -> 앞으로는 안쓸거임
//	stu.setStuNo(request.getParameter("stuNo"));
//	stu.setStuName(request.getParameter("stuName"));
//	stu.setPwd(request.getParameter("pwd"));
//	stu.setStuPhoneNo(request.getParameter("stuPhoneNo"));
//	stu.setYear(request.getParameter("year"));
//	stu.setProfName(request.getParameter("profName"));
//	stu.setDept(request.getParameter("dept"));

	// (MVC에서) controller 라는 곳으로 가게 될 것
	int result = studentService.insertStudent(stu);
	System.out.println(result + "개의 학생정보가 추가되었습니다.");
	response.sendRedirect("index.jsp");
%>

</body>
</html>