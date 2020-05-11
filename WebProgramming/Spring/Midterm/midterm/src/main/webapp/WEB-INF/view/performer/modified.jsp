<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>회원 정보 수정</title>
</head>
<body>
${modReq.name}님의 정보를 수정했습니다.
<ul>
	<li>ID: ${modReq.id}</li>
	<li>이름: ${modReq.name}</li>
	<li>주소: ${modReq.address.address1} ${modReq.address.address2} (우편번호: ${modReq.address.zipcode})
</ul>

<p><a href="<c:url value='/index' />">Go to index</a></p>
</body>
</html>