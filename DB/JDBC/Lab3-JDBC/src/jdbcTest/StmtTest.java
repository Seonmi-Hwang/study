package jdbcTest;

import java.sql.*;									// 1. JDBC ���� package import

public class StmtTest {
	public static void main(String args[]) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = // "jdbc:oracle:thin:@localhost:1521:xe";
					"jdbc:oracle:thin:@202.20.119.117:1521:orcl";		
		String user = "scott";
		String passwd = "TIGER";
			
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 2. JDBC ����̹� �ε� �� ���
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		String query =  "SELECT empno, ename, dname "
				  + "FROM emp JOIN dept ON emp.deptno = dept.deptno";						 
	
		try {			
			conn = DriverManager.getConnection(url, user, passwd);	// 3. DBMS���� ���� ȹ�� 
			stmt = conn.createStatement();				// 4. SQL�� ������ ���� Statement ��ü ���� 
			rs = stmt.executeQuery(query);				// 5. Statement ��ü�� ����Ͽ� SQL�� ����
			
			System.out.println("No    Name    Dept");
			while (rs.next()) {							// 6. DBMS ���� ���
				int no = rs.getInt("empno");
				String name = rs.getString("ename");
				String dname = rs.getString("dname");				
				System.out.println(no + "  " + name + "  " + dname);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {										// 7. �ڿ� �ݳ�
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (stmt != null) 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}	
	}	
}