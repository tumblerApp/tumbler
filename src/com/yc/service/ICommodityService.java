package com.yc.service;

import java.util.List;

import com.yc.entity.Commodity;
import com.yc.entity.OrderStatus;
import com.yc.model.CommodityModel;

import java.util.Map;


public interface ICommodityService extends IGenericService<Commodity> {
	
	/***
	 * 多条件查询订单Items
	 * @param map 发布商品ID,订单ID,订单人姓名Like,订单商品追踪号,最后状态改变日期Like,订单付款日期,订单状态,货号,下单日期
	 * @return
	 */
	public List<Commodity> getAllByParametersForOrder(Map<String, Object> map);
	
	/***
	 * 通过二级节点查热销产品
	 * @param id 二级节点ID
	 * @param limit 前几排名
	 * @return
	 */
	public List<CommodityModel> getAllByShopCategoryID(Integer id,Integer limit);
	
	/***
	 * 通过三级节点查热销产品
	 * @param id 三级节点ID
	 * @param limit 前几排名
	 * @return
	 */
	public List<CommodityModel> getAllByCommdityID(Integer id,Integer limit);
	
	/***
	 * 通过订单状态和商家ID查询订单Items
	 * @param status 订单状态 格式'xxx','xxx'
	 * @param shop_id 商家ID
	 * @return
	 */
	public List<Commodity> getAllByOrderStatus(String status, Integer shop_id);
	
	/***
	 * 通过订单ID和订单Items的ID查询Items
	 * @param orderid 订单ID
	 * @param commCode 订单Items的ID
	 * @return
	 */
	public Commodity getCommByOrderIDAndCommCode(Integer orderid, Integer commCode);
	
	/***
	 * 通过订单状态、商家ID、订单人的ID查询订单Items
	 * @param orderStatus 订单状态
	 * @param shopID 商家ID
	 * @param userID 订单人的ID
	 * @return
	 */
	public List<Commodity> getOrderByPurchaseAndUser(OrderStatus orderStatus,Integer shopID, Integer userID);
	
	/***
	 * 通过订单状态和订单人的ID查询订单Items
	 * @param userID 订单人的ID
	 * @param orderStatus 订单状态 格式 'xxx','xxx'
	 * @return
	 */
	public List<Commodity> getAllByUserAndStatus(Integer userID,String orderStatus);
	
	/***
	 * 通过订单状态和商品ID查询该商家所属状态下的商品个数
	 * @param status 订单状态 格式 'xxx','xxx'
	 * @param shop_id 商家ID
	 * @return
	 */
	public Integer getCommodityByStatusAndShop(String status, Integer shop_id);
	
	/***
	 * 获得热销的6条产品
	 * @return
	 */
	public List<CommodityModel> getHotCommodities(int num);
	
	/* (non-Javadoc)
	 * @see com.yc.service.ICommodityService#getShopCommByCateAndHot(java.lang.String)
	 * 查找当前类别的热销产品集合
	 */
	public List<CommodityModel> getShopCommByCateAndHot(String cateId, int num);
}
