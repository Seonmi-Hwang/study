<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원 목록</title>
</head>
<body>
<ul>
	<c:forEach var="mi" items="${members}">
		<li><a href="<c:url value='/members/' />${mi.id}">${mi.name}</a></li>
	</c:forEach>
</ul>

<p><a href="<c:url value='/index' />">Go to index</a></p>
</body>
</html>