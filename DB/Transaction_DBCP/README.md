## Transaction & DBCP(Database Connection Pool)  

### ⬛️ 트랜잭션(Transaction)   
\– **데이터 처리를 위한 논리적인 작업단위**  
* 여러 개의 DML 문들과 SQL 질의들로 구성  
\– **All or Nothing 방식으로 처리됨**  
* 오류가 없을 경우 실행이 완료(commit)되고 결과가 영속적으로 유지됨   
* 오류 발생 시 전체 실행 결과가 취소됨  
\– **ACID 속성**을 만족해야 함   
* **Atomicity(원자성)**  
* **Consistency(일관성)**  
* **Isolation(고립성)**  
* **Durability(영속성)**  
\– 예: 은행에서 현금 인출, 계좌 이체 등의 작업  

### ⬛️ 트랜잭션 처리  
DDL문 : 자동 commit 및 rollback  
\- **COMMIT**  
* **트랜잭션을 정상적으로 완료**하고 **처리 결과를 영구히 저장**
* Commit 실행 후에는 트랜잭션에 의해 변경된 데이터를 **복구할 수 없음**
\- ROLLBACK  
* **트랜잭션에 의한 데이터의 변경을 취소** \-> 트랜잭션 **시작 전의 상태로 돌아감**  
트랜잭션에서 데이터 변경 시 이전의 값(before image)을 **rollback segment**에 임시 저장 
\- SAVEPOINT <\- 많이 사용되진 않음    
* 여러 개의 DML 문을 포함하는 트랜잭션에서 사용자가 **트랜잭션의 중간에 savepoint들을 지정**할 수 있음 
* 트랜잭션 rollback 시 과거에 생성된 **특정 save-point까지만 rollback** 가능  

  