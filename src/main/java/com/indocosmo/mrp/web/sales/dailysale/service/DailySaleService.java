package com.indocosmo.mrp.web.sales.dailysale.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.sales.dailysale.dao.DailySaleDao;
import com.indocosmo.mrp.web.sales.dailysale.model.DailySale;


public class DailySaleService extends MasterBaseService<DailySale,DailySaleDao> implements IDailySaleService{


	
	
	private DailySaleDao uomDao;

	public DailySaleService(ApplicationContext context) {
		super(context);
		uomDao=new DailySaleDao(getContext());
	}

	@Override
	public DailySaleDao getDao() {
		// TODO Auto-generated method stub
		return uomDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.service.IBaseService#getDao()
	 */
}
