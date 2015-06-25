package com.yc.service;

import com.yc.entity.BuyCar;

public interface IBuyCarService extends IGenericService<BuyCar>{

	/***
	 * 通过用户名获得购物车
	 * @param userName 用户名
	 * @return
	 */
	BuyCar getBuyCarByUserName(String userName);
}
