package com.indocosmo.mrp.web.report.bomanalysisreport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.bomanalysisreport.dao.BomAnalysisReportDao;
import com.indocosmo.mrp.web.report.bomanalysisreport.model.BomAnalysisReportModel;


public class BomAnalysisReportService extends GeneralService<BomAnalysisReportModel, BomAnalysisReportDao> implements IBomAnalysisReportService{

	public BomAnalysisReportService(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BomAnalysisReportDao getDao() {
	
		// TODO Auto-generated method stub
		return null;
	}
	
}
