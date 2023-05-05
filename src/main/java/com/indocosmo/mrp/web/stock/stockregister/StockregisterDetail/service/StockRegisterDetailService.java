package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;

public class StockRegisterDetailService extends GeneralService<StockRegisterDetail,StockRegisterDetailDao> implements IStockRegisterDetailService{

	
	private StockRegisterDetailDao stockeregisterDetailDao;
	
	/**
	 * @param context
	 */
	public StockRegisterDetailService(ApplicationContext context) {
		super(context);
		stockeregisterDetailDao = new StockRegisterDetailDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockRegisterDetailDao getDao() {
		
		return stockeregisterDetailDao;
	}
	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.IStockRegisterDetailService#getOutQtyInRegDtl(com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail)
	 */
	@Override
	public Double getOutQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception {
		
		return stockeregisterDetailDao.getOutQtyInRegDtl(stockRegDtl);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.IStockRegisterDetailService#getInQtyInRegDtl(com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail)
	 */
	@Override
	public Double getInQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception {
		
		return stockeregisterDetailDao.getInQtyInRegDtl(stockRegDtl);
	}
	
	
	@Override
	public void delete(StockRegisterDetail item) throws Exception {
		final String where="stock_register_hdr_id="+item.getStockRegHdrid();
		super.delete(where);
	}
	
}
