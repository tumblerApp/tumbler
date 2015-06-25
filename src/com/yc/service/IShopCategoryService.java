package com.yc.service;

import java.util.List;

import com.yc.entity.ShopCategory;

public interface IShopCategoryService extends IGenericService<ShopCategory> {

	/***
	 * 通过节点级别查询商品类别
	 * @param i 节点级别
	 * @return
	 */
	List<ShopCategory> getAllByLevel(Integer i);

	/***
	 * 查询所有包含品牌的商品类别
	 * @return
	 */
	List<ShopCategory> getAllForBrand();

	/***
	 * 查询所有包含折扣类商品的类别
	 * @return
	 */
	List<ShopCategory> getAllForSpecial();

	/***
	 * 通过父节点ID查询类别
	 * @param categoryID 父节点ID
	 * @return
	 */
	List<ShopCategory> getAllByParentLevel(Integer categoryID);

	/***
	 * 查询所有一级节点类别
	 * @return
	 */
	List<ShopCategory> getAllByParent();
	
	/**
	 * 根据类型查类别
	 * @param i
	 * @return
	 */
	List<ShopCategory> getAllByType(int i);
}
