package com.indocosmo.mrp.web.report.stockadjustmentsummary.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;

public interface IStockAdjustmentReportDao extends IGeneralDao<StockAdjustmentReport>{

	JsonArray getStockAdjustmentDetails(StockAdjustmentReport stockAdjustment) throws Exception;

}
