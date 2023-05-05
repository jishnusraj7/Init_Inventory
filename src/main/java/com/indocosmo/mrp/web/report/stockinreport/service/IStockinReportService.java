package com.indocosmo.mrp.web.report.stockinreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.report.stockinreport.dao.StockinReportDao;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;


public interface IStockinReportService extends IGeneralService<StockinReportModel,StockinReportDao>{

	public List<StockinReportModel> getreportDetails(StockinReportModel pohdr) throws Exception;
	
}
