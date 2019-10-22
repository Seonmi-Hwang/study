// DAO�� ���� ������ ó���� �ϴ� Ŭ���� (���� SQL Query ���� X)
package service;

import java.util.List;
import persistence.DAOFactory;
import persistence.dao.StudentDAO;
import service.dto.StudentDTO;

public class StudentServiceImpl implements StudentService {		// �л����� ���� �������̽��� ����

	private StudentDAO dao = null;
	public StudentServiceImpl() {								// DAOFactory Ŭ������ ��ü ����
		DAOFactory factory = new DAOFactory();
		dao = factory.getStudentDAO();
	}
	public List<StudentDTO> ListingStudents() {					// StudentDAO �� ���� ��ü �л����� ����� ȹ��
		return dao.getStudentList();
	}
	public StudentDTO getStudent(String name) {					// StudentDAO �� ���� ���� �ش��ϴ� �л����� ȹ��		
		return dao.getStudentByName(name);
	}
	public int insertStudent(StudentDTO stu) { 					// StudentDAO �� ���� �л����� �߰�
		return dao.insertStudent(stu);
	}
	public int updateStudent(StudentDTO stu) {					// StudentDAO �� ���� �л����� ���� 		
		return dao.updateStudent(stu);
	}
	public int deleteStudent(String stuNo) {					// StudentDAO �� ���� �й��� �ش��ϴ� �л����� ����
		return dao.deleteStudent(stuNo);
	}
}