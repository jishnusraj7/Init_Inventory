package com.indocosmo.mrp.web.masters.billparameters.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.billparameters.dao.BillParametersDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;

public class BillParametersService extends GeneralService<BillParameters, BillParametersDao>{

	private BillParametersDao billParametersDao;
	public BillParametersService(ApplicationContext context) {
		super(context);
		billParametersDao=new BillParametersDao(getContext());
	}
	@Override
	public BillParametersDao getDao() {
		return billParametersDao;
	}
	
	public BillParameters saveItem(BillParameters BillParameters) throws Exception {
		billParametersDao.save(BillParameters);
		
		return BillParameters;
	}

	public JsonArray getEditData()throws Exception{
		return billParametersDao.getEditData();
	}
}
