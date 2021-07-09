package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food")
public class Recipe {
	//フィールド
	@Id
	private int code;
	private String dish;
	private String recipe;
	private String review;
	private String image;





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

	public int getCode() {
		return code;
	}

	public String getRecipe() {
		return recipe;
	}

	public String getReview() {
		return review;
	}
	public String getImage() {
		return image;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	public void setReview(String review) {
		this.review = review;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
