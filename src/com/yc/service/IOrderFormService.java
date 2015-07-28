package com.yc.service;


import com.yc.entity.OrderForm;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IOrderFormService extends IGenericService<OrderForm>{
	
	/***
	 * 通过包裹ID查询订单
	 * @param id 包裹ID
	 * @return
	 */
	List<OrderForm> findByPackAgeID(Integer id);

	/***
	 * 通过订单人的姓名查询订单
	 * @param userName 订单人的姓名
	 * @return
	 */
	List<OrderForm> findByUserName(String userName);

	/***
	 * 通过订单人的ID查询订单
	 * @param id 订单人的ID
	 * @return
	 */
	List<OrderForm> findByUserID(Integer id);

	/***
	 * 通过大包裹的ID查询订单
	 * @param id 大包裹的ID
	 * @return
	 */
	List<OrderForm> getOrderByOrderGroup(Integer id);

	/***
	 * 通过订单的状态和商家ID查询订单个数
	 * @param status 订单状态 'xxx','xxx'
	 * @param shop_id 商家ID
	 * @return
	 */
	Integer getShopOrderByStatusAndShop(String status, Integer shop_id);
	
	/***
	 * 通过订单状态、订单ID、商家名称、下单日期、买家付款日期、货品追踪号、订单人姓名查询订单并根据订单ID倒序排序
	 * @param map orderstatus订单状态、orderID订单ID、shopName like 商家名称、orderDate like 下单日期、paymentDate买家付款日期、tpek货品追踪号、orderUser like订单人姓名查询
	 * @return
	 */
	List<OrderForm> getOrderFormByParameters(Map<String, Object> map);
	
	/***
	 * 通过订单状态查询订单
	 * @param orderStatus 订单状态
	 * @return
	 */
	List<OrderForm> getAllByOrderStatus(String orderStatus);

	/***
	 * 通过订单状态和下单日期查询订单
	 * @param map orderStatus状态为wanjie是完结,下单日期 orderDate为volvo表示一周内、为saab表示一月内、为fiat表示三个月内
	 * @param userID 用户ID
	 * @return
	 */
	List<OrderForm> getAllByParams(Map<String, Object> map, Integer userID);

	/***
	 * 通过商家ID查询三个月内的订单
	 * @param shopID 商家ID
	 * @return
	 */
	List<OrderForm> getShopOrderByShop(Integer shopID);

	/***
	 * 通过商家ID、商品名称、订单人姓名 like、订单ID、订单状态、订单日期
	 * @param map nameOfGoods商品名称、orderUserName订单人姓名 like、orderFormID订单ID、orderStatusFrom订单状态、paymentDateLeft从某日期、paymentDateRight到某日期
	 * @param shopID商家ID
	 * @return
	 * @throws ParseException
	 */
	List<OrderForm> getShopOrderByParam(Map<String, Object> map, Integer shopID) throws ParseException ;

	/***
	 * 通过状态不在completionTransaction完成、closeTransaction关闭的订单
	 * @return
	 */
	List<OrderForm> getAllByStatus();
	
	/**
	 * 查询退款订单：退款中的订单orderStatusRefunding、退款成功orderStatusRefundSuccess，退款失败orderStatusRefundfail
	 */
	List<OrderForm> getAllRefundByStatus(Map<String, Object> map,Integer shopID);
	
	/**
	 * 通过 用户ID 查询退款订单：退款中的订单orderStatusRefunding、退款成功orderStatusRefundSuccess，退款失败orderStatusRefundfail
	 */
	List<OrderForm> getAllRefundByStatusUID(Map<String, Object> map,Integer userID);
	
	/**
	 * 查询最新订单
	 * @return
	 */
	OrderForm searchNewst();
	
	/**
	 * 查询用户的订单信息
	 * @param phone
	 * @return
	 */
	List<OrderForm> findByPhone(String phone);
	
	/**
	 * 通过手机号查询下单到此手机号店铺的订单
	 * @param phone
	 * @return
	 */
	List<OrderForm> findShopOrderByPhone(String phone);
}
