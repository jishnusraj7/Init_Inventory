package com.indocosmo.mrp.web.masters.itemclass.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.itemclass.model.ItemClass;


public interface IItemClassDao extends IMasterBaseDao<ItemClass>{
	public List<ItemClass> getItemClassData() throws Exception;
	public JsonArray getSuperClassList() throws Exception ;

}
