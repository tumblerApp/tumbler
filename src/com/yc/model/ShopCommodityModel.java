package com.yc.model;

import java.util.List;

public class ShopCommodityModel {

	private int commCode;//商品编码
	
	private String commoidtyName;//商品名称
	
	private String commItem;// 货号
	
	private Float unitPrice = 0f;//单价
	
	private Float specialPrice = 0f; //活动价
	
	private int stock = 1 ;//库存总数量
	
	private String categoryName;//商品类别名
	
	private String brandName;//品牌表
	
	private int belongTo;//商品所属店面ID
	
	private String shopName;//商品所属店名
	
	private List<String> imagePaths;//商品照片路径
	
	private String ShopCommImage; //商品路径
	
	private List<String> reviews;//商品评价
	
	private String describes;//描述
	
	private String commSpec; //商品规格，如年份、酒精度等
	
	private Boolean isActivity; //是否参加活动
	
	private String activityName; //活动名称
	
	private int activityAmount = 0; //参加活动的数量
	
	private Float special = 1f; //打几折
	
	private Boolean shelves = true;

	public int getCommCode() {
		return commCode;
	}

	public void setCommCode(int commCode) {
		this.commCode = commCode;
	}

	public String getCommoidtyName() {
		return commoidtyName;
	}

	public void setCommoidtyName(String commoidtyName) {
		this.commoidtyName = commoidtyName;
	}

	public String getCommItem() {
		return commItem;
	}

	public void setCommItem(String commItem) {
		this.commItem = commItem;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(int belongTo) {
		this.belongTo = belongTo;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Float getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Float specialPrice) {
		this.specialPrice = specialPrice;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(List<String> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public List<String> getReviews() {
		return reviews;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Boolean getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Boolean isActivity) {
		this.isActivity = isActivity;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(int activityAmount) {
		this.activityAmount = activityAmount;
	}

	public Float getSpecial() {
		return special;
	}

	public void setSpecial(Float special) {
		this.special = special;
	}

	public String getShopCommImage() {
		return ShopCommImage;
	}

	public void setShopCommImage(String shopCommImage) {
		ShopCommImage = shopCommImage;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Boolean getShelves() {
		return shelves;
	}

	public void setShelves(Boolean shelves) {
		this.shelves = shelves;
	}
}
