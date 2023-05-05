package com.indocosmo.mrp.web.masters.users.shopuser.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.users.shopuser.dao.ShopUserDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;
import com.indocosmo.mrp.web.masters.users.shopuser.service.ShopUserService;

@Controller
@RequestMapping("/shopuser")
public class ShopUserController extends ViewController<ShopUser, ShopUserDao, ShopUserService>{

	@Override
	public ShopUserService getService() {
	
		// TODO Auto-generated method stub
		return new ShopUserService(getCurrentContext());
	}
	
	
	@RequestMapping(value = "shpdata")
	public void getUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final ShopUserService shopUserService=new ShopUserService(getCurrentContext());
		final String userId = request.getParameter("usrId");
		
		JsonArray json=shopUserService.getShpusrJsonArray(Integer.parseInt(userId));
		
		response.getWriter().print(json);
	}

}
