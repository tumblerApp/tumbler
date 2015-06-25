package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Activity;
import com.yc.service.IActivityService;

@Component
public class ActivityService extends GenericService<Activity> implements IActivityService {
    
	@Autowired
	GenericDao<Activity> ActivityDao;

	@Override
	GenericDao<Activity> getDao() {
		return ActivityDao;
	}
	
	@Override
	public Activity save(Activity t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity update(Activity t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity findById(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
