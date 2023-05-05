package com.indocosmo.mrp.web.sales.stockin.service;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.sales.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.sales.stockin.model.StockIn;

public class StockInService extends GeneralService<StockIn,StockInDao> implements IStockInService{

	private StockInDao stockInDao;
	
	/**
	 * @param context
	 */
	public StockInService(ApplicationContext context) {
		
		super(context);
		stockInDao=new StockInDao(getContext());
	}

	@Override
	public StockInDao getDao() {
		
		return stockInDao;
	}
	
	public int saveStockIn() throws Exception{
		return stockInDao.saveStockIn();
	}

}
