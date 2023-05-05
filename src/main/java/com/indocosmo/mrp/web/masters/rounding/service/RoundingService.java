package com.indocosmo.mrp.web.masters.rounding.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.rounding.dao.RoundingDao;
import com.indocosmo.mrp.web.masters.rounding.model.Rounding;

/**
 * @author jo
 *
 */
public class RoundingService extends MasterBaseService<Rounding, RoundingDao> implements IRoundingService {
	
	private RoundingDao roundingDao;
	
	/**
	 * @param context
	 */
	public RoundingService(ApplicationContext context) {
	
		super(context);
		roundingDao = new RoundingDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public RoundingDao getDao() {
	
		return roundingDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.rounding.service.IRoundingService#
	 * getEditDetails(int) */
	public JsonArray getEditDetails(int id) throws Exception {
	
		return roundingDao.getEditDetails(id);
		
	}
	
}
