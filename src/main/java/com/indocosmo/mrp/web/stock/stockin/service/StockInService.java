package com.indocosmo.mrp.web.stock.stockin.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;

public class StockInService extends GeneralService<StockIn,StockInDao> implements IStockInService{

	private StockInDao stockInDao;
	
	/**
	 * @param context
	 */
	public StockInService(ApplicationContext context) {
		
		super(context);
		stockInDao=new StockInDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockInDao getDao() {
		
		return stockInDao;
	}
	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		
		where = "id="+where;		
		return super.delete(where);
	}
	
	public void updateAftrFinalize(StockIn stockin) throws Exception{
		
		stockInDao.updateAftrFinalize(stockin);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockin.service.IStockInService#getStockInDtlData(com.indocosmo.mrp.web.stock.stockin.model.StockIn)
	 */
	@Override
	public JsonArray getStockInDtlData(StockIn stockIn) throws Exception {
		
		return stockInDao.getStockInDtlData(stockIn);
	}
	
	/**
	 * @param stockIn
	 * @return
	 * @throws Exception 
	 */
	public StockIn saveStockItem(StockIn stockIn) throws Exception {
		
		stockInDao.save(stockIn);		
		return stockIn;
	}

	/**
	 * @param stockIn
	 * @return
	 * @throws Exception
	 */
	public StockIn getInterStoreSupplier(StockIn stockIn) throws Exception {
		
		return stockInDao.getInterStoreSupplier(stockIn);
	}

	/**
	 * @param stockinHdrId
	 * @return
	 * @throws Exception 
	 */
	public StockIn getStockInData(Integer stockinHdrId) throws Exception {
		
		return stockInDao.getStockInData(stockinHdrId);
	}


	/**
	 * @param id
	 * @throws Exception 
	 */
	public void updatePOStatus(String id) throws Exception {
		// TODO Auto-generated method stub
		 stockInDao.updatePOStatus(id);
	}


	public String getSupplierAddress(Integer supllierId) throws Exception {
	
	
		return stockInDao.getSupplierAddress(supllierId);
	}


	public JsonArray getStockItemData() throws Exception {
	
		// TODO Auto-generated method stub
		return stockInDao.getStockItemData();
	}

	public JsonArray getItemData() throws Exception {
		
		// TODO Auto-generated method stub
		return stockInDao.getItemData();
	}


	public JsonArray getItemStockData(String itemCode, String itemName) throws Exception {
		// TODO Auto-generated method stub
		return stockInDao.getItemStockData(itemCode,itemName);
	}


	public JsonArray getPunitData(String uomCode) throws Exception {
		// TODO Auto-generated method stub
		return stockInDao.getPunitData(uomCode);
	}


	public JsonArray getCategory() throws Exception {		
		return stockInDao.getCategory();
	}


	public JsonArray getReportData() throws Exception {
		// TODO Auto-generated method stub
		return stockInDao.getReportData();
	}	
		public Double getCurrentStockCount(Integer stockId, Integer departId) throws Exception {
		// TODO Auto-generated method stub
		return stockInDao.getCurrentStockCount(stockId,departId);
}
}