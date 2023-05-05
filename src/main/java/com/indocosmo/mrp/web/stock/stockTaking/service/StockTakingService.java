package com.indocosmo.mrp.web.stock.stockTaking.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockTaking.dao.StockTakingDao;
import com.indocosmo.mrp.web.stock.stockTaking.modal.StockTaking;

public class StockTakingService extends GeneralService<StockTaking,StockTakingDao>{

	private StockTakingDao stockingDao;
	
	public StockTakingService(ApplicationContext currentContext) {
		// TODO Auto-generated constructor stub
		super(currentContext);
		stockingDao=new StockTakingDao(getContext());
	}

	@Override
	public StockTakingDao getDao() {
		// TODO Auto-generated method stub
		return stockingDao;
	}
	
	/*
	 * 
	 *  Done by anandu on 22-01-2020
	 *   
	 */	
     public JsonArray getgetItemCtegoryJsonArray() throws Exception {
		
		return stockingDao.getItemCtegory();
	
	}

     /*
 	 * 
 	 *  Done by anandu on 22-01-2020
 	 *   
 	 */	
	public JsonArray getTableJsonArray(String selDate,int dept_id,int cat_id) throws Exception {
		
		return stockingDao.getDataTableList(selDate,dept_id,cat_id);
	
	}
}
