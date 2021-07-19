package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favorite")

public class Favorite {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String dish;
	private String email;
	private Boolean fav;

	//コンストラクタ
	public Favorite() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getDish() {
		return dish;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getFav() {
		return fav;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFav(Boolean fav) {
		this.fav = fav;
	}

	public Favorite(int id, String dish, String email, Boolean fav) {
		super();
		this.id = id;
		this.dish = dish;
		this.email = email;
		this.fav = fav;
	}

	public Favorite(String dish, String email, Boolean fav) {
		super();
		this.dish = dish;
		this.email = email;
		this.fav = fav;
	}




}