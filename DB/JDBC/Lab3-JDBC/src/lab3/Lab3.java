package lab3;

import java.sql.*;
import java.util.Scanner;

public class Lab3 {

	public Lab3() {	// 생성자 
		// JDBC 드라이버 로딩 및 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
	}
	
	private static Connection getConnection() {
		String url = // "jdbc:oracle:thin:@localhost:1521:xe";
					"jdbc:oracle:thin:@202.20.119.117:1521:orcl";			
		String user = "scott";
		String passwd = "TIGER";

		// DBMS와의 연결 획득
		Connection conn = null; // connection도 재활용이 가능! 커넥션이 있는지  if문으로 확인하는 과정을 거치자.
		try {
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		return conn;
	}
	
	public static void printDeptInfo(String deptName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT d.deptno, manager, count(empno) numOfEmp "
					  + "FROM depts0995 d, emps0995 e "
					  + "WHERE d.deptno = e.deptno AND dname = ? "
					  + "GROUP BY d.deptno, manager";

		conn = getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, deptName);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int deptNo = rs.getInt("deptno"); // column 이름 정확하게 입력
				int managerNo = rs.getInt("manager");
				
				System.out.println("부서번호 : " + deptNo);
				System.out.println("부서관리자번호 : " + managerNo);	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pstmt != null) 
				try { 
					pstmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}
		
	}
	
	public static void printEmployeesInDept(String deptName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT empno, ename, job, sal, comm "
				 	  + "FROM depts0995 d, emps0995 e "
					  + "WHERE e.deptno = d.deptno AND dname = ?";
		
		conn = getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, deptName);
			rs = pstmt.executeQuery();
			
			System.out.println("사번\t이름\t직무\t\t월급\t수당");
			System.out.println("=====================================================");
			while (rs.next()) {
				int empNo = rs.getInt("empno"); // column 이름 정확하게 입력
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				double salary = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				
				System.out.printf("%d %10s %10s %10.2f %10.2f\n", empNo, ename, job, salary, comm);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pstmt != null) 
				try { 
					pstmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}
		
	}

	
	public static void replaceManagerOfDept(String deptName, int managerNo, double comm) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query1 = "UPDATE depts0995 "
				 	  + "SET manager = ? "
					  + "WHERE dname = ?";
		
		String query2 = "UPDATE emps0995 "
					  + "SET comm = NVL(comm, 0) + ? "
					  + "WHERE empno = ?";
		
		conn = getConnection();
		
		try {
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, managerNo);
			pstmt.setString(2, deptName);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(query2);
			pstmt.setDouble(1, comm);
			pstmt.setInt(2, managerNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) 
				try { 
					pstmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}
		
	}
	
	public static void printEmpInfo(int empNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT empno, ename, job, sal, comm, dname "
			 	  + "FROM depts0995 d, emps0995 e "
				  + "WHERE e.deptno = d.deptno AND empno = ?";

		conn = getConnection();
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			rs = pstmt.executeQuery();
			
			System.out.println("사번\t이름\t직무\t\t월급\t수당\t소속부서명");
			System.out.println("================================================================");
			if (rs.next()) {
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				double salary = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				String dname = rs.getString("dname");
				
				System.out.printf("%d %10s %10s %10.2f %10.2f	%10s\n", empNo, ename, job, salary, comm, dname);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pstmt != null) 
				try { 
					pstmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}
		
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("부서명을 입력하시오: ");
		String deptName = scanner.next();
		
		// printDeptInfo 메소드 호출
		printDeptInfo(deptName);
		
		// printEmployeesInDept 메소드 호출
		printEmployeesInDept(deptName);
		
		System.out.print("새 관리자 사번과 관리자 수당을 입력하시오: ");
		int managerNo  = scanner.nextInt();
		double commission = scanner.nextDouble();
	
		// 관리자 사번(int)과 관리자 수당(double)을 입력받음
		
		// replaceManagerOfDept 메소드 호출 
		replaceManagerOfDept(deptName, managerNo, commission);
		
		System.out.println("새 관리자 정보: ");

		// printEmpInfo 메소드 호출		
		printEmpInfo(managerNo);
		
		scanner.close();
	}
}
