// DAO를 통한 데이터 처리를 하는 클래스 (직접 SQL Query 실행 X)
package service;

import java.util.List;
import persistence.DAOFactory;
import persistence.dao.StudentDAO;
import service.dto.StudentDTO;

public class StudentServiceImpl implements StudentService {		// 학생정보 관리 인터페이스의 구현

	private StudentDAO dao = null;
	public StudentServiceImpl() {								// DAOFactory 클래스의 객체 생성
		DAOFactory factory = new DAOFactory();
		dao = factory.getStudentDAO();
	}
	public List<StudentDTO> ListingStudents() {					// StudentDAO 를 통해 전체 학생정보 목록을 획득
		return dao.getStudentList();
	}
	public StudentDTO getStudent(String name) {					// StudentDAO 를 통해 성명에 해당하는 학생정보 획득		
		return dao.getStudentByName(name);
	}
	public int insertStudent(StudentDTO stu) { 					// StudentDAO 를 통해 학생정보 추가
		return dao.insertStudent(stu);
	}
	public int updateStudent(StudentDTO stu) {					// StudentDAO 를 통해 학생정보 수정 		
		return dao.updateStudent(stu);
	}
	public int deleteStudent(String stuNo) {					// StudentDAO 를 통해 학번에 해당하는 학생정보 삭제
		return dao.deleteStudent(stuNo);
	}
}