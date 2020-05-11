<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>공연</title>
    </head>
    <style>
    table {
    	font-weight : bold;
    	border-collapse: collapse;
    	table-layout : auto;
    }
    td {
    	padding-left : 15px;
    	padding-right : 15px;
    }
    </style>
    <body>
    	<p>공연 일정:</p>
		
		<table border="1">
			<tr>
				<td>순번</td>
				<td>ID</td>
				<td>이름</td>
				<td>종류</td>
				<td>곡명</td>
				<td>공연시간</td>
			</tr>
			<c:forEach var="performer" items="${performers}">
				<tr>
					<td>${performer.id}</td>
					<td><a href="<c:url value="/join/detail" />">${performer.email}</a></td>
					<td>${performer.name}</td>
					<td>${performer.type}</td>
					<td>${performer.title}</td>
					<td>${performer.time}</td>
					<td><a href="<c:url value="/join/delete" />">삭제</a></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		
   		<a href="<c:url value="/newJoin/step1" />">참가 신청</a>
	
	</body>
</html>
