package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("shopCommodityAttribute")//商品属性表
public class ShopCommodityAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String stockWay;//储藏方法
	
	@Column
	private String deadline; //保质期
	
	@Column
	private String foodAdditive; //食品添加剂
	
	@Column
	private String productPlace; //产地
	
	@Column
	private String productProvince; //产地省份
	
	@Column
	private String packManner; //包装方式:裸装、中档、高档
	
	@Column
	private String mixtureSheet; //配料表
	
	@Column
	private Float alcoholDegree = 30f; //酒精度：10、50
	
	@Column
	private Integer particularYear = 2015; //年份
	
	@Column
	private String Weight; //净含量：500ml

	@Column
	private String soberTime; //醒酒时间

	@Column
	private String drinkTemperature; //饮用温度

	@Column
	private String productPerimitNum; //生产许可编号

	@Column
	private String productStddNum; //生产标准号：见背面

	@Column
	private String productFactoryName; //生产厂名

	@Column
	private String productFactoryAddress; //生产工厂地址

	@Column
	private String productTime; //生产日期
	
	@OneToOne
	@JoinColumn(name = "shopcomm_id")
	private ShopCommodity shopCommodity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParticularYear() {
		return particularYear;
	}

	public void setParticularYear(Integer particularYear) {
		this.particularYear = particularYear;
	}

	public Float getAlcoholDegree() {
		return alcoholDegree;
	}

	public void setAlcoholDegree(Float alcoholDegree) {
		this.alcoholDegree = alcoholDegree;
	}

	public String getStockWay() {
		return stockWay;
	}

	public void setStockWay(String stockWay) {
		this.stockWay = stockWay;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getFoodAdditive() {
		return foodAdditive;
	}

	public void setFoodAdditive(String foodAdditive) {
		this.foodAdditive = foodAdditive;
	}

	public String getProductPlace() {
		return productPlace;
	}

	public void setProductPlace(String productPlace) {
		this.productPlace = productPlace;
	}

	public String getProductProvince() {
		return productProvince;
	}

	public void setProductProvince(String productProvince) {
		this.productProvince = productProvince;
	}

	public String getPackManner() {
		return packManner;
	}

	public void setPackManner(String packManner) {
		this.packManner = packManner;
	}

	public String getMixtureSheet() {
		return mixtureSheet;
	}

	public void setMixtureSheet(String mixtureSheet) {
		this.mixtureSheet = mixtureSheet;
	}

	public String getWeight() {
		return Weight;
	}

	public void setWeight(String weight) {
		Weight = weight;
	}

	public String getSoberTime() {
		return soberTime;
	}

	public void setSoberTime(String soberTime) {
		this.soberTime = soberTime;
	}

	public String getDrinkTemperature() {
		return drinkTemperature;
	}

	public void setDrinkTemperature(String drinkTemperature) {
		this.drinkTemperature = drinkTemperature;
	}

	public String getProductPerimitNum() {
		return productPerimitNum;
	}

	public void setProductPerimitNum(String productPerimitNum) {
		this.productPerimitNum = productPerimitNum;
	}

	public String getProductStddNum() {
		return productStddNum;
	}

	public void setProductStddNum(String productStddNum) {
		this.productStddNum = productStddNum;
	}

	public String getProductFactoryName() {
		return productFactoryName;
	}

	public void setProductFactoryName(String productFactoryName) {
		this.productFactoryName = productFactoryName;
	}

	public String getProductFactoryAddress() {
		return productFactoryAddress;
	}

	public void setProductFactoryAddress(String productFactoryAddress) {
		this.productFactoryAddress = productFactoryAddress;
	}

	public String getProductTime() {
		return productTime;
	}

	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}

	public ShopCommodity getShopCommodity() {
		return shopCommodity;
	}

	public void setShopCommodity(ShopCommodity shopCommodity) {
		this.shopCommodity = shopCommodity;
	}
}
