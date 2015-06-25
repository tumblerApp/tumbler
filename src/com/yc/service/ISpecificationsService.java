package com.yc.service;

import java.util.List;

import com.yc.entity.Specifications;

public interface ISpecificationsService extends IGenericService<Specifications> {

	/***
	 * 通过商品类别ID查询规格
	 * @param ids
	 * @return
	 */
	List<Specifications> getAllByShopCateg(Integer ids);

}
