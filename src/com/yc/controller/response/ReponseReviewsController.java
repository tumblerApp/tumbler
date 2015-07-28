package com.yc.controller.response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.entity.Commodity;
import com.yc.entity.OrderForm;
import com.yc.entity.ReviewsRank;
import com.yc.entity.Shop;
import com.yc.entity.ShopCommodity;
import com.yc.entity.ShopReviews;
import com.yc.entity.user.AppUser;
import com.yc.model.ShopCommodityModel;
import com.yc.model.ShopReviewsModel;
import com.yc.service.IAppUserService;
import com.yc.service.IOrderFormService;
import com.yc.service.IShopCommodityService;
import com.yc.service.IShopReviewsService;

@Controller
@RequestMapping("/request/reviews")
public class ReponseReviewsController {
	
	@Autowired
	IShopCommodityService shopCommodityService;
	
	@Autowired
	IAppUserService appUserService;
	
	@Autowired
	IOrderFormService orderFormService;
	
	@Autowired
	IShopReviewsService shopReviewsService;
	
	/** 
	 * 通过订单id，获取订单下商品的评价状态
	 * @param orderFormID 订单id
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "isReviewed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isReviewed(Integer orderFormId) throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		OrderForm orderForm = orderFormService.findById(orderFormId);
		List<Commodity> commodities = orderForm.getCommodities();
		List<String> isReviewedList = new ArrayList<String>();
		for ( int i = 0; i < commodities.size(); i++ ) {
			int commCode = commodities.get(i).getShopCommodity().getCommCode();
			ShopReviews reviews = shopReviewsService.getAllByOrderAndComm(orderFormId, commCode);
			if ( reviews != null ) {
				if ( reviews.getAdditionalReviews() != null ) {
					isReviewedList.add("end");
				} else {
					isReviewedList.add("add");
				}
			} else {
				isReviewedList.add("first");
			}
		}
		mode.put("message", isReviewedList);
		return mode;
	}
	
	/**
	 * 商品评论
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "saveReviews", method = RequestMethod.POST)
	@ResponseBody
	//订单id 商品的 id   评论等级标示 评论内容 用户id
	public Map<String, Object> saveReviews(Integer orderFormId,Integer commCode, String reviewsRank,
			String content, String page) throws ServletException, IOException {
		if ( "user".equals(page) ) {
			ShopReviews reviews = shopReviewsService.getAllByOrderAndComm(orderFormId,commCode);
			OrderForm orderform = orderFormService.findById(orderFormId);
			AppUser user = orderform.getOrderUser();
			ShopCommodity commodity = shopCommodityService.findById(commCode);
			if(reviews == null){
				reviews = new ShopReviews();
				reviews.setId(null);
				reviews.setReviews(content);
				reviews.setReviewsRank(ReviewsRank.valueOf(reviewsRank));
				reviews.setReviewsdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				reviews.setReviewTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				reviews.setOrderForm(orderform);
				reviews.setShopscommodity(commodity);
				reviews.setUser(user);
				shopReviewsService.save(reviews);
			}else{
				reviews.setAdditionalReviews(content);
				shopReviewsService.update(reviews);
			}
		} else if ( "shop".equals(page) ) {
			ShopReviews reviews = shopReviewsService.findById(commCode);//这里的commCode实际是评论的id
			if ( reviews != null ) {
				if ( reviews.getBusinessreply() != null ) {
					reviews.setAdditionalBusinessreply(content);
				} else {
					reviews.setBusinessreply(content);
				}
				shopReviewsService.update(reviews);
			}
		}
		return null;
	}

	/**
	 * 根据商品id 查询对应的评价信息
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "getShopCommReviews", method = RequestMethod.POST)
	@ResponseBody
	//商品的 id 
	public Map<String, Object> getShopCommReviews(int shopCommId) throws ServletException, IOException {
		 ModelMap mode=new ModelMap();
		 List<ShopReviews> shopReviewsList=shopReviewsService.findByShopCommId(shopCommId);
		 List<ShopReviewsModel> shopReviewsModelList=new ArrayList<ShopReviewsModel>();
		 for (int i = 0; i < shopReviewsList.size(); i++) {
			 ShopReviewsModel shopReviewsModel=new ShopReviewsModel();
			 if(shopReviewsList.get(i).getUser().getUserName()!=null){
				 shopReviewsModel.setUserName(shopReviewsList.get(i).getUser().getUserName());
			 }else{
				 String str=shopReviewsList.get(i).getUser().getPhone().substring(0, 4);
				 StringBuffer strbuffer=new StringBuffer(str);
				 strbuffer.append("......");
				 shopReviewsModel.setUserName(strbuffer.toString());
			 }
			 shopReviewsModel.setReviewId(shopReviewsList.get(i).getId());
			 shopReviewsModel.setReviewsRan(shopReviewsList.get(i).getReviewsRank().toString());
			 shopReviewsModel.setDate(shopReviewsList.get(i).getReviewsdate()+"  "+
					 shopReviewsList.get(i).getReviewTime());
			 shopReviewsModel.setReviews(shopReviewsList.get(i).getReviews());
			 shopReviewsModel.setBusinessreply(shopReviewsList.get(i).getBusinessreply());
			 shopReviewsModel.setAdditionalReviews(shopReviewsList.get(i).getAdditionalReviews());
			 shopReviewsModel.setAdditionalBusinessreply(shopReviewsList.get(i).getAdditionalBusinessreply());
			 shopReviewsModelList.add(shopReviewsModel);
		}
		mode.put("shopReviewsModelList", shopReviewsModelList);
		return mode;
	}
	
	/**
	 * 获得店铺下的所有商品的评论
	 * @param phone 手机号
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "getShopReviews", method = RequestMethod.POST)
	@ResponseBody
	//商品的 id 
	public Map<String, Object> getAllShopReviews(String phone, int currentPos) throws ServletException, IOException {
		 ModelMap mode=new ModelMap();
		 AppUser user = appUserService.getUser(phone);
		 Shop shop = user.getShop();
		 List<ShopCommodity> commodities = shopCommodityService.getCommByShopId(shop.getId());
		 ArrayList<ArrayList<ShopReviewsModel>> reviewList = new ArrayList<ArrayList<ShopReviewsModel>>();
		 for (int i = 0; i < commodities.size(); i++) {
			 int shopCommId = commodities.get(i).getCommCode();
			 List<ShopReviews> shopReviewsList =  shopReviewsService.findByShopCommId(shopCommId);
			 ArrayList<ShopReviewsModel> shopReviewsModelList = new ArrayList<ShopReviewsModel>();
			 for (int j = 0; j < shopReviewsList.size(); j++) {
				 boolean flag = true;
				 if ( currentPos == 2 ) {
					 if ( shopReviewsList.get(j).getAdditionalBusinessreply() != null ) {
						 flag = false;
					 }
				 } else if ( currentPos == 3 ) {
					 if ( shopReviewsList.get(j).getAdditionalBusinessreply() == null ) {
						 flag = false;
					 }
				 }
				 
				 if ( flag ) {
					 ShopReviewsModel shopReviewsModel = new ShopReviewsModel();
					 if(shopReviewsList.get(j).getUser().getUserName()!=null){
						 shopReviewsModel.setUserName(shopReviewsList.get(j).getUser().getUserName());
					 }else{
						 String str=shopReviewsList.get(j).getUser().getPhone().substring(0, 4);
						 StringBuffer strbuffer=new StringBuffer(str);
						 strbuffer.append("......");
						 shopReviewsModel.setUserName(strbuffer.toString());
					 }
					 shopReviewsModel.setReviewId(shopReviewsList.get(i).getId());
					 shopReviewsModel.setReviewsRan(shopReviewsList.get(j).getReviewsRank().toString());
					 shopReviewsModel.setDate(shopReviewsList.get(j).getReviewsdate()+"  "+
							 shopReviewsList.get(i).getReviewTime());
					 shopReviewsModel.setReviews(shopReviewsList.get(j).getReviews());
					 shopReviewsModel.setBusinessreply(shopReviewsList.get(j).getBusinessreply());
					 shopReviewsModel.setAdditionalReviews(shopReviewsList.get(j).getAdditionalReviews());
					 shopReviewsModel.setAdditionalBusinessreply(shopReviewsList.get(j).getAdditionalBusinessreply());
					 shopReviewsModelList.add(shopReviewsModel);
				 }
			}
			reviewList.add((ArrayList<ShopReviewsModel>)shopReviewsModelList);
		}
		 
		mode.put("reviewList", reviewList);
		return mode;
	}
	
	/**
	 * 通过手机号获取其店铺的商品
	 * @param phone
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "getShopCommByShop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShopCommByShop(String phone, int currentPos) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
		AppUser user = appUserService.getUser(phone);
		Shop shop = user.getShop();
	    List<ShopCommodity> shopCommodityList = shopCommodityService.getCommByShopId(shop.getId());
        List<ShopCommodityModel> commodity = new ArrayList<ShopCommodityModel>();
        for (int i = 0; i < shopCommodityList.size(); i++) {
        	boolean flag = true;
        	if ( currentPos == 2 ) {
        		if ( !shopCommodityList.get(i).getShelves() ) {
        			flag = false;
        		}
        	} else if ( currentPos == 3 ) {
        		if ( shopCommodityList.get(i).getShelves() ) {
        			flag = false;
        		}
        	}
        	if ( flag ) {
        		ShopCommodityModel shopCommMode=new ShopCommodityModel();
 			    shopCommMode.setCommCode(shopCommodityList.get(i).getCommCode());
 			    shopCommMode.setCommItem(shopCommodityList.get(i).getCommItem());
 			    shopCommMode.setCommoidtyName(shopCommodityList.get(i).getCommoidtyName());
 			    shopCommMode.setShopCommImage(shopCommodityList.get(i).getShopCommImages().get(0).getImagePath());
 			    shopCommMode.setDescribes(shopCommodityList.get(i).getDescribes());
 			    shopCommMode.setUnitPrice(shopCommodityList.get(i).getUnitPrice());
 			    shopCommMode.setSpecial(shopCommodityList.get(i).getSpecial());
 			    shopCommMode.setShelves(shopCommodityList.get(i).getShelves());
 			    commodity.add(shopCommMode);
        	}
		}
        mode.put("commodityList", commodity);
	    return mode;
	}
}
