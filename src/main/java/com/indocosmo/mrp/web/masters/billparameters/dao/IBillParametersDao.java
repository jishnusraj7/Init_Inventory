package com.indocosmo.mrp.web.masters.billparameters.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;

public interface IBillParametersDao extends IGeneralDao<BillParameters>{
	public JsonArray getEditData()throws Exception;

}
