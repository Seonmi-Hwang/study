package persistence.dao;

import java.util.List;

import service.dto.DeptDTO;

// 학과 정보를 처리하기 위한 DAO
public interface DeptDAO {
	public List<DeptDTO> getDeptList();		// 전체 학과 정보를 획득
	public int insertDept(DeptDTO dept);	// 학과정보를 추가
	public int updateDept(DeptDTO dept);	// 학과정보를 수정
	public int deleteDept(int dCode);		// 학과정보를 삭제
	public DeptDTO getDeptByName(String dName);		// 학과정보를 학과명으로 찾음
	public DeptDTO getDeptByCode(String dCode);		// 학과정보를 학과코드로 찾음
}
