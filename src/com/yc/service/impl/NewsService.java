package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.AnnounType;
import com.yc.entity.News;
import com.yc.service.INewsService;

@Component
public class NewsService extends GenericService<News> implements INewsService {

	@Autowired
	GenericDao<News> newsDao;

	@Override
	GenericDao<News> getDao() {
		return newsDao;
	}

	@Override
	public List<News> getNewsByAnnounType(AnnounType type) {
		return newsDao.getBy("announType", type);
	}

	@Override
	public List<News> getNewsByIsThrough(boolean b) {
		StringBuffer hql = new StringBuffer(" from News where isThrough = ? order by noticeId desc");
		Object[] params = new Object[1];
		params[0] = b;
		return newsDao.find(hql.toString(), params, -1, -1);
	}
}
