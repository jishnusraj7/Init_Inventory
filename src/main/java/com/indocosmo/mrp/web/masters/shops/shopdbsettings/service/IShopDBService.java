package com.indocosmo.mrp.web.masters.shops.shopdbsettings.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;


public interface IShopDBService extends IGeneralService<ShopDBSettings,ShopDBDao>{
	public JsonArray getBomJsonArray(int id)throws Exception;
	public List<ShopDBSettings> getDataToImport()throws Exception;
	public List<ShopDBSettings> getUpdatedBomListToImport(int stockItemId) throws Exception;
}
