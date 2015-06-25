package com.yc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ImagePath;
import com.yc.service.IImagePathService;

@Component
public class ImagePathService extends GenericService<ImagePath> implements IImagePathService {
	
	@Autowired
	GenericDao<ImagePath> imagePathDao;

	@Override
	GenericDao<ImagePath> getDao() {
		return imagePathDao;
	}

}
