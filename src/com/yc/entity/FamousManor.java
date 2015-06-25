package com.yc.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("famousManor")//名庄
public class FamousManor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String manorName;
	
	@Column
	private String logoPath;
	
	@Column
	private String title;
	
	@Column(length=8000)
	private String describes; // 描述
	
	@Column
	private Boolean isForbidden = false; //是否禁用
	
	@OneToMany(mappedBy = "famousManor")
	private List<FamousManorAndShop> famousManorAndShops;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsForbidden() {
		return isForbidden;
	}

	public void setIsForbidden(Boolean isForbidden) {
		this.isForbidden = isForbidden;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManorName() {
		return manorName;
	}

	public void setManorName(String manorName) {
		this.manorName = manorName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public List<FamousManorAndShop> getFamousManorAndShops() {
		return famousManorAndShops;
	}

	public void setFamousManorAndShops(List<FamousManorAndShop> famousManorAndShops) {
		this.famousManorAndShops = famousManorAndShops;
	} 
	
}
