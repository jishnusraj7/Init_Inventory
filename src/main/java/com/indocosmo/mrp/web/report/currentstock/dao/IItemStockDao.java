package com.indocosmo.mrp.web.report.currentstock.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;


public interface IItemStockDao extends IGeneralDao<ItemStock>{

	public JsonArray getCurrentStock(String stockId,String department_id) throws Exception;

}
