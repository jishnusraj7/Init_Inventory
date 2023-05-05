package com.indocosmo.mrp.web.masters.tablelocation.servingtables.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.ServingTablesDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.model.ServingTables;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.PurchaseOrderdtlDao;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.dao.StockInDetailDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao.StockOutDetailDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;


public class ServingTablesService extends GeneralService<ServingTables,ServingTablesDao> implements IServingTablesService{
	
	
	//private IItemMasterDao itemMasterDao;
	
	private ServingTablesDao servingTableDao;
	
	private PurchaseOrderdtlDao purchaseorderdtlDao;
	
	
	private StockInDetailDao stockindetailDao;
	
	
	private StockOutDetailDao stockoutdetailDao;
	
	
	private StockRegisterDetailDao stockregisterdetailDao;
	
	public ServingTablesService(ApplicationContext context) {
		super(context);
		servingTableDao = new ServingTablesDao(getContext());
		purchaseorderdtlDao = new PurchaseOrderdtlDao(getContext());
		stockindetailDao = new StockInDetailDao(getContext());
		stockoutdetailDao = new StockOutDetailDao(getContext());
		stockregisterdetailDao = new StockRegisterDetailDao(getContext());
	}

	
	@Override
	public ServingTablesDao getDao() {

		return servingTableDao;
	}
	
	/*@Override
	public Integer delete(String where) throws Exception {
		where = "id="+where;
		
		return super.delete(where);
	}*/
	
	

	public ServingTables saveItem(ServingTables item) throws Exception {		
		GeneralDao<ServingTables> dao =  getDao();
		if (item.getId() == null || item.getId().equals(""))
			dao.insert(item);
		else
			dao.update(item);
		return item;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportList()  throws Exception{

		return servingTableDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportUpdatedList()  throws Exception{

		return servingTableDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ServingTables> getDataToImport()throws Exception{
		return servingTableDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ServingTables> getUpdatedDataToImport()throws Exception{
		return servingTableDao.getUpdatedHqTableRowListToImport();
	}
	
		

public JsonArray getdpData(String table)  throws Exception{

	return servingTableDao.getdpData(table);
}


/* (non-Javadoc)
 * @see com.indocosmo.mrp.web.masters.tablelocation.service.ITableLocationService#saveItem(com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster)
 */

@Override
public JsonArray getTableImages(Integer servingTableLocId) throws Exception
{
	return servingTableDao.getTableImages(servingTableLocId);
}
@Override
public Integer delete(String id) throws Exception {
	Integer dlt,dltSync;
	
	final String where = "serving_table_location_id=" + id;
	dlt=servingTableDao.delete(where);
	
	dltSync=servingTableDao.deleteSyncQueueRecord(Integer.parseInt(id));
	
	 return dlt;
}

public Integer deleteServTable(String id) throws Exception
{
	final String where = "id=" + id;
	return super.delete(where);
}


	
}
