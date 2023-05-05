package com.indocosmo.mrp.web.masters.envsettings.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;

public class EnvSerttingsDao extends GeneralDao<EnvSerttings> implements IEnvSerttingsDao{

	public EnvSerttingsDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public EnvSerttings getNewModelInstance() {
		return new EnvSerttings();
	}

	@Override
	public String getTable() {
		return "env_settings";
	}
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
	public JsonArray getEditData() throws Exception
	{
  final String sql="SELECT id,is_publish from "+getTable()+" ";
		
		return getTableRowsAsJson(sql);
	}

	
}
