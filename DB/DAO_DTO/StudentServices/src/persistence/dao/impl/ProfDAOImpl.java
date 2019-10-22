package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import persistence.dao.ProfDAO;
import service.dto.ProfDTO;

public class ProfDAOImpl implements ProfDAO {

	private JDBCUtil jdbcUtil = null;
	
	private static String query = 	"SELECT PROFESSOR.P_CODE AS PROF_CODE, " +
	  								"       PROFESSOR.P_NAME AS PROF_NAME, " +
	  								"       PROFESSOR.P_PHONE_NO AS PROF_PHONE_NO, " +
	  								"       PROFESSOR.ROOM_NO AS PROF_ROOM_NO, " +
	  								"       PROFESSOR.D_CODE AS PROF_DEPT_CODE " +
	  								"FROM PROFESSOR ";
	
	public ProfDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}
	
	@Override
	public ProfDTO getProfByName(String name) {
		String searchQuery = query + "WHERE PROFESSOR.P_NAME = ?";
		Object[] param = new Object[] {name};
		
		jdbcUtil.setSql(searchQuery);
		jdbcUtil.setParameters(param);
	
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			ProfDTO dto = new ProfDTO();
			while (rs.next()) {
				dto.setPCode(rs.getString("PROF_CODE"));
				dto.setName(rs.getString("PROF_NAME"));
				dto.setPhoneNo(rs.getString("PROF_PHONE_NO"));
				dto.setRoomNo(rs.getString("PROF_ROOM_NO"));
				dto.setDCode(rs.getString("PROF_DEPT_CODE"));
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
	public List<ProfDTO> getProfList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProf(ProfDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProf(ProfDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteProf(int pCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ProfDTO getProfByCode(String pCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
