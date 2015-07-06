package com.yc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.OrderForm;
import com.yc.service.IOrderFormService;

@Component
public class OrderFormService extends GenericService<OrderForm> implements IOrderFormService {

	@Autowired
	GenericDao<OrderForm> orderFormDao;

	@Override
	GenericDao<OrderForm> getDao() {
		return orderFormDao;
	}

	@Override
	public List<OrderForm> findByPackAgeID(Integer id) {
		return orderFormDao.getBy("packAge.packageID", id);
	}

	@Override
	public List<OrderForm> findByUserName(String userName) {
		return orderFormDao.getBy("orderUser.userName", userName);
	}

	@Override
	public List<OrderForm> findByUserID(Integer id) {
		return orderFormDao.getBy("orderUser.id", id);
	}

	@Override
	public List<OrderForm> getOrderByOrderGroup(Integer id) {
		return orderFormDao.getBy("orderGroup.orderGroupID", id);
	}
	
	@Override
	public List<OrderForm> getAllByOrderStatus(String orderStatus) {
		String hql = " from OrderForm c where c.orderstatus in ("+orderStatus+")";
		return orderFormDao.find(hql, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderForm> getOrderFormByParameters(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o left join User u on o.user_id = u.id left join Commodity com on o.orderFormID = com.orderform_id"
				+ " left join Shop shop on shop.id = com.seller_name where 1=1 ");
		if (map.get("orderstatus") !=null) {
			hql.append(" and o.orderstatus = '"+map.get("orderstatus")+"'");
		}
		if (map.get("orderID") !=null) {
			hql.append(" and o.orderFormID = '"+map.get("orderID")+"'");
		}
		if (map.get("shopName") !=null) {
			hql.append(" and shop.shopName like '%"+map.get("shopName")+"%'");
		}
		if (map.get("orderDate") !=null) {
			hql.append(" and o.orderDate like '%"+map.get("orderDate")+"%'");
		}
		if (map.get("paymentDate") !=null) {
			hql.append(" and o.paymentDate like '%"+map.get("paymentDate")+"%'");
		}
		if (map.get("tpek") !=null) {
			hql.append(" and com.tpek = '"+map.get("tpek")+"'");
		}
		if (map.get("orderUser") !=null) {
			hql.append(" and u.userName like '%"+map.get("orderUser")+"%'");
		}
		hql.append(" order by o.orderFormID desc");
		return orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class).getResultList();
	}
	
	private List<String> CalendarDays(int day) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> weekDays = new ArrayList<String>();
        for (int i = 0; i < day; i++) {
            weekDays.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
        }
        return weekDays;
    }

	@Override
	public List<OrderForm> getShopOrderByShop(Integer shopID) {
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o right join Commodity com on com.orderform_id = o.orderFormID  where com.seller_name = "+shopID);
		Calendar cal = Calendar.getInstance();
		Date d1 = new Date();
		cal.add(Calendar.MONTH, -3);
		Date d2 = cal.getTime();
		cal.setTime(new Date(d2.getTime() - 24 * 60 * 60 * 1000));
		d2 = cal.getTime();
		long daterange = d1.getTime() - d2.getTime();     
	    long time = 1000*3600*24;
	    List<String> dates = CalendarDays(Integer.parseInt(String.valueOf(daterange/time)));
		 StringBuilder takeDates = new StringBuilder();
	        for (String date : dates) {
	            if (takeDates.length() > 0) {
	                takeDates.append(",");
	            }
	            takeDates.append("'");
	            takeDates.append(date);
	            takeDates.append("'");
	        }
	    hql.append(" and o.orderDate in ("+takeDates.toString()+")"); 
	    orderFormDao.getEntityManager().clear();
		Query query = orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}
	
	@Override
	public Integer getShopOrderByStatusAndShop(String status, Integer shop_id) {
		StringBuffer hql = new StringBuffer("select COUNT(DISTINCT orderFormID) from OrderForm o right join Commodity com on com.orderform_id = o.orderFormID  where o.orderstatus in(" + status + ") and com.seller_name = "+shop_id);
		Query query =  orderFormDao.getEntityManager().createNativeQuery(hql.toString());
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<OrderForm> getShopOrderByParam(Map<String, Object> map, Integer shopID) throws ParseException {
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o right join Commodity com on com.orderform_id = o.orderFormID  where com.seller_name = "+shopID);
		if (map.get("nameOfGoods") != null) {
			hql.append(" and com.nameOfGoods like '%"+map.get("nameOfGoods")+"%'");
		}
		if (map.get("orderUserName") != null) {
			hql.append(" and o.user_id in (select id from User u where u.userName like '%"+map.get("orderUserName")+"%')");
		}
		if (map.get("orderFormID") != null) {
			hql.append(" and o.orderFormID = " + map.get("orderFormID"));
		}
		if (map.get("orderStatusFrom") != null) {
			hql.append(" and o.orderstatus = '" + map.get("orderStatusFrom") + "'");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (map.get("paymentDateLeft") != null && map.get("paymentDateRight") == null) {
			Calendar cal = Calendar.getInstance();
			Date d1 = new Date();
			Date dd = sdf.parse(map.get("paymentDateLeft").toString());
			cal.setTime(new Date(dd.getTime() - 24 * 60 * 60 * 1000));
			Date d2 = cal.getTime();
			long daterange = d1.getTime() - d2.getTime();     
		    long time = 1000*3600*24;
		    List<String> dates = CalendarDays(Integer.parseInt(String.valueOf(daterange/time)));
			 StringBuilder takeDates = new StringBuilder();
		        for (String date : dates) {
		            if (takeDates.length() > 0) {
		                takeDates.append(",");
		            }
		            takeDates.append("'");
		            takeDates.append(date);
		            takeDates.append("'");
		        }
		    hql.append(" and o.orderDate in ("+takeDates.toString()+")"); 
		}
		if (map.get("paymentDateLeft") != null && map.get("paymentDateRight") != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(map.get("paymentDateRight").toString()));
			Date d1 = cal.getTime();
			Date dd = sdf.parse(map.get("paymentDateLeft").toString());
			cal.setTime(new Date(dd.getTime() - 24 * 60 * 60 * 1000));
			Date d2 = cal.getTime();
			long daterange = d1.getTime() - d2.getTime(); 
			if (daterange>0) {
				long time = 1000*3600*24;
			    List<String> dates = CalendarDays(Integer.parseInt(String.valueOf(daterange/time)));
				 StringBuilder takeDates = new StringBuilder();
			        for (String date : dates) {
			            if (takeDates.length() > 0) {
			                takeDates.append(",");
			            }
			            takeDates.append("'");
			            takeDates.append(date);
			            takeDates.append("'");
			        }
			    hql.append(" and o.orderDate in ("+takeDates.toString()+")"); 
			}
		}
		orderFormDao.getEntityManager().clear();
		Query query = orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}
	
	@Override
	public List<OrderForm> getAllByStatus() {
		StringBuffer hql = new StringBuffer("select orders.* from OrderForm orders join Commodity comm "
				+ " on comm.orderform_id = orders.orderFormID where orders.orderstatus not in"
				+ " ('completionTransaction','closeTransaction')");
		Query query = orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}

	@Override
	public List<OrderForm> getAllByParams(Map<String, Object> map,
			Integer userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
