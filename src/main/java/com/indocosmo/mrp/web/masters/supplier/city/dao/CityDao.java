package com.indocosmo.mrp.web.masters.supplier.city.dao;


import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.supplier.city.model.City;


public class CityDao extends GeneralDao<City> implements ICityDao{


	public CityDao(ApplicationContext context) {
		super(context);
	}

	@Override
	protected String getTable() {
		return "cities";
	}
	@Override
	public City getNewModelInstance() {
		return new City();
	}
	
	public JsonArray getJsonArrayCity(String state_id) throws Exception {
		final String sql="SELECT * FROM " + getTable() +" where state_id='"+state_id+"'";
		return getTableRowsAsJson(sql);
	}
}
