package com.indocosmo.mrp.web.report.stockexcisereport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stockexcisereport.dao.StockExciseReportDao;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;

/*
 * 
 *  Done by anandu on 21-01-2020 
 *  
 */

public class StockExciseReportService extends GeneralService<StockExciseReport, StockExciseReportDao>
		implements IStockExciseReportService {

	private StockExciseReportDao stockExcisereportDao;

	public StockExciseReportService(ApplicationContext context) {
		super(context);
		stockExcisereportDao = new StockExciseReportDao(getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockExciseReportDao getDao() {

		return stockExcisereportDao;
	}


	public List<StockExciseReport> getReportResult(StockExciseReport stockExciseReport) throws Exception {

		return stockExcisereportDao.getReportResult(stockExciseReport);
	}

	public String getBTLSize(String uomcode) throws Exception {
		
		return stockExcisereportDao.getBtlSize(uomcode);
		// TODO Auto-generated method stub
		
	}

	

}