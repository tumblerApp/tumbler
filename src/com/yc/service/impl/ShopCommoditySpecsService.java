package com.yc.service.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.ShopCommoditySpecs;
import com.yc.service.IShopCommoditySpecsService;
@Component
public class ShopCommoditySpecsService extends GenericService<ShopCommoditySpecs> implements IShopCommoditySpecsService {

	@Autowired
	GenericDao<ShopCommoditySpecs> shopCommSpecsDao;
	
	@Override
	GenericDao<ShopCommoditySpecs> getDao() {
		return shopCommSpecsDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShopCommoditySpecs getSpecsByParam(Integer id, String guige) {
		String[] gui = guige.split(",");
		StringBuffer hql = new StringBuffer("select spec.* from ShopCommoidtySpecs spec where spec.shopComm_id = "+id );
		for (int i = 0; i < gui.length; i++) {
			if(!gui[i].equals("")){
				if (gui[i].contains("颜色-")) {
					hql.append(" and ('"+gui[i]+"' is null or spec.commSpec like '%,"+gui[i]+"%') ");
				}else{
					hql.append(" and ('"+gui[i]+"' is null or spec.commSpec like '%,"+gui[i]+",%') ");
				}
			}
		}
		Query query = shopCommSpecsDao.getEntityManager().createNativeQuery(hql.toString(), ShopCommoditySpecs.class);
		List<ShopCommoditySpecs> list = query.getResultList();
		if (list != null && list.size()>0) {
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<ShopCommoditySpecs> getSpecsByShopComm(int parseInt) {
		return shopCommSpecsDao.getBy("shopCommSpecs.commCode", parseInt);
	}

	
}
