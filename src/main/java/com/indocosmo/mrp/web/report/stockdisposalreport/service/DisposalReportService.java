package com.indocosmo.mrp.web.report.stockdisposalreport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.productionreport.dao.ProductionReportDao;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.stockdisposalreport.dao.DisposalReportDao;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;


public class DisposalReportService extends GeneralService<DisposalReportModel,DisposalReportDao> implements IDisposalReportService{
	
	
	private DisposalReportDao stockregisterreportDao;

	
	public DisposalReportService(ApplicationContext context) {
		super(context);
		stockregisterreportDao = new DisposalReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public DisposalReportDao getDao() {

		return stockregisterreportDao;
	}
	




	

}
