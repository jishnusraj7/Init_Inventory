package com.indocosmo.mrp.web.masters.menu.menudepartment.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.menu.menudepartment.dao.MenuDepartmentDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;

public interface IMenuDepartmentService extends IGeneralService<MenuDepartment,MenuDepartmentDao>{
	public String getShopDptId(String menuId,String dptId)throws Exception;
	public JsonArray getEditDetails(int id) throws Exception;
	public void DeleteDepartments(String deptIds,Integer menuId)throws Exception;
	public JsonArray getmdpJsonArray(int id) throws Exception;
}
