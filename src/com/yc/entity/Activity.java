package com.yc.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//活动列表
@Entity
@DiscriminatorValue("activity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityId;
	
	@Column
	private String startDate;//开始日期
	
	@Column
	private String endDate;//截止日期
	
	@Column
	private String activityName;//活动名称
	
	@ManyToOne
	@JoinColumn(name="activityStyle_id")
	private ActivityStyle activityStyle;//活动类型
	
	@OneToMany(mappedBy="activity")
	private List<ShopCommodity> commodities;//商品

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public ActivityStyle getActivityStyle() {
		return activityStyle;
	}

	public void setActivityStyle(ActivityStyle activityStyle) {
		this.activityStyle = activityStyle;
	}

	public List<ShopCommodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<ShopCommodity> commodities) {
		this.commodities = commodities;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
}
