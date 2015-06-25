package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.BuyCar;
import com.yc.service.IBuyCarService;

@Component
public class BuyCarService extends GenericService<BuyCar> implements IBuyCarService{

	@Autowired
	GenericDao<BuyCar> buyCarDao;
	
	@Override
	GenericDao<BuyCar> getDao() {
		return buyCarDao;
	}

	@Override
	public BuyCar getBuyCarByUserName(String userName) {
		return buyCarDao.getFirstRecord("appUser.phone", userName);
	}

}
