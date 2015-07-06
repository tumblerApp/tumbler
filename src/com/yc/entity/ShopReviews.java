package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yc.entity.user.AppUser;

//评论
@Entity
@DiscriminatorValue("shopreviews")
@JsonIgnoreProperties(value = { "shopcommodity"})
public class ShopReviews {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String reviews;	//评论
	
	@Column
	private String additionalReviews;	//追加评论
	
	@Column
	private String businessreply; //店家回复
	
	@Column
	private String additionalBusinessreply; //追加店家回复

	
	@Column
	private String reviewsdate;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderForm orderForm; //用来标识评论与订单的关系
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private  AppUser user;
	
	@ManyToOne
	@JoinColumn(name = "shopcomm_id")
	private  ShopCommodity shopscommodity;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ReviewsRank reviewsRank;//评论等级
	
	@Column
	private String rankImagesPath;//评论等级路径

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
	public OrderForm getOrderForm() {
		return orderForm;
	}
	public void setOrderForm(OrderForm orderForm) {
		this.orderForm = orderForm;
	}
	public ReviewsRank getReviewsRank() {
		return reviewsRank;
	}
	public void setReviewsRank(ReviewsRank reviewsRank) {
		this.reviewsRank = reviewsRank;
	}
	public String getRankImagesPath() {
		return rankImagesPath;
	}
	public void setRankImagesPath(String rankImagesPath) {
		this.rankImagesPath = rankImagesPath;
	}
	public String getReviewsdate() {
		return reviewsdate;
	}
	public void setReviewsdate(String reviewsdate){
		this.reviewsdate = reviewsdate;
	}
	public AppUser getUser() {
		return user;
	}
	public void setUser(AppUser user) {
		this.user = user;
	}
	public ShopCommodity getShopscommodity() {
		return shopscommodity;
	}
	public void setShopscommodity(ShopCommodity shopscommodity) {
		this.shopscommodity = shopscommodity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public String getBusinessreply() {
		return businessreply;
	}
	public void setBusinessreply(String businessreply) {
		this.businessreply = businessreply;
	}
    
}
