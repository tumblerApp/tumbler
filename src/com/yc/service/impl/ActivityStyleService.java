package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ActivityStyle;
import com.yc.service.IActivityStyleService;

@Component
public class ActivityStyleService extends GenericService<ActivityStyle> implements IActivityStyleService {

	@Autowired
	GenericDao<ActivityStyle> activityStyleDao;

	@Override
	GenericDao<ActivityStyle> getDao() {
		return activityStyleDao;
	}
	
	@Override
	public ActivityStyle save(ActivityStyle t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityStyle update(ActivityStyle t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityStyle findById(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityStyle> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
