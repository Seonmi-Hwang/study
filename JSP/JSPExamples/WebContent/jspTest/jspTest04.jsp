<%@ page contentType="text/html; charset=euc-kr" %>
<h1>Declaration Example2</h1>
<%!
    int one;
    int two = 1;
    public int plusMethod(){
        return one + two;
    }
    String msg;
    int three;
%>

one�� two�� ����? <%=plusMethod()%><p>
String msg�� ����? <%=msg%><p>
int three�� ����? <%=three%>
