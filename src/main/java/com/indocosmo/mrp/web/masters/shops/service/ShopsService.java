package com.indocosmo.mrp.web.masters.shops.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.shops.dao.ShopsDao;
import com.indocosmo.mrp.web.masters.shops.model.Shops;


public class ShopsService extends GeneralService<Shops,ShopsDao> implements IShopsService{
	

	private ShopsDao departmentDao;
	
	
	public ShopsService(ApplicationContext context) {
		super(context);
		departmentDao=new ShopsDao(getContext());
	
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ShopsDao getDao() {
		return departmentDao;
	}
	
	
	public Shops saveShop(Shops shop) throws Exception {		
		/*departmentDao.save(shop);*/
		
		GeneralDao<Shops> dao =  getDao();
		if (shop.getId() == null || shop.getId().equals(""))
			dao.insert(shop);
		else
			dao.update(shop);
		return shop;
	}
	
	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	
	
@Override
public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
	ApplicationContext context = departmentDao.getContext();
	
	ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
	ArrayList<String> referenceTables = new ArrayList<String>();
	if(context.getCompanyInfo().getId()!=0){
/*		referenceTables.add(usersDao.getTable());*/
		
		}
	
	for (String table : referenceTables) {
		
		RefereneceModelBase referenceModel=new RefereneceModelBase();
		
		
	}
		return referenceTableDetails;
  }
@SuppressWarnings("unchecked")
@Override
public Integer delete(String id) throws Exception {
	
	
	final String where = "id=" + id;
	Integer is_deleted = 1;
	
	ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
	
	GeneralDao<Shops> dao = (GeneralDao<Shops>) getDao();
	
	int rowCount = 0;
	 String wherePart="";
	if (referenceTables != null) {
		for (RefereneceModelBase table : referenceTables) {
			
			
				 wherePart = ""+table.getRefrenceKey()+"="+ id +" ;";

			
			rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
		}
	}
	
	if (rowCount != 0) {
		
		is_deleted = 0;
	}
	
	if (referenceTables == null || rowCount == 0) {
		
		departmentDao.delete(where);
	}
	
	return is_deleted;
}

	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportList() throws Exception {

		return departmentDao.getHqTableRowJson();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {

		return departmentDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Shops> getDataToImport()throws Exception{
		return departmentDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Shops> getUpdatedDataToImport()throws Exception{
		return departmentDao.getUpdatedHqTableRowListToImport();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.service.IShopsService#getShopDataById(int)
	 */
	@Override
	public JsonArray getShopDataById(int id) throws Exception {
		// TODO Auto-generated method stub
		return departmentDao.getShopDataById(id);
	}

	@Override
	public JsonArray getAreaByShopId(int id) throws Exception {
		// TODO Auto-generated method stub
		return departmentDao.getAreaByShopId(id);
	}

	/**
	 * @return
	 */
	public JsonArray getImagePath(Integer id) {
		// TODO Auto-generated method stub
		return  departmentDao.getImagePath(id);
	}

	/**
	 * @return
	 */
	public List<Shops> getExcelData() throws Exception {
		// TODO Auto-generated method stub
		return departmentDao.getExcelData();
	}

	
	/*public void save(Shops item) throws Exception {

		GeneralDao<Shops> dao =  getDao();
		if (item.getId() == null || item.getId().equals(""))
			dao.insert(item);
		else
			dao.update(item);
	}
	
*/
	
	
}
