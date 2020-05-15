<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="org.springframework.samples.model.PerformerType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title>참가 신청 접수</title>
</head>
<body>

${joinForm.name}님의 공연 신청을 접수했습니다.<br>
<br>

접수 내용 : <br />

<ul>
	<li><label>ID</label>: ${joinForm.email} </li>
	<li><label>이름</label>: ${joinForm.name} </li>
	<li><label>전화번호</label>: ${joinForm.phoneNumber} </li>
	<li><label>주소</label>: ${joinForm.address.street} ${joinForm.address.city} (우편번호: ${joinForm.address.zipcode}) </li>
	<li><label>공연 종류</label>: ${joinForm.type} </li>
	<li><label>곡명</label>: ${joinForm.title} </li>
	<li><label>공연 시간</label>: ${joinForm.time} </li>
	<li><label>희망 공연 요일</label>: ${joinForm.day} </li>
	<li><label>첫 공연 여부</label>: ${joinForm.first} </li>	
</ul>

접수 일시 : ${currentTime}

<p><a href="<c:url value='/index' />">Go to schedule</a></p>

</body>
</html>