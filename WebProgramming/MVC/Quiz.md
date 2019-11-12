## 사용자 관리 예제의 개선 (참고자료 p.493)  
#### Q1. MVC 구조 사용자 관리 예제가 개선되기 전에는 각 Controller 클래스가 수행했던 일을 설명하고, 개선된 후에 어떻게 바뀌었는지 설명하시오.  
(개인적인 궁금함인데 이렇게 개선해서 좋은 점이 무엇일까..? 이것도 추측해보자!!)  

#### Q2. MVC 구조 사용자 관리 예제에서 UserDAO를 이용하여 Datebase와 연동하고 business 객체를 호출하거나 직접 business logic을 수행하는 클래스는 무엇인가?  

#### Q3. Form Controller를 개선하기 위해서 입력 form 생성을 위한 controller와 입력 데이터 처리 controller를 하나로 통합한다. 이 때 입력 form 요청 처리와 form 입력 값 전송 처리를 어떻게 해야할지 설명하시오.  

<hr>

#### A1.  
각 Controller 클래스는 Manager 클래스의 메소드를 호출하여 Model에게 처리를 위임했다.  
개선된 이후에는 Controller 내부에서 UserDAO 객체를 생성하고 직접 호출하여 처리를 수행한다.  

#### A2.  
UserManager  

#### A3.  
두 request가 같은 URI를 공유하되 서로 다른 HTTP Method를 이용한다.  
입력 Form 요청 request는 GET method를 이용하도록 정의하고, Form 입력 값 전송 request는 POST method로 정의한다.  
