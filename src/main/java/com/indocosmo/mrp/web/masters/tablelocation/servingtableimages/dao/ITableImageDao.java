package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model.TableImage;


public interface ITableImageDao extends IGeneralDao<TableImage>{


	public JsonArray getdpData(String table)  throws Exception;
	
}
