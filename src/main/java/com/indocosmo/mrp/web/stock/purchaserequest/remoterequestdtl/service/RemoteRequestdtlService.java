package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao.RemoteRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;


public class RemoteRequestdtlService extends GeneralService<RPR_dtl,RemoteRequestdtlDao> implements IRemoteRequestdtlService {

	private RemoteRequestdtlDao remoteRequestdtlDao;

	public RemoteRequestdtlService(ApplicationContext context) {
		super(context);
		remoteRequestdtlDao=new RemoteRequestdtlDao(getContext());
	}

	@Override
	public RemoteRequestdtlDao getDao() {
		// TODO Auto-generated method stub
		return remoteRequestdtlDao;
	}
	
	@Override
	public JsonArray getPrDtlData(String id) throws Exception {
		
		return remoteRequestdtlDao.getPrDtlData(id);
	}
	
}
