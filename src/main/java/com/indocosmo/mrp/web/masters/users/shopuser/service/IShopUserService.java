package com.indocosmo.mrp.web.masters.users.shopuser.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.users.shopuser.dao.ShopUserDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;


public interface IShopUserService extends IGeneralService<ShopUser, ShopUserDao>{
	public JsonArray getEditDetails(int id) throws Exception;
	public void DeleteShops(String shopIds,Integer userId)throws Exception;
}
