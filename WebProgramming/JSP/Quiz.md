## ◼️ Chapter 10: JSP Standard Tag Library(JSTL)   


#### Q1. JSTL의 Core 기능을 서술하고 prefix를 적으시오.

#### Q2. Expression Language(EL, 표현언어)가 ${expression} 형식을 사용하여 접근 가능한 세 가지를 서술하시오. (Servlet에서 구현된 것들을 JSP에서 EL로 가져올 수 있다.)

#### Q3. request parameter를 접근할 수 있는 EL 내장객체는 무엇이며 어떻게 사용하는지 서술하시오. (request parameter의 이름은 name이라고 가정하자.)

#### Q4. messages라는 array가 존재한다고 가정하자. array를 전부 출력하려고 할 때 아래 두 가지 방법으로 문제를 해결하시오.
* **4-1.** JSP에서 JSTL을 쓰지 않고 출력하는 방법
* **4-2.** JSP에서 JSTL을 사용하여 출력하는 방법

<hr>

A1. 
변수 설정 및 제거, 흐름제어, URL 사용 등 / c   

A2.  
- JavaBeans의 properties  
- Map, List, Array의 원소들  
- Servlet 내장객체의 속성  

A3.  
param, paramValues  
${param.name}  
${paramValue.name[1]}  

A4.   
1. JSP에서 JSTL을 쓰지 않고 출력하는 방법   
```jsp
<%
	for (int i = 0; i < messages.length; i++) {
		String message = messages[i];
%>
	<%= message %>
<% } %>
```

2. JSP에서 JSTL을 사용하여 출력하는 방법  
```jsp
<c:forEach var="message" items="${messages}">
	${message}
</c:forEach>
```
