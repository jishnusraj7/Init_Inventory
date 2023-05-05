package com.indocosmo.mrp.web.masters.reasons.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.reasons.dao.ReasonsDao;
import com.indocosmo.mrp.web.masters.reasons.model.Reasons;


public interface IReasonsService extends IGeneralService<Reasons,ReasonsDao>{
	public JsonArray getDepartmentImportList() throws Exception ;
	public JsonArray getDepartmentImportUpdatedList() throws Exception ;
	public List<Reasons> getDataToImport()throws Exception;
	public List<Reasons> getUpdatedDataToImport()throws Exception;
	
	
}
