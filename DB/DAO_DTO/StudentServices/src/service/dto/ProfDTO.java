package service.dto;


// 교수정보를 저장하기 위한 DTO
public class ProfDTO {
	private String pCode;		// 교수코드
	private String name;		// 교수명
	private String phoneNo;		// 연락처
	private String roomNo;		// 연구실 위치
	private String dCode;		// 교수 소속 학과 코드
	
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
