package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Delivery;
import com.yc.service.IDeliveryService;

@Component
public class DeliveryService extends GenericService<Delivery> implements IDeliveryService {

	@Autowired
	GenericDao<Delivery> deliveryDao;
	
	@Override
	GenericDao<Delivery> getDao() {
		return deliveryDao;
	}

}
