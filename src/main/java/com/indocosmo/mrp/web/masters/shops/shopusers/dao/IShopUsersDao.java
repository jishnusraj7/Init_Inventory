package com.indocosmo.mrp.web.masters.shops.shopusers.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;


public interface IShopUsersDao extends IGeneralDao<ShopUsers>{
	public JsonArray getBomJsonArray(int id) throws Exception;
	public List<ShopUsers> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public void deleteData(Integer idss) throws Exception;
	public Integer isShopUserExist(Integer shopId) throws Exception;
}
