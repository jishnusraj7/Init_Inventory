package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.PurchaseOrderhdrDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;

public class PurchaseOrderhdrService extends GeneralService<PO_hdr,PurchaseOrderhdrDao> implements IPurchaseOrderhdrService {

	
	private PurchaseOrderhdrDao purchaseorderhdrDao;

	public PurchaseOrderhdrService(ApplicationContext context) {
		super(context);
		purchaseorderhdrDao=new PurchaseOrderhdrDao(getContext());
	}	
	public List<PO_hdr> getPurchaseRequestList(PO_hdr pohdr) throws Exception {

		return purchaseorderhdrDao.getPurchaseRequestList(pohdr);

	
	}
	
	
	public PO_hdr  getPohdrdata(String id) throws Exception 

	{

		return purchaseorderhdrDao.getPohdrdata(id);

	
	}


	public JsonArray getemote_pr_requestList() throws Exception {

		return purchaseorderhdrDao.getemote_pr_requestList();

	
	}

	
	public PO_hdr saveStockItem(PO_hdr pohdr) throws Exception {
		purchaseorderhdrDao.save(pohdr);
		
		return pohdr;
	}
	
	

	@Override
	public PurchaseOrderhdrDao getDao() {
		// TODO Auto-generated method stub
		return purchaseorderhdrDao;
	}
	
	/**
	 * @param supllierId
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getpoList(Integer supllierId) throws Exception {
		
		return purchaseorderhdrDao.getpoList(supllierId);
	}
	
}
