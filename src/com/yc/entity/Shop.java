package com.yc.entity;

import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yc.entity.user.AppUser;

@Entity
@DiscriminatorValue("shop")//商店
@JsonIgnoreProperties(value = { "shopCommoidties" })
public class Shop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String shopName;//店名
	
	@ManyToOne
	@JoinColumn(name = "shopCategory_id")
	private ShopCategory shopCat;//店铺类型
	
	@Column
	private String juridicalPerson;//法人
	
	@Column
	private String idCard;//身份证
	
	@Column
	private String payPwd;//支付密码；
	
	@Column
	@Enumerated(EnumType.STRING)
	private ShopType shopType;//性质
	
	@Column
	@Enumerated(EnumType.STRING)
	private Possession possession;//所属地
	
	@Column
	private String blank;//银行
	
	@Column
	private String money;//钱
	
	@Column( unique = true, updatable = false)
	private String blankAccount;//公司账户
	
	@Column
	private String phone;
	
	@Column
	private String email;
	
	@Column
	private Boolean isPermit = false;//是否许可开店
	
	@Column
	private String createDate;//开店日期
	
	@OneToMany(mappedBy = "belongTo")
	private List<ShopCommodity> shopCommoidties;//店铺商品
	
	@OneToMany(mappedBy = "shop")
	private List<CarCommodity> carCommoidties;//购物车商品
	
	@OneToOne(mappedBy = "shop")
	private AppUser user;
	
	@OneToOne(mappedBy = "shop" )
	private Collection collection;
	
	@OneToOne
	@JoinColumn(name = "blacklist_id")
	private Blacklist blacklist;
	
	@Column
	private String russinaShopName ;
	
	@Column
	private String logoPath ;
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getRussinaShopName() {
		return russinaShopName;
	}

	public void setRussinaShopName(String russinaShopName) {
		this.russinaShopName = russinaShopName;
	}

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public List<CarCommodity> getCarCommoidties() {
		return carCommoidties;
	}

	public void setCarCommoidties(List<CarCommodity> carCommoidties) {
		this.carCommoidties = carCommoidties;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public ShopCategory getShopCat() {
		return shopCat;
	}

	public void setShopCat(ShopCategory shopCat) {
		this.shopCat = shopCat;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(String juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Possession getPossession() {
		return possession;
	}

	public void setPossession(Possession possession) {
		this.possession = possession;
	}

	public ShopType getShopType() {
		return shopType;
	}

	public void setShopType(ShopType shopType) {
		this.shopType = shopType;
	}

	public List<ShopCommodity> getShopCommoidties() {
		return shopCommoidties;
	}

	public void setShopCommoidties(List<ShopCommodity> shopCommoidties) {
		this.shopCommoidties = shopCommoidties;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getBlankAccount() {
		return blankAccount;
	}

	public void setBlankAccount(String blankAccount) {
		this.blankAccount = blankAccount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsPermit() {
		return isPermit;
	}

	public void setIsPermit(Boolean isPermit) {
		this.isPermit = isPermit;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

}
