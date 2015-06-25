package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ShopCommodity;
import com.yc.model.ShopCommodityModel;
import com.yc.service.IShopCommodityService;

@Component
public class ShopCommodityService extends GenericService<ShopCommodity>
		implements IShopCommodityService {

	@Autowired
	GenericDao<ShopCommodity> shopCommoidtyDao;

	@Override
	GenericDao<ShopCommodity> getDao() {
		return shopCommoidtyDao;
	}

	@Override
	public List<ShopCommodity> getAllByShop(Integer id) {
		return shopCommoidtyDao.getBy("belongTo.id", id);
	}

	@Override
	public List<ShopCommodity> getAllByCondition(String condition,
			boolean isTrue, Integer shopID) {
		List<String> keys = new ArrayList<String>();
		keys.add(condition);
		keys.add("belongTo.id");
		List<Object> values = new ArrayList<Object>();
		values.add(isTrue ? 1 : 0);
		values.add(shopID);
		return shopCommoidtyDao.getBy(keys, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopCommodity> getAllByShopCategoryID(Integer id, String page) {
		StringBuffer hql = new StringBuffer(
				"SELECT shc.* FROM ShopCommodity shc JOIN Shop shop ON shop.id = shc.shop_id WHERE (shc.blacklist_id IS NULL AND shop.blacklist_id IS NULL AND shc.shelves = 1 ) AND shc.shop_id IS NOT NULL AND shc.shopCategory_id ="
						+ id);
		if (page.equals("brand")) {
			hql.append(" and shc.brand_id is not null");
		}
		if (page.equals("special")) {
			hql.append(" and shc.isSpecial = 1");
		}
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	// Integer id, String brand, String specs, String money
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopCommodity> getAllByParams(Map<String, Object> map,
			String page) {
		StringBuffer hql = new StringBuffer(
				"select shc.* from ShopCommodity shc JOIN Shop shop ON shop.id = shc.shop_id right join ShopCommoidtySpecs sp on shc.commCode = sp.shopComm_id where (shc.blacklist_id IS NULL AND shop.blacklist_id IS NULL AND shc.shelves = 1 ) and shc.shelves = 1 ");
		if (page.equals("special")) {
			hql.append(" and shc.isSpecial = 1");
		}
		if (page.equals("brand")) {
			hql.append(" and shc.brand_id is not null");
		}
		String[] spec = null;
		if (map.get("specs") != null && !map.get("specs").equals("")) {
			if (map.get("specs").toString().contains("@")) {
				spec = map.get("specs").toString().split("@");
				for (int i = 0; i < spec.length; i++) {
					hql.append(" and ('" + spec[i]
							+ "' is null or sp.commSpec like '" + spec[i]
							+ "') ");
				}
			} else {
				hql.append(" and ('" + map.get("specs")
						+ "' is null or sp.commSpec like '" + map.get("specs")
						+ "') ");
			}
		}
		if (map.get("money") != null && !map.get("money").equals("")) {
			if (!map.get("money").toString().split("@")[0].equals("")) {
				hql = hql.append(" and ("
						+ map.get("money").toString().split("@")[0]
						+ " is null or shc.unitPrice >= "
						+ map.get("money").toString().split("@")[0] + ")");
			}
			if (!map.get("money").toString().split("@")[1].equals("")) {
				hql = hql.append(" and ("
						+ map.get("money").toString().split("@")[1]
						+ " is null or shc.unitPrice < "
						+ map.get("money").toString().split("@")[1] + ")");
			}
		}
		if (map.get("id") != null && !map.get("id").equals("")) {
			hql = hql.append(" and (" + map.get("id")
					+ " is null or shc.shopCategory_id = " + map.get("id")
					+ ")");
		}
		if (map.get("brand") != null && !map.get("brand").equals("")) {
			hql = hql.append(" and (shc.brand_id in " + map.get("brand") + ")");
		}
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	@Override
	public List<ShopCommodity> getAllByNameAndShop(String commoName,
			Integer shopID) {
		List<String> keys = new ArrayList<String>();
		keys.add("commoidtyName");
		keys.add("belongTo.id");
		List<Object> values = new ArrayList<Object>();
		values.add(commoName);
		values.add(shopID);
		return shopCommoidtyDao.getBy(keys, values);
	}

	@Override
	public ShopCommodity getAllByCommItemAndShop(String commItem, Integer id) {
		List<String> keys = new ArrayList<String>();
		keys.add("commItem");
		keys.add("belongTo.id");
		List<Object> values = new ArrayList<Object>();
		values.add(commItem);
		values.add(id);
		return shopCommoidtyDao.getFirstRecord(keys, values);
	}

	@Override
	public List<String> getShopCommBySpeces(String speces) {
		StringBuffer hql = new StringBuffer(
				"select sp.commSpec from ShopCommodity sc join ShopCommoditySpecs sp on sp.shopComm_id = sc.commCode where sc.blacklist_id is null and sp.commSpec  LIKE '%,"
						+ speces + "%'");
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<String> list = new ArrayList<String>();
		for (Object object : objecArraytList) {
			list.add(object.toString());
		}
		return list;
	}

	@Override
	public List<ShopCommodity> getAllByParamsForBlack(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer(
				" from ShopCommodity comm where (? is null or comm.commoidtyName like ?) and (? is null or comm.commItem = ?) and (? is null or comm.commCode = ?)");
		Object[] paramete = new Object[6];
		paramete[0] = map.get("commoidtyName");
		paramete[1] = "%" + map.get("commoidtyName") + "%";
		paramete[2] = map.get("commItem");
		paramete[3] = map.get("commItem");
		paramete[4] = map.get("commCode");
		paramete[5] = map.get("commCode");
		return shopCommoidtyDao.find(hql.toString(), paramete, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yc.service.IShopCommoidtyService#getShopCommByCateAndIsspecial()
	 * 根據類型和是否折扣展示商品
	 */
	@Override
	public List<ShopCommodity> getShopCommByCateAndIsspecial(Integer CateId,
			Boolean flag) {
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM ShopCommodity WHERE ShopCommodity.isSpecial="
						+ flag);
		if (CateId <= 1) {
			hql.append("  AND ShopCommodity.shopCategory_id IS NOT NULL");
		} else {
			hql.append("  AND ShopCommodity.shopCategory_id=" + CateId);
		}
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		@SuppressWarnings("unchecked")
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.service.IShopCommoidtyService#getShopCommByCateAndBrand(java.lang
	 * .Integer) 根据商品类型和品牌查找商品
	 */
	@Override
	public List<ShopCommodity> getShopCommByCateAndBrand(Integer id) {
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM ShopCommodity WHERE ShopCommodity.brand_id IS NOT NULL");
		if (id <= 1) {
			hql.append("  AND shopcommoidty.shopCategory_id IS NOT NULL");
		} else {
			hql.append("  AND shopcommoidty.shopCategory_id=" + id);
		}
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		@SuppressWarnings("unchecked")
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.service.IShopCommoidtyService#getShopCommByBrandId(java.lang.Integer
	 * ) 根据品牌Id查找对应商品
	 */
	@Override
	public List<ShopCommodity> getShopCommByBrandId(Integer id) {
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM ShopCommodity WHERE ShopCommodity.brand_id ="
						+ id);
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		@SuppressWarnings("unchecked")
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopCommodity> searchShopComm(String content) {
		StringBuffer hql = new StringBuffer(
				" select * from ShopCommodity comm left join ShopCategory c ON comm.shopcategory_id = c.categoryID left join Brand b on b.brandID = comm.brand_id where comm.commoidtyName like '%"
						+ content
						+ "%' or c.category like '%"
						+ content
						+ "%' or b.brandName like '%" + content + "%'");
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		return query.getResultList();
	}

	@Override
	public List<ShopCommodity> getAllByFamousManorID(Integer id, int i) {
		StringBuffer hql = new StringBuffer(
				"SELECT ShopCommodity.*  FROM ShopCommodity LEFT JOIN famousmanorandshop fms ON fms.id =  ShopCommodity.famAndShop_id  WHERE  fms.famousManor_id ="
						+ id);
		if (i != -1) {
			hql.append(" LIMIT 0," + i);
		}
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString(), ShopCommodity.class);
		@SuppressWarnings("unchecked")
		List<ShopCommodity> list = query.getResultList();
		return list;
	}

	@Override
	public List<ShopCommodityModel> getSpecialShopComm(int num) {
		StringBuffer hql = null;
		if ( num == -1 ) {
			hql = new StringBuffer(
					"SELECT DISTINCT sc.unitPrice,sc.describes, sc.commCode, i.imagePath FROM ShopCommodity sc LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE sc.isSpecial IS TRUE");
		} else {
			hql = new StringBuffer(
					"SELECT DISTINCT sc.unitPrice,sc.describes, sc.commCode, i.imagePath FROM ShopCommodity sc LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE sc.isSpecial IS TRUE LIMIT "+num);
		}
		
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<ShopCommodityModel> pr = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new ShopCommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setUnitPrice(Float.parseFloat(obj[0].toString()));
					mode.setDescribes(obj[1].toString());
					mode.setCommCode(Integer.parseInt(obj[2].toString()));
					mode.setShopCommImage(obj[3].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	@Override
	public List<ShopCommodityModel> getMyShopComm(int num) {
		StringBuffer hql = null;
		if ( num == -1 ) {
			hql = new StringBuffer(
					"SELECT DISTINCT sc.unitPrice,sc.describes, sc.commCode, i.imagePath FROM ShopCommodity sc RIGHT JOIN Shop s ON s.id = sc.shop_id LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE s.shopName = '我的酒翁'");
		} else {
			hql = new StringBuffer(
					"SELECT DISTINCT sc.unitPrice,sc.describes, sc.commCode, i.imagePath FROM ShopCommodity sc RIGHT JOIN Shop s ON s.id = sc.shop_id LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE s.shopName = '我的酒翁' LIMIT "+num);
		}
		
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<ShopCommodityModel> pr = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new ShopCommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setUnitPrice(Float.parseFloat(obj[0].toString()));
					mode.setDescribes(obj[1].toString());
					mode.setCommCode(Integer.parseInt(obj[2].toString()));
					mode.setShopCommImage(obj[3].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.service.IShopCommoidtyService#getShopCommByCate(java.lang.String)
	 * 根据类型查找商品集合价钱 描述 Id 图片（shopcommodityImage）
	 */
	@Override
	public List<ShopCommodityModel> getShopCommByBrand(String brandId) {
		StringBuffer hql = new StringBuffer(
				"SELECT  sc.unitPrice,sc.describes, sc.commCode, i.imagePath, b.brandName FROM ShopCommodity sc LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id RIGHT JOIN Brand b ON b.brandID = sc.brand_id WHERE sc.brand_id = "
						+ brandId);
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<ShopCommodityModel> pr = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new ShopCommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setUnitPrice(Float.parseFloat(obj[0].toString()));
					mode.setDescribes(obj[1].toString());
					mode.setCommCode(Integer.parseInt(obj[2].toString()));
					mode.setShopCommImage(obj[3].toString());
					mode.setBrandName(obj[4].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.service.IShopCommodityService#getShopCommByCate(java.lang.String)
	 * 根据类型和是否打折查找对应的商品集合并转化为ShopCommodityModel类型
	 */
	@Override
	public List<ShopCommodityModel> getShopCommByCateAndSpecial(String cateId,
			int num) {
		StringBuffer hql = null;
		if (num == -1) {
			hql = new StringBuffer(
					"SELECT  sc.unitPrice,sc.describes, sc.commCode,i.imagePath,sc.special FROM ShopCommodity sc LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE sc.isSpecial=1 AND sc.shopCategory_id="
							+ cateId + " ORDER BY sc.special ASC");
		} else {
			hql = new StringBuffer(
					"SELECT  sc.unitPrice,sc.describes, sc.commCode,i.imagePath,sc.special FROM ShopCommodity sc LEFT JOIN ShopCommImage i ON sc.commCode = i.shopCommoidty_id WHERE sc.isSpecial=1 AND sc.shopCategory_id="
							+ cateId + " ORDER BY sc.special ASC LIMIT " + num);
		}

		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<ShopCommodityModel> pr = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new ShopCommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setUnitPrice(Float.parseFloat(obj[0].toString()));
					mode.setDescribes(obj[1].toString());
					mode.setCommCode(Integer.parseInt(obj[2].toString()));
					mode.setShopCommImage(obj[3].toString());
					mode.setSpecial(Float.parseFloat(obj[4].toString()));
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.service.IShopCommodityService#getShopCommByCateAndBoutique(java
	 * .lang.String) 根据类型和是否精品查找对应的商品集合并转化为ShopCommodityModel类型
	 */
	@Override
	public List<ShopCommodityModel> getShopCommByCateAndBoutique(String cateId,
			int num) {
		StringBuffer hql = null;
		if (num == -1) {
			hql = new StringBuffer(
					"SELECT sc.unitPrice,sc.describes, sc.commCode,i.imagePath FROM shopcommodity sc LEFT JOIN shop sp ON sc.shop_id=sp.id LEFT JOIN shopcommimage i ON i.shopCommoidty_id=sc.commCode WHERE sc.shopCategory_id="
							+ cateId
							+ " AND sp.shopName='我的酒翁' ORDER BY sc.unitPrice DESC");
		} else {
			hql = new StringBuffer(
					"SELECT sc.unitPrice,sc.describes, sc.commCode,i.imagePath FROM shopcommodity sc LEFT JOIN shop sp ON sc.shop_id=sp.id LEFT JOIN shopcommimage i ON i.shopCommoidty_id=sc.commCode WHERE sc.shopCategory_id="
							+ cateId
							+ " AND sp.shopName='我的酒翁' ORDER BY sc.unitPrice DESC LIMIT "+num);
		}
		
		Query query = shopCommoidtyDao.getEntityManager().createNativeQuery(
				hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<ShopCommodityModel> pr = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new ShopCommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setUnitPrice(Float.parseFloat(obj[0].toString()));
					mode.setDescribes(obj[1].toString());
					mode.setCommCode(Integer.parseInt(obj[2].toString()));
					mode.setShopCommImage(obj[3].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}
}
