# NoSQL Midterm - 1. MongoDB SQL

### employees.json 파일을 import & mongoDB 질의를 작성  
 (1) 
 ```sql 
 Select * from emplyees where empno=7369
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805772-85442800-9ebf-11ea-8674-ae6cab736a5b.png)  
 <br>
 
 (2) 
 ```sql
 Select ename from emlpoyees where empno=7900
 ```  
 ![image](https://user-images.githubusercontent.com/50273050/82805775-870deb80-9ebf-11ea-984a-bda19d9aa954.png)  
<br>

 (3) 
 ```sql
 Select empno, ename from employees where empno > 7500 and empno <=7600
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805793-912fea00-9ebf-11ea-97f3-a107dfc73299.png)  
 <br>
 
 (4) 
 ```sql
 Select empno from employees where empno = 7782 or empno=7844
 ```   
![image](https://user-images.githubusercontent.com/50273050/82805797-92f9ad80-9ebf-11ea-998a-e96482674316.png)  
<br>

 (5) 
 ```sql
 Select count(*) from employees
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805817-9c831580-9ebf-11ea-8321-4e01793f99d4.png)  
<br>

 (6) 
 ```sql
 Select count(*) from employees where empno > 7900
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805821-9e4cd900-9ebf-11ea-9352-3f57411e7c10.png)  
<br>

 (7) 
 ```sql
 Select distinct deptno from employees
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805843-a86ed780-9ebf-11ea-92d9-81ef59ec9f36.png)  
<br>

 (8) 
 ```sql
 Select ename, job from employees where deptno=10 order by ename desc
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805861-aefd4f00-9ebf-11ea-8bca-af142eb0d3ec.png)  
<br>

 (9) 
 ```sql
 Select sum(salary) from employees
 ```  
![image](https://user-images.githubusercontent.com/50273050/82805883-b6245d00-9ebf-11ea-8cc7-c40215f28d61.png)  
<br>

 (10) 
 ```sql
 Select deptno, avg(salary) from employees group by deptno order by deptno
 ```   
![image](https://user-images.githubusercontent.com/50273050/82805898-bc1a3e00-9ebf-11ea-94cd-4c8dd3bedd36.png)  
