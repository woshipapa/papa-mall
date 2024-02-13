package com.papa.portal.service;

import com.papa.portal.domain.FlashPromotionProduct;
import com.papa.portal.domain.HomeContentResult;

import java.util.List;

public interface HomeService {

    public HomeContentResult recommendContent();

    public void preheatCache();

    public HomeContentResult getFromRemote();

    public List<FlashPromotionProduct> getSecKillFromRemote();

}
