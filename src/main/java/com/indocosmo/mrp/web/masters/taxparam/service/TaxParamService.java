package com.indocosmo.mrp.web.masters.taxparam.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.taxparam.dao.TaxParamDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;

public class TaxParamService extends GeneralService<TaxParam, TaxParamDao>{

	private TaxParamDao taxParamDao;
	public TaxParamService(ApplicationContext context) {
		super(context);
		taxParamDao=new TaxParamDao(getContext());
	}
	@Override
	public TaxParamDao getDao() {
		return taxParamDao;
	}
	
	public TaxParam saveItem(TaxParam taxParam) throws Exception {
		taxParamDao.save(taxParam);
		
		return taxParam;
	}

	public JsonArray getEditData()throws Exception{
		return taxParamDao.getEditData();
	}
}
