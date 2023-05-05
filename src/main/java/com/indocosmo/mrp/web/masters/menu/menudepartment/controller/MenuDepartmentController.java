package com.indocosmo.mrp.web.masters.menu.menudepartment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.menu.menudepartment.dao.MenuDepartmentDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;
import com.indocosmo.mrp.web.masters.menu.menudepartment.service.MenuDepartmentService;

@Controller
@RequestMapping("/menudepartment")
public class MenuDepartmentController extends ViewController<MenuDepartment, MenuDepartmentDao, MenuDepartmentService>{

	@Override
	public MenuDepartmentService getService() {
	
		return new MenuDepartmentService(getCurrentContext());
	}
	
	@RequestMapping(value = "deptdata")
	public void getUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final MenuDepartmentService menuDepartmentService=new MenuDepartmentService(getCurrentContext());
		final String menuId = request.getParameter("menuId");
		
		JsonArray json=menuDepartmentService.getmdpJsonArray(Integer.parseInt(menuId));
		
		response.getWriter().print(json);
	}

}
