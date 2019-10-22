package persistence.dao;

import java.util.List;

import service.dto.ProfDTO;



// 교수정보를 담당하는 DAO
public interface ProfDAO {
	public List<ProfDTO> getProfList();				// 전체 교수 정보를 획득 
	public int insertProf(ProfDTO dept);			// 교수정보 추가
	public int updateProf(ProfDTO dept);			// 교수정보 수정
	public int deleteProf(int pCode);				// 교수정보 삭제
	public ProfDTO getProfByName(String name);		// 교수정보를 교수성명으로 찾음
	public ProfDTO getProfByCode(String pCode);		// 교수정보를 교수코드로 찾음
}
