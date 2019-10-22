package persistence.dao;

import java.util.List;
import service.dto.StudentDTO;

// �л����� DAO : Interface �κ�
public interface StudentDAO {
	
	// ��ü �л������� StudentDTO �� List �� ��ȯ�ϴ� �޼ҵ�
	public List<StudentDTO> getStudentList();
	
	// StudentDTO �� ��� �л������� Data Source �� �߰��ϴ� �޼ҵ�
	public int insertStudent(StudentDTO stu);
	
	// �л������� �����ϴ� �޼ҵ�
	public int updateStudent(StudentDTO stu);
	
	// �л������� �����ϴ� �޼ҵ�
	public int deleteStudent(String stuNo);
	
	// ���� �ش��ϴ� �л������� ��ȯ�ϴ� �޼ҵ�
	public StudentDTO getStudentByName(String name);
}
