package mobile.database.dbtest02;

import java.io.Serializable;

/*
하나의 주소 정보를 저장하기 위한 DTO
Intent 에 저장 가능하게 하기 위하여
Serializable 인터페이스를 구현함
*/

public class ContactDto implements Serializable {

	private long id;
	private String name;
	private String phone;
	private String category;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return id + ". " + category + " - " + name + " (" + phone + ")";
	}
	
}
