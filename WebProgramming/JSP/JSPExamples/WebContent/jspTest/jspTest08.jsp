<%@ page contentType="text/html; charset=euc-kr" %>
<h1>For Example</h1>
1부터 10까지의 합은?<p>
<%
    int i,sum = 0;
    for(i=1;i<=10;i++){
       if(i<10){
%>
    <%=(i + " +")%>
<% }
       else {
           out.println(i + " = ");
       }
     sum += i; 
    }     	
%>	    
<%=sum%>
