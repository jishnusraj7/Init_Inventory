package com.indocosmo.mrp.web.production.production.productiondetail.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;



public interface IProductionDtlDao extends IGeneralDao<ProductionDetail>{
	public JsonArray getTableRowsAsJson(Integer id) throws Exception;

}
