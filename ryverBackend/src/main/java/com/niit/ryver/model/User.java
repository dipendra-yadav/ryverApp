package com.niit.ryver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Entity
@Component
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull(message = "{register.pwd.invalid}")
	//@Size(min = 6, max = 8, message = "{register.pwd.invalid}")
	@Pattern(regexp = "{(?=.{8,8})(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])}")
	private String password;

	@NotNull
	private String emailId;

	@Column(unique = true, nullable = false)
	private String userName;

	@Column(name = "isonline")
	private String isOnline;

	// const
	public User() {
		super();
	}

	// getters+setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", emailId=" + emailId + ", userName=" + userName
				+ ", isOnline=" + isOnline + "]";
	}

}
