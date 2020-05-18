## 데이터 삽입  

db.employees.insert({empno:7369 , ename : "SMITH", job : "CLERK", manager : "FORD", hiredate : "17-12-1980", sal : 800, deptno : 20 })   
db.employees.insert({empno:7499 , ename : "ALLEN", job : "SALESMAN", manager : "BLAKE", hiredate : "20-02-1981", sal :1600, comm : 300, deptno : 30 })  
db.employees.insert({empno:7521 , ename : "WARD", job : "SALESMAN", manager : "BLAKE", hiredate : "22-02-1981", sal : 1250, comm : 500, deptno : 30 })  
db.employees.insert({empno:7566 , ename : "JONES", job : "MANAGER", manager : "KING", hiredate : "02-04-1981", sal : 2975, deptno : 20 })  
db.employees.insert({empno:7654 , ename : "MARTIN", job : "SALESMAN", manager : "BLAKE", hiredate : "28-09-1981", sal : 1250, comm : 1400, deptno : 30 })  
db.employees.insert({empno:7698 , ename : "BLAKE", job : "MANAGER", manager : "KING", hiredate : "01-05-1981", sal : 2850, deptno : 30 })  
db.employees.insert({empno:7782 , ename : "CLARK", job : "MANAGER", manager : "KING", hiredate : "09-06-1981", sal : 2450, deptno : 10 })  
db.employees.insert({empno:7788 , ename : "SCOTT", job : "ANALYST", manager : "JONES", hiredate : "13-06-1987", sal : 3000, deptno : 20 })  
db.employees.insert({empno:7839 , ename : "KING", job : "CEO", manager : "", hiredate : "17-11-1981", sal : 5000, deptno : 10 })  
db.employees.insert({empno:7844 , ename : "TURNER", job : "SALESMAN", manager : "BLAKE", hiredate : "08-09-1981", sal : 1500, deptno : 30 })   
db.employees.insert({empno:7876 , ename : "ADAMS", job : "CLERK", manager : "SCOTT", hiredate : "13-06-1987", sal : 1100, deptno : 20 })  
db.employees.insert({empno:7900 , ename : "JAMES", job : "CLERK", manager : "BLAKE", hiredate : "03-12-1981", sal : 950, deptno : 30 })  
db.employees.insert({empno:7902 , ename : "FORD", job : "ANALYST", manager : "JONES", hiredate : "03-12-1981", sal : 3000, deptno : 20 })  
db.employees.insert({empno:7934 , ename : "CLERK", job : "CLERK", manager : "KING", hiredate : "23-01-1982", sal : 1300, deptno : 10 })  
  
<hr>  

## 문제  

1. 
```sql
Select * from emplyees where empno=7369  
```

```json
db.employees.find({empno : 7369})  
db.employees.find({empno : 7369}, {_id : 0})  
```

2. 
```sql
Select ename from emlpoyees where empno=7900  
```

```json
db.employees.find({empno : 7900}, {ename : 1, _id : 0})  
```

3. 
```sql
Select empno, ename from employees where empno > 7500 and empno <=7600  
```

```json
db.employees.find({empno : {$gt : 7500, $lte : 7600}}, {empno : 1, ename : 1, _id : 0})  
```

4. 
```sql
Select empno from employees where empno = 7782 or empno=7844  
```

```json
db.employees.find({$or : [{empno : 7782}, {empno : 7844}]}, {empno : 1, _id : 0})  
```

5. 
```sql
Select count(*) from employees  
```

```json
db.employees.find().count()  
```

6. 
```sql
Select count(*) from employees where empno > 7900  
```

```json
db.employees.find({empno : {$gt : 7900}}).count()  
```

7. 
```sql
Select distinct deptno from employees  
```

```json
db.employees.distinct("deptno")  
```

8. 
```sql
Select ename, job from employees where deptno=10 order by ename desc  
```

```json
db.employees.find({deptno : 10}, {ename : 1, job : 1, _id : 0}).sort({ename : -1})  
```

9. Employees Collection의 데이터 수는?  

```json
db.employees.find().count()  
```

10. Employees Collection의 sal (급여) 평균은?  

```json
db.employees.aggregate([{$group : {_id : null, avg_sal : {$avg : "$sal"}}}])  
```

11. Employees Collection의 deptno (부서)별 급여 평균은?  

```json
db.employees.aggregate([{$group : {_id : "$deptno", avg_sal : {$avg : "$sal"}}}])  
```

12. Employees Collection의 deptno별 급여 평균을 오름차순으로 정렬  

```json
db.employees.aggregate([{$group : {_id : "$deptno", avg_sal : {$avg : "$sal"}}}, {$sort : {_id : 1}}])  
```

13. Employees Collection의 deptno별 급여 평균이 2000이상인 부서번호  

```json
db.employees.aggregate([{$group : {_id : "$deptno", avg_sal : {$avg : "$sal"}}}, {$match : {avg_sal : {$gte : 2000}}}])  
```

14. Employees deptno가 10보다 큰 deptno별 급여평균  

```json
db.employees.aggregate([{$group : {_id : "$deptno", avg_sal : {$avg : "$sal"}}}, {$match : {_id : {$gt : 10}}}])  
```

15. Employees deptno가 10보다 큰 deptno별 급여평균이 2000이상인 부서번호  

```json
db.employees.aggregate([{$group : {_id : "$deptno", avg_sal : {$avg : "$sal"}}}, {$match : {_id : {$gt : 10}, avg_sal : {$gte : 2000}}}])  
```

## 실행결과  

![1~9](https://user-images.githubusercontent.com/50273050/80944045-93080f80-8e23-11ea-94e1-9b64c831aeb0.jpg)  

![10~15번](https://user-images.githubusercontent.com/50273050/80944053-94393c80-8e23-11ea-8a84-5c1f2a9f28c1.JPG)  
