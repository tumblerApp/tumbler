package com.yc.service;

import java.util.List;
import java.util.Map;

import com.yc.entity.ShopCommodity;
import com.yc.model.ShopCommodityModel;

public interface IShopCommodityService extends IGenericService<ShopCommodity> {

	/***
	 * 通过商家ID查找该商家商品
	 * @param id 商家ID
	 * @return
	 */
	List<ShopCommodity> getAllByShop(Integer id);

	/***
	 * 通过boolean条件和商家ID查询该商家商品
	 * @param condition boolean条件
	 * @param isTrue boolean条件的值
	 * @param shopID 商家ID
	 * @return
	 */
	List<ShopCommodity> getAllByCondition(String condition, boolean isTrue, Integer shopID);

	/***
	 * 通过商品类别ID和page查询商家商品
	 * @param id 类别ID
	 * @param page page=brand 查询品牌商品 page=special 查询折扣商品
	 * @return
	 */
	List<ShopCommodity> getAllByShopCategoryID(Integer id, String page);

	/***
	 * 通过 规格、价格区段、类别ID、品牌ID查询商品
	 * @param map specs %,xx,%@%,xx,%规格、  money xxx@xxx价格区间段、 id类别ID、 brand (xxx,xxx) 品牌ID
	 * @param page special 折扣商品查询  brand品牌商品查询
	 * @return
	 */
	List<ShopCommodity> getAllByParams(Map<String, Object> map, String page);

	/***
	 * 通过商品名称、商家ID查询商品
	 * @param commoName 商品名称
	 * @param shopID 商家ID
	 * @return
	 */
	List<ShopCommodity> getAllByNameAndShop(String commoName, Integer shopID);

	/***
	 * 通过商品货号、商家ID查询商品
	 * @param commItem 商品货号
	 * @param id 商家ID
	 * @return
	 */
	ShopCommodity getAllByCommItemAndShop(String commItem, Integer id);

	/***
	 * 通过商品规格查询商品的规格
	 * @param speces 商品规格
	 * @return
	 */
	List<String> getShopCommBySpeces(String speces);

	/***
	 * 通过商品商品名称、货号、商品编码
	 * @param map commoidtyName商品名称、commItem商品货号、commCode商品编码
	 * @return
	 */
	List<ShopCommodity> getAllByParamsForBlack(Map<String, Object> map);
	
	/***
	 * 通过商品折扣、类别ID查询商品
	 * @param CateId 如果CateId<=1查询所有商品,CateId>1查询类别下的商品
	 * @param flag 是否折扣
	 * @return
	 */
	List<ShopCommodity> getShopCommByCateAndIsspecial(Integer CateId, Boolean flag);

	/***
	 * 通过商品类别Id查询品牌商品
	 * @param id 类别ID id<=1 查询所有品牌商品 id>1 查询类别下的所有品牌商品
	 * @return
	 */
	List<ShopCommodity> getShopCommByCateAndBrand(Integer id);

	/***
	 * 通过品牌ID查询品牌商品
	 * @param id 品牌ID
	 * @return
	 */
	List<ShopCommodity> getShopCommByBrandId(Integer id);
	
	/***
	 * 通过商品名称或者商品类别名称或者品牌名称模糊查询商品，用于搜索栏
	 * @param content 商品名称 or 商品类别名称 or 品牌名称
	 * @return
	 */
	List<ShopCommodity> searchShopComm(String content);
	
	/**
	 * 通过名庄ID和显示个数查询名庄商品
	 * @param id 名庄ID
	 * @param i 显示个数
	 * @return
	 */
	List<ShopCommodity> getAllByFamousManorID(Integer id, int i);
	
	/***
	 * 查询特价商品
	 * @return
	 */
	List<ShopCommodityModel> getSpecialShopComm(int num);
	
	/***
	 * 查询我们自己店铺的商品
	 * @return
	 */
	List<ShopCommodityModel> getMyShopComm(int num);

	/**
	 * 根据品牌查询商品集合
	 * @param 
	 * @return 查询到的ShopCommodityModel集合
	 */
	List<ShopCommodityModel> getShopCommByBrand(String brandId);
	
	/* (non-Javadoc)
	 * @see com.yc.service.IShopCommodityService#getShopCommByCateAndBoutique(java.lang.String)
	 * 根据类型和是否精品查找对应的商品集合并转化为ShopCommodityModel类型
	 */
	public List<ShopCommodityModel> getShopCommByCateAndBoutique(String cateId, int num);

	/* (non-Javadoc)
	 * @see com.yc.service.IShopCommodityService#getShopCommByCate(java.lang.String)
	 * 根据类型和是否打折查找对应的商品集合并转化为ShopCommodityModel类型
	 */
	public List<ShopCommodityModel> getShopCommByCateAndSpecial(String cateId, int num);
}
