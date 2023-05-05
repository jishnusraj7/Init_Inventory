package com.indocosmo.mrp.web.masters.uom.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.uom.dao.UomDao;
import com.indocosmo.mrp.web.masters.uom.model.Uom;


public interface IUomService extends IMasterBaseService<Uom,UomDao>{
	public JsonArray getUomImportList() throws Exception ;
	public JsonArray getUomImportUpdatedList() throws Exception ;
	public List<Uom> getDataToImport()throws Exception;
	public List<Uom> getUpdatedDataToImport()throws Exception;
	public JsonArray getBaseUom() throws Exception;
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Uom> getUomData() throws Exception;
}
