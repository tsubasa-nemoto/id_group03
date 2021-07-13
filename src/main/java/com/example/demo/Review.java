package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
	//フィールド
	@Id
	private int code;
	private String review;
	private String name;

	//コンストラクタ
	public Review() {

	}

	//ゲッターセッター
	public int getCode() {
		return code;
	}

	public String getReview() {
		return review;
	}

	public String getName() {
		return name;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setName(String name) {
		this.name = name;
	}

}
