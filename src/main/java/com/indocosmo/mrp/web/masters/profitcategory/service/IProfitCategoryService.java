package com.indocosmo.mrp.web.masters.profitcategory.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.profitcategory.dao.ProfitCategoryDao;
import com.indocosmo.mrp.web.masters.profitcategory.model.ProfitCategory;

public interface IProfitCategoryService extends IMasterBaseService<ProfitCategory,ProfitCategoryDao>{
	public JsonArray getHqProfitcategoryImportList() throws Exception ;
	public JsonArray getHqProfitcategoryImportUpdatedList() throws Exception ;
	public List<ProfitCategory> getDataToImport()throws Exception;
	public List<ProfitCategory> getUpdatedDataToImport()throws Exception;
}
