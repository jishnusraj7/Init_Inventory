package com.indocosmo.mrp.web.stock.stockregister.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockregister.dao.StockRegisterDao;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;

public class StockRegisterService extends GeneralService<StockRegister, StockRegisterDao> implements
		IStockRegisterService {
	
	private StockRegisterDao stockRegisterDao;
	
	/**
	 * @param context
	 */
	public StockRegisterService(ApplicationContext context) {
	
		super(context);
		stockRegisterDao = new StockRegisterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public StockRegisterDao getDao() {
	
		return stockRegisterDao;
	}
	
	/**
	 * @param stockReg
	 * @return
	 * @throws Exception
	 */
	public StockRegister saveStockRegData(StockRegister stockReg) throws Exception {
	
		stockRegisterDao.save(stockReg);
		
		return stockReg;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	@Override
	public void delete(StockRegister item) throws Exception {
	
		// final String where="ext_ref_id="+item.getExtRefId();
		final String where = "ext_ref_no='" + item.getExtRefNo() + "'";
		super.delete(where);
	}
	
	/**
	 * @param item
	 * @throws Exception
	 */
	public void deleteStockReg(StockRegister item) throws Exception {
	
		final String where = "ext_ref_no='" + item.getExtRefNo() + "'";
		super.delete(where);
		
	}
	
}
