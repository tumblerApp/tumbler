package com.yc.service;

import java.util.List;

import com.yc.entity.Address;

public interface IAddressService extends IGenericService<Address> {
	
	List<Address> getAllByUser(Integer id);

	/**
	 * 查询用户的所有地址
	 * @return
	 */
	List<Address> getAllByuserName(String userName);
	
	/**
	 * 获取默认地址
	 * @return
	 */
	List<Address> getDefaultAddress(String userName);
}
