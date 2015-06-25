package com.yc.model;

public class CommodityModel {
	
	private int sums;
	
	private int categoryID;
	
	private String category;
	
	private int shopCommodityId;
	
	private int sellerId;
	
	private String sellerName;
	
	private String commodityName;
	
	private String path;
	
	private float price;
	
	private String describes;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSums() {
		return sums;
	}

	public void setSums(int sums) {
		this.sums = sums;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public int getShopCommodityId() {
		return shopCommodityId;
	}

	public void setShopCommodityId(int shopCommodityId) {
		this.shopCommodityId = shopCommodityId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	
}
