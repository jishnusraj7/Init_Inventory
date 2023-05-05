package com.indocosmo.mrp.web.report.profitsummary.service;



import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.prodbomreport.dao.ProdBomReportDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.profitsummary.dao.ProfitSummaryDao;
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;



public class ProfitSummaryService extends GeneralService<ProfitSummaryModel,ProfitSummaryDao> implements IProfitSummaryService{
	
	
	private ProfitSummaryDao profitSummaryDao;

	
	public ProfitSummaryService(ApplicationContext context) {
		super(context);
		profitSummaryDao = new ProfitSummaryDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ProfitSummaryDao getDao() {

		return profitSummaryDao;
	}
	




	

}
