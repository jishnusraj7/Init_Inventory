package com.indocosmo.mrp.web.masters.taxparam.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;

public interface ITaxParamDao extends IGeneralDao<TaxParam>{
	public JsonArray getEditData()throws Exception;

}
