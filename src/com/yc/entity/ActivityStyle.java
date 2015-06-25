 package com.yc.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//活动方式，活动类别
@Entity
@DiscriminatorValue("activity")
public class ActivityStyle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityStyleId;
	
	@Column
	private String activityStyle;//活动方式
	
	@OneToMany(mappedBy="activityStyle")
	private List<Activity> activitys;//对应的活动列表

	public Integer getActivityStyleId() {
		return activityStyleId;
	}

	public void setActivityStyleId(Integer activityStyleId) {
		this.activityStyleId = activityStyleId;
	}

	public String getActivityStyle() {
		return activityStyle;
	}

	public void setActivityStyle(String activityStyle) {
		this.activityStyle = activityStyle;
	}

	public List<Activity> getActivitys() {
		return activitys;
	}

	public void setActivitys(List<Activity> activitys) {
		this.activitys = activitys;
	}
}
