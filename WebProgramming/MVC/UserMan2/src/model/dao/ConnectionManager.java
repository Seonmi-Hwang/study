package model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@202.20.119.117:1521:orcl";
    private static final String DB_USERNAME = "dbp";
    private static final String DB_PASSWORD = "dbp";
    private static DataSource ds = null;
    
    public ConnectionManager() {
    	try {
			// DataSource 생성 및 설정
			BasicDataSource bds = new BasicDataSource();
	        bds.setDriverClassName(DB_DRIVER);
	        bds.setUrl(DB_URL);
	        bds.setUsername(DB_USERNAME);
	        bds.setPassword(DB_PASSWORD);     
			ds = bds;
			
			// 참고: WAS의 DataSource를 이용할 경우: 
			// Context init = new InitialContext();
			// ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDS");
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	   
    }

    public Connection getConnection() {
    	Connection conn = null;
    	try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
    }
    
    public void close() {
		BasicDataSource bds = (BasicDataSource) ds;
		try {
			bds.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 현재 활성화 상태인 Connection 의 개수와 비활성화 상태인 Connection 개수 출력
	public void printDataSourceStats() {
		try {
			BasicDataSource bds = (BasicDataSource) ds;
			System.out.println("NumActive: " + bds.getNumActive());
			System.out.println("NumIdle: " + bds.getNumIdle());
		} catch (Exception ex) {
			ex.printStackTrace();
		}   
	}
}
