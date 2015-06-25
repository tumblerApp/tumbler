package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//商品照片
@Entity
@DiscriminatorValue("shopCommImage")
public class ShopCommImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer imageID;
	
	@Column
	private String imagePath;
	
	@ManyToOne
	@JoinColumn(name = "shopCommoidty_id")
	private ShopCommodity shopCommoidty;

	public Integer getImageID() {
		return imageID;
	}

	public void setImageID(Integer imageID) {
		this.imageID = imageID;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ShopCommodity getShopCommoidty() {
		return shopCommoidty;
	}

	public void setShopCommoidty(ShopCommodity shopCommoidty) {
		this.shopCommoidty = shopCommoidty;
	}
}
