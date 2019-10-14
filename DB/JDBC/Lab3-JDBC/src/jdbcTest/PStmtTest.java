package jdbcTest;
import java.sql.*;

public class PStmtTest {
	public static void main(String args[]) 		// 일반적으로 method로 구현하고 호출됨
	{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pStmt = null;			// PreparedStatment 참조 변수 생성
		ResultSet rs = null;
		String url = // "jdbc:oracle:thin:@localhost:1521:xe";
					"jdbc:oracle:thin:@202.20.119.117:1521:orcl";			
		String user = "scott";
		String passwd = "TIGER";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// JDBC 드라이버 로딩 및 등록
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
		
		String pattern = "%AR%";				// 일반적으로 method의 parameter로 전달됨

		try {
			conn = DriverManager.getConnection(url, user, passwd);	// DBMS와의 연결 획득
			
			String query1 = "SELECT ename, job, dname "
						+ "FROM emp JOIN dept USING (deptno) "
						+ "WHERE ename like " + pattern;		// 이 질의는 실행 시 오류 발생!! (작은 따옴표가 없기 때문)
			System.out.println(query1);

			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query1);				// Statement 객체를 통한 질의 실행
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// 자원 반납
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
					  	+ "WHERE ename like '" + pattern + "'";	// 문자열 값 앞뒤에 작은따옴표 필요
			System.out.println(query2);
			
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query2);				// Statement 객체를 통한 질의 실행
			System.out.println();
			while (rs.next()) {							// 커서를 통해 한 행씩 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + " " + job + " " + dname);
			}
			System.out.println();
			rs.close();

			String query3 = "SELECT ename, job, dname "
				  	+ "FROM emp JOIN dept USING (deptno) "
				  	+ "WHERE ename like ?";			// parameter를 포함하는 SQL 질의 작성
			System.out.println(query3);

			pStmt = conn.prepareStatement(query3);	// Connection에서 PreparedStatement 객체 생성
			pStmt.setString(1, pattern);				// PreparedStatement에 매개변수 값 설정
			rs = pStmt.executeQuery();				// 질의 실행 (주의: parameter 없음!!)			
			System.out.println();
			while (rs.next()) {						// 커서를 통해 한 행씩 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + " " + job + " " + dname);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// 자원 반납
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