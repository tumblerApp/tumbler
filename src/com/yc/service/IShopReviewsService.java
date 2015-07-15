package com.yc.service;

import java.util.List;

import com.yc.entity.ShopReviews;

public interface IShopReviewsService extends IGenericService<ShopReviews> {

	/***
	 * 通过商品ID查询该商品评论
	 * @param commID 商品ID
	 * @return
	 */
	public List<ShopReviews> getAllBycommCode(Integer commID);

	/***
	 * 通过商家ID查询该商家所有商品评论
	 * @param id 商家ID
	 * @return
	 */
	public List<ShopReviews> getReviewsByShop(Integer id);
	
	/**
	 * 通过用户ID查询商家对用户的所有评论
	 */
	public List<ShopReviews> getReviewsByUser(Integer id);
	/**
	 * 得到从最近一周、一个月、6个月、6个月前,好评、中评、差评
	 */
	public Integer[] getReviewsByDate(Integer weeks,Integer month,Integer shopID);

	/***
	 * 通过订单ID和商品ID查询评价
	 * @param orderFormID 订单ID
	 * @param commCode 商品ID
	 * @return
	 */
	public ShopReviews getAllByOrderAndComm(Integer orderFormID, Integer commCode);

	/**
	 * 根据商品id查询对应的评价信息
	 * @param shopCommId 
	 * @return
	 */
	public List<ShopReviews> findByShopCommId(int shopCommId);
}
