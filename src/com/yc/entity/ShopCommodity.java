package com.yc.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.yc.entity.user.AppUser;

@Entity
@DiscriminatorValue("shopCommoidty")//商品表
public class ShopCommodity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commCode;//商品编码
	
	@Column
	private String commoidtyName;//商品名称
	
	@Column(unique=true)
	private String commItem;// 货号
	
	@Column
	private Integer salesVolume; //销量
	
	@Column
	private String supplier ;//供应商
	
	@OneToOne(mappedBy = "shopCommSpecs")
	private ShopCommoditySpecs commsPecs;
	
	@OneToOne(mappedBy = "shopCommodity")
	private ShopCommodityAttribute commAttribute;
	
	@OneToMany(mappedBy = "shopCommodity")
	private List<Commodity> commodities;
	
	@Column
	private Integer proportion = 1;//显示比例
	
	@Column
	private Float unitPrice = 0f;//单价

	
	@Column
	private String commUnit;//商品单位：瓶1、箱0perBoxnum

	@Column
	private Integer perBoxnum;//每箱 几瓶
	
	@Column
	private Float specialPrice = 0f;//活动价
	
	@Column
	private Boolean isAcitvity = false;//是否有活动
	
	@Column
	private Integer stock = 1 ;//库存总数量
	
	@Column(nullable = true)
	private Float  probablyWeight = 0f;//大概重量
	
	@Column
	private Boolean shelves =true;//是否上架
	
	@Column
	private Boolean isSpecial =false;//是否折扣
	
	@Column
	private Float  special  = 1f;//打几折
	
	@Column
	private  Boolean iscChoice   = false;//是否精品
	
	@Column
	private  Boolean auction =false;//是否拍卖
	
	@ManyToOne
	@JoinColumn(name = "shopCategory_id")
	private ShopCategory shopCategory;//商品类别表
	
	@ManyToOne
	@JoinColumn(name = "famousManor_id")
	private FamousManor famousManor;//名庄
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;//品牌表
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop belongTo;//商品所属店面
	
	@OneToOne(mappedBy = "shopCommodity" )
	private Collection collection;

	@OneToMany(mappedBy = "shopCommoidty")
	private List<ShopCommImage> shopCommImages;//商品照片
	
	@Column
	private String describes;//描述
	
	@Column
	private Integer activityAmount = 0;//参加活动的数目

	@OneToOne
	@JoinColumn(name = "blacklist_id")
	private Blacklist blacklist;
	
    //评论
	@OneToMany(mappedBy = "shopscommodity")
	private List<ShopReviews> shopreviews;
	
	@OneToMany(mappedBy = "shopCommodity")
	private List<CarCommodity> carCommodities  ;//购物车商品
	
	@ManyToOne
	@JoinColumn(name = "famAndShop_id")
	private FamousManorAndShop famousManorAndShop;//名庄
	
	@ManyToOne
	@JoinColumn(name="activity_id")
	private Activity activity;//活动
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "AppUser_shopCommodity",   
     joinColumns ={@JoinColumn(name = "shopCommodity_ID", referencedColumnName = "commCode") },   
     inverseJoinColumns = { @JoinColumn(name = "appUser_ID", referencedColumnName = "id") })  
	private List<AppUser> users  ;//团购商品的用户集合
	
	@Column
	private String link;
	
	@ManyToOne
	@JoinColumn(name="advertisement_id")
	private Advertisement advertisement;//广告
	
	@Column
	private String actityImage;
	
	public FamousManorAndShop getFamousManorAndShop() {
		return famousManorAndShop;
	}

	public void setFamousManorAndShop(FamousManorAndShop famousManorAndShop) {
		this.famousManorAndShop = famousManorAndShop;
	}
	
	public List<CarCommodity> getCarCommodities() {
		return carCommodities;
	}
	public void setCarCommodities(List<CarCommodity> carCommodities) {
		this.carCommodities = carCommodities;
	}
   
	public List<ShopReviews> getShopreviews() {
		return shopreviews;
	}

	public void setShopreviews(List<ShopReviews> shopreviews) {
		this.shopreviews = shopreviews;
	}

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getAuction() {
		return auction;
	}

	public void setAuction(Boolean auction) {
		this.auction = auction;
	}

	public String getCommoidtyName() {
		return commoidtyName;
	}

	public void setCommoidtyName(String commoidtyName) {
		this.commoidtyName = commoidtyName;
	}

	public List<ShopCommImage> getShopCommImages() {
		return shopCommImages;
	}

	public void setShopCommImages(List<ShopCommImage> shopCommImages) {
		this.shopCommImages = shopCommImages;
	}
	
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	
	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
   
	public Integer getCommCode() {
		return commCode;
	}

	public void setCommCode(Integer commCode) {
		this.commCode = commCode;
	}

	public String getCommItem() {
		return commItem;
	}

	public void setCommItem(String commItem) {
		this.commItem = commItem;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Shop getBelongTo() {
		return belongTo;
	}
  
	public void setBelongTo(Shop belongTo) {
		this.belongTo = belongTo;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Float getProbablyWeight() {
		return probablyWeight;
	}

	public void setProbablyWeight(Float probablyWeight) {
		this.probablyWeight = probablyWeight;
	}

	public Boolean getShelves() {
		return shelves;
	}

	public void setShelves(Boolean shelves) {
		this.shelves = shelves;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Float getSpecial() {
		return special;
	}

	public void setSpecial(Float special) {
		this.special = special;
	}

	public Boolean getIscChoice() {
		return iscChoice;
	}

	public void setIscChoice(Boolean iscChoice) {
		this.iscChoice = iscChoice;
	}
	
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public Float getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Float specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Boolean getIsAcitvity() {
		return isAcitvity;
	}

	public void setIsAcitvity(Boolean isAcitvity) {
		this.isAcitvity = isAcitvity;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Integer getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(Integer activityAmount) {
		this.activityAmount = activityAmount;
	}

	public List<AppUser> getUsers() {
		return users;
	}

	public void setUsers(List<AppUser> users) {
		this.users = users;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public ShopCommoditySpecs getCommsPecs() {
		return commsPecs;
	}

	public void setCommsPecs(ShopCommoditySpecs commsPecs) {
		this.commsPecs = commsPecs;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public ShopCommodityAttribute getCommAttribute() {
		return commAttribute;
	}

	public void setCommAttribute(ShopCommodityAttribute commAttribute) {
		this.commAttribute = commAttribute;
	}

	public String getCommUnit() {
		return commUnit;
	}

	public void setCommUnit(String commUnit) {
		this.commUnit = commUnit;
	}

	public Integer getPerBoxnum() {
		return perBoxnum;
	}

	public void setPerBoxnum(Integer perBoxnum) {
		this.perBoxnum = perBoxnum;
	}

	public FamousManor getFamousManor() {
		return famousManor;
	}

	public void setFamousManor(FamousManor famousManor) {
		this.famousManor = famousManor;
	}

	public String getActityImage() {
		return actityImage;
	}

	public void setActityImage(String actityImage) {
		this.actityImage = actityImage;
	}
	
}
