package com.indocosmo.mrp.web.stock.stockin.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;


public interface IStockInService extends IGeneralService<StockIn,StockInDao>{

	/**
	 * @param stockin
	 * @throws Exception
	 */
	public void updateAftrFinalize(StockIn stockin) throws Exception;
	public JsonArray getStockInDtlData(StockIn stockIn) throws Exception;
	public Double getCurrentStockCount(Integer itemCode, Integer departId) throws Exception;
	
}
