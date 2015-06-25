package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yc.entity.user.Personnel;

@Entity
@DiscriminatorValue("carcommoidty")//购物车商品
@JsonIgnoreProperties(value={"buyCar","shopCommodity","carCategory","shop","currency","personnel"})
public class CarCommodity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private BuyCar buyCar; //购物车
	
	@ManyToOne
	@JoinColumn(name = "shopcommodity_id")
	private ShopCommodity shopCommodity;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@ManyToOne
	@JoinColumn(name = "shopCategory_id")
	private ShopCategory carCategory;//商品类别表
	
	@Column
	private float price;//总金额
	
	@Column
	private float unitPrice;//出售价格
	
	@Column
	private Integer amount;//购买数量
	
	@Column
	private String changePriceDate;//价格变动日期
	
	@ManyToOne
	@JoinColumn(name = "personnel_id")
	private Personnel personnel;//价格变动操作员

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BuyCar getBuyCar() {
		return buyCar;
	}

	public void setBuyCar(BuyCar buyCar) {
		this.buyCar = buyCar;
	}

	public ShopCommodity getShopCommodity() {
		return shopCommodity;
	}

	public void setShopCommodity(ShopCommodity shopCommodity) {
		this.shopCommodity = shopCommodity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public ShopCategory getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(ShopCategory carCategory) {
		this.carCategory = carCategory;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getChangePriceDate() {
		return changePriceDate;
	}

	public void setChangePriceDate(String changePriceDate) {
		this.changePriceDate = changePriceDate;
	}
	
}
