package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.CarCommodity;
import com.yc.service.ICarCommodityService;
@Component
public class CarCommodityService extends GenericService<CarCommodity> implements ICarCommodityService {

	@Autowired
	GenericDao<CarCommodity> carCommoidtyDao;

	@Override
	GenericDao<CarCommodity> getDao() {
		return carCommoidtyDao;
	}

	@Override
	public CarCommodity getCarCommodityByShopCommId(int shopCommId, String userName) {
		List<String> keys = new ArrayList<String>();
		keys.add("shopCommodity.commCode");
		keys.add("buyCar.appUser.phone");
		List<Object> values = new ArrayList<Object>();
		values.add(shopCommId);
		values.add(userName);
		return carCommoidtyDao.getFirstRecord(keys, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarCommodity> getCarCommodityByUserName(String userName) {
		StringBuffer hql = new StringBuffer("SELECT c.* FROM BuyCar b LEFT JOIN AppUser au ON au.id = b.appuser_id RIGHT JOIN carcommodity c ON c.car_id = b.catID WHERE au.phone = '"+userName+"'");
		Query query =  carCommoidtyDao.getEntityManager().createNativeQuery(hql.toString(),CarCommodity.class);
		return query.getResultList();
	}
}
