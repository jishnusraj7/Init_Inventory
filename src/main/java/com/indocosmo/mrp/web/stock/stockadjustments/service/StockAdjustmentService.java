package com.indocosmo.mrp.web.stock.stockadjustments.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockadjustments.dao.StockAdjustmentDao;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;

public class StockAdjustmentService extends GeneralService<StockAdjustment,StockAdjustmentDao> implements IStockAdjustmentService{

	private StockAdjustmentDao stockAdjustmentDao;
public StockAdjustmentService(ApplicationContext context) {
		super(context);
		stockAdjustmentDao=new StockAdjustmentDao(getContext());
	}




	@Override
	public Integer delete(String  where) throws Exception {
          where = "id="+where;
		
		return super.delete(where);

		}




	@Override
	public StockAdjustmentDao getDao() {
		return stockAdjustmentDao;
	}
	
	
	
	@Override
	public JsonArray getStockAdjstDtlData(StockAdjustment stockAdjst) throws Exception {
		
		return stockAdjustmentDao.getStockAdjstDtlData(stockAdjst);
	}
}
