package com.indocosmo.mrp.web.stock.stockout.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockout.dao.StockOutDao;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;


public class StockOutService extends GeneralService<StockOut,StockOutDao> implements IStockOutService{


	private StockOutDao stockOutDao;

	/**
	 * @param context
	 */
	public StockOutService(ApplicationContext context) {

		super(context);
		stockOutDao = new StockOutDao(getContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockOutDao getDao() {

		return stockOutDao;
	}

	public StockOut saveStockItem(StockOut stockOut) throws Exception {
		stockOutDao.save(stockOut);

		return stockOut;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockout.service.IStockOutService#updateStockOutStatus(com.indocosmo.mrp.web.stock.stockout.model.StockOut)
	 */
	@Override
	public StockOut updateStockOutStatus(StockOut stockout) throws Exception {

		return stockOutDao.updateStockOutStatus(stockout);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		where = "id="+where;

		return super.delete(where);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockout.service.IStockOutService#getRequestDataJsonArray()
	 */
	@Override
	public JsonArray getRequestDataJsonArray() throws Exception {

		return stockOutDao.getTableRequestDataRowsAsJson();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockout.service.IStockOutService#getStockOutDtlData(com.indocosmo.mrp.web.stock.stockout.model.StockOut)
	 */
	public JsonArray getStockOutDtlData(StockOut stockOut) throws Exception {

		return stockOutDao.getStockOutDtlData(stockOut);
	}

	/**
	 * @param stockInDetail
	 * @return
	 * @throws Exception 
	 */
	public StockInDetail getItemTaxDtl(StockInDetail stockInDetail) throws Exception {

		return stockOutDao.getItemTaxDtl(stockInDetail);
	}

	/**
	 * @param stockoutHdrId
	 * @return
	 * @throws Exception 
	 */
	public StockOut getStockOutData(Integer stockoutHdrId) throws Exception {

		return stockOutDao.getStockOutData(stockoutHdrId);
	}

	/**
	 * @param stockOut
	 * @throws Exception 
	 */
	public void updateStockOutPrintStatus(StockOut stockOut) throws Exception {

		stockOutDao.updateStockOutPrintStatus(stockOut);
	}

	/**
	 * @param datatableParameters
	 * @return
	 * @throws Exception
	 */
	public JsonObject getStockOutRequestTableJsonArray(DataTableCriterias datatableParameters) throws Exception{
		return stockOutDao.getStockOutRequestTableJsonArray(datatableParameters);
	}

	public Double getCurrstock(String itemId , String current_date , String department_id)  throws Exception {
	
		// TODO Auto-generated method stub
		return stockOutDao.getCurrstock(itemId,current_date,department_id);
	}
	
	public JsonArray getPendingStockOutDtlData(StockOut stockOut,String deptId,String  itmsIdArray) throws Exception {

		return stockOutDao.getPendingStockOutDtlData(stockOut,deptId,itmsIdArray);
	}
	
	
	public Double getPendingStockByDeptAndItem(String itemId , String department_id)  throws Exception {
		
		// TODO Auto-generated method stub
		return stockOutDao.getPendingStockByDeptAndItem(itemId,department_id);
	}

}
