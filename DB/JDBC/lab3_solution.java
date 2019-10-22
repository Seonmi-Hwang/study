package lab3;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
 
public class Lab3 {
 
    public Lab3() { // 생성자
        // JDBC 드라이버 로딩 및 등록
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
 
    private static Connection getConnection() {
        String url = "jdbc:oracle:thin:@202.20.119.117:1521:orcl";
        // "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "scott";
        String passwd = "TIGER";
 
        // DBMS와의 연결 획득
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
 
    public static void printDeptInfo(String deptName) {
        Connection conn = null;
        PreparedStatement pStmt = null; // PreparedStatment 참조 변수 생성
        ResultSet rs = null;
        String query = "SELECT deptno, manager, COUNT(empno) AS num " + "FROM emps JOIN depts USING (deptno) "
                + "WHERE dname = ? " + "GROUP BY deptno, manager";
        try {
            conn = getConnection(); // DBMS와의 연결 획득
            pStmt = conn.prepareStatement(query); // Connection에서 PreparedStatement 객체 생성
            pStmt.setString(1, deptName); // PreparedStatement에 매개변수 값 설정
            rs = pStmt.executeQuery();
 
            System.out.println("<부서정보>");
            if (rs.next()) {
                int deptNo = rs.getInt("deptno");
                int managerNo = rs.getInt("manager");
                int numOfEmps = rs.getInt("num");
                System.out.println("부서번호: " + deptNo);
                System.out.println("부서명: " + deptName);
                System.out.println("관리자번호: " + managerNo);
                System.out.println("사원 수: " + numOfEmps);
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally { // 자원 반납
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (pStmt != null)
                try {
                    pStmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
 
    public static void printEmployeesInDept(String deptName) {
        Connection conn = null;
        PreparedStatement pStmt = null; // PreparedStatment 참조 변수 생성
        ResultSet rs = null;
        String query = "SELECT empno, ename, job, sal, comm " + "FROM emps JOIN depts USING (deptno) "
                + "WHERE dname = ?";
        try {
            conn = getConnection(); // DBMS와의 연결 획득
            pStmt = conn.prepareStatement(query); // Connection에서 PreparedStatement 객체 생성
            pStmt.setString(1, deptName); // PreparedStatement에 매개변수 값 설정
            rs = pStmt.executeQuery();
 
            System.out.println("사번      이름      직무        급여       수당");
            System.out.println("-------------------------------------------------");
            while (rs.next()) { // 커서를 통해 한 행씩 fetch
                int empNo = rs.getInt("empno");
                String empName = rs.getString("ename");
                String job = rs.getString("job");
                double sal = rs.getDouble("sal");
                double comm = rs.getDouble("comm");
                System.out.printf("%d %10s %10s %10.2f %10.2f\n", empNo, empName, job, sal, comm);
            }
            System.out.println();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally { // 자원 반납
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (pStmt != null)
                try {
                    pStmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
 
    public static void replaceManagerOfDept(String deptName, int managerNo, double comm) {
        Connection conn = null;
        PreparedStatement pStmt = null; // PreparedStatment 참조 변수 생성
        ResultSet rs = null;
        String query1 = "UPDATE depts " + "SET manager = ? " + "WHERE dname = ?";
 
        String query2 = "UPDATE emps " + "SET comm = NVL(comm,0) + ? " // comm이 null일 경우 0으로 처리하고 계산!
                + "WHERE empno = ?";
 
        try {
            conn = getConnection(); // DBMS와의 연결 획득
            pStmt = conn.prepareStatement(query1); // Connection에서 PreparedStatement 객체 생성
            pStmt.setInt(1, managerNo); // PreparedStatement에 매개변수 값 설정
            pStmt.setString(2, deptName);
            pStmt.executeUpdate();
            pStmt.close(); // pStmt가 가리키는 객체 close!
 
            pStmt = conn.prepareStatement(query2);
            pStmt.setDouble(1, comm); // PreparedStatement에 매개변수 값 설정
            pStmt.setInt(2, managerNo);
            pStmt.executeUpdate();
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally { // 자원 반납
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (pStmt != null)
                try {
                    pStmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
 
    public static void printEmpInfo(int empNo) {
        Connection conn = null;
        PreparedStatement pStmt = null; // PreparedStatment 참조 변수 생성
        ResultSet rs = null;
        String query = // parameter 없는 SQL 질의 작성
                "SELECT ename, job, sal, comm, dname " + "FROM emps JOIN depts USING (deptno) " + "WHERE empno = ?";
 
        try {
            conn = getConnection(); // DBMS와의 연결 획득
            pStmt = conn.prepareStatement(query); // Connection에서 PreparedStatement 객체 생성
            pStmt.setInt(1, empNo); // PreparedStatement에 매개변수 값 설정
            rs = pStmt.executeQuery();
 
            if (rs.next()) {
                String empName = rs.getString("ename");
                String job = rs.getString("job");
                double sal = rs.getDouble("sal");
                double comm = rs.getDouble("comm");
                String deptName = rs.getString("dname");
                System.out.printf("%s %10s %10.2f %10.2f %10s\n", empName, job, sal, comm, deptName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally { // 자원 반납
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (pStmt != null)
                try {
                    pStmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        System.out.print("부서명을 입력하시오: ");
        String deptName = scanner.next();
  
        printDeptInfo(deptName);
  
        printEmployeesInDept(deptName);
 
        System.out.print("새 관리자 사번과 관리자 수당을 입력하시오: ");
        int managerNo = scanner.nextInt();
        double commission = scanner.nextDouble();
 
        replaceManagerOfDept(deptName, managerNo, commission);
 
        System.out.println("새 관리자 정보: ");
 
        printEmpInfo(managerNo);
 
        scanner.close();
    }
}
