package com.yc.controller.response;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.entity.BuyCar;
import com.yc.entity.CarCommodity;
import com.yc.entity.Commodity;
import com.yc.entity.Delivery;
import com.yc.entity.DeliveryAddress;
import com.yc.entity.OrderForm;
import com.yc.entity.OrderStatus;
import com.yc.entity.ShopCategory;
import com.yc.entity.ShopCommImage;
import com.yc.entity.ShopCommodity;
import com.yc.entity.user.AppUser;
import com.yc.model.AdvertisementManager;
import com.yc.model.CarCommodityModel;
import com.yc.model.CommodityModel;
import com.yc.model.BrandModel;
import com.yc.model.ShopCommodityModel;
import com.yc.service.IAdvertisementDistributionService;
import com.yc.service.IAdvertisementService;
import com.yc.service.IAppUserService;
import com.yc.service.IBrandService;
import com.yc.service.IBuyCarService;
import com.yc.service.ICarCommodityService;
import com.yc.service.ICommodityService;
import com.yc.service.IDeliveryAddressService;
import com.yc.service.IDeliveryService;
import com.yc.service.IOrderFormService;
import com.yc.service.IShopCategoryService;
import com.yc.service.IShopCommodityService;
import com.yc.util.ServiceTools;
import com.yc.util.Tools;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/request")
public class ReponseHomeController {
	
	@Resource
	ServiceTools serviceTools;
	
	@Autowired
	IAdvertisementService advertisementService;
	
	@Autowired
	IAdvertisementDistributionService adverDistributionService;
	
	@Autowired
	ICommodityService commodityService;
	
	@Autowired
	IShopCategoryService shopcategoryService;
	
	@Autowired
	IShopCommodityService shopCommoidtyService;
	
	@Autowired
	IBrandService brandService;
	
	@Autowired
	IBuyCarService buyCarService;
	
	@Autowired
	ICarCommodityService carCommodityService;
	
	@Autowired
	IAppUserService appUserService;
	
	@Autowired
	IOrderFormService orderFormService;
	
	@Autowired
	IDeliveryAddressService deliveryAddressService;
	
	@Autowired
	IDeliveryService deliveryService;

	@RequestMapping(value = "category/getShopCommByCate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getHome(@RequestParam(value="cateId")int cateId) throws ServletException, IOException{
 		ModelMap mode = new ModelMap();
 		
 		List<CommodityModel> commodities = new ArrayList<CommodityModel>();
 		List<ShopCommodityModel> specCommoidty = new ArrayList<ShopCommodityModel>();
 		List<ShopCommodityModel> goodCommoidty = new ArrayList<ShopCommodityModel>();
 		
 		if ( cateId == 0 ) {
 			commodities = commodityService.getHotCommodities(6);
 	 		specCommoidty = shopCommoidtyService.getSpecialShopComm(6);
 	 		goodCommoidty = shopCommoidtyService.getMyShopComm(6);
 	 		
 	 		AdvertisementManager manager = new AdvertisementManager();
 	 		mode.putAll(manager.getHomePageAdvertisements(adverDistributionService, advertisementService));
 		} else {
 			commodities = getHotsell(cateId,6);
 			specCommoidty = getSpecial(cateId,6);
 			goodCommoidty = getGood(cateId,6);
 		}
 		
 		mode.put("hotCommodities", commodities);
 		mode.put("specCommoidty", specCommoidty);
 		mode.put("goodCommoidty", goodCommoidty);
 		
		return mode;
	}
	
	//首页折扣产品
	private List<ShopCommodityModel> getSpecial(int cateId,int num) {
		ShopCategory shopcategory=shopcategoryService.findById(cateId);
	    Tools.lists.clear();
	    List<ShopCategory> cateList =Tools.getNodeForShopCategory(shopcategory);
		List<ShopCommodityModel> allShopCommodity = new ArrayList<ShopCommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<ShopCommodityModel> comms = shopCommoidtyService.getShopCommByCateAndSpecial(cateList.get(i).getCategoryID().toString(),num);
			if (comms != null) {
				allShopCommodity.addAll(comms);
			}
		}
		//对打折商品集合进行排序并且取前num
 		List<ShopCommodityModel> topShopCommodity = new ArrayList<ShopCommodityModel>();
 		allShopCommodity.sort(new Comparator<ShopCommodityModel>(){   
            public int compare(ShopCommodityModel object1, ShopCommodityModel object2) { 
                return object1.getSpecial().compareTo(object2.getSpecial());           
                }   
         }); 
 		
 		for ( int i = 0;i < allShopCommodity.size(); i++ ) {
 			if ( i < 7 ) {
 				topShopCommodity.add(allShopCommodity.get(i));
 			} else {
 				break;
 			}				
 		}
 		return topShopCommodity;
	}
	
	//首页精品产品
	private List<ShopCommodityModel> getGood(int cateId,int num) {
		ShopCategory shopcategory=shopcategoryService.findById(cateId);
	    Tools.lists.clear();
	    List<ShopCategory> cateList =Tools.getNodeForShopCategory(shopcategory);
		List<ShopCommodityModel> allShopCommodity = new ArrayList<ShopCommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<ShopCommodityModel> comms = shopCommoidtyService.getShopCommByCateAndBoutique(cateList.get(i).getCategoryID().toString(),num);
			if (comms != null) {
				allShopCommodity.addAll(comms);
			}
		}
		//对精品商品集合进行排序并且取前num
 		List<ShopCommodityModel> topShopCommodity = new ArrayList<ShopCommodityModel>();
 		allShopCommodity.sort(new Comparator<ShopCommodityModel>(){   
            public int compare(ShopCommodityModel object1, ShopCommodityModel object2) { 
                return object1.getUnitPrice().compareTo(object2.getUnitPrice());           
                }   
         }); 
 		
 		for ( int i = 0;i < allShopCommodity.size(); i++ ) {
 			if ( i < 7 ) {
 				topShopCommodity.add(allShopCommodity.get(i));
 			} else {
 				break;
 			}				
 		}
 		return topShopCommodity;
	}
	
	//首页热销产品
	private List<CommodityModel> getHotsell(int cateId,int num) {
		ShopCategory shopcategory=shopcategoryService.findById(cateId);
	    Tools.lists.clear();
	    List<ShopCategory> cateList =Tools.getNodeForShopCategory(shopcategory);
		List<CommodityModel> allCommodity = new ArrayList<CommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<CommodityModel> comms = commodityService.getShopCommByCateAndHot(cateList.get(i).getCategoryID().toString(),num);
			if (comms != null) {
				allCommodity.addAll(comms);
			}
		}
		//对热销商品集合进行排序并且取前num
 		List<CommodityModel> topCommodity = new ArrayList<CommodityModel>();
 		allCommodity.sort(new Comparator<CommodityModel>(){   
            public int compare(CommodityModel object1, CommodityModel object2) { 
                return object1.getSums()-object2.getSums();           
                }   
         }); 
 		
 		for ( int i = 0;i < allCommodity.size(); i++ ) {
 			if ( i < 7 ) {
 				topCommodity.add(allCommodity.get(i));
 			} else {
 				break;
 			}				
 		}
		return topCommodity;
	}
	
	//点击首页的更多
	@RequestMapping(value = "category/getShopCommByCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCarCommodity(@RequestParam(value="cateId")int cateId,@RequestParam(value="title")String title) throws ServletException, IOException{
		ModelMap mode = new ModelMap();
		List<CommodityModel> commodities = new ArrayList<CommodityModel>();
 		List<ShopCommodityModel> shopCommoidties = new ArrayList<ShopCommodityModel>();
 		
 		if ( title.equals("hotsell") ) {
 			if ( cateId == 0 ) {
 				commodities = commodityService.getHotCommodities(-1);
 			} else {
 				commodities = getHotsell(cateId,-1);
 			}
 			mode.put("commodities", commodities);
 		} else if ( title.equals("special") ) {
 			if ( cateId == 0 ) {
 				shopCommoidties = shopCommoidtyService.getSpecialShopComm(-1);
 			} else {
 				shopCommoidties = getSpecial(cateId,-1);
 			}
 			mode.put("commodities", shopCommoidties);
 		} else if ( title.equals("good") ) {
 			if ( cateId == 0 ) {
 				shopCommoidties = shopCommoidtyService.getMyShopComm(-1);
 			} else {
 				shopCommoidties = getGood(cateId,-1);
 			}
 			mode.put("commodities", shopCommoidties);
 		}
		return mode;
	}
	
	//搜索产品
	@RequestMapping(value = "home/searchCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCommodity(@RequestParam(value="content")String content) throws ServletException, IOException{
 		ModelMap mode = new ModelMap();
 		List<ShopCommodity> searchResultList = shopCommoidtyService.searchShopComm(content);
 		List<ShopCommodityModel> list = new ArrayList<ShopCommodityModel>();
 		ShopCommodityModel item = null;
 		for ( int i = 0; i < searchResultList.size(); i++ ) {
 			item = new ShopCommodityModel();
 			item.setCommCode(searchResultList.get(i).getCommCode());
 			item.setDescribes(searchResultList.get(i).getDescribes());
 			item.setUnitPrice(searchResultList.get(i).getUnitPrice());
 			String path = searchResultList.get(i).getShopCommImages().get(0).getImagePath();
 			item.setShopCommImage(path);
 			list.add(item);
 		}
 		mode.put("shopcommodities", list);
		return mode;
	}
	
	//详情页
	@RequestMapping(value = "detail/detailCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailCommodity(@RequestParam(value="id")String id) throws ServletException, IOException{
 		ModelMap mode = new ModelMap();
 		int commoCode = Integer.parseInt(id);
 		ShopCommodity commodity = shopCommoidtyService.findById(commoCode);
 		ShopCommodityModel shopcommodity = null;
 		shopcommodity = new ShopCommodityModel();
 		if ( shopcommodity != null) {
 			shopcommodity.setCommCode(commodity.getCommCode());
 			shopcommodity.setCommoidtyName(commodity.getCommoidtyName());
 			shopcommodity.setCommItem(commodity.getCommItem());
 			shopcommodity.setUnitPrice(commodity.getUnitPrice());
 			shopcommodity.setSpecialPrice(commodity.getSpecialPrice());
 			shopcommodity.setStock(commodity.getStock());
 			shopcommodity.setCategoryName(commodity.getShopCategory().getCategory());
 			shopcommodity.setBelongTo(commodity.getBelongTo().getId());
 			shopcommodity.setDescribes(commodity.getDescribes());
 			shopcommodity.setCommSpec(commodity.getCommsPecs().getCommSpec());
 			shopcommodity.setIsActivity(commodity.getIsAcitvity());
 			shopcommodity.setSpecial(commodity.getSpecial());
 			
 			//品牌
 			if ( commodity.getBrand() != null ) {
 				shopcommodity.setBrandName(commodity.getBrand().getBrandName());
 				mode.put("brandnull", false);
 			} else {
 				mode.put("brandnull", true);
 			}
 			
 			//是否参加活动
 			if ( commodity.getIsAcitvity() ) {
 				mode.put("isActivity", true);
 				shopcommodity.setActivityAmount(commodity.getActivityAmount());
 				shopcommodity.setActivityName(commodity.getActivity().getActivityName());
 			} else {
 				mode.put("isActivity", false);
 			}
 			
 			//描述图片
 			List<String> paths = new ArrayList<String>();
 			List<ShopCommImage> images = commodity.getShopCommImages();
 			for ( int j = 0; j < images.size(); j++ ) {
 				paths.add(images.get(j).getImagePath());
 			}
 			shopcommodity.setImagePaths(paths);
 		}
 		mode.put("shopcommodity", shopcommodity);
 		
		return mode;
	}
	
  
	//分类页面
	@RequestMapping(value = "category/getSecondCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSecondCategory() throws ServletException, IOException{
 		ModelMap mode = new ModelMap();
 		
 		List<BrandModel> winRedChildren = brandService.getBrands("红酒");
 		List<BrandModel> winForeignChildren = brandService.getBrands("洋酒");
 		List<BrandModel> winWhiteChildren = brandService.getBrands("白酒");
 		List<BrandModel> beerChildren = brandService.getBrands("啤酒");
 		List<BrandModel> snacksChildren = brandService.getBrands("小食品");
 		
 		mode.put("winRedChildren", winRedChildren);
 		mode.put("winForeignChildren", winForeignChildren);
 		mode.put("winWhiteChildren", winWhiteChildren);
 		mode.put("beerChildren", beerChildren);
 		mode.put("snacksChildren", snacksChildren);
 		
		return mode;
	}
	
	/**
	 * 根据类型Id查找对应商品集合
	 * @param cateId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "category/getShopCommByBrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShopCommByBrand(@RequestParam(value="brandId")String brandId) throws ServletException, IOException{
	    ModelMap mode = new ModelMap();
	    List<ShopCommodityModel> shopcommList=shopCommoidtyService.getShopCommByBrand(brandId);
	    mode.put("shopcommList", shopcommList);
		return mode; 
	}
	
	/**
	 * 加入购物车
	 * @param shopCommId 商品id
	 * @param userName 用户名
	 * @param num 购买数量
	 * @return 是否加入成功
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "shopcar/addBuyCar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBuyCar(String shopCommId,String userName,String num) throws ServletException, IOException{
		ModelMap mode = new ModelMap();
		int id = Integer.parseInt(shopCommId);
		int buyAmount = Integer.parseInt(num);
		AppUser user = appUserService.getUser(userName);
		mode = serviceTools.addCarCommodity(buyAmount, id, mode, user);
		return mode;
	}
	
	/**
	 * 刷新购物车方法
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "shopcar/reCarCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCarCommodity(String userName) throws ServletException, IOException, ParseException{
		ModelMap mode = new ModelMap();
		List<CarCommodity> carCommodityList=carCommodityService.getCarCommodityByUserName(userName);
		List<CarCommodity> handleCarCommodities=serviceTools.handleCarCommodity(carCommodityList, userName);
		//返回CarCommodityModel类型集合
		List<CarCommodityModel> carCommodityModelList=new ArrayList<CarCommodityModel>();
		for (int i = 0; i < handleCarCommodities.size(); i++) {
			CarCommodityModel carCommodityMode=new CarCommodityModel();
		    carCommodityMode.setImagePath(handleCarCommodities.get(i).getShopCommodity().getShopCommImages().get(0).getImagePath());
		    carCommodityMode.setPrice(handleCarCommodities.get(i).getPrice());
		    carCommodityMode.setQuantity(handleCarCommodities.get(i).getAmount());
		    carCommodityMode.setShopComName(handleCarCommodities.get(i).getShopCommodity().getCommoidtyName());
		    carCommodityMode.setShopCommCode(handleCarCommodities.get(i).getShopCommodity().getCommCode());
		    carCommodityMode.setUnitPrice(handleCarCommodities.get(i).getUnitPrice());
		    carCommodityModelList.add(carCommodityMode);
		}		
		mode.put("carCommodityModelList",carCommodityModelList);
		return mode;
	}
	
	/**
	 * 立即下单操作
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "shopcar/ImmediatelyOrder", method = RequestMethod.GET)
	@ResponseBody
	//Integer shopCommCode,AppUser appuser,DeliveryAddress address,Delivery delivery,Integer Amount
	public Map<String, Object> ImmediatelyOrder(String shopCommCode,String userName,String addressId,String num,String deliveryName,String endorse) throws ServletException, IOException{
		ModelMap mode = new ModelMap();
		Integer commID = Integer.parseInt(shopCommCode);
		Integer addressID = Integer.parseInt(addressId);
		Integer amount = Integer.parseInt(num);
		
		AppUser appuser=appUserService.getUser(userName);
		DeliveryAddress address=deliveryAddressService.findById(addressID);
		ShopCommodity shopcommodity=shopCommoidtyService.findById(commID);
		
		List<Commodity> commodities = new ArrayList<Commodity>();
		Commodity commodity=new Commodity();
		commodity.setShopCommodity(shopcommodity);
		commodity.setShopcategory(shopcommodity.getShopCategory());
		commodity.setSeller(shopcommodity.getBelongTo());
		commodity.setWeight(shopcommodity.getProbablyWeight()*amount);
     	commodity.setCommSpec(shopcommodity.getCommsPecs().getCommSpec());//添加规格信息 ????
		commodity.setQuantity(amount);
		commodity.setPrice(shopcommodity.getUnitPrice());
		commodity.setMoney(amount*shopcommodity.getUnitPrice());
		commodities.add(commodity);
		
		//运输操作 级联操作
	    Delivery delivery=new Delivery();
	    delivery.setAddress(address);
	    delivery.setDeliveryMoney(250f);
	    delivery.setDeliveryName(deliveryName);
	    delivery.setEndorse(endorse);
		//订单存储操作
		OrderForm orderform=new OrderForm();
		orderform.setCommodities(commodities);
		orderform.setOrderstatus(OrderStatus.valueOf("waitPayment"));
		orderform.setDelivery(delivery);
		//积分
		orderform.setOrderUser(appuser);
	    orderFormService.save(orderform);
	    
	    commodity.setOrderNumber(orderform);
	    commodityService.save(commodity);
	    mode.put("orderform", orderform);
		return  null;
	}

	
	/**
	 *购物车生成订单
	 * @param CarIds
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "shopcar/SaveOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SaveOrder(String userName,String addressId,String deliveryName,String endorse) throws ServletException, IOException{
		ModelMap mode = new ModelMap();
		String[] carIds=new String[]{"12","13","14"};
		Integer addressID = Integer.parseInt(addressId);
		AppUser appuser=appUserService.getUser(userName);
		DeliveryAddress address=deliveryAddressService.findById(addressID);
		List<Commodity> commodities = new ArrayList<Commodity>();//存放所有的commodity集合
		List<OrderForm> orderformList=new ArrayList<OrderForm>();//存放生成的orderform集合
 		for (int i = 0; i < carIds.length; i++) {
			Commodity commodity=new Commodity();
			int carId = Integer.parseInt(carIds[i]);
			CarCommodity carCommodity=carCommodityService.findById(carId);
			commodity.setShopCommodity(carCommodity.getShopCommodity());
			commodity.setShopcategory(carCommodity.getShopCommodity().getShopCategory());
			commodity.setSeller(carCommodity.getShop());
			commodity.setWeight(carCommodity.getShopCommodity().getProbablyWeight()*carCommodity.getAmount());
			commodity.setCommSpec(carCommodity.getShopCommodity().getCommsPecs().getCommSpec());//添加规格信息 
			commodity.setQuantity(carCommodity.getAmount());
			commodity.setPrice(carCommodity.getUnitPrice());
			commodity.setMoney(carCommodity.getPrice());
			commodities.add(commodity);
		}
		while( commodities.size() > 0 ) {
			List<Commodity> list = new ArrayList<Commodity>();//存放分组后的commodity集合
			list.add(commodities.get(0));
			int shopId = commodities.get(0).getSeller().getId();
			commodities.remove(0);
			for ( int i = 0; i < commodities.size(); i++ ) {
				int id = commodities.get(i).getSeller().getId();
				if ( shopId == id ) {
					list.add(commodities.get(i));
					commodities.remove(i);
					i--;
				}
			}
			//运输操作 级联操作
		    Delivery delivery=new Delivery();
		    delivery.setAddress(address);
		    delivery.setDeliveryMoney(250f);
		    delivery.setDeliveryName(deliveryName);
		    delivery.setEndorse(endorse);
			//订单存储操作
			OrderForm orderform=new OrderForm();
			orderform.setCommodities(list);
			orderform.setOrderstatus(OrderStatus.valueOf("waitPayment"));
			orderform.setDelivery(delivery);
			//积分
			orderform.setOrderUser(appuser);
 			orderFormService.save(orderform);
 			orderformList.add(orderform);
 			for (int i = 0; i < list.size(); i++) {
 				list.get(i).setOrderNumber(orderform);
 				commodityService.save(list.get(i));
			}
 			//删除下单商品
		}//while
		    for (int i = 0; i < carIds.length; i++) {
				String carId = carIds[i];
				carCommodityService.delete(Integer.parseInt(carId));
			}
		    //用户是否有购物车
		    BuyCar buycar=buyCarService.getBuyCarByUserName(appuser.getPhone().toString());
		    if(buycar!=null){
		    	//如果购物车为空 删除购物车
				List<CarCommodity> carcomms=buycar.getCarCommodities();
				if(carcomms.size()==0||carcomms==null){
					buyCarService.delete(buyCarService.getBuyCarByUserName(appuser.getPhone().toString()).getCatID());
				}
		    }
		mode.put("orderformList",orderformList);
		return null;
	}
}
