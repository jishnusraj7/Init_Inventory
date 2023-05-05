package com.indocosmo.mrp.web.masters.tax.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.tax.dao.TaxDao;
import com.indocosmo.mrp.web.masters.tax.model.Tax;

public interface ITaxService extends IGeneralService<Tax, TaxDao> {
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Tax> getTaxData() throws Exception;
}
