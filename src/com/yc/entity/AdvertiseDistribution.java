package com.yc.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//广告分配
@Entity
@DiscriminatorValue("adverDistribution")
public class AdvertiseDistribution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adverDisId;
	
	@Column
	private String whichPage;//所在页面
	
	@Column
	private Integer position;//位置
	
	@Column
	private Integer num;//所在位置的广告数量
	
	@Column
	private String lAndW;//宽高
	
	@OneToMany(mappedBy = "adverDistribution")
	private List<Advertisement> advertisementList;//广告

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}

	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getAdverDisId() {
		return adverDisId;
	}

	public void setAdverDisId(Integer adverDisId) {
		this.adverDisId = adverDisId;
	}

	public String getlAndW() {
		return lAndW;
	}

	public void setlAndW(String lAndW) {
		this.lAndW = lAndW;
	}

	public void setWhichPage(String whichPage) {
		this.whichPage = whichPage;
	}

}
