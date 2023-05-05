package com.indocosmo.mrp.web.masters.rounding.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.rounding.model.Rounding;

public interface IRoundingDao extends IMasterBaseDao<Rounding> {
	public JsonArray getEditDetails(int id) throws Exception;

}
