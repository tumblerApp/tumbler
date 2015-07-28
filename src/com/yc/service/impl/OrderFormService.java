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
import com.yc.entity.OrderStatus;
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
		String hql = " from OrderForm c where c.orderstatus = '"+orderStatus+"'";
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
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o where o.user_id = "+userID);
		if (map.get("orderStatus") != null) {
			if (map.get("orderStatus").equals("wanjie")) {
				hql.append(" and o.orderstatus in('"+OrderStatus.completionTransaction+"')");
			}else if(map.get("orderStatus").equals("tuikuan")){
				hql.append(" and o.orderstatus in ('"+OrderStatus.ApplicationForRefund+"','"+OrderStatus.refundOrderForm+"','"+OrderStatus.refundSuccess+"','"+OrderStatus.refundFailed+"')");
			}else {
				hql.append(" and o.orderstatus in ('"+OrderStatus.valueOf(map.get("orderStatus").toString())+"')");
			}
		}
		if (map.get("orderDate") != null) {
			if (map.get("orderDate").equals("volvo")) {
				List<String> dates = CalendarDays(5);
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
			}else if(map.get("orderDate").equals("saab")){
				Calendar cal = Calendar.getInstance();
				Date d1 = new Date();
				cal.add(Calendar.MONTH, -1);
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
			}else if(map.get("orderDate").equals("fiat")){
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
			}
		}
		orderFormDao.getEntityManager().clear();
		Query query = orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}
	
	@Override
	public List<OrderForm> getAllRefundByStatus(Map<String, Object> map,
			Integer shopID) {
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o right join Commodity com on com.orderform_id = o.orderFormID  where com.seller_name = "+shopID);
		
		if (map.get("orderStatusRefunding") != null) {
			hql.append(" and o.orderstatus = '" + map.get("orderStatusRefunding") + "'");
		}if (map.get("orderStatusRefundSuccess") != null) {
			hql.append(" or o.orderstatus = '" + map.get("orderStatusRefundSuccess") + "'");
		}if (map.get("orderStatusRefundfail") != null) {
			hql.append(" or o.orderstatus = '" + map.get("orderStatusRefundfail") + "'");
		}if (map.get("ApplicationForRefund") != null) {
			hql.append(" or o.orderstatus = '" + map.get("ApplicationForRefund") + "'");
		}
		orderFormDao.getEntityManager().clear();
		Query query =  orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}

	@Override
	public List<OrderForm> getAllRefundByStatusUID(Map<String, Object> map,
			Integer userID) {
		StringBuffer hql = new StringBuffer("select DISTINCT o.* from OrderForm o right join Commodity com on com.orderform_id = o.orderFormID  where o.user_id = "+userID);
		if (map.get("orderStatusRefunding") != null) {
			hql.append(" and o.orderstatus = '" + map.get("orderStatusRefunding") + "'");
		}if (map.get("orderStatusRefundSuccess") != null) {
			hql.append(" or o.orderstatus = '" + map.get("orderStatusRefundSuccess") + "'");
		}if (map.get("orderStatusRefundfail") != null) {
			hql.append(" or o.orderstatus = '" + map.get("orderStatusRefundfail") + "'");
		}
		Query query =  orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		@SuppressWarnings("unchecked")
		List<OrderForm> list =  query.getResultList();
		return list;
	}
	
	/* (non-Javadoc)
	 * 查询最新订单信息
	 * @see com.yc.service.IOrderFormService#searchNewst()
	 */
	@Override
	public OrderForm searchNewst() {
		StringBuffer hql=new StringBuffer("SELECT * FROM orderform ORDER BY orderform.orderFormID DESC LIMIT 1");
		Query query =  orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		OrderForm newstOrderForm=(OrderForm) query.getSingleResult();
		return newstOrderForm;
	}
	
	/* (non-Javadoc)
	 * 查询用户的订单信息
	 * @see com.yc.service.IOrderFormService#findByPhone(java.lang.String)
	 */
	@Override
	public List<OrderForm> findByPhone(String phone) {
		return orderFormDao.getBy("orderUser.phone", phone);
	}

	/* (non-Javadoc)
	 * 通过手机获取店铺的订单
	 * @see com.yc.service.IOrderFormService#findShopOrderByPhone(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderForm> findShopOrderByPhone(String phone) {
		StringBuffer hql=new StringBuffer("SELECT o.* FROM orderform o LEFT JOIN commodity c ON c.orderform_id = o.orderFormID "
				+ "LEFT JOIN shopcommodity sc ON sc.commCode = c.shopcommodity_id LEFT JOIN shop s ON s.id = sc.shop_id "
				+ "LEFT JOIN appuser u ON u.shop_id = s.id WHERE u.phone = '"+ phone+"' GROUP BY o.orderFormID");
		Query query =  orderFormDao.getEntityManager().createNativeQuery(hql.toString(), OrderForm.class);
		List<OrderForm> orderFormList=query.getResultList();
		return orderFormList;
	}
}
