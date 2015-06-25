package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.dao.orm.commons.GenericDao;
import com.yc.entity.Brand;
import com.yc.model.BrandModel;
import com.yc.service.IBrandService;

@Component
public class BrandService extends GenericService<Brand> implements IBrandService {

	@Autowired
	GenericDao<Brand> brandService;
	
	@Override
	GenericDao<Brand> getDao() {
		return brandService;
	}

	@Override
	public Brand getBrandName(String brandName) {
		return brandService.getFirstRecord("brandName", brandName);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<BrandModel> getBrands(String category) {
		StringBuffer hql = new StringBuffer("SELECT b.brandName, b.brandID FROM brand b right JOIN brand_shopcategory bs ON bs.brands_brandID = b.brandID left JOIN shopcategory c ON c.categoryID = bs.shopCateges_categoryID WHERE c.category='"+category+"'");
		Query query = brandService.getEntityManager().createNativeQuery(hql.toString());
		List objecArraytList = query.getResultList();
		List<BrandModel> pr = new ArrayList<BrandModel>();
		BrandModel mode = null;
		if (objecArraytList != null && objecArraytList.size() > 0) {
			for (int i = 0; i < objecArraytList.size(); i++) {
				mode = new BrandModel();
				Object[] obj = (Object[]) objecArraytList.get(i);
				if (obj != null) {
					mode.setBrandName(obj[0].toString());
					mode.setId(Integer.parseInt(obj[1].toString()));
					pr.add(mode);
				}
			}
		}
		return pr;
	}
}
