package com.indocosmo.mrp.web.report.stockinreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stockinreport.dao.StockinReportDao;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;


public class StockinReportService extends GeneralService<StockinReportModel,StockinReportDao> implements IStockinReportService{
	
	
	private StockinReportDao stockregisterreportDao;

	
	public StockinReportService(ApplicationContext context) {
		super(context);
		stockregisterreportDao = new StockinReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public StockinReportDao getDao() {

		return stockregisterreportDao;
	}
	

	public List<StockinReportModel> getreportDetails(StockinReportModel pohdr) throws Exception {
		
		return stockregisterreportDao.getreportDetails(pohdr);
	}

}
