package com.indocosmo.mrp.web.stock.stockadjustments.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;

public interface IStockAdjustmentDao extends IGeneralDao<StockAdjustment>{
	public JsonArray getStockAdjstDtlData(StockAdjustment stockAdjst) throws Exception;

}
