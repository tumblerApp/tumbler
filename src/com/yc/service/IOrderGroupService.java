package com.yc.service;

import java.util.List;
import java.util.Map;

import com.yc.entity.OrderGroup;

public interface IOrderGroupService extends IGenericService<OrderGroup> {

	/***
	 * 通过大包裹ID、任务号、订单状态查询大包裹
	 * @param map orderGroupID大包裹ID、taskNumber任务号、orderStatus订单状态 
	 * @return
	 */
	List<OrderGroup> getOrderGroupByParameters(Map<String, Object> map);
	
}
