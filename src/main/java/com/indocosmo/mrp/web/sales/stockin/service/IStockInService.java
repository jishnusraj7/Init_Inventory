package com.indocosmo.mrp.web.sales.stockin.service;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.sales.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.sales.stockin.model.StockIn;



public interface IStockInService extends IGeneralService<StockIn,StockInDao>{

	/**
	 * @param stockin
	 * @throws Exception
	 */
	public int saveStockIn() throws Exception;
	
}
