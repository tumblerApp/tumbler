package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.FamousManorAndShop;
import com.yc.service.IFamousManorAndShopService;

@Component
public class FamousManorAndShopService extends GenericService<FamousManorAndShop> implements IFamousManorAndShopService{

	@Autowired
	GenericDao<FamousManorAndShop> famousAndShopDao;
	
	@Override
	GenericDao<FamousManorAndShop> getDao() {
		return famousAndShopDao;
	}

}
