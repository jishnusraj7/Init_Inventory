package com.indocosmo.mrp.web.masters.users.shopuser.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.users.shopuser.dao.ShopUserDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;


public class ShopUserService extends GeneralService<ShopUser, ShopUserDao>{
private ShopUserDao shopUserDao=new ShopUserDao(getContext());
	
	public ShopUserService(ApplicationContext context) {
	
		super(context);
		shopUserDao=new ShopUserDao(getContext());
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShopUserDao getDao() {
	
		// TODO Auto-generated method stub
		return shopUserDao;
	}
	
	public JsonArray getShpusrJsonArray(int id) throws Exception{
		return shopUserDao.getShpusrJsonArray(id);
		
	}
	
	public JsonArray getEditDetails(int id) throws Exception{
		return shopUserDao.getEditDetails(id);
		
	}
	
	public void DeleteShops(String shopIds,Integer userId)throws Exception{
		shopUserDao.DeleteShops(shopIds,userId);
	}
	
	public String getShopUserId(String userId,String shopId)throws Exception
	{
		return shopUserDao.getShopUserId(userId,shopId);
	}
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "user_id=" + id;
		
		return shopUserDao.delete(where);
	}
	
	public Integer deleteSyncQue(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return shopUserDao.deleteSyncQue(id);
	}
}
