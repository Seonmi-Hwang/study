package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import persistence.dao.DeptDAO;
import service.dto.DeptDTO;

public class DeptDAOImpl implements DeptDAO {

	private JDBCUtil jdbcUtil = null;
	
	private static String query = "SELECT DEPARTMENT.D_CODE AS DEPT_CODE, " +
								  "       DEPARTMENT.FACULTY AS DEPT_FAC, " +
								  "       DEPARTMENT.COLLEGE AS DEPT_COL, " +
								  "       DEPARTMENT.D_NAME AS DEPT_NAME " +
								  "FROM DEPARTMENT ";
		
	public DeptDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}	
	
	@Override
	public DeptDTO getDeptByName(String dName) {
		String searchQuery = query + "WHERE DEPARTMENT.D_NAME = ?";
		Object[] param = new Object[] {dName};

		jdbcUtil.setSql(searchQuery);
		jdbcUtil.setParameters(param);
	
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			DeptDTO dto = new DeptDTO();
			while (rs.next()) {
				dto.setDCode(rs.getString("DEPT_CODE"));
				dto.setFaculty(rs.getString("DEPT_FAC"));
				dto.setCollege(rs.getString("DEPT_COL"));
				dto.setDName(rs.getString("DEPT_NAME"));
			}
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	@Override
	public List<DeptDTO> getDeptList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertDept(DeptDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDept(DeptDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteDept(int dCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DeptDTO getDeptByCode(String dCode) {
		// TODO Auto-generated method stub
		return null;
	}	
}
