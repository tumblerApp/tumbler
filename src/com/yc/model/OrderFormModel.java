package com.yc.model;

import java.util.List;

//id  下单时间  状态 总价 图片路径
public class OrderFormModel {
	private Integer id;
    private String orderFormDate;
    private String orderFormStatus;
    private Float totalPrice;
    private String imagePath;
    private Float deliveryMoney;//运费
    private String endorse;//备注
    private String toName;
    private String address;
    private String phone;
    
    private List<CommodityModel> commodities;
                  
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderFormDate() {
		return orderFormDate;
	}
	public void setOrderFormDate(String orderFormDate) {
		this.orderFormDate = orderFormDate;
	}
	public String getOrderFormStatus() {
		return orderFormStatus;
	}
	public void setOrderFormStatus(String orderFormStatus) {
		this.orderFormStatus = orderFormStatus;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Float getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(Float deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	public String getEndorse() {
		return endorse;
	}
	public void setEndorse(String endorse) {
		this.endorse = endorse;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<CommodityModel> getCommodities() {
		return commodities;
	}
	public void setCommodities(List<CommodityModel> commodities) {
		this.commodities = commodities;
	}
	
}
