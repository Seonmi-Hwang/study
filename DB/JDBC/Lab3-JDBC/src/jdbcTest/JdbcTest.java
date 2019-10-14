package jdbcTest;
import java.sql.*;  // 1. JDBC ���� package import

public class JdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			// ���� ���� & null �ʱ�ȭ
		    Connection conn = null;
		    PreparedStatement pStmt = null; // PreparedStatement ���� ���� ����
		    ResultSet rs = null;
		    
		    String url = "jdbc:oracle:thin:@202.20.119.117:1521:orcl", user = "scott", passwd = "TIGER";
		    
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver"); // 2. JDBC Driver �ε� �� ���
		    } catch (ClassNotFoundException ex) { ex.printStackTrace(); }
		    
		    String pattern = "%AR%";
		    
		    try {
		      conn = DriverManager.getConnection(url, user, passwd);  // 3. DBMS���� ���� ȹ��
		      
		      String query = "SELECT ename, job FROM emp WHERE ename like ?";
		      pStmt = conn.prepareStatement(query);
		      pStmt.setString(1, pattern);
		      rs = pStmt.executeQuery();
		      
		      while (rs.next()) {						// Ŀ���� ���� �� �྿ fetch
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
