package com.yc.model;

public class CarCommodityModel {
	private String imagePath;
	private Integer quantity;// 数量
	private float price;// 总价格
	private String shopComName;
	private Integer shopCommCode;
	private float unitPrice;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getShopComName() {
		return shopComName;
	}

	public void setShopComName(String shopComName) {
		this.shopComName = shopComName;
	}

	public Integer getShopCommCode() {
		return shopCommCode;
	}

	public void setShopCommCode(Integer shopCommCode) {
		this.shopCommCode = shopCommCode;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
