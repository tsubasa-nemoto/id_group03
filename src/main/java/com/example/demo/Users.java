package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class Users {
	//フィールド


	@Column(name="name")
	private String name;

	@Id
	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	//コンストラクタ
	public Users() {

	}

	public Users(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}



	//ゲッタ・セッタ

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}










}
