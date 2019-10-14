package jdbcTest;

import java.sql.*;

public class CallableStatementTest {
	public static void main(String args[]) 
	{
		Connection conn = null;
		CallableStatement cStmt = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String passwd = "TIGER";
		String query = "{call cs_proc(?, ?, ?)}"; // 미리  procedure를 오라클 안에 만들어놔야 함.
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(url, user, passwd);	 
			cStmt = conn.prepareCall(query);
			cStmt.setInt(1, 10);
			cStmt.setString(2, "item01");
			cStmt.setString(3, "item01 is the best one.");
			cStmt.execute();
			System.out.println ("Insertion complete");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		
			if (cStmt != null) try { cStmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		}	
	}	
}