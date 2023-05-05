package com.indocosmo.mrp.web.masters.menu.menudepartment.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;

public interface IMenuDepartmentDao extends IGeneralDao<MenuDepartment> {
	public String getShopDptId(String menuId,String dptId)throws Exception;
	public JsonArray getEditDetails(int id) throws Exception;
	public void DeleteDepartments(String deptIds,Integer menuId)throws Exception;
	public JsonArray getmdpJsonArray(int id) throws Exception;
}
