# SQL BASIC

◼️ **LIKE 연산자** : 패턴을 이용한 문자열 검색  
* **wildcard**  
% : 길이가 0 이상인 임의의 문자열에 대응  
_ : 임의의 한 문자에 대응  

> 'A'이 포함된 ~ B : WHERE B **LIKE** '%A%'  
> 두 번째 문자가 'A'인 ~ B : WHERE B **LIKE** '_A%'  

* **WARNING**  
WHERE ename LIKE '%\_%' ESCAPE '\'  
=> \를 escape character로 사용, \ 뒤에 오는 하나의 문자를 순수한 문자로 해석  
=> _를 포함하는 문자열을 검색  


◼️ **논리 연산자** : 여러 개의 조건을 논리적으로 결합  
* **우선순위**  
NOT > AND > ALL, ANY, BETWEEN, IN, LIKE, OR, SOME  
우선순위에 혼선을 주지 않기 위해 괄호로 묶어줘야 한다.  

* **BETWEEN 연산자** : 검색하고자 하는 값의 최대 값과 최소 값을 설정 (경계 값 포함)  
> WHERE sal **BETWEEN** 3000 **AND** 4000  
> => WHERE sal >= 3000 AND sal <= 4000  


◼️ **JOIN** : 하나의 SQL 질의에서 여러 테이블들을 결합하여 검색하기 위해 실행  
\- **Equi-Join** : 가장 일반적인 join 방법  
> 1. WHERE e.deptno **=** d.deptno  
> 1. FROM emp e **JOIN** dept d **ON** e.deptno = d.deptno  

\- **Non-Equi-Join** : join 조건으로 동등 비교 외의 다른 연산자 사용  
> WHERE e.sal >= s.losal AND e.sal <= s.hisal
