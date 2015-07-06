package com.yc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@DiscriminatorValue("shopCategory")//商品类别表
@JsonIgnoreProperties(value = { "shops", "shopCommoidties", "brands", "specifications","commodity" })
public class ShopCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer categoryID;//列别ID
	
	@Column
	private String category;//商品类名;
	@Column
	private Boolean isForbidden = false;//是否禁用
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch= FetchType.EAGER)
	@JoinColumn(name = "parentLevel")
	private ShopCategory parentLevel;//父节点；
	
	@OneToMany(mappedBy = "parentLevel",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ShopCategory> children = new ArrayList<ShopCategory>();
	
	@OneToMany(mappedBy = "shopCat")
	private List<Shop>  shops;//商店
	
	@OneToMany(mappedBy = "shopCategory")
	private List<ShopCommodity>  shopCommoidties;//商品
	
	@OneToMany(mappedBy = "carCategory")
	private List<CarCommodity>  carCommoidties;//商品

	@OneToMany(mappedBy = "shopCategory")
	private List<FamousManorAndShop> manorAndShops;

	@ManyToMany(mappedBy = "shopCateges")
	private List<Brand> brands;//品牌
	
	@OneToMany(mappedBy = "shopcategory")
	private List<Commodity> commodity;//
	
	@ManyToMany(mappedBy = "shopCategories")
	private List<Specifications> specifications;//规格
	
	@Column
	private String russinaCategory;//商品类名;

	public List<FamousManorAndShop> getManorAndShops() {
		return manorAndShops;
	}

	public void setManorAndShops(List<FamousManorAndShop> manorAndShops) {
		this.manorAndShops = manorAndShops;
	}
	public Boolean getIsForbidden() {
		return isForbidden;
	}

	public void setIsForbidden(Boolean isForbidden) {
		this.isForbidden = isForbidden;
	}

	public String getRussinaCategory() {
		return russinaCategory;
	}

	public void setRussinaCategory(String russinaCategory) {
		this.russinaCategory = russinaCategory;
	}

	public List<CarCommodity> getCarCommoidties() {
		return carCommoidties;
	}

	public void setCarCommoidties(List<CarCommodity> carCommoidties) {
		this.carCommoidties = carCommoidties;
	}

	public List<ShopCategory> getChildren() {
		return children;
	}

	public void setChildren(List<ShopCategory> children) {
		this.children = children;
	}

	public ShopCategory getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(ShopCategory parentLevel) {
		this.parentLevel = parentLevel;
	}

	public List<Specifications> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Specifications> specifications) {
		this.specifications = specifications;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
	
	public List<ShopCommodity> getShopCommoidties() {
		return shopCommoidties;
	}
	
	public void setShopCommoidties(List<ShopCommodity> shopCommoidties) {
		this.shopCommoidties = shopCommoidties;
	}
	
	public List<Brand> getBrands() {
		return brands;
	}
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
   
	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Commodity> getCommodity() {
		return commodity;
	}

	public void setCommodity(List<Commodity> commodity) {
		this.commodity = commodity;
	}
	
}
