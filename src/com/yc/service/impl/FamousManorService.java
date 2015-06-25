package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.FamousManor;
import com.yc.service.IFamousManorService;

@Component
public class FamousManorService  extends GenericService<FamousManor> implements IFamousManorService{

	@Autowired
	GenericDao<FamousManor> famousManorDao;
	
	@Override
	GenericDao<FamousManor> getDao() {
		return famousManorDao;
	}

}
