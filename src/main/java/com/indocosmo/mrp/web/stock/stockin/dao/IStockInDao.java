package com.indocosmo.mrp.web.stock.stockin.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;


public interface IStockInDao extends IGeneralDao<StockIn>{
		
	public void updateAftrFinalize(StockIn stockin) throws Exception;
	
	public JsonArray getStockInDtlData(StockIn stockIn) throws Exception;	
	public JsonArray getStockregData(Integer id) throws Exception;	
	public JsonArray getStockregdtlData(Integer id) throws Exception;	
	public Double getLatestCostPrice(Integer stockId,Integer depId) throws Exception;
	public Double getCurrentStockCount(Integer stockId,Integer depId) throws Exception;
}
