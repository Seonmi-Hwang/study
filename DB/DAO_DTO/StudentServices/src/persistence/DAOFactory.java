package persistence;

import persistence.dao.*;
import persistence.dao.impl.*;

// DAO 를 구현한 Implementation 객체를 생성하는 클래스
public class DAOFactory {
	
	// StudentDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public StudentDAO getStudentDAO() {  // 재사용할 수 있는 방법을 생각해보자.
		return new StudentDAOImpl();		 
	}
	
	// DeptDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public DeptDAO getDeptDAO() {  // 재사용할 수 있는 방법을 생각해보자.
		return new DeptDAOImpl();		
	}
	
	// ProfDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public ProfDAO getProfDAO() {  // 재사용할 수 있는 방법을 생각해보자.
		return new ProfDAOImpl();		
	}
}
