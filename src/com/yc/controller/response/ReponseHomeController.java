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
import com.yc.entity.Collection;
import com.yc.entity.Shop;
import com.yc.entity.ShopCategory;
import com.yc.entity.ShopCommImage;
import com.yc.entity.ShopCommodity;
import com.yc.entity.user.AppUser;
import com.yc.model.AdvertisementManager;
import com.yc.model.CarCommodityModel;
import com.yc.model.CommodityModel;
import com.yc.model.BrandModel;
import com.yc.model.ShopCommodityModel;
import com.yc.model.ShopModel;
import com.yc.service.IAdvertisementDistributionService;
import com.yc.service.IAdvertisementService;
import com.yc.service.IAppUserService;
import com.yc.service.IBrandService;
import com.yc.service.IBuyCarService;
import com.yc.service.ICarCommodityService;
import com.yc.service.ICollectionService;
import com.yc.service.ICommodityService;
import com.yc.service.IShopCategoryService;
import com.yc.service.IShopCommodityService;
import com.yc.service.IShopService;
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
	IShopService shopService;
	
	@Autowired
	ICollectionService collectionService;

	//首页内容加载
	@RequestMapping(value = "category/getShopCommByCate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getHome(int cateId) throws ServletException, IOException {
		ModelMap mode = new ModelMap();

		List<CommodityModel> commodities = new ArrayList<CommodityModel>();
		List<ShopCommodityModel> specCommoidty = new ArrayList<ShopCommodityModel>();
		List<ShopCommodityModel> goodCommoidty = new ArrayList<ShopCommodityModel>();

		if (cateId == 0) {
			commodities = commodityService.getHotCommodities(6);
			specCommoidty = shopCommoidtyService.getSpecialShopComm(6);
			goodCommoidty = shopCommoidtyService.getMyShopComm(6);

			AdvertisementManager manager = new AdvertisementManager();
			mode.putAll(manager.getHomePageAdvertisements(
					adverDistributionService, advertisementService));
		} else {
			commodities = getHotsell(cateId, 6);
			specCommoidty = getSpecial(cateId, 6);
			goodCommoidty = getGood(cateId, 6);
		}

		mode.put("hotCommodities", commodities);
		mode.put("specCommoidty", specCommoidty);
		mode.put("goodCommoidty", goodCommoidty);

		return mode;
	}

	// 首页折扣产品
	private List<ShopCommodityModel> getSpecial(int cateId, int num) {
		ShopCategory shopcategory = shopcategoryService.findById(cateId);
		Tools.lists.clear();
		List<ShopCategory> cateList = Tools
				.getNodeForShopCategory(shopcategory);
		List<ShopCommodityModel> allShopCommodity = new ArrayList<ShopCommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<ShopCommodityModel> comms = shopCommoidtyService
					.getShopCommByCateAndSpecial(cateList.get(i)
							.getCategoryID().toString(), num);
			if (comms != null) {
				allShopCommodity.addAll(comms);
			}
		}
		// 对打折商品集合进行排序并且取前num
		List<ShopCommodityModel> topShopCommodity = new ArrayList<ShopCommodityModel>();
		allShopCommodity.sort(new Comparator<ShopCommodityModel>() {
			public int compare(ShopCommodityModel object1,
					ShopCommodityModel object2) {
				return object1.getSpecial().compareTo(object2.getSpecial());
			}
		});

		for (int i = 0; i < allShopCommodity.size(); i++) {
			if (i < 7) {
				topShopCommodity.add(allShopCommodity.get(i));
			} else {
				break;
			}
		}
		return topShopCommodity;
	}

	// 首页精品产品
	private List<ShopCommodityModel> getGood(int cateId, int num) {
		ShopCategory shopcategory = shopcategoryService.findById(cateId);
		Tools.lists.clear();
		List<ShopCategory> cateList = Tools
				.getNodeForShopCategory(shopcategory);
		List<ShopCommodityModel> allShopCommodity = new ArrayList<ShopCommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<ShopCommodityModel> comms = shopCommoidtyService
					.getShopCommByCateAndBoutique(cateList.get(i)
							.getCategoryID().toString(), num);
			if (comms != null) {
				allShopCommodity.addAll(comms);
			}
		}
		// 对精品商品集合进行排序并且取前num
		List<ShopCommodityModel> topShopCommodity = new ArrayList<ShopCommodityModel>();
		allShopCommodity.sort(new Comparator<ShopCommodityModel>() {
			public int compare(ShopCommodityModel object1,
					ShopCommodityModel object2) {
				return object1.getUnitPrice().compareTo(object2.getUnitPrice());
			}
		});

		for (int i = 0; i < allShopCommodity.size(); i++) {
			if (i < 7) {
				topShopCommodity.add(allShopCommodity.get(i));
			} else {
				break;
			}
		}
		return topShopCommodity;
	}

	// 首页热销产品
	private List<CommodityModel> getHotsell(int cateId, int num) {
		ShopCategory shopcategory = shopcategoryService.findById(cateId);
		Tools.lists.clear();
		List<ShopCategory> cateList = Tools
				.getNodeForShopCategory(shopcategory);
		List<CommodityModel> allCommodity = new ArrayList<CommodityModel>();
		for (int i = 0; i < cateList.size(); i++) {
			List<CommodityModel> comms = commodityService
					.getShopCommByCateAndHot(cateList.get(i).getCategoryID()
							.toString(), num);
			if (comms != null) {
				allCommodity.addAll(comms);
			}
		}
		// 对热销商品集合进行排序并且取前num
		List<CommodityModel> topCommodity = new ArrayList<CommodityModel>();
		allCommodity.sort(new Comparator<CommodityModel>() {
			public int compare(CommodityModel object1, CommodityModel object2) {
				return object1.getSums() - object2.getSums();
			}
		});

		for (int i = 0; i < allCommodity.size(); i++) {
			if (i < 7) {
				topCommodity.add(allCommodity.get(i));
			} else {
				break;
			}
		}
		return topCommodity;
	}

	// 点击首页的更多
	@RequestMapping(value = "category/getShopCommByCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCarCommodity(int cateId, String title) throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		List<CommodityModel> commodities = new ArrayList<CommodityModel>();
		List<ShopCommodityModel> shopCommoidties = new ArrayList<ShopCommodityModel>();

		if (title.equals("hotsell")) {
			if (cateId == 0) {
				commodities = commodityService.getHotCommodities(-1);
			} else {
				commodities = getHotsell(cateId, -1);
			}
			mode.put("commodities", commodities);
		} else if (title.equals("special")) {
			if (cateId == 0) {
				shopCommoidties = shopCommoidtyService.getSpecialShopComm(-1);
			} else {
				shopCommoidties = getSpecial(cateId, -1);
			}
			mode.put("commodities", shopCommoidties);
		} else if (title.equals("good")) {
			if (cateId == 0) {
				shopCommoidties = shopCommoidtyService.getMyShopComm(-1);
			} else {
				shopCommoidties = getGood(cateId, -1);
			}
			mode.put("commodities", shopCommoidties);
		}
		return mode;
	}

	// 搜索产品
	@RequestMapping(value = "home/searchCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCommodity(String content)
			throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		List<ShopCommodity> searchResultList = shopCommoidtyService.searchShopComm(content);
		List<ShopCommodityModel> list = new ArrayList<ShopCommodityModel>();
		ShopCommodityModel item = null;
		for (int i = 0; i < searchResultList.size(); i++) {
			item = new ShopCommodityModel();
			item.setCommCode(searchResultList.get(i).getCommCode());
			item.setDescribes(searchResultList.get(i).getDescribes());
			item.setUnitPrice(searchResultList.get(i).getUnitPrice());
			item.setSpecial(searchResultList.get(i).getSpecial());
			String path = searchResultList.get(i).getShopCommImages().get(0)
					.getImagePath();
			item.setShopCommImage(path);
			item.setCommoidtyName(searchResultList.get(i).getCommoidtyName());
			list.add(item);
		}
		mode.put("shopcommodities", list);
		return mode;
	}

	/**
	 * 搜寻商铺
	 * 
	 * @param content
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "home/searchShop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchShop(String content)
			throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		List<Shop> searchResultList = shopService.searchShop(content);
		List<ShopModel> shopList = new ArrayList<ShopModel>();
		for (int i = 0; i < searchResultList.size(); i++) {
			ShopModel shopModel = new ShopModel();
			shopModel.setShopId(searchResultList.get(i).getId());
			shopModel.setAddress(searchResultList.get(i).getAddress());
			shopModel.setLogoPath(searchResultList.get(i).getShopLogo());
			shopModel.setShopName(searchResultList.get(i).getShopName());
			shopList.add(shopModel);
		}
		mode.put("shopList", shopList);
		return mode;
	}

	// 详情页
	@RequestMapping(value = "detail/detailCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailCommodity(String id) throws ServletException,
			IOException {
		ModelMap mode = new ModelMap();
		int commoCode = Integer.parseInt(id);
		ShopCommodity commodity = shopCommoidtyService.findById(commoCode);
		ShopCommodityModel shopcommodity = null;
		shopcommodity = new ShopCommodityModel();
		if (shopcommodity != null) {
			shopcommodity.setCommCode(commodity.getCommCode());
			shopcommodity.setCommoidtyName(commodity.getCommoidtyName());
			shopcommodity.setCommItem(commodity.getCommItem());
			shopcommodity.setUnitPrice(commodity.getUnitPrice());
			shopcommodity.setSpecialPrice(commodity.getSpecialPrice());
			shopcommodity.setStock(commodity.getStock());
			shopcommodity.setCategoryName(commodity.getShopCategory()
					.getCategory());
			shopcommodity.setBelongTo(commodity.getBelongTo().getId());
			shopcommodity.setShopName(commodity.getBelongTo().getShopName());
			shopcommodity.setDescribes(commodity.getDescribes());
			shopcommodity.setCommSpec(commodity.getCommsPecs().getCommSpec());
			shopcommodity.setIsActivity(commodity.getIsAcitvity());
			shopcommodity.setSpecial(commodity.getSpecial());

			// 品牌
			if (commodity.getBrand() != null) {
				shopcommodity.setBrandName(commodity.getBrand().getBrandName());
				mode.put("brandnull", false);
			} else {
				mode.put("brandnull", true);
			}

			// 是否参加活动
			if (commodity.getIsAcitvity()) {
				mode.put("isActivity", true);
				shopcommodity.setActivityAmount(commodity.getActivityAmount());
				shopcommodity.setActivityName(commodity.getActivity()
						.getActivityName());
			} else {
				mode.put("isActivity", false);
			}

			// 描述图片
			List<String> paths = new ArrayList<String>();
			List<ShopCommImage> images = commodity.getShopCommImages();
			for (int j = 0; j < images.size(); j++) {
				paths.add(images.get(j).getImagePath());
			}
			shopcommodity.setImagePaths(paths);
		}
		mode.put("shopcommodity", shopcommodity);
		return mode;

	}
	
	//用户是否收藏了商品或者店铺
	@RequestMapping(value = "detail/isCollected", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isCollected(String userName, Integer commID, Integer shopID )
			throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		AppUser user = appUserService.getUser(userName);
		List<Collection> collections = collectionService.getAllByUser(user.getId());
		int i;
		for ( i = 0; i < collections.size(); i++ ) {
			if ( commID != 0 && collections.get(i).getShopCommodity() != null ) {
				if ( collections.get(i).getShopCommodity().getCommCode() == commID ) {
					mode.put("isCollected", true);
					break;
				}
			} else if ( shopID != 0 && collections.get(i).getShop() != null ) {
				if ( collections.get(i).getShop().getId() == shopID ) {
					mode.put("isCollected", true);
					break;
				}
			}
		}
		if ( i >= collections.size() ) {
			mode.put("isCollected", false);
		}
		return mode;
	}

	// 分类页面
	@RequestMapping(value = "category/getSecondCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSecondCategory() throws ServletException,
			IOException {
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
	 * 
	 * @param cateId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "category/getShopCommByBrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShopCommByBrand(
			@RequestParam(value = "brandId") String brandId)
			throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		List<ShopCommodityModel> shopcommList = shopCommoidtyService
				.getShopCommByBrand(brandId);
		mode.put("shopcommList", shopcommList);
		return mode;
	}

	/**
	 * 加入购物车
	 * 
	 * @param shopCommId
	 *            商品id
	 * @param userName
	 *            用户名
	 * @param num
	 *            购买数量
	 * @return 是否加入成功
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "shopcar/addBuyCar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBuyCar(String shopCommId, String userName,
			String num) throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		int id = Integer.parseInt(shopCommId);
		int buyAmount = Integer.parseInt(num);
		AppUser user = appUserService.getUser(userName);
		mode = serviceTools.addCarCommodity(buyAmount, id, mode, user);
		return mode;
	}

	/**
	 * 刷新购物车方法
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "shopcar/reCarCommodity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCarCommodity(String userName)
			throws ServletException, IOException, ParseException {
		ModelMap mode = new ModelMap();
		List<CarCommodity> carCommodityList = carCommodityService
				.getCarCommodityByUserName(userName);
		List<CarCommodity> handleCarCommodities = serviceTools
				.handleCarCommodity(carCommodityList, userName);
		// 返回CarCommodityModel类型集合
		List<CarCommodityModel> carCommodityModelList = new ArrayList<CarCommodityModel>();
		for (int i = 0; i < handleCarCommodities.size(); i++) {
			CarCommodityModel carCommodityMode = new CarCommodityModel();
			carCommodityMode.setImagePath(handleCarCommodities.get(i)
					.getShopCommodity().getShopCommImages().get(0)
					.getImagePath());
			carCommodityMode.setPrice(handleCarCommodities.get(i).getPrice());
			carCommodityMode.setQuantity(handleCarCommodities.get(i)
					.getAmount());
			carCommodityMode.setShopComName(handleCarCommodities.get(i)
					.getShopCommodity().getCommoidtyName());
			carCommodityMode.setShopCommCode(handleCarCommodities.get(i)
					.getShopCommodity().getCommCode());
			carCommodityMode.setCarCommID(handleCarCommodities.get(i)
					.getId());
			carCommodityMode.setUnitPrice(handleCarCommodities.get(i)
					.getUnitPrice());
			carCommodityModelList.add(carCommodityMode);
		}
		mode.put("carCommodityModelList", carCommodityModelList);
		return mode;
	}

	// 删除购物车产品
	@RequestMapping(value = "shopcar/deleteShopCar", method = RequestMethod.POST)
	@ResponseBody
	public void deleteShopCar(Integer id) throws ServletException,
			IOException {
		CarCommodity carCommodity = carCommodityService.findById(id);
		if (carCommodity != null) {
			BuyCar car = carCommodity.getBuyCar();
			if (car != null) {
				car.getCarCommodities().remove(carCommodity);
				carCommodityService.delete(id);
			}
			if (car.getCarCommodities().size() == 0) {
				buyCarService.delete(car.getCatID());
			}
		}
	}
	
	/**
	 * 通过类别名获取其下所有商品
	 * @param shopCate
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "getShopCommByCategoryName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShopCommByCategoryName(String categoryName) throws ServletException, IOException {
		ModelMap mode=new ModelMap();
		ShopCategory  shopCategory =shopcategoryService.getByshopCate(categoryName);
		Tools.lists.clear();
		List<ShopCategory> lists=Tools.getNodeForShopCategory(shopCategory);
		List<ShopCommodityModel> shopCommodityModelList = new ArrayList<ShopCommodityModel>();
		List<ShopCommodity> shopCommodityList= new ArrayList<ShopCommodity>();
		for ( int i = 0; i < lists.size(); i++ ) {			
 			    List<ShopCommodity> ShopCommoditys=shopCommoidtyService.
 			    		getAllByShopCategoryID(lists.get(i).getCategoryID(), null);
 			   shopCommodityList.addAll(ShopCommoditys);
 		}
        for (int i = 0; i < shopCommodityList.size(); i++) {
			   ShopCommodityModel shopCommMode=new ShopCommodityModel();
			   shopCommMode.setCommCode(shopCommodityList.get(i).getCommCode());//id
			   shopCommMode.setCommoidtyName(shopCommodityList.get(i).getCommoidtyName());//商品名称
			   shopCommMode.setShopCommImage(shopCommodityList.get(i).getShopCommImages().get(0).getImagePath());//图片路径
			   shopCommMode.setUnitPrice(shopCommodityList.get(i).getUnitPrice());//单价
			   shopCommMode.setSpecial(shopCommodityList.get(i).getSpecial());
			   shopCommMode.setDescribes(shopCommodityList.get(i).getDescribes());//描述
			   shopCommodityModelList.add(shopCommMode);
		}
        mode.put("shopCommodityModelList", shopCommodityModelList);
		return mode;
	}
}
