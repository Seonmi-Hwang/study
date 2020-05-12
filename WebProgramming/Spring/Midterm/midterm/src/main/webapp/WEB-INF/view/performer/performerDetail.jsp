<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원 상세 정보</title>
</head>
<body>
<ul>
	<li>ID: ${login.email}</li>
	<li>이름: ${login.name}</li>
	<li>전화번호 : ${login.phoneNumber}</li>
	<li>주소: ${login.address.street} ${login.address.city} (우편번호: ${login.address.zipcode})</li>
	<li>공연 종류 : ${login.type}</li>
	<li>곡명 : ${login.title}</li>
	<li>공연 시간 : ${login.time}</li>
	<li>첫 공연 여부 : ${login.first}</li>
</ul>

<p><a href="<c:url value='/index' />">Go to schedule</a></p>
</body>
</html>