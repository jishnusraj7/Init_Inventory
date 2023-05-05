package com.indocosmo.mrp.web.masters.supplier.state.dao;


import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.supplier.state.model.State;


public class StateDao extends GeneralDao<State> implements IStateDao{

	
	
	public StateDao(ApplicationContext context) {
		super(context);
	}


	@Override
	protected String getTable() {
		return "states";
	}

	@Override
	public State getNewModelInstance() {
		return new State();
	}
		
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable() +" where is_active=1";
		return getTableRowsAsJson(sql);
	}
	
}
