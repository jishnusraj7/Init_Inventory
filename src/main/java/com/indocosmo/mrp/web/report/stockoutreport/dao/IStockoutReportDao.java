package com.indocosmo.mrp.web.report.stockoutreport.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;

public interface IStockoutReportDao extends IGeneralDao<StockoutReportModel> {
	
	public List<StockoutReportModel> getreportDetails(StockoutReportModel stkoutrpt) throws Exception;

}
