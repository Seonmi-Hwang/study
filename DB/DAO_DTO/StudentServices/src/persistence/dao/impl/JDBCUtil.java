// Java Project �� JDBCUtil
// Web Project ���� ����ϱ� ���ؼ� DBCP �κ��� �����Ǿ�� ��
// DBCP ���� jar ������ ������Ʈ�� �����Ͽ��� ������ (properties ���� �߰��� ��, ���� ������ ��� ����)
// commons-collections-3.2.jar 
// commons-dbcp-1.2.2.jar
// commons-pool-1.3.jar

package persistence.dao.impl;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCUtil {
	private String sql = null; // ������ query
	private Object[] parameters = null;; // PreparedStatement �� �Ű����� ���� �����ϴ� �迭
	private static DataSource ds = null; // DBCP DataSource
	private static Connection conn = null;
	private PreparedStatement pstmt = null;
	private CallableStatement cstmt = null;
	private ResultSet rs = null;

	// �⺻ ������
	public JDBCUtil() {
		initJDBCUtil();
	}

	// �Ű����� ���� query�� ���޹޾� query�� �����ϴ� ������
	public JDBCUtil(String sql) {
		this.setSql(sql);
		initJDBCUtil();
	}

	// �Ű������� �迭�� �Բ� query�� ���޹޾� ������ �����ϴ� ������
	public JDBCUtil(String sql, Object[] parameters) {
		this.setSql(sql);
		this.setParameters(parameters);
		initJDBCUtil();
	}

	// DBCP ���� �ʱ�ȭ �޼ҵ�
	private static void initJDBCUtil() {
		try {
			if (ds == null) { // DBCP ����
				BasicDataSource bds = new BasicDataSource();
				bds.setDriverClassName("oracle.jdbc.driver.OracleDriver"); // Oracle �� JDBC Driver Ŭ����
				bds.setUsername("dbp"); // DB ���ӿ� ID
				bds.setPassword("dbp"); // DB ���ӿ� �н�����
				bds.setUrl("jdbc:oracle:thin:@202.20.119.117:1521:orcl"); // DBMS ���� �ּ�
				ds = bds;
				// Context init = new InitialContext();
				// ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDS");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// sql ���� getter
	public String getSql() {
		return this.sql;
	}

	// sql ���� setter
	public void setSql(String sql) {
		this.sql = sql;
	}

	// Object[] ���� setter
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	// �Ű����� �迭���� Ư����ġ�� �Ű������� ��ȯ�ϴ� �޼ҵ�
	private Object getParameter(int index) throws Exception {
		if (index >= getParameterSize())
			throw new Exception("INDEX ���� �Ķ������ �������� �����ϴ�.");
		return parameters[index];
	}

	// �Ű������� ������ ��ȯ�ϴ� �޼ҵ�
	private int getParameterSize() {
		return parameters == null ? 0 : parameters.length;
	}

	// ������ PreparedStatement �� ��ȯ
	private PreparedStatement getPreparedStatement() throws SQLException {
		if (conn == null) {
			conn = ds.getConnection();
			conn.setAutoCommit(false);	// Ʈ�����ǰ� ���õ� ����
		}
		if (pstmt != null) pstmt.close();
		pstmt = conn.prepareStatement(sql); // preparedStatement�� ������ �Ұ����ϴ�.
		// JDBCUtil.printDataSourceStats(ds);
		return pstmt;
	}

	// JDBCUtil �� ������ �Ű������� �̿��� executeQuery �� �����ϴ� �޼ҵ�
	public ResultSet executeQuery() {
		try {
			pstmt = getPreparedStatement();
			for (int i = 0; i < getParameterSize(); i++) {
				pstmt.setObject(i + 1, getParameter(i));
			}
			rs = pstmt.executeQuery();
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// JDBCUtil �� ������ �Ű������� �̿��� executeUpdate �� �����ϴ� �޼ҵ�
	public int executeUpdate() throws SQLException, Exception {
		pstmt = getPreparedStatement();
		int parameterSize = getParameterSize();
		for (int i = 0; i < parameterSize; i++) {
			if (getParameter(i) == null) { // �Ű����� ���� ���� �κ��� ���� ���
				pstmt.setString(i + 1, null);
			} else {
				pstmt.setObject(i + 1, getParameter(i));
			}
		}
		return pstmt.executeUpdate();
	}

	// ������ CallableStatement �� ��ȯ
	private CallableStatement getCallableStatement() throws SQLException {
		if (conn == null) {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		}
		if (cstmt != null) cstmt.close();
		cstmt = conn.prepareCall(sql);
		return cstmt;
	}

	// JDBCUtil �� ������ �Ű������� �̿��� CallableStatement �� execute �� �����ϴ� �޼ҵ�
	public boolean execute(JDBCUtil source) throws SQLException, Exception {
		cstmt = getCallableStatement();
		for (int i = 0; i < source.getParameterSize(); i++) {
			cstmt.setObject(i + 1, source.getParameter(i));
		}
		return cstmt.execute();
	}

	// �ڿ� ��ȯ
	public void close() {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
				pstmt = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (cstmt != null) {
			try {
				cstmt.close();
				cstmt = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void commit() {
		try {
			conn.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// DBCP Pool �� ����
	public void shutdownPool() {
		this.close();
		BasicDataSource bds = (BasicDataSource) ds;
		try {
			bds.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// ���� Ȱ��ȭ ������ Connection �� ������ ��Ȱ��ȭ ������ Connection ���� ���
	public void printDataSourceStats(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		System.out.println("NumActive: " + bds.getNumActive());
		System.out.println("NumIdle: " + bds.getNumIdle());
	}
}
