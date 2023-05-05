package com.indocosmo.mrp.web.masters.itemclass.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.itemclass.dao.ItemClassDao;
import com.indocosmo.mrp.web.masters.itemclass.model.ItemClass;

/**
 * @author jo
 *
 */
public interface IItemClassService extends IMasterBaseService<ItemClass, ItemClassDao> {
	
	public JsonArray getDepartmentImportList() throws Exception;
	
	public JsonArray getDepartmentImportUpdatedList() throws Exception;
	
	public List<ItemClass> getDataToImport() throws Exception;
	
	public List<ItemClass> getUpdatedDataToImport() throws Exception;
	
	public List<ItemClass> getItemClassData() throws Exception;
	
	public JsonArray getSuperClassList() throws Exception;
}
