package service;

import java.util.List;

import service.dto.StudentDTO;


public interface StudentService {				// �л������� ���� ������ �������̽�
	public List<StudentDTO> ListingStudents();		// ��ü �л������� List ���·� ��ȯ
	public StudentDTO getStudent(String name);		// ���� �ش��ϴ� �л� ������ ��ȯ(�������� ���ٰ� ����)
	public int insertStudent(StudentDTO stu);		// �л������� �߰�
	public int updateStudent(StudentDTO stu);		// �л������� ����
	public int deleteStudent(String stuNo);			// �л������� ����
}