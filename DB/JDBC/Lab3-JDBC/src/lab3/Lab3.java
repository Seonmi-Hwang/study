package lab3;

import java.sql.*;
import java.util.Scanner;

public class Lab3 {

	public Lab3() {	// ������ 
		// JDBC ����̹� �ε� �� ���
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

		// DBMS���� ���� ȹ��
		Connection conn = null; // connection�� ��Ȱ���� ����! Ŀ�ؼ��� �ִ���  if������ Ȯ���ϴ� ������ ��ġ��.
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
				int deptNo = rs.getInt("deptno"); // column �̸� ��Ȯ�ϰ� �Է�
				int managerNo = rs.getInt("manager");
				
				System.out.println("�μ���ȣ : " + deptNo);
				System.out.println("�μ������ڹ�ȣ : " + managerNo);	
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
			
			System.out.println("���\t�̸�\t����\t\t����\t����");
			System.out.println("=====================================================");
			while (rs.next()) {
				int empNo = rs.getInt("empno"); // column �̸� ��Ȯ�ϰ� �Է�
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
			
			System.out.println("���\t�̸�\t����\t\t����\t����\t�ҼӺμ���");
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
		
		System.out.print("�μ����� �Է��Ͻÿ�: ");
		String deptName = scanner.next();
		
		// printDeptInfo �޼ҵ� ȣ��
		printDeptInfo(deptName);
		
		// printEmployeesInDept �޼ҵ� ȣ��
		printEmployeesInDept(deptName);
		
		System.out.print("�� ������ ����� ������ ������ �Է��Ͻÿ�: ");
		int managerNo  = scanner.nextInt();
		double commission = scanner.nextDouble();
	
		// ������ ���(int)�� ������ ����(double)�� �Է¹���
		
		// replaceManagerOfDept �޼ҵ� ȣ�� 
		replaceManagerOfDept(deptName, managerNo, commission);
		
		System.out.println("�� ������ ����: ");

		// printEmpInfo �޼ҵ� ȣ��		
		printEmpInfo(managerNo);
		
		scanner.close();
	}
}
