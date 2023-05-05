package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.PurchaseOrderhdrDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;


public interface IPurchaseOrderhdrService  extends IGeneralService<PO_hdr,PurchaseOrderhdrDao>{

	public JsonArray getemote_pr_requestList() throws Exception ;

	public List<PO_hdr> getPurchaseRequestList(PO_hdr pohdr) throws Exception ;
	public PO_hdr  getPohdrdata(String id) throws Exception ;
}
