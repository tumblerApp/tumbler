package com.yc.entity.user;

import java.beans.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.yc.entity.BuyCar;
import com.yc.entity.Shop;
import com.yc.entity.ShopCommodity;

@Entity
@DiscriminatorValue("user")//用户
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 32, unique = true, updatable = false)
	private String phone;

	@Column(length = 32)
	private String password;

	@Column(length = 32)
	private String userName;

	@Column
	private String email;
	
	@Column
	private String validateCode;//邮箱激活码
	
	@Column
	private Boolean  status;
	
	@Column
	private String emailBindTime;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@OneToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@Column
	private String birthday;
	
	@ManyToMany(mappedBy = "users")
	private List<ShopCommodity> activityCommodities;//商品类别
	
	@OneToOne(mappedBy = "appUser")
	private BuyCar buyCar;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getEmailBindTime() {
		return emailBindTime;
	}

	public void setEmailBindTime(String emailBindTime) {
		this.emailBindTime = emailBindTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public List<ShopCommodity> getActivityCommodities() {
		return activityCommodities;
	}

	public void setActivityCommodities(List<ShopCommodity> activityCommodities) {
		this.activityCommodities = activityCommodities;
	}

	public BuyCar getBuyCar() {
		return buyCar;
	}

	public void setBuyCar(BuyCar buyCar) {
		this.buyCar = buyCar;
	}

	@Transient
    public Date getLastActivateTime() throws ParseException {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(emailBindTime));
        cl.add(Calendar.DATE , 2);
        return cl.getTime();
    }
}
