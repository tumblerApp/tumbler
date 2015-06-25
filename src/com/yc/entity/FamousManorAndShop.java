package com.yc.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("famousManorAndShop")//商家和名庄
public class FamousManorAndShop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "famousManor_id")
	private FamousManor famousManor;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@Column
	private String startDate;
	
	@Column
	private String endDate;
	
	@OneToMany(mappedBy = "famousManorAndShop")
	private List<ShopCommodity> shopCommoidties;
	
	@ManyToOne
	@JoinColumn(name = "shopCategory_id")
	private ShopCategory shopCategory;

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FamousManor getFamousManor() {
		return famousManor;
	}

	public void setFamousManor(FamousManor famousManor) {
		this.famousManor = famousManor;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<ShopCommodity> getShopCommoidties() {
		return shopCommoidties;
	}

	public void setShopCommoidties(List<ShopCommodity> shopCommoidties) {
		this.shopCommoidties = shopCommoidties;
	}
}
