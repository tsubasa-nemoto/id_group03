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
	private String name;
	private Boolean fav;

	//コンストラクタ
	public Favorite() {
		super();
	}


	public Favorite(String dish, String name, Boolean fav) {
		super();
		this.dish = dish;
		this.name = name;
		this.fav = fav;
	}


	public Favorite(int id, String dish, String name, Boolean fav) {
		super();
		this.id = id;
		this.dish = dish;
		this.name = name;
		this.fav = fav;
	}

	//ゲッターセッター

	public int getId() {
		return id;
	}


	public String getDish() {
		return dish;
	}


	public String getName() {
		return name;
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


	public void setName(String name) {
		this.name = name;
	}


	public void setFav(Boolean fav) {
		this.fav = fav;
	}






}