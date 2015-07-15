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
import com.yc.entity.ShopCommodity;
import com.yc.entity.ShopReviews;
import com.yc.entity.user.AppUser;
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
		ShopReviews reviews = shopReviewsService.getAllByOrderAndComm(orderFormId,commCode);
		OrderForm orderform = orderFormService.findById(orderFormId);
		AppUser user = orderform.getOrderUser();
		ShopCommodity commodity = shopCommodityService.findById(commCode);
		if(reviews == null){
			reviews = new ShopReviews();
			reviews.setReviews(content);
			reviews.setReviewsRank(ReviewsRank.valueOf(reviewsRank));
			reviews.setReviewsdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			reviews.setReviewTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			reviews.setOrderForm(orderform);
			reviews.setShopscommodity(commodity);
			reviews.setUser(user);
			shopReviewsService.save(reviews);
		}else{
			if ( "user".equals(page) ) {
				reviews.setAdditionalReviews(content);
				shopReviewsService.update(reviews);
			} else if ( "shop".equals(page) ) {
				if ( reviews.getBusinessreply() != null ) {
					reviews.setBusinessreply(content);
				} else {
					reviews.setAdditionalBusinessreply(content);
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
			 shopReviewsModel.setReviewsRan(shopReviewsList.get(i).getReviewsRank().toString());
			 shopReviewsModel.setDate(shopReviewsList.get(i).getReviewsdate()+shopReviewsList.get(i).getReviewTime());
			 shopReviewsModel.setReviews(shopReviewsList.get(i).getReviews());
			 shopReviewsModel.setBusinessreply(shopReviewsList.get(i).getBusinessreply());
			 shopReviewsModel.setAdditionalReviews(shopReviewsList.get(i).getAdditionalReviews());
			 shopReviewsModel.setAdditionalBusinessreply(shopReviewsList.get(i).getAdditionalBusinessreply());
			 shopReviewsModelList.add(shopReviewsModel);
		}
		mode.put("shopReviewsModelList", shopReviewsModelList);
		return mode;
	}
}
