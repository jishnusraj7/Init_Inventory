package com.indocosmo.mrp.web.masters.shops.shopdepartments.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.ShopDepartmentDao;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;


public class ShopDepartmentService extends GeneralService<ShopDepartment,ShopDepartmentDao> implements IShopDepartmentService{

	private ShopDepartmentDao shopDepartmentDao;
	public ShopDepartmentService(ApplicationContext context) {
		super(context);
		shopDepartmentDao = new ShopDepartmentDao(getContext());
		// TODO Auto-generated constructor stub
	}

	

	
	@Override
	public ShopDepartmentDao getDao() {

		return shopDepartmentDao;
	}
	
	public JsonArray getBomJsonArray(int id) throws Exception{
		return shopDepartmentDao.getBomJsonArray(id);
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ShopDepartment> getDataToImport()throws Exception{
		return shopDepartmentDao.getHqTableRowListToImport();
	}
	
	
	public void deleteData(Integer idss) throws Exception
	{
		shopDepartmentDao.deleteData(idss);
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.service.IShopDepartmentService#getDepdataByShopId(java.lang.Integer)
	 */
	@Override
	public JsonArray getDepdataByShopId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return shopDepartmentDao.getDepdataByShopId(id);
	}




	/**
	 * @param id
	 */
	public void updateIsDelete(Integer id) throws Exception {
		// TODO Auto-generated method stub
		 shopDepartmentDao.updateIsDelete(id);
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.service.IShopDepartmentService#DepData(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer DepData(Integer depId, Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		return  shopDepartmentDao.DepData(depId, shopId);
	}




	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.service.IShopDepartmentService#DbData(java.lang.Integer)
	 */
	@Override
	public Integer DbData(Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		return shopDepartmentDao.DbData(shopId);
	}
	
	public ShopDepartment saveShopDep(ShopDepartment shop) throws Exception {		
		/*departmentDao.save(shop);*/
		
		GeneralDao<ShopDepartment> dao =  getDao();
		if (shop.getId() == null || shop.getId().equals(""))
			dao.insert(shop);
		else
			dao.update(shop);
		return shop;
	}




	/**
	 * @param dptIds
	 * @param id
	 * @throws Exception 
	 */
	public void DeleteDepartments(String dptIds, Integer id) throws Exception {
		
		shopDepartmentDao.DeleteDepartments(dptIds, id);
		
	}
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "shop_id=" + id;
		
		return shopDepartmentDao.delete(where);
	}




	public Integer deleteSyncQue(String tableName,int shopId) throws Exception{
		return shopDepartmentDao.deleteSyncQueue(tableName,shopId);
		
	}




	/**
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getDptJsonArray(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return shopDepartmentDao. getDptJsonArray(id);
	}
	
}
