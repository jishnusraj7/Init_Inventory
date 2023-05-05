package com.indocosmo.mrp.web.masters.envsettings.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;

public interface IEnvSerttingsDao extends IGeneralDao<EnvSerttings>{
	public JsonArray getEditData()throws Exception;

}
