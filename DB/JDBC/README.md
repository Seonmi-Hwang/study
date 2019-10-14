# JDBC Programming  
JDBC에 대한 개념과 그 개념을 바탕으로 programming한 것들이 담긴 directory  

### ⬛️ JDBC API 사용 절차  

&nbsp;&nbsp;준비(1-3)  -> 사용(4-6) -> 종료(7)  
1. JDBC 관련 Package Import  
2. JDBC Driver 로딩 및 등록  
3. DBMS와의 Connection 획득    
4. SQL문 수행을 위한 Statement 객체 생성(PreparedStatement)  
5. Statement 객체를 사용하여 SQL문 수행(DBMS에 질의 전송 및 실행)  
6. DBMS 응답 사용(ResultSet/return 값 이용)  
7. 자원 반납(ResultSet, Statement, Connection)  

**아래의 예시를 통해 사용 절차를 확인해보자.**  
```java
package jdbcTest;
import java.sql.*;  // 1. JDBC 관련 package import

public class JdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 변수 선언 & null 초기화
		    Connection conn = null;
		    PreparedStatement pStmt = null; // PreparedStatement 참조 변수 생성
		    ResultSet rs = null;
		    
		    String url = "jdbc:oracle:thin:@202.20.119.117:1521:orcl", user = "scott", passwd = "TIGER";
		    
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver"); // 2. JDBC Driver 로딩 및 등록
		    } catch (ClassNotFoundException ex) { ex.printStackTrace(); }
		    
		    String pattern = "%AR%";
		    
		    try {
		      conn = DriverManager.getConnection(url, user, passwd);  // 3. DBMS와의 연결 획득
		      
		      String query = "SELECT ename, job FROM emp WHERE ename like ?";
		      pStmt = conn.prepareStatement(query); // 4. SQL문을 위한 PreparedStatement 객체 생성
		      pStmt.setString(1, pattern);
		      rs = pStmt.executeQuery();  // 5. PreparedStatement 객체를 사용하여 SQL문 실행
		      
		      while (rs.next()) { // 6. DBMS 응답 사용 (커서를 통해 한 행씩 fetch)
		    	 String ename = rs.getString("ename");
		    	 String job = rs.getString("job");
		    	 System.out.println(ename + " " + job);
		      }
		    } catch (SQLException ex) { 
		    	ex.printStackTrace(); 
		    } finally { // 7. 자원 반납
				if (rs != null) 
					try { 
						rs.close(); 
					} catch (SQLException ex) { ex.printStackTrace(); }
				if (pStmt != null) 
					try { 
						pStmt.close(); 
					} catch (SQLException ex) { ex.printStackTrace(); }
				if (conn != null) 
					try { 
						conn.close(); 
					} catch (SQLException ex) { ex.printStackTrace(); }
			}	
	}
}
```
Statement 객체 대신에 PreparedStatement 객체를 사용하였다.  

### ⬛️ JDBC에서 SQL문 사용  
* **Statement**  
\-일반적인 SQL문 사용 시 쓰임.  
\- 코드 작성 및 가독성 문제가 있음.  
\-동일한 형태의 SQL문이 반복적으로 컴파일 됨.
=> DBMS 부하 증가  

* **PreparedStatement** <- Statement의 자식클래스임.   
\- 사전에 compile한 SQL문 실행 -> 반복적인 compile을 피함으로써 DBMS 부하 감소 효과  
\- SQL문 내에 매개변수 사용 가능  
\- 매개변수를 제외하고 SQL 문장의 구조가 동일한 경우 사용  
**\- executeQuery() 수행 시 매개변수 절대 사용불가** -> runtime error 발생!  

**\- WHY? runtime error 발생**  
PreparedStatement가 Statement의 자식클래스인데, executeQuery에 매개변수가 있는 메소드는 Statement에서만 쓰인다.  
그런데 자식클래스인 PreparedStatement가 그 메소드를 (실수로)쓰게 되면 overriding을 하지 않았기 때문에 runtime error가 발생하게 된다.  


