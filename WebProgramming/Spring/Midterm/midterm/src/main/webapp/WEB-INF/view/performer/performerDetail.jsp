<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원 상세 정보</title>
</head>
<body>
<ul>
	<li>ID: ${performer.id}</li>
	<li>이름: ${performer.name}</li>
	<li>주소: ${performer.address.street} ${performer.address.city} (우편번호: ${performer.address.zipcode})</li>
</ul>

<p><a href="<c:url value='/index' />">Go to index</a></p>
</body>
</html>