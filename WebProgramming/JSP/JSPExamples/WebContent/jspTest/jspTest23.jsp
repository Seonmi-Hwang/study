<%@ page contentType="text/html; charset=euc-kr" %>
<jsp:useBean id="test" class="dbp.beans.SimpleBean" scope="page" />
<jsp:setProperty name="test" property="message" value="JavaBeans!!!" />

<html>
<body>
<h1>������ �� ���α׷���</h1>
<br>
Message: <jsp:getProperty name="test" property="message" />
</body>
</html>
