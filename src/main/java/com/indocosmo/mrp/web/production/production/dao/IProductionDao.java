package com.indocosmo.mrp.web.production.production.dao;

import java.util.LinkedHashMap;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.production.model.Production;

public interface IProductionDao extends IGeneralDao<Production>{
	public JsonArray getStockDispDtlData(Production production) throws Exception;
	public Integer updateStatus(Integer id) throws Exception;
	public int updateProDBomItems(Integer dtl_id ,Integer roundVal ,Integer source_dep,Integer dest_dep,Integer stock_hdr_id) throws Exception;
	public JsonArray getOnProductionIdsShopwise(String currentDate , String prod_upto ,String custId) throws Exception ;
	public LinkedHashMap<Integer,Double> updateIssuedQty(String req_date,String issuedQty,int stock_item_id)throws Exception;
	public Double getcurrentIssuedqty(Integer key)throws Exception;
	public void updateissuedValue(Integer key, Double prodQty)throws Exception;


}
