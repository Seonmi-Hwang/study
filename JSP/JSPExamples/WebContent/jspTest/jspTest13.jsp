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
������ ��¥�� �ð���?<p> 
<%=date%>
