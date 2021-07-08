package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class Recipe {
	//フィールド
	@Id
	private int code;
	private String dish;
	private String recipe;
	private String review;


	//コンストラクタ
	public Recipe() {

	}

	public Recipe(String dish) {
		this.dish = dish;
	}


	//ゲッタ・セッタ
	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}


}
