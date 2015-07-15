package com.yc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ReviewsRank;
import com.yc.entity.ShopReviews;
import com.yc.service.IShopReviewsService;

@Component
public class ShopReviewsService extends GenericService<ShopReviews> implements IShopReviewsService {
	
	@Autowired
	GenericDao<ShopReviews> shopReviewsDao;
	
	@Override
	GenericDao<ShopReviews> getDao() {
		return shopReviewsDao;
	}

	@Override
	public List<ShopReviews> getAllBycommCode(Integer commID) {
		return shopReviewsDao.getBy("shopscommodity.commCode", commID);
	}

	@Override
	public List<ShopReviews> getReviewsByShop(Integer id) {
		return shopReviewsDao.getBy("shopscommodity.belongTo.id", id);
	}
	@Override
	public List<ShopReviews> getReviewsByUser(Integer id) {
		return shopReviewsDao.getBy("shopscommodity.belongTo.user.id", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer[] getReviewsByDate(Integer weeks,Integer month,Integer shopID) {
		int goodNum = 0,betterNum = 0, badNum = 0;
		StringBuffer hql = new StringBuffer("SELECT DISTINCT r.* FROM ShopReviews r ,ShopCommodity com WHERE com.commCode = r.shopcomm_id AND com.shop_id="+shopID);
		Calendar cal = Calendar.getInstance();
		Date d1 = new Date();
		cal.add(Calendar.MONTH, -month);
		cal.add(Calendar.DAY_OF_MONTH, -weeks*7);
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
	        List<ShopReviews> list = new ArrayList<ShopReviews>();
	        if (month==-1) {
	        	//查询全部评论：
		        shopReviewsDao.getEntityManager().clear();
				Query queryAll = shopReviewsDao.getEntityManager().createNativeQuery(hql.toString(), ShopReviews.class);
				list =  queryAll.getResultList();
	        	System.out.println("查询所有订单评论："+list.size());
				
			}else{
			    //加上时间查询
			    hql.append(" and r.reviewsdate in ("+takeDates.toString()+")"); 
			    shopReviewsDao.getEntityManager().clear();
				Query query = shopReviewsDao.getEntityManager().createNativeQuery(hql.toString(), ShopReviews.class);
				list =  query.getResultList();
			}
	        
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getReviewsRank().equals(ReviewsRank.good)) {
					goodNum++;
				}else if (list.get(i).getReviewsRank().equals(ReviewsRank.better)) {
					betterNum++;
				}else if (list.get(i).getReviewsRank().equals(ReviewsRank.bad)) {
					badNum++;
				}
			}
			Integer[]reviewsNum = new Integer [3];
			reviewsNum [0] = goodNum;
			reviewsNum [1] = betterNum;
			reviewsNum [2] = badNum;
			
			return reviewsNum;
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

	
	
	
	//app使用方法
	@Override
	public ShopReviews getAllByOrderAndComm(Integer orderFormID, Integer commCode) {
		List<String> keys = new ArrayList<String>();
		keys.add("orderForm.orderFormID");
		keys.add("shopscommodity.commCode");
		List<Object> values = new ArrayList<Object>();
		values.add(orderFormID);
		values.add(commCode);
		return shopReviewsDao.getFirstRecord(keys, values);
	}
	
	/* (non-Javadoc)
	 * 根据商品id查询对应的商品信息
	 * @see com.yc.service.IShopReviewsService#findByShopCommId()
	 */
	@Override
	public List<ShopReviews> findByShopCommId(int shopCommId) {
		StringBuffer hql=new StringBuffer("SELECT * FROM shopreviews WHERE shopreviews.shopcomm_id="+shopCommId);
		Query query = shopReviewsDao.getEntityManager().createNativeQuery(hql.toString(), ShopReviews.class);
		@SuppressWarnings("unchecked")
		List<ShopReviews> list =  query.getResultList();
		return list;
	}

}
