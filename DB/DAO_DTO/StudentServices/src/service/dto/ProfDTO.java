package service.dto;


// ���������� �����ϱ� ���� DTO
public class ProfDTO {
	private String pCode;		// �����ڵ�
	private String name;		// ������
	private String phoneNo;		// ����ó
	private String roomNo;		// ������ ��ġ
	private String dCode;		// ���� �Ҽ� �а� �ڵ�
	
	public String getPCode() {
		return pCode;
	}
	public void setPCode(String code) {
		pCode = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getDCode() {
		return dCode;
	}
	public void setDCode(String code) {
		dCode = code;
	}
}
