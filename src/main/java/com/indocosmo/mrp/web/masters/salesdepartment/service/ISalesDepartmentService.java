package com.indocosmo.mrp.web.masters.salesdepartment.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.salesdepartment.dao.SalesDepartmentDao;
import com.indocosmo.mrp.web.masters.salesdepartment.model.SalesDepartment;

public interface ISalesDepartmentService extends IMasterBaseService<SalesDepartment, SalesDepartmentDao>{
	public JsonArray getEditDetails(int id) throws Exception;
	public JsonArray getMastersRowJson() throws Exception;
}
