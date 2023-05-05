package com.indocosmo.mrp.web.masters.tax.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.tax.model.Tax;

public interface ITaxDao extends IGeneralDao<Tax>{
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Tax> getTaxData() throws Exception;
}
