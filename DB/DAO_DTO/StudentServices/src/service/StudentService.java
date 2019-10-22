package service;

import java.util.List;

import service.dto.StudentDTO;


public interface StudentService {				// 학생정보를 관리 목적의 인터페이스
	public List<StudentDTO> ListingStudents();		// 전체 학생정보를 List 형태로 반환
	public StudentDTO getStudent(String name);		// 성명에 해당하는 학생 정보를 반환(동명이인 없다고 가정)
	public int insertStudent(StudentDTO stu);		// 학생정보를 추가
	public int updateStudent(StudentDTO stu);		// 학생정보를 갱신
	public int deleteStudent(String stuNo);			// 학생정보를 삭제
}