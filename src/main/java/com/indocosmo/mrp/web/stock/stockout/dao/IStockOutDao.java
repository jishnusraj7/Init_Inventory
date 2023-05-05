package com.indocosmo.mrp.web.stock.stockout.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;


public interface IStockOutDao extends IGeneralDao<StockOut>{

 
	/**
	 * @param stockout
	 * @return
	 * @throws Exception
	 */
	public	StockOut updateStockOutStatus(StockOut stockout) throws Exception;


	/**
	 * @param datatableParameters
	 * @param tableFields
	 * @return
	 * @throws Exception
	 *//*
	public JsonArray getTableRequestDataRowsAsJson(DataTableCriterias datatableParameters,List<String> tableFields) throws Exception;*/

	public JsonArray getTableRequestDataRowsAsJson() throws Exception;

	public JsonArray getStockOutDtlData(StockOut stockOut) throws Exception;
	//added by udhay on 31st Aug 2021
	//get the unit price 
	//public Double getUnitPrice(String item_code ) throws Exception;
	

}
