package com.indocosmo.mrp.web.masters.users.shopuser.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;


public interface IShopUserDao extends IGeneralDao<ShopUser>{
	public JsonArray getEditDetails(int id) throws Exception;
	public void DeleteShops(String shopIds,Integer userId)throws Exception;
	
}
