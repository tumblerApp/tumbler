package com.yc.model;

public class ShopReviewsModel {
	//评论等级  评论内容  用户名  （日期+时间）  追评 卖家回复 卖家追评回复
	private String userName;
    private String reviewsRan;
    private String date;
    private String reviews;
    private String businessreply;
    private String additionalReviews;
    private String additionalBusinessreply;
				
    public String getUserName() {
    	 return userName;
    }
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReviewsRan() {
		return reviewsRan;
	}
	public void setReviewsRan(String reviewsRan) {
		this.reviewsRan = reviewsRan;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public String getAdditionalReviews() {
		return additionalReviews;
	}
	public void setAdditionalReviews(String additionalReviews) {
		this.additionalReviews = additionalReviews;
	}
	public String getAdditionalBusinessreply() {
		return additionalBusinessreply;
	}
	public void setAdditionalBusinessreply(String additionalBusinessreply) {
		this.additionalBusinessreply = additionalBusinessreply;
	}
	public String getBusinessreply() {
		return businessreply;
	}
	public void setBusinessreply(String businessreply) {
		this.businessreply = businessreply;
	}
}
