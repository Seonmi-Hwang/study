<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원 정보 수정</title>
</head>
<body>
	<form:form modelAttribute="modReq">
		<form:input type="hidden" path="id" />
		<label for="email">이메일</label>: 
		<form:input type="text" path="email" />
		<form:errors path="email" />
		<br />

		<label for="name">이름</label>: 
		<form:input type="text" path="name" />
		<form:errors path="name" />
		<br />

		<label for="address1">주소1</label>: 
		<form:input type="text" path="address.address1" />
		<form:errors path="address.address1" />
		<br />
		<label for="address2">주소2</label>:
		<form:input type="text" path="address.address2" />
		<form:errors path="address.address2" />
		<br />
		<label for="zipcode">우편번호</label>:
		<form:input type="text" path="address.zipcode" />
		<br />

		<label> <form:checkbox path="allowNoti" />이메일을 수신합니다.
		</label>
		<br />
		
		<label for="currentPassword">현재 암호</label>: 
		<form:password path="currentPassword" />
		<form:errors path="currentPassword" />
		<br />
		
		<input type="submit" value="수정" />
	</form:form>
</body>
</html>