package com.indocosmo.mrp.web.masters.shops.shopdepartments.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;


public interface IShopDepartmentDao extends IGeneralDao<ShopDepartment>{
	public JsonArray getBomJsonArray(int id) throws Exception;
	public void deleteData(Integer idss) throws Exception;
	public JsonArray getDepdataByShopId(Integer id) throws Exception;
	public Integer DepData(Integer depId,Integer shopId) throws Exception;
	public Integer DbData(Integer shopId) throws Exception;
}
