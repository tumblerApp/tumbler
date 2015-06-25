
package com.yc.service;

import com.yc.entity.user.AppUser;

public interface IUserService extends IGenericService<AppUser>{

	AppUser getUser(String mobile);

	AppUser getUserByEmail(String email);
}
