package com.indocosmo.mrp.web.report.stockoutreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.report.stockoutreport.dao.StockoutReportDao;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;

public interface IStockoutReportService  extends IGeneralService<StockoutReportModel, StockoutReportDao>{

	public List<StockoutReportModel> getreportDetails(StockoutReportModel stkoutrpt) throws Exception;

}
