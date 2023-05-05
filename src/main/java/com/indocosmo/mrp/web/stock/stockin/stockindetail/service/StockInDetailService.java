package com.indocosmo.mrp.web.stock.stockin.stockindetail.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.dao.StockInDetailDao;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;

public class StockInDetailService extends GeneralService<StockInDetail,StockInDetailDao> implements IStockInDetailService{
	
	private StockInDetailDao stockInDtlDao;
	
	public StockInDetailService(ApplicationContext context) {
		
		super(context);
		stockInDtlDao = new StockInDetailDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockInDetailDao getDao() {
		
		return stockInDtlDao;
	}
		 
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo.mrp.web.core.base.model.ModelBase)
	 */
	@Override
	public void delete(StockInDetail item) throws Exception {
		
		final String where = "stock_in_hdr_id="+item.getStockInHdrId();
		super.delete(where);
	}

	/**
	 * @param stockinHdrId
	 * @return
	 */
	public ArrayList<StockInDetail> getstockInDtlData(Integer stockinHdrId) throws Exception{
		
		return stockInDtlDao.getstockInDtlData(stockinHdrId);
	}
}
