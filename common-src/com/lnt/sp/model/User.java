
package com.lnt.sp.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Kavita Khanna
 *
 */
@Entity
@Table(name = "User")
public class User implements Serializable{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;	

	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	@Column(name = "PASSWORD", length = 50) 
	private String password;
	
	@Column(name = "PHONE_NUM", length = 15)
	private String phoneNum;

	@Column(name = "ADDRESS", length = 300)
	private String address;
	
	@Column(name = "CITY", length = 50)
	private String city;
	
	@Column(name = "STATE", length = 50)
	private String state;
	
	@Column(name = "COUNTRY", length = 50)
	private String country;
	
	@Column(name = "ZIPCODE")
	private int zipCode;

	@Column(name = "PRIMARY_EMAIL_ID", length = 50)
	private String primaryEmailId;

	@Column(name = "ACTIVE")
	private boolean  active;

	@Column(name = "QUESTION")
	private String sQuestion;

	@Column(name = "ANSWER")
	private String sAnswer;
	
	
	public User() {
	}
	
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
	
	public boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
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

}
