package com.indocosmo.mrp.web.report.stockinreport.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;


public interface IStockinReportDao extends IGeneralDao<StockinReportModel>{

	public List<StockinReportModel> getreportDetails(StockinReportModel pohdr) throws Exception;
	
}
