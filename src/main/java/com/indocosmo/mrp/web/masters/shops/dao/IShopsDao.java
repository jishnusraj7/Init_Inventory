package com.indocosmo.mrp.web.masters.shops.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.shops.model.Shops;


public interface IShopsDao extends IGeneralDao<Shops>{

	public JsonArray getShopDataById(int id) throws Exception;
	public JsonArray getMsterShopJson() throws Exception;
	
}
