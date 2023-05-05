package com.indocosmo.mrp.web.stock.stockdisposal.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockdisposal.dao.StockDisposalDao;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;

public class StockDisposalService extends GeneralService<StockDisposal,StockDisposalDao> implements IStockDisposalService{

	private StockDisposalDao stockDisposalDao;
public StockDisposalService(ApplicationContext context) {
		super(context);
		stockDisposalDao=new StockDisposalDao(getContext());
	}




	@Override
	public Integer delete(String  where) throws Exception {
          where = "id="+where;
		
		return super.delete(where);

		}




	@Override
	public StockDisposalDao getDao() {
		return stockDisposalDao;
	}
	
	
	
	@Override
	public JsonArray getStockDispDtlData(StockDisposal stockDisp) throws Exception {
		
		return stockDisposalDao.getStockDispDtlData(stockDisp);
	}
}
