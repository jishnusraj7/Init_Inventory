package com.indocosmo.mrp.web.report.stockregisterdetailsreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.dao.StockRegisterDetailReportDao;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.model.StockRegisterDetailReport;
import com.indocosmo.mrp.web.report.stockregisterreport.dao.StockRegisterReportDao;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;

public class StockRegisterDetailReportService extends GeneralService<StockRegisterDetailReport,StockRegisterDetailReportDao> implements IStockRegisterDetailReportService{

	private StockRegisterDetailReportDao stockRegisterDetailReportDao;
	
	public StockRegisterDetailReportService(ApplicationContext context) {
		
		super(context);
		stockRegisterDetailReportDao = new StockRegisterDetailReportDao(getContext());
	}

	@Override
	public StockRegisterDetailReportDao getDao() {
		// TODO Auto-generated method stub
		return stockRegisterDetailReportDao;
	}

	public List<StockRegisterDetailReport> getReportResult(StockRegisterDetailReport stockRegisterDetailsReport) throws Exception {
		// TODO Auto-generated method stub
		return stockRegisterDetailReportDao.getReportResult(stockRegisterDetailsReport);
	}

	
}
