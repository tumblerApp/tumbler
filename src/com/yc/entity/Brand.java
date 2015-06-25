package com.yc.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("brand")//品牌表
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer brandID;//品牌ID
	
	@Column
	private String brandName;//品牌名称;
	
	@Column
	private String website;//官网；
	
	@Column
	private String logo;//logo链接地址
	
	@Column
	private Boolean isIndex;//是否品牌首页
	
	@OneToMany(mappedBy = "brand")
	private List<ShopCommodity> shopCommoidties;//品牌商品
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ShopCategory> shopCateges;//商品类别

	public List<ShopCommodity> getShopCommoidties() {
		return shopCommoidties;
	}

	public void setShopCommoidties(List<ShopCommodity> shopCommoidties) {
		this.shopCommoidties = shopCommoidties;
	}

	public List<ShopCategory> getShopCateges() {
		return shopCateges;
	}

	public void setShopCateges(List<ShopCategory> shopCateges) {
		this.shopCateges = shopCateges;
	}

	public Integer getBrandID() {
		return brandID;
	}

	public void setBrandID(Integer brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Boolean getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(Boolean isIndex) {
		this.isIndex = isIndex;
	}
	
}
