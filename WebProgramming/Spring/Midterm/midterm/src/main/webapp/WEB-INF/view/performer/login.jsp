<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
</head>
<body>
<form:form modelAttribute="performer" action="login">
<br>

<c:if test="${!empty message}">
  <font><c:url value="${message}" /></font>
</c:if>
<br><br>

<label for="email">아이디</label>: 
<form:input path="email" value="${performer.email}"/>
<br>

<label for="password">암호</label>: 
<form:password path="password" />
<form:errors path="password" />
<br>

<input type="submit" value="로그인" />
</form:form>
</body>
</html>