# Servlet and JSP  

### ⬛️ GET & POST  
* **GET**  
\- 가장 일반적인 reques method  
**\- Message body나 header가 필요없음**  
\- Parameters(data) passing  
&nbsp;&nbsp;&nbsp;&nbsp; HTML form으로부터 입력된 값  
&nbsp;&nbsp;&nbsp;&nbsp; URL내에 query string(name/value pairs)으로 포함되어 전달됨  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => **URL 상에 노출됨**  
&nbsp;&nbsp;&nbsp;&nbsp; ASCII 문자열만 가능, 길이 제한 O    

* **POST**  
\- Message body를 가짐  
\- Parameters(data) passing  
&nbsp;&nbsp;&nbsp;&nbsp; HTML form으로부터 입력된 값  
&nbsp;&nbsp;&nbsp;&nbsp; **Message body에 포함**되어 전달  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => **URL 상에 노출되지 않음**  
&nbsp;&nbsp;&nbsp;&nbsp; Binary data 가능, 길이 제한 X  
&nbsp;&nbsp;&nbsp;&nbsp; **대량의 중요 데이터 전송이나 file upload에 적합**

### ⬛️ Status Codes  
* **Status Codes & Messages**  
\- Response message의 status line에 포함  
&nbsp;&nbsp;&nbsp;&nbsp; 1xx : informational  
&nbsp;&nbsp;&nbsp;&nbsp; **2xx : sucessful response**  
&nbsp;&nbsp;&nbsp;&nbsp; 3xx : redirection 실행  
&nbsp;&nbsp;&nbsp;&nbsp; **4xx : client request errors**  
&nbsp;&nbsp;&nbsp;&nbsp; **5xx : server errors**  

### ⬛️ Web Programming  
* **Servlet**  
\- **JAVA 언어 기반**의 **웹 프로그래밍 기술**   
&nbsp;&nbsp;&nbsp;&nbsp; 모든 JAVA API 사용 가능  
&nbsp;&nbsp;&nbsp;&nbsp; 이식성 및 확장가능성 높음  
\- **Multi-thread**로 실행되어 빠른 처리 속도  
\- 웹 애플리케이션을 Servlet으로만 작성 시 Java 프로그램 내에서 화면 출력 생성 코드를 포함해야 함.  
**\- MVC design pattern에서 _Controller_ 구현을 위해 사용됨**  

* **JSP(JavaServer Pages)**  
\- **Servlet 기반**의 server-side script 언어  
\- Java web application에서 **presentation layer**의 핵심 구현 기술로 사용  
\- JSP page는 최초 요청 처리 시 servlet으로 변환되고 메모리에 load됨  
\- **HTML page 내에서 Java 언어를 사용하여 프로그램 작성 가능**   
\- **Expression Language(EL), JSP Standard Tag Library(JSTL)** 등 다양한 확장 기술 이용 가능  
**\- MVC design pattern에서 _View_ 구현을 위해 사용됨**  

