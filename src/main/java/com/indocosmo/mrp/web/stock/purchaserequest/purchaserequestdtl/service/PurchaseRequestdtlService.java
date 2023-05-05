package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.dao.PurchaseRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;


public class PurchaseRequestdtlService extends GeneralService<PR_dtl,PurchaseRequestdtlDao> implements IPurchaseRequestdtlService {

	private PurchaseRequestdtlDao purchaserequestdtlDao;

	public PurchaseRequestdtlService(ApplicationContext context) {
		super(context);
		purchaserequestdtlDao=new PurchaseRequestdtlDao(getContext());
	}

	@Override
	public PurchaseRequestdtlDao getDao() {
		// TODO Auto-generated method stub
		return purchaserequestdtlDao;
	}
	
	
	@Override
	public JsonArray getPrDtlData(String id) throws Exception {
		
		return purchaserequestdtlDao.getPrDtlData(id);
	}


}
