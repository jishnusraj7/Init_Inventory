package com.indocosmo.mrp.web.masters.rounding.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.rounding.dao.RoundingDao;
import com.indocosmo.mrp.web.masters.rounding.model.Rounding;

public interface IRoundingService extends IMasterBaseService<Rounding,RoundingDao> {
	public JsonArray getEditDetails(int id) throws Exception;

}
