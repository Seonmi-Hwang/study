<h1>Directive Example</h1>
<%@ page contentType="text/html;charset=EUC-KR"
         import="java.util.*"
		 session="true"
		 buffer="16kb"
		 autoFlush="true"
		 isThreadSafe="true"
%>
<%
    Date date = new Date();
%>
현재의 날짜와 시간은?<p> 
<%=date%>
