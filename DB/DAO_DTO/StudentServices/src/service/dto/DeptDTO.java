package service.dto;

// �а� ������ ���� DTO
public class DeptDTO {
	private String dCode;			// �а��ڵ�
	private String faculty;			// �к�
	private String college;			// �ҼӴ���
	private String dName;			// �а���
	
	public String getDCode() {
		return dCode;
	}
	public void setDCode(String code) {
		dCode = code;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDName() {
		return dName;
	}
	public void setDName(String name) {
		dName = name;
	}	
}
