<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="org.springframework.samples.model.PerformerType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title>공연 참가 신청</title>
</head>
<body>
<h2>공연 참가 신청 - Step3</h2>

다음 정보로 신청하시겠습니까?<br /><br />

<form:form modelAttribute="joinForm" action="done">

<label>ID</label>: ${joinForm.email} <br>
<label>이름</label>: ${joinForm.name} <br>
<label>전화번호</label>: ${joinForm.phoneNumber} <br>
<label>주소</label>: ${joinForm.address.street} ${joinForm.address.city} (우편번호: ${joinForm.address.zipcode}) <br>
<label>공연 종류</label>: ${joinForm.type} <br>
<label>곡명</label>: ${joinForm.title} <br>
<label>공연 시간</label>: ${joinForm.time} <br>
<label>희망 공연 요일</label>: ${joinForm.day} <br>
<label>첫 공연 여부</label>: ${joinForm.first} <br>
<br>

<a href="step2">[이전 단계로]</a>
<input type="submit" value="확인" />

</form:form>
</body>
</html>