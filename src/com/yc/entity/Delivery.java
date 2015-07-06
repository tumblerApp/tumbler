package com.yc.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("delivery")
public class Delivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String deliveryName;//运输方式  EMS 圆通  顺风 
	
	@OneToOne(mappedBy = "delivery")
	private OrderForm orderForm;
	
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "address_id")
	private DeliveryAddress address;//交付地址

	@Column
	private Float deliveryMoney;//运输费
	
	@Column
	private String endorse;//背书
	
	@Column(unique=true)
	private String packAgeTpek; //订单号

	public DeliveryAddress getAddress() {
		return address;
	}

	public void setAddress(DeliveryAddress address) {
		this.address = address;
	}

	public Float getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Float deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getPackAgeTpek() {
		return packAgeTpek;
	}

	public void setPackAgeTpek(String packAgeTpek) {
		this.packAgeTpek = packAgeTpek;
	}

	public String getEndorse() {
		return endorse;
	}

	public void setEndorse(String endorse) {
		this.endorse = endorse;
	}

	public OrderForm getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(OrderForm orderForm) {
		this.orderForm = orderForm;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	
}
