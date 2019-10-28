<%@ page contentType="text/html;charset=euc-kr" %>
<html>
<head><title> Redirected Page </title> 
<script>
function back()
{ 
	history.go(-1);
} 
</script>
</head>
<body>
이 페이지는 jspTest10.jsp 로부터 리다이렉트된 페이지입니다.
<input type="button" value="돌아가기" onClick="javascript:back()">
</body>
</html>
