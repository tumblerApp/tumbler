package com.yc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yc.entity.Activity;
import com.yc.entity.ActivityStyle;
import com.yc.entity.BuyCar;
import com.yc.entity.CarCommodity;
import com.yc.entity.Shop;
import com.yc.entity.ShopCategory;
import com.yc.entity.ShopCommodity;
import com.yc.entity.user.AppUser;
import com.yc.service.IActivityService;
import com.yc.service.IBuyCarService;
import com.yc.service.ICarCommodityService;
import com.yc.service.IShopCommodityService;

@Service
public class ServiceTools {
	@Autowired
    ICarCommodityService carcommoidtyService;
	
	@Autowired
    IActivityService activityService;
	
	@Autowired
    IShopCommodityService shopcommodityService;
	
	@Autowired
	IBuyCarService buyCarService;
	/**
  	 * 进入购物车对购物车商品进行过期判断处理
  	 * @param carcommodities
  	 * @return
  	 * @throws ParseException 
  	 */
  	public   List<CarCommodity> handleCarCommodity(List<CarCommodity> carcommodities,String userName) throws ParseException{
  		  List<CarCommodity> handleCarCommodities=new ArrayList<CarCommodity>();
  		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		  System.out.println("carcommodities===="+carcommodities.size()+"---"+userName);
            for (int i = 0; i < carcommodities.size();i++) {
  			        CarCommodity carcommodity=carcommodities.get(i);// 循环获得购物车商品
  			        ShopCommodity shopcommodity=carcommodity.getShopCommodity();//获得购物车商品所属的shopCommodity
  			        Boolean flag=shopcommodity.getIsAcitvity();//是否活动的开关量
  			        Boolean isSign=shopcommodity.getIsSpecial();//是否打折的开关量
  			        if(flag==false){
  			        	if(isSign){//不活动打折情况(1)
	  			        		carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
		  			        	carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
		  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
		  			      	    if(shopcommodity.getUsers().size()>0){
	 			            	 for (int j = 0; j < shopcommodity.getUsers().size(); j++) {
	 			            		shopcommodity.getUsers().get(j).getActivityCommodities().remove(shopcommodity);
									}
	 			            	   shopcommodity.getUsers().clear();//团购时间结束 当前商品的user集合清空
	 		   			           shopcommodityService.update(shopcommodity);
	 			                }
	  			        	    handleCarCommodities.add(carcommodity);
  			        	}else{
		  			        	carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
		  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
		  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
		  			        	if(shopcommodity.getUsers().size()>0){
		 			            	 for (int j = 0; j < shopcommodity.getUsers().size(); j++) {
		 			            		shopcommodity.getUsers().get(j).getActivityCommodities().remove(shopcommodity);
										}
		 			            	   shopcommodity.getUsers().clear();//团购时间结束 当前商品的user集合清空
		 		   			           shopcommodityService.update(shopcommodity);
		 			            }
		  			        	handleCarCommodities.add(carcommodity);
  			        	}
  			        }else{
  			        	Activity activity=shopcommodity.getActivity();//活动
  			        	System.out.println("==========="+shopcommodity.getUsers());
  			        	ActivityStyle activityStyle=activity.getActivityStyle();//活动方式
  			        	Date now=sdf.parse(sdf.format(new Date()));//现在时间
			            Date endDate=sdf.parse(activity.getEndDate());//活动截止时间
			            Boolean sign=endDate.before(now);// 判断截止日期(endDate)是否当前日期(now)之前，false即活动进行中
			            System.out.println("activityStyleName==="+activityStyle.getActivityStyleId()+"  "+activityStyle.getActivityStyle()+" "+sign);
  			        	if(activityStyle.getActivityStyle().equals("限时抢购")){
  			        		  if(sign==false){
  			        			carcommodity.setUnitPrice(shopcommodity.getSpecialPrice());//把购物车商品更新为限时活动价位
  		  			        	carcommodity.setPrice(shopcommodity.getSpecialPrice()*carcommodity.getAmount());//重新计算购物车总价
  		  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
  		  			        	handleCarCommodities.add(carcommodity);
  			        		  }else{
  			        			    carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
			  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
			  			            shopcommodity.setIsAcitvity(false);
		        			        shopcommodityService.update(shopcommodity);
			  			        	handleCarCommodities.add(carcommodity);
  			        		  }
  			        	}else if(activityStyle.getActivityStyle().equals("限量抢购")){
  			        		Integer activityamount=shopcommodity.getActivityAmount();//活动数量
  			        		 if(sign==false){
  			        			 if(activityamount>0){
  			        			    if(activityamount>carcommodity.getAmount()){
		  			        			  	carcommodity.setUnitPrice(shopcommodity.getSpecialPrice());//把购物车商品更新为限量活动价位
		  	  		  			        	carcommodity.setPrice(shopcommodity.getSpecialPrice()*carcommodity.getAmount());//重新计算购物车总价
		  	  		  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
		  	  		  			        	handleCarCommodities.add(carcommodity);
  			        			    }else{
  			        			    	//提示信息  仅剩x件
  			        			    }
  			        			 }else{
  			        				//活动数量不足 修改为原价并且关闭活动
  			         				carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
  			  			        	carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
  			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
  			  			            shopcommodity.setIsAcitvity(false);
			        			    shopcommodityService.update(shopcommodity);
  			  			        	handleCarCommodities.add(carcommodity);
  			        			 }
  			        		  }else{
  			        			  if(isSign){
  			        				carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
  			  			        	carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
  			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
	  			  			        shopcommodity.setIsAcitvity(false);
		        			        shopcommodityService.update(shopcommodity);
			  			        	handleCarCommodities.add(carcommodity);
  			        			  }else{
  			        			   carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
			  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
			  			            shopcommodity.setIsAcitvity(false);
		        			         shopcommodityService.update(shopcommodity);
			  			        	handleCarCommodities.add(carcommodity);
  			        			  }
			        		  }
  			        	}else if(activityStyle.getActivityStyle().equals("团购")){
  			        		 List<AppUser> appUserList=shopcommodity.getUsers();//抢到活动商品的的用户
  			        		 Integer activityAmount=shopcommodity.getActivityAmount();//参加团购活动的商品数量
  			        		 if(sign==false){
  			        			     if(activityAmount==100){//如果相等  用户集合享受团购价格
  			        			    	Boolean isExist=false;////判断用户是否在团购名单中 false 不在团购名单
 			        			         for (int j = 0; j < appUserList.size(); j++) {
											    if(appUserList.get(j).getPhone().equals(userName)){
											    	isExist=true;
					  		  			        	break;
											    }
										}
 			        			         if(isExist){
			  			        			       	carcommodity.setUnitPrice(shopcommodity.getSpecialPrice());//把购物车商品更新为团购活动价位
					  		  			        	carcommodity.setPrice(shopcommodity.getSpecialPrice()*carcommodity.getAmount());//重新计算购物车总价
					  		  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
					  		  			        	handleCarCommodities.add(carcommodity);
 			        			         }else{
 			        			        	       if(isSign){//折扣
 			        			        	    		carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
 			         			  			        	carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
 			         			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
 			         			  			            handleCarCommodities.add(carcommodity);
 			        			        	       }else{
	  			        			        	    carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
							  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
							  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
							  			        	handleCarCommodities.add(carcommodity);
 			        			        	       }
 			        			         }
 			        			         
  			        			    	 
  			        			     }else if(appUserList.size()<activityAmount){
  			        			    	     if(isSign){
  			        			    	    	    carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
		         			  			        	carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
		         			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
		         			  			            handleCarCommodities.add(carcommodity);
  			        			    	     }else{
			  			        			        carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
							  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
							  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
							  			        	handleCarCommodities.add(carcommodity);
  			        			    	    }
  			        			     } 
  			        		  }else{
  			        			   if(isSign){
  			        				   carcommodity.setUnitPrice(shopcommodity.getSpecial()*shopcommodity.getUnitPrice());//把购物车商品更新为折扣价位
   			  			        	   carcommodity.setPrice(shopcommodity.getSpecial()*carcommodity.getAmount()*shopcommodity.getUnitPrice());//重新计算购物车总价
   			  			        	   carcommoidtyService.update(carcommodity);//更新购物车商品信息
  				  			            shopcommodity.setIsAcitvity(false);
  				  			            if(shopcommodity.getUsers().size()>0){
  				  			            	 for (int j = 0; j < shopcommodity.getUsers().size(); j++) {
  				  			            		shopcommodity.getUsers().get(j).getActivityCommodities().remove(shopcommodity);
  												}
  				  			            	shopcommodity.getUsers().clear();//团购时间结束 当前商品的user集合清空
  				        			        shopcommodityService.update(shopcommodity);
  				  			            }
  				  			        	handleCarCommodities.add(carcommodity);
  	  			        			   
  			        			   }else{
  			        			    carcommodity.setUnitPrice(shopcommodity.getUnitPrice());//把购物车商品更新为现实价位
			  			        	carcommodity.setPrice(shopcommodity.getUnitPrice()*carcommodity.getAmount());//重新计算购物车总价
			  			        	carcommoidtyService.update(carcommodity);//更新购物车商品信息
			  			            shopcommodity.setIsAcitvity(false);
			  			            if(shopcommodity.getUsers().size()>0){
			  			            	 for (int j = 0; j < shopcommodity.getUsers().size(); j++) {
			  			            		shopcommodity.getUsers().get(j).getActivityCommodities().remove(shopcommodity);
											}
			  			            	shopcommodity.getUsers().clear();//团购时间结束 当前商品的user集合清空
			        			        shopcommodityService.update(shopcommodity);
			  			            }
			  			        	handleCarCommodities.add(carcommodity);
  			        			   }
			        		  }
  			        	}//团购
  			        }
  		}//外层循环
  		  return handleCarCommodities;
  	}  
  	
  	/**
	 * 货品加入购物车
	 * 
	 * @param carcommodities
	 * @return
	 * @throws ParseException
	 */
	public ModelMap addCarCommodity(Integer buyAmount, Integer shopCommId, ModelMap mode, AppUser user) {
		BuyCar buyCar = buyCarService.getBuyCarByUserName(user.getPhone());
		if(buyCar == null){
			buyCar = new BuyCar();
			buyCar.setAppUser(user);
			buyCar = buyCarService.save(buyCar);
		}
		ShopCommodity shopCommodity = shopcommodityService.findById(shopCommId);
		if(shopCommodity != null){
			CarCommodity carCommodity = carcommoidtyService.getCarCommodityByShopCommId(shopCommId, user.getPhone());
			if (carCommodity != null) {
				if (shopCommodity.getCommCode() == carCommodity.getShopCommodity().getCommCode()) {
					if (shopCommodity.getIsSpecial()) {
						carCommodity.setUnitPrice(shopCommodity.getUnitPrice() * shopCommodity.getSpecial());
					} else {
						carCommodity.setUnitPrice(shopCommodity.getUnitPrice());
					}
					carCommodity.setAmount(buyAmount + carCommodity.getAmount());
					carCommodity.setPrice(carCommodity.getUnitPrice() * carCommodity.getAmount());
					carCommodity.setBuyCar(buyCar);
					carCommodity = carcommoidtyService.update(carCommodity);
					List<CarCommodity> list = buyCar.getCarCommodities();
					list.add(carCommodity);
					buyCar.setCarCommodities(list);
					buyCarService.update(buyCar);
					mode.put("message", "existed");
				}
			} else {
				Shop shop = shopCommodity.getBelongTo();
				ShopCategory shopCate = shopCommodity.getShopCategory();

				carCommodity = new CarCommodity();
				carCommodity.setShopCommodity(shopCommodity);
				carCommodity.setShop(shop);
				carCommodity.setCarCategory(shopCate);
				carCommodity.setAmount(buyAmount);
				if (shopCommodity.getIsSpecial()) {
					carCommodity.setUnitPrice(shopCommodity.getUnitPrice() * shopCommodity.getSpecial());
				} else {
					carCommodity.setUnitPrice(shopCommodity.getUnitPrice());
				}
				carCommodity.setPrice(carCommodity.getUnitPrice() * carCommodity.getAmount());
				carCommodity.setBuyCar(buyCar);
				carCommodity = carcommoidtyService.save(carCommodity);
				List<CarCommodity> list = buyCar.getCarCommodities();
				if (list != null) {
					list.add(carCommodity);
					buyCar.setCarCommodities(list);
				} else {
					list = new ArrayList<CarCommodity>();
					list.add(carCommodity);
					buyCar.setCarCommodities(list);
				}
				buyCarService.update(buyCar);
				mode.put("message", "success");
			}
		}
		return mode;
	}
}
