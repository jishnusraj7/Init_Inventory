package com.indocosmo.mrp.web.report.bomratecomparison.service;



import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.bomratecomparison.dao.BomRateComparisonDao;
import com.indocosmo.mrp.web.report.bomratecomparison.model.BomRateComparison;



public class BomRateComparisonService extends GeneralService<BomRateComparison,BomRateComparisonDao> implements IBomRateComparisonService{
	
	
	private BomRateComparisonDao bomRateComparisonDao;

	
	public BomRateComparisonService(ApplicationContext context) {
		super(context);
		bomRateComparisonDao = new BomRateComparisonDao(getContext());
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public BomRateComparisonDao getDao() {

		return bomRateComparisonDao;
	}
	




	

}
