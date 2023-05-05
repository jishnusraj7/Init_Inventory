package com.indocosmo.mrp.web.masters.shops.shopdbsettings.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.service.ShopDBService;

@Controller
@RequestMapping(value="/shopdbsetting")
public class ShopDBController extends ViewController<ShopDBSettings,ShopDBDao,ShopDBService>{


	@RequestMapping(value = "shopdbList")
	public void getBatchlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ItemMasterBomService itemmasterbomService = new ItemMasterBomService(getCurrentContext());
		List<ItemMasterBom> bomlist = itemmasterbomService.getList();
		String json = new Gson().toJson(bomlist);
		
		response.getWriter().print(json);
	
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ShopDBService getService() {
		
		return new ShopDBService(getCurrentContext());
	}

}
