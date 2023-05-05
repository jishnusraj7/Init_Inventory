package com.indocosmo.mrp.web.production.bomanalysis.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.bomanalysis.model.BomAnalysis;


public interface IBomAnalysisDao extends IGeneralDao<BomAnalysis>{
	
	public JsonArray getItemData(String startDate, String endDate, String departmentId) throws Exception;
}
