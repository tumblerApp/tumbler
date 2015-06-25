package com.yc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("news")//新闻
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noticeId;

	@Column(length=8000)
	private String  content;
	
	@Column
	private String title;
	
	@Column
	private String activityUrl;
	
	@Column
	private String newsDate;
	
	@Column(columnDefinition="bit default 0")
	private Boolean  isThrough = false;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AnnounType announType;

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public Boolean getIsThrough() {
		return isThrough;
	}

	public void setIsThrough(Boolean isThrough) {
		this.isThrough = isThrough;
	}

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public AnnounType getAnnounType() {
		return announType;
	}

	public void setAnnounType(AnnounType announType) {
		this.announType = announType;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
