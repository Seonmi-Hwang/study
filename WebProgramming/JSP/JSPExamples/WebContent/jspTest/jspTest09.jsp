<%@ page contentType="text/html; charset=euc-kr" %>
<h1>While Example</h1>
<%
	request.setCharacterEncoding("euc-kr");
	String msg = request.getParameter("msg");
    int number = Integer.parseInt(request.getParameter("number"));
    int count = 0;
    while(number>count){
%>
<b>
    <%=msg%>
</b><br>
<%
    count++;
}
%>
