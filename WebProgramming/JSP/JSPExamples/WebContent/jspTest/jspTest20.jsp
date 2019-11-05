<%@ page contentType="text/html;charset=EUC-KR"%>
<%
    String name = request.getParameter("name"); // 사용자가 browser에서 입력한 값
	String name2 = (String)request.getAttribute("name2");    // jspTest19에서 저장한 값. type 변환 필요
%>
include ActionTag의 Top입니다.<p>
name: <%=name%> (사용자 request parameter) <br>
Dept. of Computer Science<br>
<br>
name2: <%=name2%> (jspTest19에서 생성되고 전달된 데이터) <br><br>

<hr>
