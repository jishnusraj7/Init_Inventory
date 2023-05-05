package com.indocosmo.mrp.web.masters.shops.shopusers.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;
import com.indocosmo.mrp.web.masters.shops.shopusers.dao.ShopUsersDao;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;


public interface IShopUsersService extends IGeneralService<ShopUsers,ShopUsersDao>{
	public JsonArray getBomJsonArray(int id)throws Exception;
	public List<ShopUsers> getDataToImport()throws Exception;
	public List<ShopUsers> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public Integer isShopUserExist(Integer shopId) throws Exception;
}
