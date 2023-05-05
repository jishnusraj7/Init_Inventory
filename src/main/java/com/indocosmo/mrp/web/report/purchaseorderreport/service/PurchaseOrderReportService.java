package com.indocosmo.mrp.web.report.purchaseorderreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.purchaseorderreport.dao.PurchaseOrderReportDao;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;


public class PurchaseOrderReportService extends GeneralService<PurchaseOrderReportModel,PurchaseOrderReportDao> implements IPurchaseOrderReportService{
	
	
	private PurchaseOrderReportDao stockregisterreportDao;

	
	public PurchaseOrderReportService(ApplicationContext context) {
		super(context);
		stockregisterreportDao = new PurchaseOrderReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public PurchaseOrderReportDao getDao() {

		return stockregisterreportDao;
	}
	

	public List<PurchaseOrderReportModel> getreportDetails(PurchaseOrderReportModel pohdr) throws Exception {
		
		return stockregisterreportDao.getreportDetails(pohdr);
	}

}
