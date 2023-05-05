package com.indocosmo.mrp.web.production.planning.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;
import com.indocosmo.mrp.web.production.planning.model.Planning;



public interface IPlanningService extends IGeneralService<Planning,PlanningDao>{
	public  JsonArray getCustomerDataAsJson() throws Exception ;
}
