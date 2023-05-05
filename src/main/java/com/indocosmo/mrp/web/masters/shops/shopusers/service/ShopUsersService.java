package com.indocosmo.mrp.web.masters.shops.shopusers.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;
import com.indocosmo.mrp.web.masters.shops.shopusers.dao.ShopUsersDao;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;


public class ShopUsersService extends GeneralService<ShopUsers,ShopUsersDao> implements IShopUsersService{

	private ShopUsersDao shopDbDao;
	public ShopUsersService(ApplicationContext context) {
		super(context);
		shopDbDao = new ShopUsersDao(getContext());
		// TODO Auto-generated constructor stub
	}

	

	
	@Override
	public ShopUsersDao getDao() {

		return shopDbDao;
	}
	
	public JsonArray getBomJsonArray(int id) throws Exception{
		return shopDbDao.getBomJsonArray(id);
	}

	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ShopUsers> getDataToImport()throws Exception{
		return shopDbDao.getHqTableRowListToImport();
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ShopUsers> getUpdatedBomListToImport(int stockItemId) throws Exception{
		return shopDbDao.getUpdatedBomListToImport(stockItemId);
	}
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ShopUsers> getBomListForItem(int stockItemId) throws Exception
	{
		return shopDbDao.getBomListForItem(stockItemId);
	}
	
	public void deleteData(Integer idss) throws Exception
	{
		shopDbDao.deleteData(idss);
	}
	public ShopUsers saveShopUser(ShopUsers shop) throws Exception {		
		/*departmentDao.save(shop);*/
		
		GeneralDao<ShopUsers> dao =  getDao();
		if (shop.getId() == null || shop.getId().equals(""))
			dao.insert(shop);
		else
			dao.update(shop);
		return shop;
	}
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "shop_id=" + id;
		
		return shopDbDao.delete(where);
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopusers.service.IShopUsersService#isShopUserExist(java.lang.Integer)
	 */
	@Override
	public Integer isShopUserExist(Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		return shopDbDao.isShopUserExist(shopId);
	}
	
}
