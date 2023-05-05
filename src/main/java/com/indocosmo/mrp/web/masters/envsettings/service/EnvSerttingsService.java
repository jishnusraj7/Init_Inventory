package com.indocosmo.mrp.web.masters.envsettings.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.envsettings.dao.EnvSerttingsDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;

public class EnvSerttingsService extends GeneralService<EnvSerttings, EnvSerttingsDao>{

	private EnvSerttingsDao envSerttingsDao;
	public EnvSerttingsService(ApplicationContext context) {
		super(context);
		envSerttingsDao=new EnvSerttingsDao(getContext());
	}
	@Override
	public EnvSerttingsDao getDao() {
		return envSerttingsDao;
	}
	
	public EnvSerttings saveItem(EnvSerttings EnvSerttings) throws Exception {
		envSerttingsDao.save(EnvSerttings);
		
		return EnvSerttings;
	}

	public JsonArray getEditData()throws Exception{
		return envSerttingsDao.getEditData();
	}
}
