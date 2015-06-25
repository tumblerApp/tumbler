package com.yc.service;

import java.util.List;

import com.yc.entity.AnnounType;
import com.yc.entity.News;

public interface INewsService extends IGenericService<News> {

	/**
	 * 通过活动或者公告类型查询新闻
	 * @param type 类型
	 * @return
	 */
	List<News> getNewsByAnnounType(AnnounType type);

	/**
	 * 通过是否发布活动或者公告查询新闻
	 * @param b 是否发布 布尔值
	 * @return
	 */
	List<News> getNewsByIsThrough(boolean b);

}
