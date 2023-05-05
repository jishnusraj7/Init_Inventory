package com.indocosmo.mrp.web.production.bomanalysis.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.production.bomanalysis.dao.BomAnalysisDao;
import com.indocosmo.mrp.web.production.bomanalysis.model.BomAnalysis;


public interface IBomAnalysisService extends IGeneralService<BomAnalysis, BomAnalysisDao>{
	
	public JsonArray getItemData(String startDate, String endDate, String departmentId) throws Exception;
}
