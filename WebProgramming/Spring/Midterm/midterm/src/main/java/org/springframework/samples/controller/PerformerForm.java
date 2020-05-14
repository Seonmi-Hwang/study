package org.springframework.samples.controller;

import org.springframework.samples.model.Address;
import org.springframework.samples.model.PerformerType;

public class PerformerForm {
	private String name;
	private Address address;
	private String email;
	private String password;
	private String confirmPassword;
	private String phoneNumber;
	private PerformerType type;
	private String title;
	private String time;
	private boolean first;
	
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isSamePasswordConfirmPassword() {
		if (password == null || confirmPassword == null)
			return false;
		return password.equals(confirmPassword);
	}

	public boolean hasPassword() {
		return password != null && password.trim().length() > 0;
	}

	@Override
	public String toString() {
		return "PerformerForm [name=" + name + ", address=" + address + ", email=" + email + ", password="
				+ password + ", confirmPassword=" + confirmPassword + ", phoneNumber=" + phoneNumber + ", type=" + type
				+ ", title=" + title + ", time=" + time + ", first=" + first + "]";
	}
}
