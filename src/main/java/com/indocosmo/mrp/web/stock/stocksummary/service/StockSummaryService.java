package com.indocosmo.mrp.web.stock.stocksummary.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stocksummary.dao.StockSummaryDao;
import com.indocosmo.mrp.web.stock.stocksummary.model.StockSummary;

public class StockSummaryService extends GeneralService<StockSummary, StockSummaryDao> implements IStockSummaryService{

	private StockSummaryDao stockSummaryDao;

	public StockSummaryService(ApplicationContext context) {
		super(context);
		stockSummaryDao = new StockSummaryDao(getContext());
	}
	
	@Override
	public StockSummaryDao getDao() {
		
		return stockSummaryDao;
	}
	/*
	 * 
	 *  Done by anandu on 25-01-2020
	 *   
	 */	
     public JsonArray getgetItemCtegoryJsonArray() throws Exception {
		
		return stockSummaryDao.getItemCtegory();
	
	}

     /*
 	 * 
 	 *  Done by anandu on 25-01-2020
 	 *   
 	 */	
	public JsonArray getStockSummary(String date_from, String date_to, String department_id, String category_id) throws Exception {
		
		return stockSummaryDao.getStockSummary(date_from, date_to, department_id, category_id);
	}

}
