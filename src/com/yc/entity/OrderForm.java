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

import com.yc.entity.user.AppUser;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@DiscriminatorValue("orderform")
@JsonIgnoreProperties(value = { "orderUser", "orderNumber", "address","packAge","imagePaths" ,"storeOperator", })
public class OrderForm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderFormID;

	@OneToMany(mappedBy = "orderNumber")
	private List<Commodity> commodities;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser orderUser;

	@OneToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus orderstatus;

	@Column
	private String changeStatusDate;// 状态改变日期

	@ManyToOne
	@JoinColumn(name = "orderGroup_id")
	private OrderGroup orderGroup;
	
	@Column
	private String orderDate;// 下单日期
	
	@Column
	private String orderTime;// 下单时间

	@Column
	private String paymentDate;// 付款日期
	
	@Column
	private String paymentTime;// 付款时间

	@Column
	private String sendDate;//发货日期
	
	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public String getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(String changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public Integer getOrderFormID() {
		return orderFormID;
	}

	public void setOrderFormID(Integer orderFormID) {
		this.orderFormID = orderFormID;
	}

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public OrderGroup getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(OrderGroup orderGroup) {
		this.orderGroup = orderGroup;
	}

	public AppUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(AppUser orderUser) {
		this.orderUser = orderUser;
	}

}
