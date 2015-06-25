package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ShopReviews;
import com.yc.service.IShopReviewsService;

@Component
public class ShopReviewsService extends GenericService<ShopReviews> implements IShopReviewsService {
	
	@Autowired
	GenericDao<ShopReviews> shopReviewsDao;
	
	@Override
	GenericDao<ShopReviews> getDao() {
		return shopReviewsDao;
	}

	@Override
	public List<ShopReviews> getAllBycommCode(Integer commID) {
		return shopReviewsDao.getBy("shopscommodity.commCode", commID);
	}

	@Override
	public List<ShopReviews> getReviewsByShop(Integer id) {
		return shopReviewsDao.getBy("shopscommodity.belongTo.id", id);
	}

}
