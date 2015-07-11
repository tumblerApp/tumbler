package com.yc.service.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ShopCategory;
import com.yc.service.IShopCategoryService;
@Component
public class ShopCategoryService extends GenericService<ShopCategory> implements IShopCategoryService {

	@Autowired
	GenericDao<ShopCategory> shopCategoryDao;
	
	@Override
	GenericDao<ShopCategory> getDao() {
		return shopCategoryDao;
	}

	@Override
	public List<ShopCategory> getAllByLevel(Integer level) {
		return shopCategoryDao.getBy("level", level);
	}

	@Override
	public List<ShopCategory> getAllForBrand() {
		String hql = "select distinct s from ShopCategory s right join s.brands br ";
		return shopCategoryDao.find(hql, null, -1, -1);
	}

	@Override
	public List<ShopCategory> getAllForSpecial() {
		String hql = "select distinct s from ShopCategory s right join s.shopCommoidties sh with sh.isSpecial = 1 order by parentLevel ";
		return shopCategoryDao.find(hql, null, -1, -1);
	}

	@Override
	public List<ShopCategory> getAllByParentLevel(Integer categoryID) {
		return shopCategoryDao.getBy("parentLevel", categoryID);
	}
	
	@Override
	public List<ShopCategory> getAllByParent() {
		StringBuffer hql = new StringBuffer(" from ShopCategory depart where depart.parentLevel.categoryID is null ");
		return shopCategoryDao.find(hql.toString(), null, null);
	}
	
    @Override
	public List<ShopCategory> getAllByType(int i) {
		StringBuffer hql = new StringBuffer(" from ShopCategory cate where cate.categoryID = "+i);
		return shopCategoryDao.find(hql.toString(), null,1,8);
	}
    
    /* (non-Javadoc)
	 * 根据类名查找类别对象
	 * @see com.yc.service.IShopCategoryService#getByshopCate(java.lang.String)
	 */
	@Override
	public ShopCategory getByshopCate(String shopCate) {
		StringBuffer hql=new StringBuffer("SELECT * FROM shopcategory WHERE shopcategory.category='"+shopCate+"'");
		Query query = shopCategoryDao.getEntityManager().createNativeQuery(hql.toString(), ShopCategory.class);
		ShopCategory shopCategory=(ShopCategory) query.getSingleResult();
		return shopCategory;
	}
}
