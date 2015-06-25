
package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;

@Component
public class AppUserService extends GenericService<AppUser> implements IAppUserService {

	@Autowired
	GenericDao<AppUser> userDao;

	@Override
	GenericDao<AppUser> getDao() {
		return userDao;
	}

	@Override
	public AppUser getUser(String mobile) {
		return userDao.getFirstRecord("phone", mobile);
	}

	@Override
	public AppUser getUserByEmail(String email) {
		return userDao.getFirstRecord("email", email);
	}
}
