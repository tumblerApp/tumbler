package com.yc.controller.response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.entity.Collection;
import com.yc.entity.CollectionType;
import com.yc.entity.Shop;
import com.yc.entity.ShopCommodity;
import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;
import com.yc.service.ICollectionService;
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
	
	@RequestMapping(value = "addCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCollection(Integer commID, Integer shopID, String userName) throws ServletException, IOException {
		ModelMap mode = new ModelMap();
		ShopCommodity shopComm = null;
		Shop shop = null;
		int i;
		List<Collection> collections = null;
		AppUser user = appUserService.getUser(userName);
		if (commID != null) {
			shopComm = shopCommoidtyService.findById(commID);
			collections = collectionService.getAllByUser(user.getId());
			for (i = 0; i < collections.size(); i++) {
				if (collections.get(i).getShopCommodity() != null && collections.get(i).getShopCommodity().getCommCode() == commID) {
					mode.put("success", "existed");
					break;
				}
			}
				
			if (i >= collections.size()) {
				Collection collection = new Collection();
				collection.setShopCommodity(shopComm);
				collection.setUser(user);
				collection.setCollectionType(CollectionType.commodity);
				collectionService.save(collection);
				mode.put("success", "true");
			}
		}
			
		if (shopID != null) {
			collections = collectionService.getAllByUser(user.getId());
			shop = shopService.findById(shopID);
			for (i = 0; i < collections.size(); i++) {
				if (collections.get(i).getShop() != null && collections.get(i).getShop().getId() == shopID) {
					mode.put("success", "existed");
					break;
				}
			}
				
			if (i >= collections.size()) {
				Collection collection = new Collection();
				if (shop != null) {
					collection.setShop(shop);
					collection.setUser(user);
					collection.setCollectionType(CollectionType.shop);
					collectionService.save(collection);
					mode.put("success", "true");
				}
			}
		}
		return mode;
	}
}
