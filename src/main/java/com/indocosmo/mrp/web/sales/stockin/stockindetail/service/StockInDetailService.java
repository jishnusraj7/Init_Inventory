package com.indocosmo.mrp.web.sales.stockin.stockindetail.service;


import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.sales.stockin.stockindetail.dao.StockInDetailDao;
import com.indocosmo.mrp.web.sales.stockin.stockindetail.model.StockInDetail;

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
}
