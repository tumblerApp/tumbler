package com.yc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.yc.entity.AdvertiseDistribution;
import com.yc.entity.Advertisement;
import com.yc.service.IAdvertisementDistributionService;
import com.yc.service.IAdvertisementService;

//广告
public class AdvertisementManager {
	
	public AdvertisementManager(){}
	
	public ModelMap getHomePageAdvertisements(IAdvertisementDistributionService adverDistributionService,IAdvertisementService advertisementService) {
		ModelMap mode = new ModelMap();
		AdvertiseDistribution adverDistribution1 = adverDistributionService.findByWhichPageAndPosition("homePage", 1);
		
		int position1 = 0;
		if ( adverDistribution1 != null ) {
			position1 = adverDistribution1.getAdverDisId();
		}

    	List<Advertisement> advertisements = advertisementService.getAll();
    	ArrayList<AdvertisementModel> advertisements1 = new ArrayList<AdvertisementModel>();

    	AdvertisementModel ad = null;
    	for ( int i = 0; i < advertisements.size(); i++ ) {
    		if ( position1 != 0 && advertisements.get(i).getAdverDistribution().getAdverDisId() == position1 ) {
    			ad = new AdvertisementModel();
    			ad.setLink(advertisements.get(i).getLink());
    			ad.setPath(advertisements.get(i).getImagePath());
    			advertisements1.add(ad);
    		}
    	}
    	mode.put("advertisements1", advertisements1);
    	
		return mode;
	}

}
