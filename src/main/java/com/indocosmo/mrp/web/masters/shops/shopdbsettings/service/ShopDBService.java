package com.indocosmo.mrp.web.masters.shops.shopdbsettings.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;


public class ShopDBService extends GeneralService<ShopDBSettings,ShopDBDao> implements IShopDBService{

	private ShopDBDao shopDbDao;
	public ShopDBService(ApplicationContext context) {
		super(context);
		shopDbDao = new ShopDBDao(getContext());
		// TODO Auto-generated constructor stub
	}

	

	
	@Override
	public ShopDBDao getDao() {

		return shopDbDao;
	}
	
	public JsonArray getBomJsonArray(int id) throws Exception{
		return shopDbDao.getBomJsonArray(id);
	}

	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ShopDBSettings> getDataToImport()throws Exception{
		return shopDbDao.getHqTableRowListToImport();
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ShopDBSettings> getUpdatedBomListToImport(int stockItemId) throws Exception{
		return shopDbDao.getUpdatedBomListToImport(stockItemId);
	}
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ShopDBSettings> getBomListForItem(int stockItemId) throws Exception
	{
		return shopDbDao.getBomListForItem(stockItemId);
	}
	
	public void deleteData(Integer idss) throws Exception
	{
		shopDbDao.deleteData(idss);
	}
	public ShopDBSettings saveShopDb(ShopDBSettings shop) throws Exception {		
		/*departmentDao.save(shop);*/
		
		GeneralDao<ShopDBSettings> dao =  getDao();
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
}
