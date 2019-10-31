# HTTP, Servlet & JSP  

### ⬛️ HTTP  
* **HTTP (HyperText Transfer Protocol)**  
\- 웹 메시지 전송을 위한 응용 계층 프로토콜  

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

* **JSP Request 처리 과정**  
  ① 웹 브라우저에서 JSP 파일 요청 (HTTP request message)  
  ② Web Server에서 Web Container에게 사용자 요구 처리 요청  
  ③ Web Container에서 아래 일들이 이루어진다.  
&nbsp;&nbsp;&nbsp;&nbsp;3-1. JSP 파일 parsing  
&nbsp;&nbsp;&nbsp;&nbsp;3-2. Servlet으로 변환  
&nbsp;&nbsp;&nbsp;&nbsp;3-3. class 파일 생성  
&nbsp;&nbsp;&nbsp;&nbsp;3-4. 메모리에 적재 후 실행  
  ④ Web Server에게 처리결과(HTML)를 돌려준다.  
  ⑤ 처리 결과 전송 (HTTP response message)  
  ⑥ 화면 출력  
  
### ⬛️ Script   
* **Script : 선언부(Declaration)**   
   JSP page 안에서 사용할 변수나 메소드를 선언하는 문장  
    ▪ ``` <%! 변수 선언 또는 메소드 선언 %> ```  
  \- JSP내의 메소드 선언은 가급적 피하고 별도의 Java class나 JavaBeans를 사용  
  \- 선언부 내에서는 JSP 내장 객체(default object) 참조 불가  
  
* **Script : Scriptlet**  
   JSP page 내에 Java code를 기술하는 부분  
    ▪ ``` <% ..Java codes.. %> ```  
  \- JSP page 작성 시 가장 많이 쓰임  
  \- JSP page가 Servlet으로 변환될 때 _jspService() 메소드 안에 포함됨  
	\-> 변수 선언 시 local 변수가 되므로 자동으로 초기화 X  
  \- 복잡한 로직을 포함하는 scriptlet은 개발 및 유지보수 어려움  
	=> 표현언어(EL), JSTL, Custom Tag, JavaBeans의 사용 권장  
  
* **Script : 표현식(Expression)**    
   JSP code에서 생성한 값을 출력  
    ▪ ``` <%= 변수명, 산술식, 또는 메소드 호출 %> ```  
  \- JSP code 내의 변수 값 또는 메소드 호출 결과 값을 출력할 때 사용   
  \- 세미콜론(;) 생략, out.print() 메소드 호출과 동일한 효과  
