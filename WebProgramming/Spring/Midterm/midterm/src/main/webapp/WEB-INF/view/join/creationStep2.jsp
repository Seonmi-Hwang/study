<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="org.springframework.samples.model.PerformerType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>공연 참가 신청</title>
</head>
<body>
	<h2>공연 참가 신청 - Step2</h2>
	<form:form modelAttribute="joinForm" action="step3">
		<label for="type">공연 종류</label>: 
		<form:select path="type" >
			<form:options items="<%= PerformerType.values() %>"/>
		</form:select>
		<form:errors path="type"/>
		<br />

		<label for="title">곡명</label>: 
		<form:input path="title" />
		<form:errors path="title" />
		<br />

		<label for="time" >공연 시간</label>: 
		<form:input path="time" style="width : 40px;"/>
		<form:errors path="time" />
		<br />

		<label for="first"> 
		첫 공연?
		<form:checkbox path="first" />
		</label>
		<br /> <br />

		<a href="step1">[이전 단계로]</a>
		<input type="submit" value="다음 단계로" />

	</form:form>
</body>
</html>