package com.indocosmo.mrp.web.report.productionreport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.productionreport.dao.ProductionReportDao;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;


public class ProductionReportService extends GeneralService<ProductionReportModel,ProductionReportDao> implements IProductionReportService{
	
	
	private ProductionReportDao stockregisterreportDao;

	
	public ProductionReportService(ApplicationContext context) {
		super(context);
		stockregisterreportDao = new ProductionReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ProductionReportDao getDao() {

		return stockregisterreportDao;
	}
	




	

}
