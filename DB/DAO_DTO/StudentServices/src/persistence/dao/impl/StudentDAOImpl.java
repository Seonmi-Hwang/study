package persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import service.dto.*;
import persistence.DAOFactory;
import persistence.dao.*;

public class StudentDAOImpl implements StudentDAO {

	private JDBCUtil jdbcUtil = null;		// JDBCUtil 객체를 지정하기 위한 변수
	
	// Student 의 기본 정보를 포함하는 query 문
	private static String query = "SELECT STUDENT.STU_NO AS STUDENT_NO, " +
								         "STUDENT.STU_NAME AS STUDENT_NAME, " +
								         "STUDENT.PASSWORD AS STUDENT_PASSWD, " +
								         "STUDENT.STU_PHONE_NO AS STUDENT_PHONE_NO, " +
								         "STUDENT.YEAR AS STUDENT_YEAR ";		
		
	// StudentDAOImpl 생성자
	public StudentDAOImpl() {			
		jdbcUtil = new JDBCUtil();		// StudentDAOImpl 객체 생성 시 JDBCUtil 객체 생성
	}
	
	// 전체 학생정보를 List 로 반환하는 메소드
	public List<StudentDTO> getStudentList() {
		// 기본 쿼리와 합쳐져 지도교수 코드 및 학과코드를 가져오는  query 문
		String allQuery = query + ", " + "STUDENT.P_CODE AS STUDENT_P_CODE, " +
								         "STUDENT.D_CODE AS STUDENT_D_CODE  " + 
								    "FROM STUDENT ORDER BY STUDENT.STU_NO ASC ";		
		jdbcUtil.setSql(allQuery);		// JDBCUtil 에 query 설정
		
		try { 
			ResultSet rs = jdbcUtil.executeQuery();		// query 문 실행			
			List<StudentDTO> list = new ArrayList<StudentDTO>();		// StudentDTO 객체들을 담기위한 list 객체
			while (rs.next()) {	
				StudentDTO dto = new StudentDTO();		// 하나의 StudentDTO 객체 생성 후 정보 설정
				dto.setStuNo(rs.getString("STUDENT_NO"));
				dto.setStuName(rs.getString("STUDENT_NAME"));
				dto.setPwd(rs.getString("STUDENT_PASSWD"));
				dto.setStuPhoneNo(rs.getString("STUDENT_PHONE_NO"));
				dto.setYear(rs.getString("STUDENT_YEAR"));
				dto.setProfCode(rs.getString("STUDENT_P_CODE"));
				dto.setDeptCode(rs.getString("STUDENT_D_CODE"));
				list.add(dto);		// list 객체에 정보를 설정한 StudentDTO 객체 저장
			}
			return list;		// 학생정보를 저장한 dto 들의 목록을 반환
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 반환
		}		
		return null;	
	}

	// 학생의 이름으로 학생정보를 검색하여 해당학생의 정보를 갖고 있는 StudentDTO 객체를 반환하는 메소드
	public StudentDTO getStudentByName(String name) {
		// 기본 쿼리와 합쳐져 교수테이블에서 지도교수명, 학과 테이블에서 학과명을 가져오는 테이블
		String searchQuery = query + ", " + "PROFESSOR.P_NAME AS PROFESSOR_NAME, " +
		  							        "DEPARTMENT.D_NAME AS DEPT_NAME " +
		  							  "FROM STUDENT, PROFESSOR, DEPARTMENT " +
		  							  "WHERE STUDENT.STU_NAME = ? AND " +
		  							        "STUDENT.P_CODE = PROFESSOR.P_CODE AND " +
		  							        "STUDENT.D_CODE = DEPARTMENT.D_CODE ";	 
		jdbcUtil.setSql(searchQuery);				// JDBCUtil 에 query 문 설정
		Object[] param = new Object[] { name };		// 학생을 찾기 위한 조건으로 이름을 설정
		jdbcUtil.setParameters(param);				// JDBCUtil 에 query 문의 매개변수 값으로 사용할 매개변수 설정
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 문 실행
			StudentDTO stu = null;
			if (rs.next()) {						// 찾은 학생의 정보를 StudentDTO 객체에 설정
				stu = new StudentDTO();
				stu.setStuNo(rs.getString("STUDENT_NO"));
				stu.setStuName(rs.getString("STUDENT_NAME"));
				stu.setPwd(rs.getString("STUDENT_PASSWD"));
				stu.setStuPhoneNo(rs.getString("STUDENT_PHONE_NO"));
				stu.setYear(rs.getString("STUDENT_YEAR"));
				stu.setDept(rs.getString("DEPT_NAME"));
				stu.setProfName(rs.getString("PROFESSOR_NAME"));
			}
			return stu;				// 찾은 학생의 정보를 담고 있는 StudentDTO 객체 반환
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 반환
		}
		return null;
	}

	// StudentDTO 객체에 담겨 있는 학생의 정보를 기반으로 학생정보를 student 테이블에 삽입하는 메소드
	public int insertStudent(StudentDTO stu) {
		int result = 0;
		String insertQuery = "INSERT INTO STUDENT (STU_NO, STU_NAME, PASSWORD, STU_PHONE_NO, YEAR, P_CODE, D_CODE) " +
							 "VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		DAOFactory factory = new DAOFactory();		// 교수정보와 학과정보를 알아오기 위해 DAO 객체를 생성하는 factory 객체 생성
		
		// ProfDAO 객체를 생성하여 학생정보에 포함되어 있는 지도교수의 교수코드를 알아옴
		ProfDAO profDAO = factory.getProfDAO();		// factory 를 통해 교수에 대한 DAO 획득
		ProfDTO profDTO = profDAO.getProfByName(stu.getProfName());		// 교수 DAO 의 이름을 사용하여 교수코드를 얻어오는 메소드 사용
		String pCode = profDTO.getPCode();		// 교수코드를 설정
		if (pCode == null) {
			System.out.println("해당 지도교수가 없습니다." + stu.getProfName());
			return 0;
		}
		
		// DeptDAO 객체를 생성하여 학생정보에 포함되어 있는 학과의 학과코드를 알아옴
		DeptDAO deptDAO = factory.getDeptDAO();		// factory 를 통해 학과에 대한 DAO 획득
		DeptDTO deptDTO = deptDAO.getDeptByName(stu.getDept());		// 학과 DAO 의 학과명을 사용하여 학과코드를 얻어오는 메소드 사용
		String dCode = deptDTO.getDCode();			// 학과코드를 설정
		if (dCode == null) {
			System.out.println("해당 학과가 없습니다.");
			return 0;
		}
		// query 문에 사용할 매개변수 값을 갖는 매개변수 배열 생성
		Object[] param = new Object[] {stu.getStuNo(), stu.getStuName(), 
							stu.getPwd(), stu.getStuPhoneNo(), stu.getYear(), pCode, dCode};		
		jdbcUtil.setSql(insertQuery);			// JDBCUtil 에 insert 문 설정
		jdbcUtil.setParameters(param);			// JDBCUtil 에 매개변수 설정
				
		try {				
			result = jdbcUtil.executeUpdate();		// insert 문 실행
			System.out.println(stu.getStuNo() + " 학번의 학생정보가 삽입되었습니다.");
		} catch (SQLException ex) {
			System.out.println("입력오류 발생!!!");
			if (ex.getErrorCode() == 1)
				System.out.println("동일한 학생정보가 이미 존재합니다."); 
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 반환
		}		
		return result;		// insert 에 의해 반영된 레코드 수 반환	
	}
	
	// StudentDTO 객체에 설정되어 있는 정보를 토대로 테이블의 정보를 수정하는 메소드
	public int updateStudent(StudentDTO stu) {
		
		String updateQuery = "UPDATE STUDENT SET ";
		int index = 0;
		Object[] tempParam = new Object[10];		// update 문에 사용할 매개변수를 저장할 수 있는 임시 배열
		
		if (stu.getStuName() != null) {		// 이름이 설정되어 있을 경우
			updateQuery += "STU_NAME = ?, ";		// update 문에 이름 수정 부분 추가
			tempParam[index++] = stu.getStuName();		// 매개변수에 수정할 이름 추가
		}
		if (stu.getPwd() != null) {		// 패스워드가 설정되어 있을 경우
			updateQuery += "PASSWORD = ?, ";		// update 문에 패스워드 수정 부분 추가
			tempParam[index++] = stu.getPwd();		// 매개변수에 수정할 패스워드 추가
		}
		if (stu.getStuPhoneNo() != null) {		// 휴대폰 번호가 설정되어 있을 경우
			updateQuery += "STU_PHONE_NO = ?, ";		// update 문에 휴대폰 수정 부분 추가
			tempParam[index++] = stu.getStuPhoneNo();		// 매개변수에 수정할 휴대폰 추가
		}
		if (stu.getYear() != null) {		// 학년이 설정되어 있을 경우
			updateQuery += "YEAR = ?, ";		// update 문에 학년 수정 부분 추가
			tempParam[index++] = stu.getYear();		// 매개변수에 수정할 학년 추가
		}
		if (stu.getProfCode() != null) {		// 교수코드가 설정되어 있을 경우
			updateQuery += "P_CODE = ?, ";		// update 문에 지도교수 수정 부분 추가
			tempParam[index++] = stu.getProfCode();		// 매개변수에 수정할 지도교수코드 추가
		}
		if (stu.getDeptCode() != null) {		// 학과코드가 설정되어 있을 경우
			updateQuery += "D_CODE = ?, ";		// update 문에 학과 수정 부분 추가
			tempParam[index++] = stu.getDeptCode();		// 매개변수에 수정할 학과코드 추가
		}
		updateQuery += "WHERE STU_NO = ? ";		// update 문에 조건 지정
		updateQuery = updateQuery.replace(", WHERE", " WHERE");		// update 문의 where 절 앞에 있을 수 있는 , 제거
		
		tempParam[index++] = stu.getStuNo();		// 찾을 조건에 해당하는 학번에 대한 매개변수 추가
		
		Object[] newParam = new Object[index];	// 정확한 사이즈 만큼만 데이터를 넣기 위해 새로운 배열 만듦.
		for (int i=0; i < newParam.length; i++)		// 매개변수의 개수만큼의 크기를 갖는 배열을 생성하고 매개변수 값 복사
			newParam[i] = tempParam[i];
		
		jdbcUtil.setSql(updateQuery);			// JDBCUtil에 update 문 설정
		jdbcUtil.setParameters(newParam);		// JDBCUtil 에 매개변수 설정
		
		try {
			int result = jdbcUtil.executeUpdate();		// update 문 실행
			return result;			// update 에 의해 반영된 레코드 수 반환
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 반환 -> 코드 간결~
		}		
		return 0;
	}
	
	// 학번을 전달받아 해당 학번의 학생 정보를 삭제하는 메소드
	public int deleteStudent(String stuNo) {
		String deleteQuery = "DELETE FROM STUDENT WHERE STU_NO = ?";
		
		jdbcUtil.setSql(deleteQuery);			// JDBCUtil 에 query 문 설정
		Object[] param = new Object[] {stuNo};
		jdbcUtil.setParameters(param);			// JDBCUtil 에 매개변수 설정
		
		try {
			int result = jdbcUtil.executeUpdate();		// delete 문 실행
			return result;						// delete 에 의해 반영된 레코드 수 반환
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();		
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 반환
		}
		return 0;
	}

}
