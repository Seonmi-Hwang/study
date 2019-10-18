# QUIZ (JDBC & SQL)  

#### Q1. 아래 빈칸을 채우시오.  
```java
package jdbcTest;
import     ①    ;  // 1. JDBC 관련 package import

public class JdbcTest {

	public static void main (String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		    
		String url ="jdbc:oracle:thin:@202.20.119.117:1521:orcl", user ="scott", passwd ="TIGER";
		    
		try {
		     ②  .   ③   ("oracle.jdbc.driver.OracleDriver"); // 2. JDBC Driver 로딩 및 등록
		} catch (ClassNotFoundException ex) { ex.printStackTrace(); }
		    
		   String pattern ="%AR%";
		    
		  try {
		    conn = DriverManager.     ④     (url, user, passwd);  // 3. DBMS와의 연결 획득
		      
		    String query ="SELECT ename, job FROM emp WHERE ename like ?";
		 	 pStmt = conn.     ⑤     (query); // 4. SQL문을 위한 PreparedStatement 객체 생성
		    pStmt.      ⑥      ; // pattern을 ?에 넣는 작업
		    rs = pStmt.     ⑦     ;  // 5. PreparedStatement 객체를 사용하여 SQL문 실행
		      
		    while (   ⑧   ) { // 6. DBMS 응답 사용 (커서를 통해 한 행씩 fetch)
		    	 String ename = rs.getString("ename");
		    	 String job = rs.getString("job");
		    	 System.out.println(ename +""+ job);
		    }
		  } catch (SQLException ex) { 
		    	ex.printStackTrace(); 
		  } finally { // 7. 자원 반납
				if (rs != null) 
					try { 
						    ⑨-1    ; 
					} catch (SQLException ex) { ex.printStackTrace(); }
				if (pStmt != null) 
					try { 
						    ⑨-2    ; 
					} catch (SQLException ex) { ex.printStackTrace(); }
				if (conn != null) 
					try { 
						    ⑨-3    ; 
					} catch (SQLException ex) { ex.printStackTrace(); }
			}	
	}
}
```
#### A1.  
① java.sql.*&nbsp;&nbsp;&nbsp;&nbsp;② Class&nbsp;&nbsp;&nbsp;&nbsp;③ forName&nbsp;&nbsp;&nbsp;&nbsp;④ getConnection&nbsp;&nbsp;&nbsp;&nbsp;⑤ prepareStatement  
⑥ setString(1, pattern)&nbsp;&nbsp;&nbsp;&nbsp;⑦ executeQuery()&nbsp;&nbsp;&nbsp;&nbsp;⑧ rs.next()    
⑨-1 rs.close()&nbsp;&nbsp;&nbsp;&nbsp;⑨-2 pStmt.close()&nbsp;&nbsp;&nbsp;&nbsp;⑨-3 conn.close()    
<br></br>
#### Q2. Statement와 PreparedStatement의 장단점 및 사용 시 주의점을 기술하시오.  

#### A2.  
Statement는 가독성의 문제가 있고, 동일한 형태의 SQL문이 반복적으로 컴파일되어 DBMS의 부하가 증가한다.  
그러나 PreparedStatement는 사전에 컴파일한 SQL문을 실행하여 반복적인 컴파일을 피함으로써 DBMS의 부하가 감소된다.   

Statement는 executeQuery() 사용 시 매개변수를 사용하지만, PreparedStatement는 매개변수를 사용하지 않는다.  
PreparedStatement는 Statement의 자식클래스이므로 executeQuery() 수행 시 매개변수를 사용하게 되면 runtime error가 발생한다.   
<br></br>
#### Q3. emp 테이블에 나타나지 않는 부서 번호에 대해서도 dept 테이블의 dname을 결과에 포함시키는 SQL문을 두 가지 표현으로 작성하시오. (사원이 한 명도 없는 부서)   
#### A3.  
```sql
-- 방법1
SELECT e.ename, d.dname
FROM emp e, dept d
WHERE e.deptno (+) = d.deptno;

-- 방법2
SELECT e.ename, d.dname
FROM emp e RIGHT OUTER JOIN dept d ON e.deptno = d.deptno;
```
<br></br>
#### Q4. 아래 코드에서 잘못된 것을 모두 찾아 설명하시오. (3개)  
```java
(생략)

String tname = "emp";
int salary = 2500;

String query ="SELECT empno, mgr, sal FROM ? WHERE mgr = NULL AND sal > ?";
pStmt = conn.prepareStatement(query); // 4. SQL문을 위한 PreparedStatement 객체 생성
pStmt.setString(0, "emp");
pStmt.setInt(1, salary);
rs = pStmt.executeQuery();

(생략) 
```
#### A4.   
① FROM ? WHERE  
질의문 상에서 값에 해당하는 부분만 대치 가능하다. 따라서 테이블 이름은 매개변수로 대치 불가하다.  

② mgr = NULL   
위처럼 하면 모든 mgr이 NULL로 나온다. mgr IS NULL 로 쓰는 것이 적합하다.  

③ pStmt.setString(0, "emp")  
매개변수 위치는 0이 아니라 1부터 시작한다.   
