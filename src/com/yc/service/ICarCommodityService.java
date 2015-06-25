package com.yc.service;

import java.util.List;

import com.yc.entity.CarCommodity;

public interface ICarCommodityService extends IGenericService<CarCommodity> {

	/***
	 * 获得某件购物车商品
	 * @param userName 用户名
	 * @param shopCommId 商品ID
	 * @return
	 */
	CarCommodity getCarCommodityByShopCommId(int shopCommId, String userName);
	
	/***
	 * 获得购物车商品
	 * @param userName 用户名
	 * @return
	 */
	List<CarCommodity> getCarCommodityByUserName(String userName);
}
