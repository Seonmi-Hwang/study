package persistence.dao;

import java.util.List;
import service.dto.StudentDTO;

// 학생정보 DAO : Interface 부분
public interface StudentDAO {
	
	// 전체 학생정보를 StudentDTO 의 List 로 반환하는 메소드
	public List<StudentDTO> getStudentList();
	
	// StudentDTO 에 담긴 학생정보를 Data Source 에 추가하는 메소드
	public int insertStudent(StudentDTO stu);
	
	// 학생정보를 수정하는 메소드
	public int updateStudent(StudentDTO stu);
	
	// 학생정보를 삭제하는 메소드
	public int deleteStudent(String stuNo);
	
	// 성명에 해당하는 학생정보를 반환하는 메소드
	public StudentDTO getStudentByName(String name);
}
