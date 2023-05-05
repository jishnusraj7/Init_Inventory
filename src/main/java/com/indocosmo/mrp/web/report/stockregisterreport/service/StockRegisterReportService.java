package com.indocosmo.mrp.web.report.stockregisterreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.report.stockregisterreport.dao.StockRegisterReportDao;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;


public class StockRegisterReportService extends GeneralService<StockRegisterReport,StockRegisterReportDao> implements IStockRegisterReportService{
	
	
	private StockRegisterReportDao stockregisterreportDao;

	
	public StockRegisterReportService(ApplicationContext context) {
		super(context);
		stockregisterreportDao = new StockRegisterReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockRegisterReportDao getDao() {

		return stockregisterreportDao;
	}
	

	/*public Boolean getreportDetails(String where ) throws Exception {
		
		return stockregisterreportDao.getreportDetails(where);
	}*/


	public List<StockRegisterReport> getReportResult(StockRegisterReport stockRegisterReport) throws Exception {
		
		return stockregisterreportDao.getReportResult(stockRegisterReport);
	}


	public List<ItemMaster> getItembyCategory(String itmctgry) throws Exception {
		
		return stockregisterreportDao.getItembyCategory(itmctgry);
	}

}