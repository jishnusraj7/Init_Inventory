package com.indocosmo.mrp.web.masters.supplier.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.supplier.dao.SupplierDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;

public interface ISupplierService extends IGeneralService<Supplier,SupplierDao>{

	public Supplier getIsSystemSupplier() throws Exception;
	public JsonArray getSupplierImportList() throws Exception ;
	public JsonArray getSupplierImportUpdatedList() throws Exception ;
	public List<Supplier> getDataToImport()throws Exception;
	public List<Supplier> getUpdatedDataToImport()throws Exception;
	
}
