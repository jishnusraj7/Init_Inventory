package com.indocosmo.mrp.web.masters.taxparam.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.taxparam.dao.TaxParamDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;

public interface ITaxParamService extends IGeneralService<TaxParam, TaxParamDao> {

	public JsonArray getEditData()throws Exception;
	
}
