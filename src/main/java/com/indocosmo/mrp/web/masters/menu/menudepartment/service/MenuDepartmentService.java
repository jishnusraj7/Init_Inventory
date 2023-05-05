package com.indocosmo.mrp.web.masters.menu.menudepartment.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.menu.menudepartment.dao.MenuDepartmentDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;

public class MenuDepartmentService extends GeneralService<MenuDepartment,MenuDepartmentDao> implements IMenuDepartmentService{

	private	MenuDepartmentDao menuDepartmentDao;	
	
	public MenuDepartmentService(ApplicationContext context) {
		super(context);
		menuDepartmentDao=new MenuDepartmentDao(getContext());
		// TODO Auto-generated constructor stub
	}

	@Override
	public MenuDepartmentDao getDao() {
		// TODO Auto-generated method stub
		return menuDepartmentDao;
	}
	public String getShopDptId(String menuId,String dptId)throws Exception
	{
		return menuDepartmentDao.getShopDptId(menuId,dptId);
	}
	
	public JsonArray getEditDetails(int id) throws Exception{
		return menuDepartmentDao.getEditDetails(id);
		
	}

	public void DeleteDepartments(String deptIds,Integer menuId)throws Exception{
		 menuDepartmentDao.DeleteDepartments(deptIds,menuId);
	}
	public Integer deleteSyncQue(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return menuDepartmentDao.deleteSyncQue(id);
	}
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "menu_id=" + id;
		
		return menuDepartmentDao.delete(where);
	}
	
	public JsonArray getmdpJsonArray(int id) throws Exception{
		return menuDepartmentDao.getmdpJsonArray(id);
	}
	
}
