package com.indocosmo.mrp.web.masters.shops.shopusers.controller;

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
import com.indocosmo.mrp.web.masters.shops.shopusers.dao.ShopUsersDao;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;
import com.indocosmo.mrp.web.masters.shops.shopusers.service.ShopUsersService;

@Controller
@RequestMapping(value="/shopusers")
public class ShopUsersController extends ViewController<ShopUsers,ShopUsersDao,ShopUsersService>{


	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ShopUsersService getService() {
		
		return new ShopUsersService(getCurrentContext());
	}

}
