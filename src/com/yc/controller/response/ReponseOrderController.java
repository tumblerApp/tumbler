package com.yc.controller.response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yc.entity.Address;
import com.yc.entity.BuyCar;
import com.yc.entity.CarCommodity;
import com.yc.entity.Collection;
import com.yc.entity.CollectionType;
import com.yc.entity.Commodity;
import com.yc.entity.Delivery;
import com.yc.entity.DeliveryAddress;
import com.yc.entity.OrderForm;
import com.yc.entity.OrderStatus;
import com.yc.entity.Shop;
import com.yc.entity.ShopCommodity;
import com.yc.entity.user.AppUser;
import com.yc.model.AddressModel;
import com.yc.model.CommodityModel;
import com.yc.model.OrderFormModel;
import com.yc.model.ShopCommodityModel;
import com.yc.model.ShopModel;
import com.yc.service.IAddressService;
import com.yc.service.IAppUserService;
import com.yc.service.IBuyCarService;
import com.yc.service.ICarCommodityService;
import com.yc.service.ICollectionService;
import com.yc.service.ICommodityService;
import com.yc.service.IDeliveryAddressService;
import com.yc.service.IDeliveryService;
import com.yc.service.IOrderFormService;
import com.yc.service.IShopCommodityService;
import com.yc.service.IShopService;

@Controller
@RequestMapping("/request")
public class ReponseOrderController {

	@Autowired
	IShopCommodityService shopCommoidtyService;
	
	@Autowired
	ICollectionService collectionService;
	
	@Autowired
	IShopService shopService;
	
	@Autowired
	IAppUserService appUserService;
	
	@Autowired
	ICommodityService commodityService;

	@Autowired
	IBuyCarService buyCarService;

	@Autowired
	ICarCommodityService carCommodityService;
	
	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IDeliveryAddressService deliveryAddressService;

	@Autowired
	IDeliveryService deliveryService;
	
	@Autowired
	IAddressService addressService;
	
	/**
	 * 添加收藏
	 * @param commID
	 * @param shopID
	 * @param userName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "collection/addCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCollection(Integer commID, Integer shopID, String userName) throws ServletException, IOException {
		AppUser user = appUserService.getUser(userName);
		ShopCommodity shopComm = null;
		if (commID != null && commID != 0) {
			Collection collection = collectionService.getByUserNameAndCommCode(userName, commID);
			if ( collection == null ) {
				shopComm = shopCommoidtyService.findById(commID);
				collection = new Collection();
				collection.setShopCommodity(shopComm);
				collection.setUser(user);
				collection.setCollectionType(CollectionType.commodity);
				collection = collectionService.save(collection);
			}
		}
			
		Shop shop = null;
		if (shopID != null && shopID != 0) {
			Collection collection = collectionService.getByUserNameAndShopID(userName, shopID);
			if ( collection == null ) {
				shop = shopService.findById(shopID);
				collection = new Collection();
				collection.setShop(shop);
				collection.setUser(user);
				collection.setCollectionType(CollectionType.shop);
				collection = collectionService.save(collection);
			}
		}
		return null;
	}
	
	/**
	 * 删除收藏
	 * @param commID
	 * @param shopID
	 * @param userName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "collection/deleteCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCollection(Integer commID, Integer shopID, String userName) throws ServletException, IOException {
		if (commID != null && commID != 0) {
			Collection collection = collectionService.getByUserNameAndCommCode(userName, commID);
			if ( collection != null ) {
				collectionService.delete(collection.getId());
			}
		}
			
		if (shopID != null && shopID != 0) {
			Collection collection = collectionService.getByUserNameAndShopID(userName, shopID);
			if ( collection != null ) {
				collectionService.delete(collection.getId());
			}
		}
		return null;
	}
	
	/**
	 * 查询收藏的商品
	 * @param userName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/searchCollectionByComm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCollectionByComm(String userName) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
         List<Collection> collections=collectionService.getByTypeAndUerName(userName,"commodity");
         List<ShopCommodityModel> commodity=new ArrayList<ShopCommodityModel>();
         for (int i = 0; i < collections.size(); i++) {
			   ShopCommodityModel shopCommMode=new ShopCommodityModel();
			   //价格   路径 名称  id  描述
			   shopCommMode.setCommCode(collections.get(i).getShopCommodity().getCommCode());
			   shopCommMode.setCommoidtyName(collections.get(i).getShopCommodity().getCommoidtyName());
			   shopCommMode.setShopCommImage(collections.get(i).getShopCommodity().getShopCommImages().get(0).getImagePath());
			   shopCommMode.setUnitPrice(collections.get(i).getShopCommodity().getUnitPrice());
			   shopCommMode.setDescribes(collections.get(i).getShopCommodity().getDescribes());
			   commodity.add(shopCommMode);
		}
        mode.put("commodity", commodity);
		return mode;
	}
	
	/**
	 * 查询收藏的店铺
	 * @param userName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/searchCollectionByShop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCollectionByShop(String userName) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
         List<Collection> collections=collectionService.getByTypeAndUerName(userName,"shop");
         List<ShopModel> shopList=new ArrayList<ShopModel>();
         //商店名称 商店logo  id  地址
         for (int i = 0; i < collections.size(); i++) {
			    ShopModel shopModel=new ShopModel();
			    shopModel.setShopId(collections.get(i).getShop().getId());
			    shopModel.setAddress(collections.get(i).getShop().getAddress());
			    shopModel.setLogoPath(collections.get(i).getShop().getShopLogo());
			    shopModel.setShopName(collections.get(i).getShop().getShopName());
			    shopList.add(shopModel);
		}
         mode.put("shopList", shopList);
		 return mode;
	}
	
	/**
	 * 根据商店的id展示商店下的所有商品
	 * @param shopId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "shop/getCommodityByShopId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCommodityByShopId(Integer shopId) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
	    List<ShopCommodity> shopcommodityList=shopCommoidtyService.getCommByShopId(shopId);
        List<ShopCommodityModel> commodity=new ArrayList<ShopCommodityModel>();
        for (int i = 0; i < shopcommodityList.size(); i++) {
			   ShopCommodityModel shopCommMode=new ShopCommodityModel();
			   shopCommMode.setCommCode(shopcommodityList.get(i).getCommCode());
			   shopCommMode.setCommoidtyName(shopcommodityList.get(i).getCommoidtyName());
			   shopCommMode.setShopCommImage(shopcommodityList.get(i).getShopCommImages().get(0).getImagePath());
			   shopCommMode.setUnitPrice(shopcommodityList.get(i).getUnitPrice());
			   shopCommMode.setDescribes(shopcommodityList.get(i).getDescribes());
			   shopCommMode.setSpecial(shopcommodityList.get(i).getSpecial());
			   commodity.add(shopCommMode);
		}
       mode.put("commodity", commodity);
		return mode;
	}
	
	/**
	 * 查询用户的收货地址
	 * @param userName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/searchAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchAddress(String userName) throws ServletException, IOException {
		 ModelMap mode=new ModelMap();
		 List<Address> address=addressService.getAllByuserName(userName);
		 List<AddressModel> addressModelList=new ArrayList<AddressModel>();
		 for (int i = 0; i < address.size(); i++) {
			 AddressModel addressModel=new AddressModel();
			 addressModel.setAddressId(address.get(i).getId());
			 addressModel.setName(address.get(i).getToName());
			 addressModel.setPhone(address.get(i).getPhone());
			 addressModel.setProvience(address.get(i).getProvience());
			 addressModel.setCity(address.get(i).getCity());
			 addressModel.setCountry(address.get(i).getDistrict());
			 addressModel.setOther(address.get(i).getOther());
			 addressModel.setProvienceId(address.get(i).getProvienceId());
			 addressModel.setCityId(address.get(i).getCityId());
			 addressModel.setCountryId(address.get(i).getDistrictId());
			 addressModel.setTheDefault(address.get(i).getTheDefault());
			 addressModelList.add(addressModel);
		}
		 mode.put("address", addressModelList);
		return mode;
	}
	
	/**
	 * 新增地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/saveAddresss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAddresss(String userName,String country,String provience,
			String city,String district,String other,String toEmail,String toName, String phone,
			Integer provienceId, Integer cityId, Integer countryId, Boolean defaultAdd) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
		Address address=new Address();
		AppUser user=appUserService.getUser(userName);
		address.setCountry(country);
		address.setProvience(provience);
		address.setCity(city);
		address.setDistrict(district);
		address.setOther(other);
		address.setToEmail(toEmail);
		address.setToName(toName);
		address.setPhone(phone);
		address.setUser(user);
		address.setProvienceId(provienceId);
		address.setCityId(cityId);
		address.setDistrictId(countryId);
		address.setTheDefault(defaultAdd);
		addressService.save(address);
		
		List<Address> addressList = addressService.getAllByuserName(userName);
		address = addressList.get(addressList.size()-1);
		AddressModel addressModel = new AddressModel();
		addressModel.setAddressId(address.getId());
		addressModel.setName(address.getToName());
		addressModel.setPhone(address.getPhone());
		addressModel.setProvience(address.getProvience());
		addressModel.setCity(address.getCity());
		addressModel.setCountry(address.getDistrict());
		addressModel.setOther(address.getOther());
		addressModel.setProvienceId(address.getProvienceId());
		addressModel.setCityId(address.getCityId());
		addressModel.setCountryId(address.getDistrictId());
		addressModel.setTheDefault(address.getTheDefault());
		mode.put("address", addressModel);
		return mode;
	}
	
	/**
	 * 更新地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/updateAddresss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddresss(String addressId,String userName,String country,String provience,
			String city,String district,String other,String toEmail,String toName, String phone,
			Integer provienceId, Integer cityId, Integer countryId) throws ServletException, IOException {
		Address address = addressService.findById(addressId);
		AppUser user=appUserService.getUser(userName);
		address.setCountry(country);
		address.setProvience(provience);
		address.setCity(city);
		address.setDistrict(district);
		address.setOther(other);
		address.setCountry("中国");
		address.setToEmail(toEmail);
		address.setToName(toName);
		address.setPhone(phone);
		address.setUser(user);
		address.setProvienceId(provienceId);
		address.setCityId(cityId);
		address.setDistrictId(countryId);
		addressService.update(address);
		return null;
	}
	
	/**
	 * 新删除地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/deleteAddresss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAddresss(Integer addressId) 
			throws ServletException, IOException {
		addressService.delete(addressId);
		return null;
	}
	
	/**
	 * 订单页面获取默认地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/getDefaultAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDefaultAddress(String userName, Integer addressId) 
			throws ServletException, IOException {
		ModelMap mode=new ModelMap();
		Address address = null;
		
		List<Address> addressList = addressService.getAllByuserName(userName);
		if ( addressList.size() == 0 ) {
			mode.put("message", "noAddress");
		} else {
			address = addressService.findById(addressId);
			if ( address == null ) {
				address = addressList.get(0);
			}
			
			AddressModel addressModel = new AddressModel();
			addressModel.setAddressId(address.getId());
			addressModel.setProvience(address.getProvience());
			addressModel.setCity(address.getCity());
			addressModel.setCountry(address.getDistrict());
			addressModel.setOther(address.getOther());
			addressModel.setName(address.getToName());
			addressModel.setPhone(address.getPhone()); 
			addressModel.setTheDefault(address.getTheDefault());
			mode.put("message", "success");
			mode.put("defaultAddress", addressModel);
		} 
		return mode;
	}
	
	/**
	 * 存储默认地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "my/saveDefaultAddress", method = RequestMethod.POST)
	public void saveDefaultAddress(String userName, Integer addressId) 
			throws ServletException, IOException {
		Address address = new Address();
		List<Address> defaultAddress = addressService.getDefaultAddress(userName);
		if ( defaultAddress.size() == 0 ) {
			List<Address> addressList = addressService.getAllByuserName(userName);
			address = addressList.get(0);
		} else {
			address = addressService.findById(addressId);
			defaultAddress.get(0).setTheDefault(false);
			addressService.update(defaultAddress.get(0));
		}
		
		if ( address != null ) {
			address.setTheDefault(true);
			addressService.update(address);
		}
	}

	/**
	 * 生成订单
	 * @param ids 购物车商品IDs
	 * @param transportationStyle 收货时间
	 * @param transportationStyle 运输方式
	 * @param deliveryMoney 运费
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "orderGenerate", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView orderGenerate(Integer destinationId, String ids,String deliveryTime,
			String transportationStyle,Float deliveryMoney, String userName, Float totalPrice) throws ServletException, IOException{
		if(destinationId != -1){
			String[] carIds = ids.split(",");
			ModelMap mode = new ModelMap();
			AppUser user = appUserService.getUser(userName);
			DeliveryAddress delivery = new DeliveryAddress();
			Address address = addressService.findById(destinationId);
			BeanUtils.copyProperties(address, delivery);
			delivery.setId(null);
			Delivery delivery2 = new Delivery();
			delivery2.setAddress(delivery);
			delivery2.setDeliveryName(transportationStyle);
			delivery2.setEndorse(deliveryTime);
			delivery2.setDeliveryMoney(deliveryMoney);
			saveOrder(carIds, user, delivery2, totalPrice,mode);
		}
		return null;
	}
	
	private ModelMap saveOrder(String[] CarIds,AppUser appuser,Delivery delivery,Float totalPrice,
			ModelMap mode) throws ServletException, IOException{
		List<Commodity> commodities = new ArrayList<Commodity>();//存放所有的commodity集合
		List<OrderForm> orderformList=new ArrayList<OrderForm>();//存放生成的orderform集合
 		for (int i = 0; i < CarIds.length; i++) {
 			if(!CarIds[i].equals("")){
 				Commodity commodity=new Commodity();
 				int carId = Integer.parseInt(CarIds[i]);
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
			
			//订单存储操作
			OrderForm orderform=new OrderForm();
			orderform.setCommodities(list);
			orderform.setOrderstatus(OrderStatus.waitPayment);
			orderform.setDelivery(delivery);
			orderform.setChangeStatusDate(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			orderform.setOrderDate(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			orderform.setOrderTime(new SimpleDateFormat("HH:mm:ss")
					.format(new Date()));
			orderform.setOrderstatus(OrderStatus.waitDelivery);
			orderform.setOrderUser(appuser);
			orderform.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			orderform.setPaymentTime(new SimpleDateFormat("HH:mm:ss")
					.format(new Date()));
			orderform.setTotalPrice(totalPrice);
 			orderformList.add(orderform);
 			//积分
 			Integer jifen = 0;
 			for (int i = 0; i < list.size(); i++) {
 				list.get(i).setOrderNumber(orderform);
 				Commodity comm = commodityService.save(list.get(i));
 				if(comm != null){
 					jifen = jifen + comm.getMoney().intValue();
 				}
			}
 			jifen  = (jifen/100);
 			if(jifen.intValue()>0){
 				if ( appuser.getPoints() != null) {
 					appuser.setPoints(appuser.getPoints()+jifen);
 				} else {
 					appuser.setPoints(jifen);
 				}
 				appUserService.update(appuser);
 			}
 			//删除下单商品
		}//while
	    for (int i = 0; i < CarIds.length; i++) {
			String carId = CarIds[i];
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
		return mode;
	}
	
	/**
	 * 查询订单
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "searchOrderForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchOrderForm(String userName) throws ServletException, IOException {
		 ModelMap mode=new ModelMap();
		  List<OrderForm> orderFormList=orderFormService.findByPhone(userName);
		  List<OrderFormModel> orderFormModelList=new ArrayList<OrderFormModel>();
		  for (int i = 0; i < orderFormList.size(); i++) {
			  OrderFormModel orderFormModel=new OrderFormModel();
			  orderFormModel.setId(orderFormList.get(i).getOrderFormID());
			  orderFormModel.setImagePath(orderFormList.get(i).getCommodities().get(0).getShopCommodity().getShopCommImages().get(0).getImagePath());
			  orderFormModel.setOrderFormDate(orderFormList.get(i).getOrderDate());
			  orderFormModel.setOrderFormStatus(statusTrans(orderFormList.get(i).getOrderstatus().toString()));
			  orderFormModel.setTotalPrice(orderFormList.get(i).getTotalPrice());
			  orderFormModelList.add(orderFormModel);
		  }
		  mode.put("orderFormModelList", orderFormModelList);
		  return mode;
	}
	
	/**
	 * 删除订单
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "deleteOrderForm", method = RequestMethod.POST)
	@ResponseBody
	public void deleteOrderForm(Integer orderFormId) throws ServletException, IOException {
		orderFormService.delete(orderFormId);
	}
	
	/**
	 * 查询订单详情
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "detailOrderForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailOrderForm(Integer orderFormId) throws ServletException, IOException {
		 ModelMap mode=new ModelMap();
		 OrderForm orderForm=orderFormService.findById(orderFormId);
		 OrderFormModel orderFormModel=new OrderFormModel();
		 orderFormModel.setId(orderForm.getOrderFormID());
		 orderFormModel.setImagePath(orderForm.getCommodities().get(0).getShopCommodity().getShopCommImages().get(0).getImagePath());
		 orderFormModel.setOrderFormDate(orderForm.getOrderDate());
		 orderFormModel.setOrderFormStatus(statusTrans(orderForm.getOrderstatus().toString()));
		 orderFormModel.setTotalPrice(orderForm.getTotalPrice());
		 orderFormModel.setDeliveryMoney(orderForm.getDelivery().getDeliveryMoney());
		 orderFormModel.setEndorse(orderForm.getDelivery().getEndorse());
		 DeliveryAddress address=orderForm.getDelivery().getAddress();
		 orderFormModel.setToName(address.getToName());
		 orderFormModel.setPhone(address.getPhone());
		 orderFormModel.setAddress(address.getProvience()+address.getCity()+address.getDistrict()+address.getOther());
		  //地址
		 List<Commodity> commodities=orderForm.getCommodities();
		 List<CommodityModel> commodityModelList=new ArrayList<CommodityModel>();
		 for (int i = 0; i < commodities.size(); i++) {
			 CommodityModel commodityModel=new CommodityModel();
			 commodityModel.setShopCommodityId(commodities.get(i).getShopCommodity().getCommCode());
			 commodityModel.setTotalPrice(commodities.get(i).getMoney());
			 commodityModel.setSums(commodities.get(i).getQuantity());
			 commodityModel.setCommodityName(commodities.get(i).getShopCommodity().getCommoidtyName());
			 commodityModel.setPath(commodities.get(i).getShopCommodity().getShopCommImages().get(0).getImagePath());
			 commodityModelList.add(commodityModel);
		 }
		 orderFormModel.setCommodities(commodityModelList);
		 mode.put("orderFormModel", orderFormModel);
		 return mode;
	}
	
	/**
	 * 对订单确认收货操作
	 * @param orderFormId 订单id
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "completeOrderForm", method = RequestMethod.POST)
	@ResponseBody
	public void completeOrderForm(Integer orderFormId) throws ServletException, IOException {
		OrderForm orderForm = orderFormService.findById(orderFormId);
		if ( orderForm != null ) {
			orderForm.setOrderstatus(OrderStatus.completionTransaction);
			orderFormService.update(orderForm);
		}
	}
	
	private String statusTrans(String status) {
		if("waitPayment".equals(status)){
			return "等待买家付款";
		}else if("BuyersHavePaid".equals(status)){
			return "买家已付款";
		}else if("waitDelivery".equals(status)){
			  return "等待卖家发货";
		}else if("transitGoods".equals(status)){
			return "卖家已发货";
		}else if("consigneeSigning".equals(status)){
			return "等待收货人签单";
		}else if("completionTransaction".equals(status)){
			 return "完成交易";
		}else if("closeTransaction".equals(status)){
			return "关闭交易";
		}else if("refundOrderForm".equals(status)){
			return "退款中的订单";
		}else if("refundSuccess".equals(status)){
			return "退款成功";
		}else if("refundSuccess".equals(status)){
			return "退款失败";
		} else {
			return null;
		}
	}
}
