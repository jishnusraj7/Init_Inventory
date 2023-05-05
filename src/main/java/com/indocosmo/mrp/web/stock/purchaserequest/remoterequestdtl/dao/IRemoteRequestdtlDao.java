package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;


public interface IRemoteRequestdtlDao extends IGeneralDao<RPR_dtl> {
	public JsonArray getPrDtlData(String where) throws Exception; 
}
