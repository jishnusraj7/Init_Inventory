package com.indocosmo.mrp.web.production.production.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.production.production.dao.ProductionDao;
import com.indocosmo.mrp.web.production.production.model.Production;


public interface IProductionService extends IGeneralService<Production,ProductionDao> {
	public JsonArray getStockDispDtlData(Production stockDisp) throws Exception;
	public Integer updateStatus(Integer id) throws Exception ;

	public int updateProDBomItems(Integer dtl_id, Integer roundVal,
			Integer source_dep, Integer dest_dep, Integer stock_hdr_id)
					throws Exception;
	public JsonArray getOnProductionIdsShopwise(String currentDate , String prod_upto ,String custId) throws Exception ;
	
	//public void updateIssuedQty(String req_date,String issuedQty)throws Exception;

}
