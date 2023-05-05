package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.PurchaseOrderdtlDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;

public class PurchaseOrderdtlService extends GeneralService<PO_dtl,PurchaseOrderdtlDao> implements IPurchaseOrderdtlService {


	
	private PurchaseOrderdtlDao purchaseorderdtlDao;

	public PurchaseOrderdtlService(ApplicationContext context) {
		super(context);
		purchaseorderdtlDao=new PurchaseOrderdtlDao(getContext());
	}

	@Override
	public PurchaseOrderdtlDao getDao() {
		// TODO Auto-generated method stub
		return purchaseorderdtlDao;
	}

	/**
	 * @param poId
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getpoDtlList(Integer poId) throws Exception {
		
		return purchaseorderdtlDao.getpoDtlList(poId);
	}
	
	@Override
	public JsonArray getPrDtlData(String id) throws Exception {
		
		return purchaseorderdtlDao.getPrDtlData(id);
	}

}
