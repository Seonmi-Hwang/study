<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원 상세 정보</title>
</head>
<body>
<ul>
	<li>ID: ${performer.email}</li>
	<li>이름: ${performer.name}</li>
	<li>전화번호 : ${performer.phoneNumber}</li>
	<li>주소: ${performer.address.street} ${performer.address.city} (우편번호: ${performer.address.zipcode})</li>
	<li>공연 종류 : ${performer.type}</li>
	<li>곡명 : ${performer.title}</li>
	<li>공연 시간 : ${performer.time}</li>
	<li>첫 공연 여부 : ${performer.first}</li>
</ul>

<p><a href="<c:url value='/index' />">Go to schedule</a></p>
</body>
</html>