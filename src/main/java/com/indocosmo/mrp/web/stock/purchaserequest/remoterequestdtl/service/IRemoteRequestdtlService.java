package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao.RemoteRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;

public interface IRemoteRequestdtlService  extends IGeneralService<RPR_dtl,RemoteRequestdtlDao>{
	public JsonArray getPrDtlData(String id) throws Exception;
}

