package com.indocosmo.mrp.web.masters.shops.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.shops.dao.ShopsDao;
import com.indocosmo.mrp.web.masters.shops.model.Shops;


public interface IShopsService extends IGeneralService<Shops,ShopsDao>{
	public JsonArray getDepartmentImportList() throws Exception ;
	public JsonArray getDepartmentImportUpdatedList() throws Exception ;
	public List<Shops> getDataToImport()throws Exception;
	public List<Shops> getUpdatedDataToImport()throws Exception;
	public JsonArray getShopDataById(int id) throws Exception;
	public JsonArray getAreaByShopId(int id) throws Exception;
	
}
