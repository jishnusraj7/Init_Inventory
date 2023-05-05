package com.indocosmo.mrp.web.masters.salesdepartment.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.salesdepartment.model.SalesDepartment;


public interface ISalesDepartmentDao extends IMasterBaseDao<SalesDepartment>{
	public JsonArray getEditDetails(int id) throws Exception;
	public JsonArray getMastersRowJson() throws Exception;

}
