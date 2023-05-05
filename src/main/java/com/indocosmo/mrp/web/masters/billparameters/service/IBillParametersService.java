package com.indocosmo.mrp.web.masters.billparameters.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.billparameters.dao.BillParametersDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;

public interface IBillParametersService extends IGeneralService<BillParameters, BillParametersDao> {

	public JsonArray getEditData()throws Exception;
	
}
