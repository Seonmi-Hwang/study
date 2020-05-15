<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>공연 일정</title>
    </head>
    <style>
    table {
    	border-collapse: collapse;
    	text-align : center;
    	table-layout : auto;
    }
    
    tr, td {
        padding-left : 15px;
    	padding-right : 15px;
    }
    
    </style>
    <body>
    	<p>공연 일정:</p>
		
		<table border="1">
			<tr>
				<td><b>순번</b></td>
				<td><b>ID</b></td>
				<td><b>이름</b></td>
				<td><b>종류</b></td>
				<td><b>곡명</b></td>
				<td><b>공연시간</b></td>
			</tr>
			<c:forEach var="performer" items="${performers}" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="<c:url value="/performer/detail">
									<c:param name="email" value="${performer.email}" />
								</c:url>">${performer.email}</a></td>
					<td>${performer.name}</td>
					<td>${performer.type}</td>
					<td>${performer.title}</td>
					<td>${performer.time}</td>
					<td><a href="<c:url value="/performer/delete">
									<c:param name="email" value="${performer.email}" />
								</c:url>">삭제</a></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		
	<!-- 현재 세션 : <%= session.getAttribute("login") %> <br> -->
		
   		<a href="<c:url value="/newJoin/step1" />">참가 신청</a>
	
	</body>
</html>
