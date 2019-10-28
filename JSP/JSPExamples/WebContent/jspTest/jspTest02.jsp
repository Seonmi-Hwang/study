<%@ page contentType="text/html;charset=euc-kr" %>
<html>
<head>
    <title> HTML 문서 제목 </title>
</head>
<body>
Univ. :
<%! String univ= "Dong-duk Women's Univ."; %>
<%
    String name ="Dongduk";
    out.print(univ);
%>
<b><br>name : <%= name%></b>
</body>
</html>
