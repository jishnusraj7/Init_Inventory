package com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.dao.PurchaseReturnDetailDao;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public class PurchaseReturnDetailService extends GeneralService<PurchaseReturnDetail, PurchaseReturnDetailDao> implements IPurchaseReturnDetailService{

	private PurchaseReturnDetailDao purchaseReturnDetailDao;
	public PurchaseReturnDetailService(ApplicationContext context) {
		super(context);	
		purchaseReturnDetailDao = new PurchaseReturnDetailDao(getContext());
	}

	@Override
	public PurchaseReturnDetailDao getDao() {
		
		return purchaseReturnDetailDao;
	}

	public JsonArray getPurchaseReturnDetailData(int stock_return_hdr_id) throws Exception{
		return purchaseReturnDetailDao.getPurchaseReturnDetailData(stock_return_hdr_id);
	}
}
