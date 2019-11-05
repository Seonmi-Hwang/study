<%@ page contentType="text/html; charset=euc-kr" %>
<h1>Expression Example1</h1>
<%!
    String name[] = {"Sun","Java","DBP"};
%>
<table border=1 style="width:150">
    <% for (int i=0;i<name.length;i++){%>
    <tr><td><%=i%></td>
    <td><%=name[i]%></td>
    </tr>   <%}%>
</table>
