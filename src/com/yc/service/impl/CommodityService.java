package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Commodity;
import com.yc.entity.OrderStatus;
import com.yc.model.CommodityModel;
import com.yc.service.ICommodityService;

import java.util.Map;

import javax.persistence.Query;

@Component
public class CommodityService extends GenericService<Commodity> implements ICommodityService {

	@Autowired
	GenericDao<Commodity> commodityDao;

	@Override
	GenericDao<Commodity> getDao() {
		return commodityDao;
	}
	
	@Override
	public List<Commodity> getAllByParametersForOrder(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Commodity c where (? is null or c.transNumForTaobao = ?) " 
		+ " and (? is null or c.orderNumber.orderFormID = ?) and (? is null or c.orderNumber.orderUser.userName like ?) " 
		+ " and (? is null or c.tpek = ?) and (? is null or c.orderNumber.changeStatusDate like ?) " 
		+ " and (? is null or c.orderNumber.paymentDate like ?) and (? is null or c.orderNumber.orderstatus = ?) "
		+ " and  (? is null or c.commItem = ?) and (? is null or c.orderNumber.orderDate = ?)");
		Object[] paramete = new Object[18];
		paramete[0] = map.get("transNumForTaobao");
		paramete[1] = map.get("transNumForTaobao");
		paramete[2] = map.get("orderFormID");
		paramete[3] = map.get("orderFormID");
		paramete[4] = map.get("userName");
		paramete[5] = "%" + map.get("userName") + "%";
		paramete[6] = map.get("tpek");
		paramete[7] = map.get("tpek");
		paramete[8] = map.get("changeStatusDate");
		paramete[9] = "%" + map.get("changeStatusDate") + "%";
		paramete[10] = map.get("paymentDate");
		paramete[11] = "%" + map.get("paymentDate") + "%";
		paramete[12] = map.get("orderstatus");
		paramete[13] = map.get("orderstatus");
		paramete[14] = map.get("commItem");
		paramete[15] = map.get("commItem");
		paramete[16] = map.get("orderDate");
		paramete[17] = map.get("orderDate");
		return commodityDao.find(hql.toString(), paramete, -1, -1);
	}

	// 分类查询
	@Override
	public List<CommodityModel> getAllByShopCategoryID(Integer id,Integer limit) {
		StringBuffer hql = new StringBuffer("SELECT DISTINCT s.categoryID,s.category FROM commodity c RIGHT JOIN shopcategory s ON s.categoryID = c.shopcategory WHERE  s.parent_ID =" + id + " limit ="+limit);
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<CommodityModel> list = new ArrayList<CommodityModel>();
		CommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new CommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				mode.setCategoryID(Integer.parseInt(obj[0].toString()));
				mode.setCategory(obj[1].toString());
				list.add(mode);
			}
		}
		return list;
	}

    // 热销商品查询
	@Override
	public List<CommodityModel> getAllByCommdityID(Integer id,Integer limit) {	
		StringBuffer hql = new StringBuffer("SELECT DISTINCT c.transNumForTaobao,s.categoryID,c.seller_name,c.nameOfGoods,i.path,SUM(quantity) ss FROM commodity c RIGHT JOIN shopcategory s ON s.categoryID = c.shopcategory LEFT JOIN  ImagePath i  ON c.commodityID = i.from_commodity WHERE s.categoryID = "+id+" GROUP BY c.transNumForTaobao  ORDER BY ss DESC,c.transNumForTaobao LIMIT "+limit);
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<CommodityModel> pr = new ArrayList<CommodityModel>();
		CommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new CommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj[i] != null) {
					mode.setPath(obj[4].toString());
					mode.setSums(Integer.parseInt(obj[5].toString()));
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commodity> getAllByOrderStatus(String status, Integer shop_id) {
		StringBuffer hql = new StringBuffer("select DISTINCT comm.* from Commodity comm left join OrderForm orders on orders.orderFormID = comm.orderform_id where orders.orderstatus in (" + status + ") and comm.seller.id = '" +  shop_id + "'");
		return commodityDao.getEntityManager().createNativeQuery(hql.toString(), Commodity.class).getResultList();
	}

	@Override
	public Commodity getCommByOrderIDAndCommCode(Integer orderid, Integer commCode) {
		List<String> keys = new ArrayList<String>();
		keys.add("orderNumber.orderFormID");
		keys.add("commodityID");
		List<Object> values = new ArrayList<Object>();
		values.add(orderid);
		values.add(commCode);
		return commodityDao.getFirstRecord(keys, values);
	}

	@Override
	public List<Commodity> getOrderByPurchaseAndUser(OrderStatus orderStatus,Integer shopID, Integer userID) {
		StringBuffer hql = new StringBuffer("SELECT DISTINCT comm.* FROM Commodity comm LEFT JOIN OrderForm orders ON orders.orderFormID = comm.orderform_id WHERE orders.orderstatus = '"+orderStatus+"' AND comm.seller_name = "+shopID+" AND orders.user_id =  " + userID);
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString(), Commodity.class);
		@SuppressWarnings("unchecked")
		List<Commodity> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Commodity> getAllByUserAndStatus(Integer userID,String orderStatus) {
		StringBuffer hql = new StringBuffer(" select com.* from commodity com LEFT JOIN orderform orders on orders.orderFormID = com.orderform_id where orders.user_id = "+userID
				+" AND orders.orderstatus IN ("+orderStatus+")");
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString(), Commodity.class);
		@SuppressWarnings("unchecked")
		List<Commodity> list = query.getResultList();
		return list;
	}

	@Override
	public Integer getCommodityByStatusAndShop(String status, Integer shop_id) {
		StringBuffer hql = new StringBuffer("select COUNT(DISTINCT commodityID) from Commodity c LEFT JOIN orderform orders on orders.orderFormID = c.orderform_id where c.seller_name = " + shop_id 
				+" and orders.orderstatus IN ("+status+")");
		Query query =  commodityDao.getEntityManager().createNativeQuery(hql.toString());
		return Integer.parseInt(query.getSingleResult().toString());
	}

	//获得热销产品
	@Override
	public List<CommodityModel> getHotCommodities(int num) {
		StringBuffer hql = null;
		if ( num == -1 ) {
			hql = new StringBuffer("SELECT DISTINCT sc.commCode,c.price,sc.commoidtyName,i.imagePath,SUM(c.quantity) ss,c.describes FROM commodity c LEFT JOIN shopcommodity sc ON sc.commCode = c.shopcommodity_id LEFT JOIN shopcommimage i ON i.shopCommoidty_id = sc.commCode GROUP BY c.shopcommodity_id ORDER BY ss DESC");
		} else {
			hql = new StringBuffer("SELECT DISTINCT sc.commCode,c.price,sc.commoidtyName,i.imagePath,SUM(c.quantity) ss,c.describes FROM commodity c LEFT JOIN shopcommodity sc ON sc.commCode = c.shopcommodity_id LEFT JOIN shopcommimage i ON i.shopCommoidty_id = sc.commCode GROUP BY c.shopcommodity_id ORDER BY ss DESC LIMIT "+num);
		}
		 
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<CommodityModel> pr = new ArrayList<CommodityModel>();
		CommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new CommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setShopCommodityId(Integer.parseInt(obj[0].toString()));
					mode.setPrice(Float.parseFloat(obj[1].toString()));
					mode.setCommodityName(obj[2].toString());
					mode.setPath(obj[3].toString());
					mode.setSums(Integer.parseInt(obj[4].toString()));
					//mode.setDescribes(obj[5].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}

	/* (non-Javadoc)
	 * @see com.yc.service.ICommodityService#getShopCommByCateAndHot(java.lang.String)
	 * 查找当前类别的热销产品集合
	 */
	@Override
	public List<CommodityModel> getShopCommByCateAndHot(String cateId,int num) {
		StringBuffer hql = null;
		if ( num == -1 ) {
			hql=new StringBuffer("SELECT DISTINCT sc.commCode,c.price,sc.commoidtyName,i.imagePath,SUM(c.quantity) ss,c.describes FROM commodity c LEFT JOIN shopcommodity sc ON sc.commCode = c.shopcommodity_id LEFT JOIN shopcommimage i ON i.shopCommoidty_id = sc.commCode WHERE c.shopcategory = "+cateId+" GROUP BY c.shopcommodity_id ORDER BY ss DESC");
		} else {
			hql=new StringBuffer("SELECT DISTINCT sc.commCode,c.price,sc.commoidtyName,i.imagePath,SUM(c.quantity) ss,c.describes FROM commodity c LEFT JOIN shopcommodity sc ON sc.commCode = c.shopcommodity_id LEFT JOIN shopcommimage i ON i.shopCommoidty_id = sc.commCode WHERE c.shopcategory = "+cateId+" GROUP BY c.shopcommodity_id ORDER BY ss DESC LIMIT "+num);
		}
		Query query = commodityDao.getEntityManager().createNativeQuery(hql.toString());
		@SuppressWarnings("rawtypes")
		List objecArraytList = query.getResultList();
		List<CommodityModel> pr = new ArrayList<CommodityModel>();
		CommodityModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new CommodityModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setShopCommodityId(Integer.parseInt(obj[0].toString()));
					mode.setPrice(Float.parseFloat(obj[1].toString()));
					mode.setCommodityName(obj[2].toString());
					mode.setPath(obj[3].toString());
					mode.setSums(Integer.parseInt(obj[4].toString()));
					//mode.setDescribes(obj[5].toString());
					pr.add(mode);
				}
			}
		}
		return pr;
	}
}
