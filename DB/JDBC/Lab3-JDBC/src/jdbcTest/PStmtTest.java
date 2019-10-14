package jdbcTest;
import java.sql.*;

public class PStmtTest {
	public static void main(String args[]) 		// �Ϲ������� method�� �����ϰ� ȣ���
	{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pStmt = null;			// PreparedStatment ���� ���� ����
		ResultSet rs = null;
		String url = // "jdbc:oracle:thin:@localhost:1521:xe";
					"jdbc:oracle:thin:@202.20.119.117:1521:orcl";			
		String user = "scott";
		String passwd = "TIGER";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// JDBC ����̹� �ε� �� ���
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
		
		String pattern = "%AR%";				// �Ϲ������� method�� parameter�� ���޵�

		try {
			conn = DriverManager.getConnection(url, user, passwd);	// DBMS���� ���� ȹ��
			
			String query1 = "SELECT ename, job, dname "
						+ "FROM emp JOIN dept USING (deptno) "
						+ "WHERE ename like " + pattern;		// �� ���Ǵ� ���� �� ���� �߻�!! (���� ����ǥ�� ���� ����)
			System.out.println(query1);

			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query1);				// Statement ��ü�� ���� ���� ����
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// �ڿ� �ݳ�
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (stmt != null) 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}

		try {
			String query2 = "SELECT ename, job, dname "
					  	+ "FROM emp JOIN dept USING (deptno) "
					  	+ "WHERE ename like '" + pattern + "'";	// ���ڿ� �� �յڿ� ��������ǥ �ʿ�
			System.out.println(query2);
			
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query2);				// Statement ��ü�� ���� ���� ����
			System.out.println();
			while (rs.next()) {							// Ŀ���� ���� �� �྿ fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + " " + job + " " + dname);
			}
			System.out.println();
			rs.close();

			String query3 = "SELECT ename, job, dname "
				  	+ "FROM emp JOIN dept USING (deptno) "
				  	+ "WHERE ename like ?";			// parameter�� �����ϴ� SQL ���� �ۼ�
			System.out.println(query3);

			pStmt = conn.prepareStatement(query3);	// Connection���� PreparedStatement ��ü ����
			pStmt.setString(1, pattern);				// PreparedStatement�� �Ű����� �� ����
			rs = pStmt.executeQuery();				// ���� ���� (����: parameter ����!!)			
			System.out.println();
			while (rs.next()) {						// Ŀ���� ���� �� �྿ fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + " " + job + " " + dname);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// �ڿ� �ݳ�
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) 
				try { 
					pStmt.close(); 
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