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

${performerForm.name}님의 공연 신청을 접수했습니다.<br>
<br>

접수 내용 : <br />

<ul>
	<li><label>ID</label>: ${performerForm.email} </li>
	<li><label>이름</label>: ${performerForm.name} </li>
	<li><label>전화번호</label>: ${performerForm.phoneNumber} </li>
	<li><label>주소</label>: ${performerForm.address.street} ${performerForm.address.city} (우편번호: ${performerForm.address.zipcode}) </li>
	<li><label>공연 종류</label>: ${performerForm.type} </li>
	<li><label>곡명</label>: ${performerForm.title} </li>
	<li><label>공연 시간</label>: ${performerForm.time} </li>
	<li><label>희망 공연 요일</label>: ${performerForm.day} </li>
	<li><label>첫 공연 여부</label>: ${performerForm.first} </li>	
</ul>

접수 일시 : ${currentTime}

<p><a href="<c:url value='/index' />">Go to schedule</a></p>

</body>
</html>