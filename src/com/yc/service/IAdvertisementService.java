package com.yc.service;

import java.util.List;

import com.yc.entity.Advertisement;

public interface IAdvertisementService extends IGenericService<Advertisement>{

	public Integer getAdvertiseBywhichPageAndPostion(String whichPage, Integer position);
	
	public List<Advertisement> getAdvertiseBywhichPageAndPostions(String whichPage, Integer position);
}
