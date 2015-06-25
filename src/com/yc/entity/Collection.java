package com.yc.entity;

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
import javax.persistence.OneToOne;

import com.yc.entity.user.AppUser;
//收藏
@Entity
@DiscriminatorValue("collection")
public class Collection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "shopComm_commCode")
	private ShopCommodity shopCommodity;
	
	@OneToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CollectionType collectionType = CollectionType.commodity;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public ShopCommodity getShopCommodity() {
		return shopCommodity;
	}

	public void setShopCommodity(ShopCommodity shopCommodity) {
		this.shopCommodity = shopCommodity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
