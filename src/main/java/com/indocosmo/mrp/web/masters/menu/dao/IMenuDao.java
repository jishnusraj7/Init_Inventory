package com.indocosmo.mrp.web.masters.menu.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.menu.model.Menu;

public interface IMenuDao extends IMasterBaseDao<Menu>{
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Menu> getExcelData() throws Exception;
}
