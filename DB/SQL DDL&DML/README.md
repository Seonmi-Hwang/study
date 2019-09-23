# SQL DDL & DML  
#### ⬛️ DDL(Data Definition Language)  
* **TABLE 생성**  
**\- TABLE 정의**  
CREATE TABLE *emp01* (*empno NUMBER(4)*, ...);  
**\- Subquery를 이용한 TABLE 생성**  
CREATE TABLE *emp01* **AS** SELECT * FROM emp;  
CREATE TABLE *emp01* (*emp_number*, *emp_name*) AS SELECT *empno*, *ename* FROM *emp*;  
CREATE TABLE *emp01* AS SELECT \* FROM *emp* WHERE 1 = 0;  

* **TABLE 구조 변경**  
**\- Column 추가**  
ALTER TABLE *emp01* ADD (email VARCHAR2(10), age NUMBER(2));  
**\- Column 변경**  
ALTER TABLE *emp01* MODIFY (email VARCHAR2(40), age CHAR(2));  
**\- Column 이름 변경**  
ALTER TABLE *emp01* RENAME COLUMN *ename* TO *emp_name*;  
**\- Column 삭제**  
ALTER TABLE *emp01* DROP COLUMN *email*;  
**\- TABLE 이름 변경**  
RENAME *emp01* TO *emp_01*;   

* **TABLE 및 데이터 삭제**  
**\- TABLE 및 데이터 삭제**  
DROP TABLE *dept*;  // CASCADE CONSTRAINTS 적용 가능  
**\- TABLE 자체는 삭제하지 않고 데이터만 삭제**   
TRUNCATE TABLE dept;  

#### ⬛️ DML(Data Manipulation Language)  
* **데이터 삽입**  
**\- Column 값들을 지정하여 하나의 행을 삽입**   
INSERT INTO *dept* (*deptno, dname, loc*) VALUES (*60, '회계과', '서울'*);  
**\- Subquery를 사용하여 입력될 데이터 생성**  
INSERT INTO *dept* SELECT * FROM old_dept; // VALUES 생략  

* **데이터 수정**  
**\- 변경할 column에 대해 값 지정**  
UPDATE *dept* SET *dname = 'Production', loc='서울'* WHERE *deptno = 10*;   
**\- Subquery를 이용하여 변경할 값 생성**  
UPDATE *dept* SET *(dname, loc)* = (SELECT *dname, loc* FROM *dept* WHERE *deptno = 40*) WHERE *deptno = 20*;  

* **데이터 삭제**  
**\- 조건을 만족하는 행들을 table에서 삭제**  
DELETE FROM *dept* WHERE *deptno = 30*;  
**\- 조건절에서 subquery 사용**  
DELETE FROM *emp* WHERE *deptno* = (SELECT *deptno* FROM *dept* WHERE *dname = 'SALES'*);  

* **데이터 병합**  
**\- Table의 데이터를 다른 table에 병합**  
MERGE INTO *department n* USING *dept d* ON (*n.deptno = d.deptno*) WHEN MATCHED THEN UPDATE SET *n.dname = d.dname*, *n.loc = d.loc* WHEN NOT MATCHED THEN INSERT VALUES *(d.deptno, d.dname, d.loc)*;
