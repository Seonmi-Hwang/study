-- DROP TABLE EMP0995;
-- 1
CREATE TABLE EMP09951
    AS SELECT empno, ename, dname, sal, comm, hiredate, mgr
    FROM emp e, dept d
    WHERE e.deptno = d.deptno AND dname = 'SALES';
    
-- 2
INSERT INTO EMP09951 (empno, ename, hiredate, sal)
VALUES (8000, 'JULIET', SYSDATE, 2000);

-- 3
INSERT INTO EMP09951
SELECT empno, ename, dname, sal, comm, hiredate, mgr
FROM emp e JOIN dept d ON (e.deptno = d.deptno)
WHERE dname = 'RESEARCH';

-- 4
UPDATE EMP09951
SET comm = sal * 0.1
WHERE comm IS NULL;

-- 5
UPDATE EMP09951
SET (dname, mgr) = (SELECT dname, mgr
                    FROM EMP09951
                    WHERE ename = 'SMITH')
WHERE ename = 'JULIET';

-- 6
ALTER TABLE EMP09951
ADD (retiredate VARCHAR2(10));

UPDATE EMP09951
SET retiredate = TO_CHAR(ADD_MONTHS(hiredate, 30 * 12), 'YYYY-MM-DD');

-- 7
UPDATE EMP09951
SET mgr = 7369
WHERE mgr = 7839;

ALTER TABLE EMP09951
ADD CONSTRAINT emp09951_empno_pk PRIMARY KEY(empno);

ALTER TABLE EMP09951
ADD CONSTRAINT emp09951_mgr_fk FOREIGN KEY(mgr)
    REFERENCES EMP09951(empno);

UPDATE EMP09951
SET mgr = 9000;

-- 8
CREATE VIEW SAL0995 AS
SELECT empno, ename, dname, sal
FROM EMP09951
WHERE dname = 'SALES';

-- (a)
UPDATE SAL0995
SET sal = 1000;

commit;

-- (b)
UPDATE SAL0995
SET dname = 'ACCOUNTING';

rollback;

-- (c)
DROP VIEW SAL0995;

CREATE VIEW SAL0995 AS
SELECT empno, ename, dname, sal
FROM EMP09951
WHERE dname = 'SALES' WITH READ ONLY;

UPDATE SAL0995
SET sal = 1000; -- error 발생

-- 9
SELECT ROWNUM, ename, TRUNC(MONTHS_BETWEEN(sysdate, hiredate) / 12, 0) "workperiod"
FROM (SELECT ename, hiredate
      FROM EMP09951
      ORDER BY hiredate)
WHERE ROWNUM <= 3;

-- 10
CREATE SEQUENCE empno_sequence0995
INCREMENT BY 10
START WITH 8010
MAXVALUE 9990;

INSERT INTO EMP09951 (empno, mgr)
VALUES (EMPNO_SEQUENCE0995.nextval, EMPNO_SEQUENCE0995.nextval);

INSERT INTO EMP09951 (empno, mgr)
VALUES (EMPNO_SEQUENCE0995.nextval, EMPNO_SEQUENCE0995.nextval);

INSERT INTO EMP09951 (empno, mgr)
VALUES (EMPNO_SEQUENCE0995.nextval, EMPNO_SEQUENCE0995.nextval);
