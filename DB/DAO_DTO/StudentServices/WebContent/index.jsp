<!-- 제일 처음 화면에 출력될 것임. -->
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="service.*" %>
<%@ page import="service.dto.StudentDTO" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<body>
<%
	StudentService studentService = new StudentServiceImpl();
	List<StudentDTO> list = studentService.ListingStudents();
	if (list != null ) {
%>
학번&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성명<br>
<%
	for (Iterator<StudentDTO> it = list.iterator(); it.hasNext(); ) {
	StudentDTO dto = (StudentDTO)it.next();
	out.println(dto.getStuNo() + "&nbsp;&nbsp;&nbsp;" + dto.getStuName()+"<br>");
		}
	}
%>
<br>
<a href="setStudent.jsp?stuNo=20150003&stuName=Jain
						&pwd=1111&stuPhoneNo=010-3456-7890&year=3
		 				&profName=Andy&dept=Computer">setStudent</a><br>
<a href="getStudent.jsp?stuName=Jain">getStudent</a><br>
<a href="updateStudent.jsp?stuNo=20150003
						&year=1&stuPhoneNo=010-1111-2222">updateStudent</a><br>
<a href="deleteStudent.jsp?stuNo=20150003">deleteStudent</a><br>

</body>
</html>