package com.indocosmo.mrp.web.masters.shops.shopdepartments.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.controller.ViewController;

import com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.ShopDepartmentDao;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.service.ShopDepartmentService;

@Controller
@RequestMapping(value="/shopdepartments")
public class ShopDepartmentController extends ViewController<ShopDepartment,ShopDepartmentDao,ShopDepartmentService>{


	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ShopDepartmentService getService() {
		
		return new ShopDepartmentService(getCurrentContext());
	}
	
	@RequestMapping(value = "/getDepIdsByShopId")
	public void getDepIdsByShopId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
       JsonObject jsonResponse = new JsonObject();
       Integer shopId=Integer.parseInt(request.getParameter("curshopId"));
		
		final ShopDepartmentService shpSrvice=new ShopDepartmentService(getCurrentContext());
		JsonArray depDtls=shpSrvice.getDepdataByShopId(shopId);
		
		
		
		jsonResponse.add("depDtls", depDtls);	
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
		
	}
	
	@RequestMapping(value = "deptdata")
	public void getUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String shopId = request.getParameter("shopId");
		final ShopDepartmentService shopDepartmentService=new ShopDepartmentService(getCurrentContext());
			
		JsonArray json=shopDepartmentService.getDptJsonArray(Integer.parseInt(shopId));
		
		response.getWriter().print(json);
	}


}
