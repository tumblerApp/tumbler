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

}
