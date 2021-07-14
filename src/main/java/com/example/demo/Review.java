package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review")

public class Review {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int code;
	private String review;
	private String name;

	//コンストラクタ
	public Review() {

	}


	public Review(int code, String review, String name) {
		super();
		this.code = code;
		this.review = review;
		this.name = name;
	}


	public Review(int id, int code, String review, String name) {
		super();
		this.id = id;
		this.code = code;
		this.review = review;
		this.name = name;
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
	public int getId() {
		return id;
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

	public void setId(int id) {
		this.id = id;
	}

}
