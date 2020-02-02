package dduwcom.mobile.ma02_20170995.contact;

import java.io.Serializable;

public class ContactDto implements Serializable {

	private long id;
	private String name;
	private String phone;


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

	@Override
	public String toString() {
		return name + " (" + phone + ")";
	}
	
}
