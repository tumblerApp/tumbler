package com.yc.service;

import java.util.List;

import com.yc.entity.Brand;
import com.yc.model.BrandModel;

public interface IBrandService extends IGenericService<Brand> {

	/***
	 * 通过品牌名称查询品牌
	 * @param brandName
	 * @return
	 */
	Brand getBrandName(String brandName);
	
	/***
	 * 通过一级节点类别名查询其下所有品牌
	 * @param category 父节点类别名
	 * @return
	 */
	List<BrandModel> getBrands(String category);

}
