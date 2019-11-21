# Transaction & DBCP(Database Connection Pool)  

### ⬛️ 트랜잭션(Transaction)   
* **데이터 처리를 위한 논리적인 작업단위**  
\- 여러 개의 DML 문들과 SQL 질의들로 구성  

* **All or Nothing 방식으로 처리됨**  
\- 오류가 없을 경우 실행이 완료(commit)되고 결과가 영속적으로 유지됨   
\- 오류 발생 시 전체 실행 결과가 취소됨  

* **ACID 속성**을 만족해야 함   
\- **Atomicity(원자성)**  
\- **Consistency(일관성)**  
\- **Isolation(고립성)**  
\- **Durability(영속성)**  

* 예: 은행에서 현금 인출, 계좌 이체 등의 작업  

### ⬛️ 트랜잭션 처리  
DDL문 : 자동 commit 및 rollback  
* **COMMIT**  
\- **트랜잭션을 정상적으로 완료**하고 **처리 결과를 영구히 저장**  
\- Commit 실행 후에는 트랜잭션에 의해 변경된 데이터를 **복구할 수 없음**
* **ROLLBACK**  
\- **트랜잭션에 의한 데이터의 변경을 취소** \-> 트랜잭션 **시작 전의 상태로 돌아감**  
트랜잭션에서 데이터 변경 시 이전의 값(before image)을 **rollback segment**에 임시 저장 
* SAVEPOINT <\- 많이 사용되진 않음    
\- 여러 개의 DML 문을 포함하는 트랜잭션에서 사용자가 **트랜잭션의 중간에 savepoint들을 지정**할 수 있음 
\- 트랜잭션 rollback 시 과거에 생성된 **특정 save-point까지만 rollback** 가능  

* **DELETE와 TRUNCATE TABLE의 차이**  
DELETE : DML | 자동 commit X | 속도 느림  
TRUNCATE TABLE : DDL | 자동 commit O | 속도 빠름  

### ⬛️ JDBC Transcation  
* **JDBC의 Transcation 처리 방식**  
\- 기본적으로 각 DML문이 하나의 트랜잭션으로 실행됨(auto-commit 모드 : 실행 후 자동 commit)  
\- 여러 DML문들로 구성된 트랜잭션을 정의하기 위해서는 auto-commit 모드 해제하고 필요한 시점에 명시적으로 commit/rollback을 실행해야 함  

* **Transaction 정의**  
\- java.sql.Connection interface 이용  
![image](https://user-images.githubusercontent.com/50273050/69060703-25298080-0a5b-11ea-8519-fb4fd6a95a8c.png)  

* **일괄 갱신(batch update)**  
\- 여러 개의 DML문을 한 번에 전송하여 실행함으로써 **성능 향상**  
=> **Network traffic 감소** (only 1 round trip)  
=> **DBMS에서 효율적으로 처리**(e.g. via parallel execution)  
\- **Statement 및 PreparedStatement 객체**에서 사용 가능  
\- 실행 후 각 DML문의 실행결과를 나타내는 **integer 배열을 반환**함  
```java
Connection conn; // 주의: Connection 객체 생성 및 초기화 필요
Statement stmt = conn.createStatement(); 
stmt.addBatch("INSERT INTO employees VALUES (1000, 'Joe Jones')"); 
stmt.addBatch("INSERT INTO departments VALUES (260, 'Shoe')"); 
stmt.addBatch("INSERT INTO emp_dept VALUES (1000, 260)"); 
int[] insertCounts = stmt.executeBatch(); // batch update 실행
```

### ⬛️ Database Connection Pool   
* **Connection Pool**  
\- 일정 개수의 DBMS connection들을 미리 생성하고 pool로 관리함으로써 시스템 성능을 향상시키기 위한 방법  
\- 애플리케이션은 필요할 때 connection pool에 connection을 요청, 사용 후에는 다시 connection pool에 반환  
\- Connection pool에서는 애플리케이션에서 재사용되도록 유휴 connection들을 삭제하지 않고 유지함 

* **사용 방법**  
1. Commons DBCP library를 직접 사용  
* 두 jar 파일들을 웹 애플리케이션에 직접 포함시킴  
\- WEB-INF/lib 폴더에 저장  
2. 애플리케이션 서버에서 제공하는 DBCP 기능 이용  
* Tomcat의 경우 내부적으로 Commons DBCP2 library가 포함되어 있으므로 별도의 설치가 필요 없음  
\- <TOMCAT_HOME>/lib/tomcat-dbcp.jar  

