<%@ page contentType="text/html;charset=euc-kr" %>
<%
     String info = request.getParameter("direction");
     if (info.equals("home"))
          response.sendRedirect("http://www.dongduk.ac.kr");
     else if (info.equals("jsp"))
          response.sendRedirect("jspTest11.jsp");
%>
