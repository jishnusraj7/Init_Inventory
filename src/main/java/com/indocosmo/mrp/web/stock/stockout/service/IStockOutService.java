package com.indocosmo.mrp.web.stock.stockout.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.stockout.dao.StockOutDao;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;


public interface IStockOutService extends IGeneralService<StockOut,StockOutDao>{

	/**
	 * @param stockout
	 * @return
	 * @throws Exception
	 */
	public	StockOut updateStockOutStatus(StockOut stockout) throws Exception;

	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getRequestDataJsonArray() throws Exception;

	/**
	 * @param stockOut
	 * @return
	 * @throws Exception
	 */
	public JsonArray getStockOutDtlData(StockOut stockOut) throws Exception;


	
}
