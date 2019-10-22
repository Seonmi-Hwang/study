package persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import service.dto.*;
import persistence.DAOFactory;
import persistence.dao.*;

public class StudentDAOImpl implements StudentDAO {

	private JDBCUtil jdbcUtil = null;		// JDBCUtil ��ü�� �����ϱ� ���� ����
	
	// Student �� �⺻ ������ �����ϴ� query ��
	private static String query = "SELECT STUDENT.STU_NO AS STUDENT_NO, " +
								         "STUDENT.STU_NAME AS STUDENT_NAME, " +
								         "STUDENT.PASSWORD AS STUDENT_PASSWD, " +
								         "STUDENT.STU_PHONE_NO AS STUDENT_PHONE_NO, " +
								         "STUDENT.YEAR AS STUDENT_YEAR ";		
		
	// StudentDAOImpl ������
	public StudentDAOImpl() {			
		jdbcUtil = new JDBCUtil();		// StudentDAOImpl ��ü ���� �� JDBCUtil ��ü ����
	}
	
	// ��ü �л������� List �� ��ȯ�ϴ� �޼ҵ�
	public List<StudentDTO> getStudentList() {
		// �⺻ ������ ������ �������� �ڵ� �� �а��ڵ带 ��������  query ��
		String allQuery = query + ", " + "STUDENT.P_CODE AS STUDENT_P_CODE, " +
								         "STUDENT.D_CODE AS STUDENT_D_CODE  " + 
								    "FROM STUDENT ORDER BY STUDENT.STU_NO ASC ";		
		jdbcUtil.setSql(allQuery);		// JDBCUtil �� query ����
		
		try { 
			ResultSet rs = jdbcUtil.executeQuery();		// query �� ����			
			List<StudentDTO> list = new ArrayList<StudentDTO>();		// StudentDTO ��ü���� ������� list ��ü
			while (rs.next()) {	
				StudentDTO dto = new StudentDTO();		// �ϳ��� StudentDTO ��ü ���� �� ���� ����
				dto.setStuNo(rs.getString("STUDENT_NO"));
				dto.setStuName(rs.getString("STUDENT_NAME"));
				dto.setPwd(rs.getString("STUDENT_PASSWD"));
				dto.setStuPhoneNo(rs.getString("STUDENT_PHONE_NO"));
				dto.setYear(rs.getString("STUDENT_YEAR"));
				dto.setProfCode(rs.getString("STUDENT_P_CODE"));
				dto.setDeptCode(rs.getString("STUDENT_D_CODE"));
				list.add(dto);		// list ��ü�� ������ ������ StudentDTO ��ü ����
			}
			return list;		// �л������� ������ dto ���� ����� ��ȯ
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ
		}		
		return null;	
	}

	// �л��� �̸����� �л������� �˻��Ͽ� �ش��л��� ������ ���� �ִ� StudentDTO ��ü�� ��ȯ�ϴ� �޼ҵ�
	public StudentDTO getStudentByName(String name) {
		// �⺻ ������ ������ �������̺��� ����������, �а� ���̺��� �а����� �������� ���̺�
		String searchQuery = query + ", " + "PROFESSOR.P_NAME AS PROFESSOR_NAME, " +
		  							        "DEPARTMENT.D_NAME AS DEPT_NAME " +
		  							  "FROM STUDENT, PROFESSOR, DEPARTMENT " +
		  							  "WHERE STUDENT.STU_NAME = ? AND " +
		  							        "STUDENT.P_CODE = PROFESSOR.P_CODE AND " +
		  							        "STUDENT.D_CODE = DEPARTMENT.D_CODE ";	 
		jdbcUtil.setSql(searchQuery);				// JDBCUtil �� query �� ����
		Object[] param = new Object[] { name };		// �л��� ã�� ���� �������� �̸��� ����
		jdbcUtil.setParameters(param);				// JDBCUtil �� query ���� �Ű����� ������ ����� �Ű����� ����
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query �� ����
			StudentDTO stu = null;
			if (rs.next()) {						// ã�� �л��� ������ StudentDTO ��ü�� ����
				stu = new StudentDTO();
				stu.setStuNo(rs.getString("STUDENT_NO"));
				stu.setStuName(rs.getString("STUDENT_NAME"));
				stu.setPwd(rs.getString("STUDENT_PASSWD"));
				stu.setStuPhoneNo(rs.getString("STUDENT_PHONE_NO"));
				stu.setYear(rs.getString("STUDENT_YEAR"));
				stu.setDept(rs.getString("DEPT_NAME"));
				stu.setProfName(rs.getString("PROFESSOR_NAME"));
			}
			return stu;				// ã�� �л��� ������ ��� �ִ� StudentDTO ��ü ��ȯ
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ
		}
		return null;
	}

	// StudentDTO ��ü�� ��� �ִ� �л��� ������ ������� �л������� student ���̺� �����ϴ� �޼ҵ�
	public int insertStudent(StudentDTO stu) {
		int result = 0;
		String insertQuery = "INSERT INTO STUDENT (STU_NO, STU_NAME, PASSWORD, STU_PHONE_NO, YEAR, P_CODE, D_CODE) " +
							 "VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		DAOFactory factory = new DAOFactory();		// ���������� �а������� �˾ƿ��� ���� DAO ��ü�� �����ϴ� factory ��ü ����
		
		// ProfDAO ��ü�� �����Ͽ� �л������� ���ԵǾ� �ִ� ���������� �����ڵ带 �˾ƿ�
		ProfDAO profDAO = factory.getProfDAO();		// factory �� ���� ������ ���� DAO ȹ��
		ProfDTO profDTO = profDAO.getProfByName(stu.getProfName());		// ���� DAO �� �̸��� ����Ͽ� �����ڵ带 ������ �޼ҵ� ���
		String pCode = profDTO.getPCode();		// �����ڵ带 ����
		if (pCode == null) {
			System.out.println("�ش� ���������� �����ϴ�." + stu.getProfName());
			return 0;
		}
		
		// DeptDAO ��ü�� �����Ͽ� �л������� ���ԵǾ� �ִ� �а��� �а��ڵ带 �˾ƿ�
		DeptDAO deptDAO = factory.getDeptDAO();		// factory �� ���� �а��� ���� DAO ȹ��
		DeptDTO deptDTO = deptDAO.getDeptByName(stu.getDept());		// �а� DAO �� �а����� ����Ͽ� �а��ڵ带 ������ �޼ҵ� ���
		String dCode = deptDTO.getDCode();			// �а��ڵ带 ����
		if (dCode == null) {
			System.out.println("�ش� �а��� �����ϴ�.");
			return 0;
		}
		// query ���� ����� �Ű����� ���� ���� �Ű����� �迭 ����
		Object[] param = new Object[] {stu.getStuNo(), stu.getStuName(), 
							stu.getPwd(), stu.getStuPhoneNo(), stu.getYear(), pCode, dCode};		
		jdbcUtil.setSql(insertQuery);			// JDBCUtil �� insert �� ����
		jdbcUtil.setParameters(param);			// JDBCUtil �� �Ű����� ����
				
		try {				
			result = jdbcUtil.executeUpdate();		// insert �� ����
			System.out.println(stu.getStuNo() + " �й��� �л������� ���ԵǾ����ϴ�.");
		} catch (SQLException ex) {
			System.out.println("�Է¿��� �߻�!!!");
			if (ex.getErrorCode() == 1)
				System.out.println("������ �л������� �̹� �����մϴ�."); 
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ
		}		
		return result;		// insert �� ���� �ݿ��� ���ڵ� �� ��ȯ	
	}
	
	// StudentDTO ��ü�� �����Ǿ� �ִ� ������ ���� ���̺��� ������ �����ϴ� �޼ҵ�
	public int updateStudent(StudentDTO stu) {
		
		String updateQuery = "UPDATE STUDENT SET ";
		int index = 0;
		Object[] tempParam = new Object[10];		// update ���� ����� �Ű������� ������ �� �ִ� �ӽ� �迭
		
		if (stu.getStuName() != null) {		// �̸��� �����Ǿ� ���� ���
			updateQuery += "STU_NAME = ?, ";		// update ���� �̸� ���� �κ� �߰�
			tempParam[index++] = stu.getStuName();		// �Ű������� ������ �̸� �߰�
		}
		if (stu.getPwd() != null) {		// �н����尡 �����Ǿ� ���� ���
			updateQuery += "PASSWORD = ?, ";		// update ���� �н����� ���� �κ� �߰�
			tempParam[index++] = stu.getPwd();		// �Ű������� ������ �н����� �߰�
		}
		if (stu.getStuPhoneNo() != null) {		// �޴��� ��ȣ�� �����Ǿ� ���� ���
			updateQuery += "STU_PHONE_NO = ?, ";		// update ���� �޴��� ���� �κ� �߰�
			tempParam[index++] = stu.getStuPhoneNo();		// �Ű������� ������ �޴��� �߰�
		}
		if (stu.getYear() != null) {		// �г��� �����Ǿ� ���� ���
			updateQuery += "YEAR = ?, ";		// update ���� �г� ���� �κ� �߰�
			tempParam[index++] = stu.getYear();		// �Ű������� ������ �г� �߰�
		}
		if (stu.getProfCode() != null) {		// �����ڵ尡 �����Ǿ� ���� ���
			updateQuery += "P_CODE = ?, ";		// update ���� �������� ���� �κ� �߰�
			tempParam[index++] = stu.getProfCode();		// �Ű������� ������ ���������ڵ� �߰�
		}
		if (stu.getDeptCode() != null) {		// �а��ڵ尡 �����Ǿ� ���� ���
			updateQuery += "D_CODE = ?, ";		// update ���� �а� ���� �κ� �߰�
			tempParam[index++] = stu.getDeptCode();		// �Ű������� ������ �а��ڵ� �߰�
		}
		updateQuery += "WHERE STU_NO = ? ";		// update ���� ���� ����
		updateQuery = updateQuery.replace(", WHERE", " WHERE");		// update ���� where �� �տ� ���� �� �ִ� , ����
		
		tempParam[index++] = stu.getStuNo();		// ã�� ���ǿ� �ش��ϴ� �й��� ���� �Ű����� �߰�
		
		Object[] newParam = new Object[index];	// ��Ȯ�� ������ ��ŭ�� �����͸� �ֱ� ���� ���ο� �迭 ����.
		for (int i=0; i < newParam.length; i++)		// �Ű������� ������ŭ�� ũ�⸦ ���� �迭�� �����ϰ� �Ű����� �� ����
			newParam[i] = tempParam[i];
		
		jdbcUtil.setSql(updateQuery);			// JDBCUtil�� update �� ����
		jdbcUtil.setParameters(newParam);		// JDBCUtil �� �Ű����� ����
		
		try {
			int result = jdbcUtil.executeUpdate();		// update �� ����
			return result;			// update �� ���� �ݿ��� ���ڵ� �� ��ȯ
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ -> �ڵ� ����~
		}		
		return 0;
	}
	
	// �й��� ���޹޾� �ش� �й��� �л� ������ �����ϴ� �޼ҵ�
	public int deleteStudent(String stuNo) {
		String deleteQuery = "DELETE FROM STUDENT WHERE STU_NO = ?";
		
		jdbcUtil.setSql(deleteQuery);			// JDBCUtil �� query �� ����
		Object[] param = new Object[] {stuNo};
		jdbcUtil.setParameters(param);			// JDBCUtil �� �Ű����� ����
		
		try {
			int result = jdbcUtil.executeUpdate();		// delete �� ����
			return result;						// delete �� ���� �ݿ��� ���ڵ� �� ��ȯ
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();		
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ
		}
		return 0;
	}

}
