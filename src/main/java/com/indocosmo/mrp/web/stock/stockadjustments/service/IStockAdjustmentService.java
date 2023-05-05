package com.indocosmo.mrp.web.stock.stockadjustments.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.stockadjustments.dao.StockAdjustmentDao;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;

public interface IStockAdjustmentService extends IGeneralService<StockAdjustment,StockAdjustmentDao> {
	public JsonArray getStockAdjstDtlData(StockAdjustment stockAdjst) throws Exception;

}
