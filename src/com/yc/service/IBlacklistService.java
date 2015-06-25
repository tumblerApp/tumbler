package com.yc.service;

import java.util.List;

import com.yc.entity.Blacklist;
import com.yc.entity.BlacklistType;

public interface IBlacklistService extends IGenericService<Blacklist> {
	/***
	 * 通过黑名单类型查询黑名单
	 * @param stores 黑名单类型
	 * @return
	 */
	List<Blacklist> getBlacklistByBlacklistType(BlacklistType stores);

}
