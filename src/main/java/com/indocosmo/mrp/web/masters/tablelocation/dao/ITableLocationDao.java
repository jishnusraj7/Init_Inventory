package com.indocosmo.mrp.web.masters.tablelocation.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;


public interface ITableLocationDao extends IGeneralDao<TableLocation>{
	

	public JsonArray getdpData(String table)  throws Exception;
	
}
