package com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.dao.StockTransDetailDao;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.model.StockTransDetail;


public class StockTransDetailService extends GeneralService<StockTransDetail,StockTransDetailDao> implements IStockTransDetailService{
	private StockTransDetailDao stockTransDetailDao;
	
	public StockTransDetailService(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
		stockTransDetailDao=new StockTransDetailDao(getContext());
	}

	@Override
	public StockTransDetailDao getDao() {
	
		// TODO Auto-generated method stub
		return stockTransDetailDao;
	}
	
	@Override
	public void delete(StockTransDetail item) throws Exception {
       final String where = "stock_transfer_hdr_id="+item.getStock_transfer_hdr_id();
		
		super.delete(where);
	}

	
}
