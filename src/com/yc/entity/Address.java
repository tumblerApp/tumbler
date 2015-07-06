package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yc.entity.user.AppUser;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String toName;// 给xx 
	@Column
	private String toEmail;// 邮件
	@Column
	private String phone;// 电话  
	@Column
	private String country;// 国家  
	@Column
	private String provience ; // 省：新疆  
	@Column
	private String city ;// 市：乌鲁木齐   
	@Column
	private String district ;// 区：新市区  
	@Column
	private String other ;// 其它    
	@Column
	private Boolean theDefault;// 是否默认   
	@ManyToOne
	@JoinColumn(name = "user_id")
    private AppUser user;  
	@Column
	private Integer provienceId;
	@Column
	private Integer cityId;
	@Column
	private Integer districtId;
	
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getProvience() {
		return provience;
	}

	public void setProvience(String provience) {
		this.provience = provience;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Boolean getTheDefault() {
		return theDefault;
	}

	public void setTheDefault(Boolean theDefault) {
		this.theDefault = theDefault;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getProvienceId() {
		return provienceId;
	}

	public void setProvienceId(Integer provienceId) {
		this.provienceId = provienceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
}
