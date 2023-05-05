package com.indocosmo.mrp.web.masters.itemcategory.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.itemcategory.dao.ItemCategoryDao;
import com.indocosmo.mrp.web.masters.itemcategory.model.ItemCategory;


public interface IItemCategoryService extends IMasterBaseService<ItemCategory,ItemCategoryDao>{

	public JsonArray getHqItemCategoryImportList() throws Exception;
	public JsonArray getHqItemCategoryImportUpdatedList() throws Exception;
	public List<ItemCategory> getDataToImport()throws Exception;
	public List<ItemCategory> getUpdatedDataToImport()throws Exception;
	

}
