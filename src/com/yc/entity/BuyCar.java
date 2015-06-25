package com.yc.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.yc.entity.user.AppUser;

/**
 * 购物车
 * @author Administrator
 *
 */
@Entity
@DiscriminatorValue("buycar")
public class BuyCar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer catID;
	
	@OneToOne
	@JoinColumn(name="appuser_id",insertable=true,unique=true)  
	private AppUser appUser;
	
	@OneToMany(mappedBy = "buyCar")
	//@Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private List<CarCommodity> carCommodities;//购物车商品
    
	public List<CarCommodity> getCarCommodities() {
		return carCommodities;
	}

	public void setCarCommodities(List<CarCommodity> carCommodities) {
		this.carCommodities = carCommodities;
	}

	public Integer getCatID() {
		return catID;
	}

	public void setCatID(Integer catID) {
		this.catID = catID;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	 
}
