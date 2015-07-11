package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Collection;
import com.yc.service.ICollectionService;

@Component
public class CollectionService extends GenericService<Collection> implements ICollectionService{

	@Autowired
	GenericDao<Collection> collectionDao;
	
	@Override
	GenericDao<Collection> getDao() {
		return collectionDao;
	}

	@Override
	public List<Collection> getAllByUser(Integer id) {
		return collectionDao.getBy("user.id", id);
	}

	@Override
	public Collection getByUserNameAndCommCode(String userName, Integer commCode) {
		List<String> keys = new ArrayList<String>();
		keys.add("user.phone");
		keys.add("shopCommodity.commCode");
		List<Object> values = new ArrayList<Object>();
		values.add(userName);
		values.add(commCode);
		return collectionDao.getFirstRecord(keys, values);
	}

	@Override
	public Collection getByUserNameAndShopID(String userName, Integer shopID) {
		List<String> keys = new ArrayList<String>();
		keys.add("user.phone");
		keys.add("shop.id");
		List<Object> values = new ArrayList<Object>();
		values.add(userName);
		values.add(shopID);
		return collectionDao.getFirstRecord(keys, values);
	}
	
	/* 查询收藏
	 * (non-Javadoc)
	 * @see com.yc.service.ICollectionService#getByTypeAndUerName(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Collection> getByTypeAndUerName(String userName,String collectionType) {
		StringBuffer hql=new StringBuffer("SELECT * FROM collection c LEFT JOIN appuser a ON c.user_id=a.id WHERE c.collectionType='"+collectionType+"' AND a.phone='"+userName+"'");
		Query query = collectionDao.getEntityManager().createNativeQuery(hql.toString(), Collection.class);
		@SuppressWarnings("unchecked")
		List<Collection> list =  query.getResultList();
		return list;
	}
}
