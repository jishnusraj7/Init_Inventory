package com.indocosmo.mrp.web.masters.supplier.country.dao;


import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.supplier.country.model.Country;


public class CountryDao extends GeneralDao<Country> implements ICountryDao{

	public CountryDao(ApplicationContext context) {
		super(context);
	}

	@Override
	protected String getTable() {
		return "countries";
	}

	@Override
	public Country getNewModelInstance() {
		return new Country();
	}
	
		
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable() +" where is_active=1";
		return getTableRowsAsJson(sql);
	}
}
