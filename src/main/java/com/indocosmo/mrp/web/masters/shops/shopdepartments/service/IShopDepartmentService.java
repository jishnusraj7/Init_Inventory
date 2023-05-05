package com.indocosmo.mrp.web.masters.shops.shopdepartments.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.ShopDepartmentDao;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;


public interface IShopDepartmentService extends IGeneralService<ShopDepartment,ShopDepartmentDao>{
	public JsonArray getBomJsonArray(int id)throws Exception;
	public List<ShopDepartment> getDataToImport()throws Exception;
	public JsonArray getDepdataByShopId(Integer id) throws Exception;
	public Integer DepData(Integer depId,Integer shopId) throws Exception;
	public Integer DbData(Integer shopId) throws Exception;
}
