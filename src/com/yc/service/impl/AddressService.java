package com.yc.service.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Address;
import com.yc.service.IAddressService;
@Component
public class AddressService extends GenericService<Address> implements IAddressService {

	@Autowired
	GenericDao<Address> addressDao;
	
	@Override
	GenericDao<Address> getDao() {
		return addressDao;
	}
	
	@Override
	public List<Address> getAllByUser(Integer id) {
		return addressDao.getBy("user.id", id);
	}
	
	/* 
	 * 查询用户所有的收货地址
	 * (non-Javadoc)
	 * @see com.yc.service.IAddressService#getAllByuserName()
	 */
	@Override
	public List<Address> getAllByuserName(String userName) {
		StringBuffer hql=new StringBuffer("SELECT * FROM address LEFT JOIN appuser ON address.user_id=appuser.id WHERE appuser.phone='"+userName+"'");
		Query query = addressDao.getEntityManager().createNativeQuery(hql.toString(), Address.class);
		@SuppressWarnings("unchecked")
		List<Address> list =  query.getResultList();
		return list;
	}
}
