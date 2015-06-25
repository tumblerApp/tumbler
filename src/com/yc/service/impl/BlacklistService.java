package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Blacklist;
import com.yc.entity.BlacklistType;
import com.yc.service.IBlacklistService;

@Component
public class BlacklistService extends GenericService<Blacklist> implements IBlacklistService {

	@Autowired
	GenericDao<Blacklist> blacklistDao;
	
	@Override
	GenericDao<Blacklist> getDao() {
		return blacklistDao;
	}
	
	/***
	 * 通过黑名单类型查询黑名单
	 * @param stores 黑名单类型
	 * @return
	 */
	@Override
	public List<Blacklist> getBlacklistByBlacklistType(BlacklistType stores) {
		return blacklistDao.getBy("blacklistType", stores);
	}
}
