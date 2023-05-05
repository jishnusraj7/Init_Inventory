package com.indocosmo.mrp.web.report.stockoutreport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stockoutreport.dao.StockoutReportDao;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;
import java.util.List;

public class StockoutReportService extends GeneralService<StockoutReportModel, StockoutReportDao>{

	private StockoutReportDao stockoutReportDao;
	
	
	public StockoutReportService(ApplicationContext context) {
		super(context);
		stockoutReportDao=new StockoutReportDao(getContext());
		
	}

	@Override
	public StockoutReportDao getDao() {
		return stockoutReportDao;
	}
	
	
public List<StockoutReportModel> getreportDetails(StockoutReportModel stkoutrpt) throws Exception {
		
		return stockoutReportDao.getreportDetails(stkoutrpt);
	}	
	


}
