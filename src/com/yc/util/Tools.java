package com.yc.util;

import java.util.ArrayList;
import java.util.List;

import com.yc.entity.ShopCategory;

   public class Tools {
               
    public static List<ShopCategory> lists = new ArrayList<ShopCategory>();
    
	/**
	 * 根据类型查找到底层节点
	 * @param shopCate
	 * @return
	 */
    public static List<ShopCategory> getNodeForShopCategory(ShopCategory shopCate) {
		List<ShopCategory> list = shopCate.getChildren();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				getNodeForShopCategory(list.get(i));
			}
		} else {
			lists.add(shopCate);
		}
		return lists;
	}
	  
}
