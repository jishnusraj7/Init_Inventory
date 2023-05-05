package com.indocosmo.mrp.web.masters.menu.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.menu.dao.MenuDao;
import com.indocosmo.mrp.web.masters.menu.model.Menu;

public interface IMenuService extends IMasterBaseService<Menu, MenuDao>{
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Menu> getExcelData() throws Exception;
}
