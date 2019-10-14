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
		      pStmt = conn.prepareStatement(query);
		      pStmt.setString(1, pattern);
		      rs = pStmt.executeQuery();
		      
		      while (rs.next()) {						// 커서를 통해 한 행씩 fetch
		    	 String ename = rs.getString("ename");
		    	 String job = rs.getString("job");
		    	 System.out.println(ename + " " + job);
		      }
		    } catch (SQLException ex) { 
		    	ex.printStackTrace(); 
		    } finally {
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
