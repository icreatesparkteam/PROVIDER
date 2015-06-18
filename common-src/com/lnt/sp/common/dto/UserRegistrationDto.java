package com.lnt.sp.common.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.model.User;

@XmlRootElement
public class UserRegistrationDto {

	private int id;	

	private String userName;
	
	private String password;
	
	private String phoneNum;

	private String address;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private int zipCode;

	private String primaryEmailId;

	private boolean  active;

	private String sQuestion;

	private String sAnswer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getPrimaryEmailId() {
		return primaryEmailId;
	}

	public void setPrimaryEmailId(String primaryEmailId) {
		this.primaryEmailId = primaryEmailId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getsQuestion() {
		return sQuestion;
	}

	public void setsQuestion(String sQuestion) {
		this.sQuestion = sQuestion;
	}

	public String getsAnswer() {
		return sAnswer;
	}

	public void setsAnswer(String sAnswer) {
		this.sAnswer = sAnswer;
	}
	
	public User toUser(User user) throws ServiceApplicationException {
		user.setUserName(this.getUserName());
		user.setPhoneNum(this.getPhoneNum());
		user.setPrimaryEmailId(this.getPrimaryEmailId());
		user.setAddress(this.getAddress());
		user.setCity(this.getCity());
		user.setState(this.getState());
		user.setCountry(this.getCountry());
		return user;
	}

	public UserRegistrationDto fromUser(User user) {
		this.userName = user.getUserName();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.state = user.getState();
		this.country = user.getCountry();
		this.phoneNum = user.getPhoneNum();
		this.primaryEmailId = user.getPrimaryEmailId();
		return this;
	}

	
}
