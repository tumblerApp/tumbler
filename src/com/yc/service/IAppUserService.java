
package com.yc.service;

import com.yc.entity.user.AppUser;

public interface IAppUserService extends IGenericService<AppUser>{

	AppUser getUser(String mobile);

	AppUser getUserByEmail(String email);
}
