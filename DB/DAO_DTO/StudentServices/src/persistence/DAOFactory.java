package persistence;

import persistence.dao.*;
import persistence.dao.impl.*;

// DAO �� ������ Implementation ��ü�� �����ϴ� Ŭ����
public class DAOFactory {
	
	// StudentDAO �� ���� RDB �� DAO Implementation ��ü�� ��ȯ
	public StudentDAO getStudentDAO() {  // ������ �� �ִ� ����� �����غ���.
		return new StudentDAOImpl();		 
	}
	
	// DeptDAO �� ���� RDB �� DAO Implementation ��ü�� ��ȯ
	public DeptDAO getDeptDAO() {  // ������ �� �ִ� ����� �����غ���.
		return new DeptDAOImpl();		
	}
	
	// ProfDAO �� ���� RDB �� DAO Implementation ��ü�� ��ȯ
	public ProfDAO getProfDAO() {  // ������ �� �ִ� ����� �����غ���.
		return new ProfDAOImpl();		
	}
}
