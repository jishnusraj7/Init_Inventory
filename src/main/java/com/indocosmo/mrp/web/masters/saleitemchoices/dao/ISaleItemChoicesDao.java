package com.indocosmo.mrp.web.masters.saleitemchoices.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.saleitemchoices.model.SaleItemChoices;

public interface ISaleItemChoicesDao extends IGeneralDao<SaleItemChoices>{
	public JsonArray getEditDetails(int id) throws Exception;
	public List<SaleItemChoices> getExcelData() throws Exception;
}
