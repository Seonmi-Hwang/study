// Java Project 용 JDBCUtil
// DBCP2 관련 jar 파일을 프로젝트에 포함해야 동작함
// commons-dbcp2-X.X.X.jar, commons-pool2-X.X.X.jar, commons-logging-X.X.jar
package model.dao;

import java.sql.*;

public class JDBCUtil {
	private static ConnectionManager connMan = new ConnectionManager();  
	private String sql = null; 				// 실행할 query
	private Object[] parameters = null;; 	// PreparedStatement 의 매개변수 값을 저장하는 배열
	private static Connection conn = null;
	private PreparedStatement pstmt = null;
	private CallableStatement cstmt = null;
	private ResultSet rs = null;
	private int resultSetType = ResultSet.TYPE_FORWARD_ONLY,
				resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;

	// 기본 생성자
	public JDBCUtil() {	}

	/*
	// 매개변수 없는 query를 전달받아 query를 설정하는 생성자
	public JDBCUtil(String sql) {
		this.setSql(sql);		
	}

	// 매개변수의 배열과 함께 query를 전달받아 각각을 설정하는 생성자
	public JDBCUtil(String sql, Object[] parameters) {
		this.setSql(sql);
		this.setParameters(parameters);
	}
	
	// sql 변수 setter
	public void setSql(String sql) {
		this.sql = sql;
	}

	// Object[] 변수 setter
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	*/
	
	// sql 변수 getter
	public String getSql() {
		return this.sql;
	}

	// 매개변수 배열에서 특정위치의 매개변수를 반환하는 메소드
	private Object getParameter(int index) throws Exception {
		if (index >= getParameterSize())
			throw new Exception("INDEX 값이 파라미터의 갯수보다 많습니다.");
		return parameters[index];
	}

	// 매개변수의 개수를 반환하는 메소드
	private int getParameterSize() {
		return parameters == null ? 0 : parameters.length;
	}

	// sql 및 Object[] 변수 setter
	public void setSqlAndParameters(String sql, Object[] parameters) {
		this.sql = sql;
		this.parameters = parameters;
		this.resultSetType = ResultSet.TYPE_FORWARD_ONLY;
		this.resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
	}
	
	// sql 및 Object[], resultSetType, resultSetConcurrency 변수 setter
	public void setSqlAndParameters(String sql, Object[] parameters, int resultSetType, int resultSetConcurrency) {
		this.sql = sql;
		this.parameters = parameters;
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;
	}

	// 현재의  PreparedStatement를 반환
	private PreparedStatement getPreparedStatement() throws SQLException {
		if (conn == null) {
			conn = connMan.getConnection();
			conn.setAutoCommit(false);
		}
		if (pstmt != null) pstmt.close();
		pstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
		// JDBCUtil.printDataSourceStats(ds);
		return pstmt;
	}

	// JDBCUtil의 쿼리와 매개변수를 이용해  executeQuery를 수행하는 메소드
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

	// JDBCUtil의 쿼리와 매개변수를 이용해 executeUpdate를 수행하는 메소드
	public int executeUpdate() throws SQLException, Exception {
		pstmt = getPreparedStatement();
		int parameterSize = getParameterSize();
		for (int i = 0; i < parameterSize; i++) {
			if (getParameter(i) == null) { // 매개변수 값이 널이 부분이 있을 경우
				pstmt.setString(i + 1, null);
			} else {
				pstmt.setObject(i + 1, getParameter(i));
			}
		}
		return pstmt.executeUpdate();
	}

	// 현재의  CallableStatement를 반환
	private CallableStatement getCallableStatement() throws SQLException {
		if (conn == null) {
			conn = connMan.getConnection();
			conn.setAutoCommit(false);
		}
		if (cstmt != null) cstmt.close();
		cstmt = conn.prepareCall(sql);
		return cstmt;
	}

	// JDBCUtil의 쿼리와 매개변수를 이용해  CallableStatement의  execute를 수행하는 메소드
	public boolean execute(JDBCUtil source) throws SQLException, Exception {
		cstmt = getCallableStatement();
		for (int i = 0; i < source.getParameterSize(); i++) {
			cstmt.setObject(i + 1, source.getParameter(i));
		}
		return cstmt.execute();
	}

	// 자원 반환
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

	// DataSource 를 종료
	public void shutdownPool() {
		this.close();
		connMan.close();
	}

	// 현재 활성화 상태인 Connection 의 개수와 비활성화 상태인 Connection 개수 출력
	public void printDataSourceStats() {
		connMan.printDataSourceStats();
	}
}
