package com.indocosmo.mrp.web.report.prodbomreport.service;



import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.prodbomreport.dao.ProdBomReportDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;



public class ProdBomReportService extends GeneralService<ProdBomReportModel,ProdBomReportDao> implements IProdBomReportService{
	
	
	private ProdBomReportDao prodBomreportDao;

	
	public ProdBomReportService(ApplicationContext context) {
		super(context);
		prodBomreportDao = new ProdBomReportDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ProdBomReportDao getDao() {

		return prodBomreportDao;
	}
	




	

}
