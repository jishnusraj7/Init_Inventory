package com.indocosmo.mrp.web.masters.envsettings.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.envsettings.dao.EnvSerttingsDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;

public interface IEnvSerttingsService extends IGeneralService<EnvSerttings, EnvSerttingsDao> {

	public JsonArray getEditData()throws Exception;
	
}
