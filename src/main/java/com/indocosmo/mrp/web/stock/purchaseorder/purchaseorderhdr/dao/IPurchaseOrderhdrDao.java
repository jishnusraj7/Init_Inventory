package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;

public interface IPurchaseOrderhdrDao extends IGeneralDao<PO_hdr> {
	public List<PO_hdr> getPurchaseRequestList(PO_hdr pohdr) throws Exception ;
	public JsonArray getemote_pr_requestList() throws Exception ;
	public PO_hdr  getPohdrdata(String id) throws Exception ;


}
