package com.yc.service;

import java.util.List;

import com.yc.entity.AdvertiseDistribution;

public interface IAdvertisementDistributionService extends IGenericService<AdvertiseDistribution>{

	public List<AdvertiseDistribution> getAdvertiseDistributionsByWhichPage(String whichPage);
	
	public AdvertiseDistribution findByWhichPageAndPosition(String whichPage, Integer position);
	
	public List<String> getDistinctWhichPage();
}
