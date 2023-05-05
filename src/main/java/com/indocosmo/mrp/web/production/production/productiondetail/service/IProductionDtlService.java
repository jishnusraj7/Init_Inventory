package com.indocosmo.mrp.web.production.production.productiondetail.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.production.production.productiondetail.dao.ProductionDtlDao;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;




public interface IProductionDtlService extends IGeneralService<ProductionDetail,ProductionDtlDao>{
	public JsonArray getTableRowsAsJson(Integer id) throws Exception;

}
