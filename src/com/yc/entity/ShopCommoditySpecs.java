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
@DiscriminatorValue("shopcommoidtyspecs")//商品规格表
public class ShopCommoditySpecs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "shopComm_id")
	private ShopCommodity shopCommSpecs;
	
	@Column
	private String commSpec;//规格【，颜色-红色，尺寸-12L,】
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ShopCommodity getShopCommSpecs() {
		return shopCommSpecs;
	}

	public void setShopCommSpecs(ShopCommodity shopCommSpecs) {
		this.shopCommSpecs = shopCommSpecs;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	
}
