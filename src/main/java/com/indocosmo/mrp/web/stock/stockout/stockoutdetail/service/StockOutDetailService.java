package com.indocosmo.mrp.web.stock.stockout.stockoutdetail.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao.StockOutDetailDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;


public class StockOutDetailService extends GeneralService<StockOutDetail,StockOutDetailDao> implements IStockOutDetailService{

	
	private StockOutDetailDao stockOutDtlDao;
	
	/**
	 * @param context
	 */
	public StockOutDetailService(ApplicationContext context) {
		super(context);
		stockOutDtlDao = new StockOutDetailDao(getContext());
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockOutDetailDao getDao() {
		
		return stockOutDtlDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo.mrp.web.core.base.model.ModelBase)
	 */
	@Override
	public void delete(StockOutDetail item) throws Exception {
		
		final String where = "stock_out_hdr_id="+item.getStockOutHdrId();
		
		super.delete(where);
	}

	/**
	 * @param stockoutHdrId
	 * @return
	 * @throws Exception 
	 */
	public List<StockOutDetail> getStockOutDtlData(Integer stockoutHdrId) throws Exception {
		
		return stockOutDtlDao.getStockOutDtlData(stockoutHdrId);
	}
	//Added By Udhay on  31st Aug 2021 for StockOut Data Issue
	public Double getUnitPrice(String item_code) throws Exception {
		// TODO Auto-generated method stub
		return stockOutDtlDao.getUnitPrice(item_code);
	}
}
