-- 1.

CREATE TABLE emp0000 AS
         SELECT empno, ename, dname, sal, comm, hiredate, mgr
         FROM emp e, dept d
         WHERE e.deptno=d.deptno AND dname = ‘SALES’;

 

-- 2.

INSERT INTO emp0000 (empno, name, sal, hiredate)

VALUES (8000, ‘JULIET’, 2000, SYSDATE);

 

-- 3. 

INSERT INTO emp0000 (empno, ename, dname, sal, comm, hiredate, mgr)
         SELECT empno, ename, dname, sal, comm, hiredate, mgr
         FROM emp e, dept d
         WHERE e.deptno=d.deptno AND dname = ‘RESEARCH’;

 

-- 4. 

UPDATE emp0000

SET comm = sal * 0.1

WHERE comm IS NULL;

 

-- 5.

UPDATE emp0000

SET (dname, mgr) = (SELECT dname, mgr FROM emp0000 WHERE ename = ‘SMITH’)

WHERE empno = 8000;

 

-- 6. 

ALTER TABLE emp0000 ADD (retiredate VARCHAR2(8));

UPDATE emp0000 SET retiredate = TO_CHAR(ADD_MONTHS(hiredate, 12*30), ‘YYYY-MM-DD’);


-- 7. 

ALTER TABLE emp0000 ADD CONSTRAINT emp0000_pk PRIMARY KEY (empno);

ALTER TABLE emp0000 ADD CONSTRAINT emp0000_fk FOREIGN KEY (mgr) REFERENCES emp0000 (empno);

INSERT INTO emp0000 VALUES (empno, ename, sal, hiredate, mgr)

VALUES (8001, ‘JUULIET’, 2000, SYSDATE, 7777);   -- 오류 발생!

 

-- 8.

CREATE VIEW SAL0000 AS
    SELECT empno, ename, dname, sal
    FROM EMP0000
    WHERE dname = 'SALES';

 

-- (a) 
UPDATE SAL0000 SET sal = 1000;

SELECT * FROM SAL0000;

SELECT * FROM EMP0000;

COMMIT;

 

-- (b) 

UPDATE SAL0000 SET dname = 'ACCOUNTING';

SELECT * FROM SAL0000;

SELECT * FROM EMP0000;

ROLLBACK;

 

-- (c)

CREATE OR REPLACE VIEW SAL0000 AS
      SELECT empno, ename, dname, sal
      FROM EMP0000
      WHERE dname = 'SALES' WITH CHECK OPTION;

UPDATE SAL0000 SET dname = 'ACCOUNTING';    -- 오류 발생!

 

-- 9.

SELECT rownum, empno, ename, 근무기간 
FROM (SELECT empno, ename, TRUNC(SYSDATE - hiredate) 근무기간
             FROM EMP0000
             ORDER BY 근무기간 DESC) 
WHERE rownum <= 3; 

 

-- 10.

CREATE SEQUENCE empno_seq

        START WITH 8010 INCREMENT BY 10 MAXVALUE 9990;

INSERT INTO emp0000 (empno, ename, hiredate, sal)

        VALUES (empno_seq.NEXTVAL, 'ROMIO', SYSDATE, 2000);

INSERT INTO emp0000 (empno, ename, hiredate, sal)

        VALUES (empno_seq.NEXTVAL, 'ROBINS’, SYSDATE, 3000);

INSERT INTO emp0000 (empno, ename, hiredate, sal)

        VALUES (empno_seq.NEXTVAL, ‘SATANE’, SYSDATE, 4000);
