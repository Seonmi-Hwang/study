-- 1
select ename, deptno, job
from emp
where (deptno = 20 or deptno = 30) and ename like '%AR%';

-- 2-(a)
select count(sal)
from emp e, dept d
where e.deptno = d.deptno AND sal <= 2000 AND dname='RESEARCH';

-- 2-(b)
select count(sal)
from emp
where sal <= 2000
    AND deptno = (select deptno
                  from dept
                  where dname = 'RESEARCH');

-- 3
select ename, sal, NVL(comm, 0), sal * 12 + NVL(comm, 0) "ANNUAL SALARY"
from emp, salgrade
where sal >= losal AND sal <= hisal AND grade = 5 AND NVL(comm, 0) < 500;


-- 4
select ename, TO_CHAR(hiredate, 'YYYY"년" MM"월" DD"일"') "HIREDATE", 
    TRUNC(MONTHS_BETWEEN(sysdate, hiredate) / 12, 0) "ASSLIFE",
    TO_CHAR(ADD_MONTHS(hiredate, 30 * 12), 'YYYY"년" MM"월" DD"일"') "RETIREDATE"
from emp
order by hiredate;

-- 5
select deptno, job, count(*), avg(sal)
from emp
group by deptno, job;

-- 6-(a)
select dname, AVG(sal)
from emp e, dept d
where e.deptno = d.deptno
group by dname
having count(*) >= 2;

-- 6-(b)
select dname, AVG(sal)
from dept d, emp e
where e.deptno = d.deptno
    AND (select count(*)
         from emp) >= 2
group by dname;

-- 7
select dname, count(empno), max(sal)
from emp e, dept d
where e.deptno(+) = d.deptno
group by dname;

-- 8
select e.ename || '의 동료는 ' || m.ename "COWORKER"
from emp e, emp m
where e.deptno = m.deptno AND e.empno != m.empno // ename 아님!
order by e.ename;

/* 
* 아래 코드는 LISTAGG 함수를 사용한 코드
SELECT e.ename, LISTAGG(m.ename,', ') WITHIN GROUP(ORDER BY m.ename)
FROM emp e, emp m
WHERE e.deptno = m.deptno
GROUP BY e.ename
ORDER BY e.ename;
*/

-- 9
select ename, sal, CASE WHEN sal < 1000 THEN sal * 0.01
                        WHEN sal < 2000 THEN sal * 0.015
                        ELSE sal * 0.02 END "Deducted amount"
from emp e
order by sal desc;
