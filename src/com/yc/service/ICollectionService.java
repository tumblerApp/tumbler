package com.yc.service;

import java.util.List;

import com.yc.entity.Collection;

public interface ICollectionService extends IGenericService<Collection>{

	/**
	 * 通过用户ID查询用户所有自己的收藏
	 * @param id 用户ID
	 * @return
	 */
	List<Collection> getAllByUser(Integer id);
	
	/**
	 * 查询收藏是否存在
	 * @param userName 用户名
	 * @param commCode 商品ID
	 * @return
	 */
	Collection getByUserNameAndCommCode(String userName, Integer commCode);

	/**
	 * 查询收藏是否存在
	 * @param userName 用户名
	 * @param shopID 店铺ID
	 * @return
	 */
	Collection getByUserNameAndShopID(String userName, Integer shopID);
	
	/**
	 * 通过收藏类型 来查询
	 * @param userName
	 * @param collectionType
	 * @return
	 */
	List<Collection> getByTypeAndUerName(String userName, String collectionType);
}
