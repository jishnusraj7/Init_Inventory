package com.indocosmo.mrp.web.masters.uom.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.uom.model.Uom;



public interface IUomDao extends IMasterBaseDao<Uom>{
	public JsonArray getBaseUom() throws Exception;
	public JsonArray getEditDetails(int id) throws Exception;
	public List<Uom> getUomData() throws Exception;

}
