package org.springframework.samples.model;

public class PerformerInfo {

	private String id;
	private String name;
	private Address address;
	private String email;
	private String password;
	private String phoneNumber;
	private PerformerType type;
	private String title;
	private String time; // 공연 시간
	private String day; // 희망 공연 요일
	private boolean first; // 첫 공연 여부
	
	public PerformerInfo() {
	}

	public PerformerInfo(String id, String name, String email, 
			String password, String phoneNumber, Address address,
			PerformerType type, String title, String day, String time, boolean first) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.type = type;
		this.title = title;
		this.day = day;
		this.time = time;
		this.first = first;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public PerformerType getType() {
		return type;
	}

	public void setType(PerformerType type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public String getId() {
		return id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean matchPassword(String inputPassword) {
		return password.equals(inputPassword);
	}
}
