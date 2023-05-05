package com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;


public interface IShopDBDao extends IGeneralDao<ShopDBSettings>{
	public JsonArray getBomJsonArray(int id) throws Exception;
	public List<ShopDBSettings> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public void deleteData(Integer idss) throws Exception;
}
