-- PL/SQL Examples in Lecture Notes 

SET SERVEROUTPUT ON

-- ù��° PL/SQL ���α׷� ��
DECLARE 
     v_ename VARCHAR2(20);
     v_deptno NUMBER(4);
BEGIN 
    SELECT ename, deptno INTO v_ename, v_deptno
    FROM emp 
    WHERE empno=7934; 
    dbms_output.put_line('***** �� �� �� �� *****'); 
    dbms_output.put_line(v_ename|| ' IN ' ||v_deptno); 
END; 

-- %ROWTYPE �Ӽ� ��� ��
DECLARE 
    v_emp   emp%ROWTYPE; 
BEGIN 
    SELECT * INTO v_emp 
    FROM emp 
    WHERE empno=7934; 
    dbms_output.put_line('���   �̸�   ����     �����'); 
    dbms_output.put_line( v_emp.empno || '  ' || v_emp.ename
                || '  ' || v_emp.job || '  ' || v_emp.hiredate); 
END; 

-- Record Ÿ�� ��� ��
DECLARE 
    TYPE EmpRec IS RECORD ( 			-- Ÿ�� ����
        emp_name   VARCHAR2(10), 
        dept_name   VARCHAR2(20), 
        salary 	 NUMBER(7,2)); 
    emp_info  EmpRec;				-- ���� ����
BEGIN 
    SELECT ename, dname, sal INTO emp_info 
    FROM emp e, dept d
    WHERE e.deptno = d.deptno AND empno = 7934;
    dbms_output.put_line(emp_info.emp_name||' IN '||emp_info.dept_name); 
END;

-- VARRAY ��� ��
DECLARE 
    TYPE ProjectList IS VARRAY(50) OF VARCHAR2(15);		-- Ÿ�� ����     
    projects ProjectList; -- ���� ����
BEGIN 
    projects := ProjectList('Expense Report', 'Outsourcing', 'Auditing');    -- �ʱ�ȭ
    projects(3) := 'Operations'; 					 -- ���� ����
    FOR i IN 1..3 LOOP 
	 dbms_output.put_line(projects(i));				 -- ���� ��ȸ, ���
    END LOOP;
END;

-- Nested Table ��� ��
DECLARE
    TYPE Roster IS TABLE OF VARCHAR2(15);	                 	-- Ÿ�� ����
    names Roster := Roster('M Ford', 'J Hamil', 'D Caruso', 'R Singh');   -- ���� ���� ��
BEGIN							-- �ʱ�ȭ
    names.DELETE(1);				            -- ù��° ���� ����
    dbms_output.put_line(names.FIRST || ' ' || names.LAST);		-- 2 4 ���
    FOR i IN names.FIRST .. names.LAST  LOOP			-- 2 ~ 4
       IF names(i) = 'J Hamil' THEN
           dbms_output.put_line(i); 			  	-- 2 ���
       END IF;
    END LOOP;
END;

-- �Ͻ��� Ŀ�� ��� ��
DECLARE
	v_empno  VARCHAR2(20);
	v_ename  VARCHAR2(20);
BEGIN
	SELECT empno, ename INTO v_empno, v_ename
	FROM emp
	WHERE empno=7934; 		              -- empno is a primary key
	dbms_output.put_line(SQL%ROWCOUNT);     	-- 1
  IF SQL%FOUND THEN 			-- true
	 	dbms_output.put_line('FOUND');
  ELSIF SQL%NOTFOUND THEN 			-- false
	 	dbms_output.put_line('NOT FOUND');
  END IF;
  IF SQL%ISOPEN THEN 			-- false
		dbms_output.put_line('OPEN'); 
	END IF;
END;


-- ����� Ŀ�� ��� ��
DECLARE		 	
	v_empno 	emp.empno%TYPE;
	v_ename 	emp.ename%TYPE; 
	v_sal		emp.sal%TYPE; 
	CURSOR  C1  IS 
		SELECT empno, ename, sal FROM emp WHERE deptno=20;
BEGIN
	OPEN C1;
	LOOP 
		FETCH C1 INTO v_empno, v_ename, v_sal; 
  		EXIT WHEN C1%NOTFOUND; 
 		dbms_output.put_line(v_empno || v_ename || v_sal); 
	END LOOP;
 	dbms_output.put_line(C1%ROWCOUNT); 
	CLOSE C1;
END;
/

-- ����� Ŀ���� ���� %ROWTYPE �Ӽ� ��� ��
DECLARE
	CURSOR  C1  IS
		 SELECT empno, ename, sal FROM emp WHERE deptno=20;
	emp_rec  C1%ROWTYPE;
BEGIN
	OPEN C1;
	LOOP 
		FETCH C1 INTO emp_rec; 
   		EXIT WHEN C1%NOTFOUND;
 		dbms_output.put_line(
			emp_rec.empno || emp_rec.ename || emp_rec.sal); 
	END LOOP;
 	dbms_output.put_line(C1%ROWCOUNT); 
	CLOSE C1;
END;
/

-- Explicit Cursor For Loop ��� ��
DECLARE
	CURSOR C1 IS
		SELECT empno, ename, sal FROM emp WHERE deptno=20;
BEGIN
	FOR emp_rec IN C1
	LOOP
		dbms_output.put_line(
			emp_rec.empno  || emp_rec.ename || emp_rec.sal); 
	END LOOP; 
END;
/

-- Implicit Cursor For Loop ��� ��
DECLARE					 
BEGIN
	FOR emp_rec IN (	
		SELECT empno, ename, sal FROM emp WHERE deptno=20
	) LOOP
		dbms_output.put_line(
			emp_rec.empno || emp_rec.ename || emp_rec.sal); 
	END LOOP; 
END;
/

-- BULK COLLECT ��� ��
DECLARE 
	TYPE EmployeeSet IS TABLE OF emp%ROWTYPE;             -- nested table
	underpaid EmployeeSet;                          -- Holds set of rows from emp table
BEGIN 
	SELECT * BULK COLLECT INTO underpaid 
	FROM emp
	WHERE sal < 2500 ORDER BY sal DESC; 
	dbms_output.put_line(underpaid.COUNT || ' people make less than 2500.');
	FOR i IN underpaid.FIRST .. underpaid.LAST LOOP
		dbms_output.put_line(
			underpaid(i).ename || ' makes ' || underpaid(i).sal); 
	END LOOP; 
END;
/
 
-- ���� ���ν��� ��� �� 1
CREATE OR REPLACE PROCEDURE update_sal (v_empno IN emp.empno%TYPE) IS
BEGIN 
	UPDATE emp 
	SET sal = sal * 1.1			-- salary�� 10% �λ�
	WHERE empno = v_empno; 
END update_sal;
/

-- ���� ���ν��� ��� �� 2
CREATE OR REPLACE PROCEDURE calc_bonus (
 	empid IN emp.empno%TYPE,
	bonus OUT NUMBER) 
IS
	v_hiredate   emp.hiredate%TYPE;
BEGIN 
	SELECT sal * 0.1, hiredate 
	INTO bonus, v_hiredate 
	FROM emp 
	WHERE empno = empid; 
	
	IF MONTHS_BETWEEN(SYSDATE, v_hiredate) > 60 THEN 
	       bonus := bonus + 500;  	
	END IF; 
END calc_bonus;
/

-- ���� �Լ� ��� �� 1
CREATE OR REPLACE FUNCTION update_and_return_sal
(v_empno IN emp.empno%TYPE)
RETURN emp.sal%TYPE
IS
     v_sal  emp.sal%TYPE;
BEGIN
    UPDATE emp
    SET sal = sal *1.1
    WHERE empno = v_empno;
    COMMIT;

    SELECT sal INTO v_sal  FROM emp
    WHERE empno = v_empno;

    RETURN v_sal;
END;
/

-- Exception ó��
CREATE OR REPLACE FUNCTION calc_salary_per_day
(v_empno IN emp.empno%TYPE) 
RETURN NUMBER 
IS
	salary emp.sal%TYPE; 
	days NUMBER := 0;	
BEGIN 
	SELECT sal INTO salary 
	FROM emp 
	WHERE empno = v_empno;
	RETURN salary / days;		                        -- divide by 0
EXCEPTION
	WHEN NO_DATA_FOUND THEN 
		dbms_output.put_line('No data for empno '|| v_empno  || ' was found.'); 
	WHEN ZERO_DIVIDE THEN 
		dbms_output.put_line('Divide-by-zero error.');
	WHEN OTHERS THEN 
		dbms_output.put_line('Unexpected error.'); 
END;
/

-- User-defined Exception
CREATE OR REPLACE PROCEDURE User_Exception
(v_deptno IN emp.deptno%TYPE)  
IS
	too_few_employees EXCEPTION;	-- step 1: exception ����
	cnt  NUMBER;
BEGIN 
	SELECT COUNT(empno) INTO cnt
	FROM emp WHERE deptno = v_deptno;
	IF cnt < 5 THEN
		RAISE too_few_employees;	--  step 2: exception �߻�
	END IF;
	dbms_output.put_line('Number of employees: ' || cnt);
EXCEPTION
	WHEN too_few_employees THEN  	-- step 3: exception ó�� 
		RAISE_APPLICATION_ERROR(-20001, 'Too few employees in the department');
END;
/


CREATE TABLE goods
	(item_id		VARCHAR2(10)	PRIMARY KEY,
	 stock		NUMBER		NOT NULL);

CREATE TABLE storing
	(st_no		NUMBER		PRIMARY KEY,
	 item_id		VARCHAR2(10) 	REFERENCES goods, 
	 st_amt		NUMBER 		NOT NULL, 
	 st_date		DATE);

CREATE OR REPLACE TRIGGER TRG02
AFTER INSERT ON storing			-- ���ο� ���� ���Ե� ��
FOR EACH ROW				-- ���Ե� �࿡ ���� ���� 
DECLARE
	v_cnt  NUMBER;
BEGIN
	SELECT count(*) INTO v_cnt		-- �԰�� ��ǰ�� �ش��ϴ�
	FROM goods			-- ��ǰ ���� �����ϴ��� �˻�
	WHERE item_id = :NEW.item_id;
	IF v_cnt = 0  THEN			-- ��ǰ ���� �������� �ʴ� ���
		INSERT INTO goods (item_id, stock)	-- �� ���� �� ����
		VALUES (:NEW.item_id, :NEW.st_amt);
	ELSE				-- ��ǰ ���� �����ϴ� ��� 
		UPDATE goods 	
		SET stock = stock + :NEW.st_amt  -- stock�� �԰���� ��ŭ ���� 		
		WHERE item_id = :NEW.item_id;	
	END IF;
END;
/

CREATE OR REPLACE TRIGGER TRG03
AFTER UPDATE OF st_amt ON storing   -- �԰� ���� �԰������ ����� �� 
FOR EACH ROW			 -- ����� �� �԰� �࿡ ���� ���� 
BEGIN
	UPDATE goods 		 -- ���� ��ǰ ���� ã�� stock ����
	SET stock = stock - :OLD.st_amt + :NEW.st_amt
	WHERE item_id = :NEW.item_id;	
END;
/

CREATE OR REPLACE TRIGGER TRG04
AFTER DELETE ON storing		-- �԰� ����� ������ �� 
FOR EACH ROW			-- ������ �� �԰� �࿡ ���� ���� 
BEGIN
	UPDATE goods 		-- ���� ��ǰ ���� ã�� stock ����
	SET stock = stock - :OLD.st_amt    -- stock�� �԰���� ��ŭ ���� 
	WHERE item_id = :OLD.item_id;
END;
/

CREATE OR REPLACE TRIGGER secure_emp
BEFORE INSERT OR UPDATE OR DELETE ON emp
BEGIN
	IF (TO_CHAR(SYSDATE,'DY') IN ('SAT','SUN') OR
	    TO_CHAR(SYSDATE,'HH24') NOT BETWEEN '09' AND '17') 
	THEN
		RAISE_APPLICATION_ERROR(-20201, 'Time not allowed');
	END IF;
END;
/

CREATE OR REPLACE TRIGGER salary_check
BEFORE INSERT OR UPDATE OF sal, job ON emp
FOR EACH ROW
WHEN (new.hiredate > '80/01/01')	                      
CALL check_sal(:new.job, :new.sal)   -- check_sal ���ν��� ȣ��

/

CREATE OR REPLACE PROCEDURE check_sal(
	job IN emp.job%TYPE,
	sal IN emp.sal%TYPE)
IS
BEGIN
	IF (job = 'SALESMAN' AND sal < 1000) THEN
		RAISE_APPLICATION_ERROR(-20202, 'Too little salary for salesman');
	END IF;
END;
/

CREATE OR REPLACE PACKAGE emp_pack   -- ��Ű�� ����
IS 
	avg_sal  NUMBER;                        -- ���� ���� ����
	PROCEDURE update_sal (v_empno IN NUMBER);        -- ���ν��� ����
	FUNCTION calc_bonus (emp_id  IN NUMBER) RETURN NUMBER;   -- �Լ� ����
END;
/

CREATE OR REPLACE PACKAGE BODY emp_pack  IS 	  -- ��Ű�� ��ü ����
	PROCEDURE update_sal (v_empno IN NUMBER) IS	  -- ���ν��� ����
	BEGIN 
		UPDATE emp 
		SET sal = sal * 1.1		
		WHERE empno = v_empno and sal > avg_sal;       -- �������� ���
		COMMIT;
	END update_sal;

	FUNCTION calc_bonus (emp_id IN NUMBER) RETURN NUMBER IS    -- �Լ� ����
		hire_date DATE; 
		bonus NUMBER;
	BEGIN 
		SELECT sal * 0.10, hiredate INTO bonus, hire_date 
		FROM emp WHERE empno = emp_id and sal < avg_sal;        -- �������� ���
		IF MONTHS_BETWEEN(SYSDATE, hire_date) > 60 THEN 
			bonus := bonus + 500; 	
		END IF; 
		RETURN bonus;
	END calc_bonus;   
BEGIN 			                         -- One-time only procedure
	SELECT avg(sal) INTO avg_sal          -- �������� �ʱ�ȭ ����
	FROM emp;
END emp_pack;
/

CREATE OR REPLACE PROCEDURE delete_rows(
  	table_name IN VARCHAR2, 
  	condition IN VARCHAR2 DEFAULT NULL) 
IS
 	where_clause VARCHAR2(150) := NULL;
BEGIN 
	IF condition IS NOT NULL THEN 
		 where_clause := ' WHERE ' || condition; 
    	END IF;
    	dbms_output.put_line('DELETE FROM '|| table_name || where_clause ||';');
    	EXECUTE IMMEDIATE 'DELETE FROM ' || table_name || where_clause;
END;
/
