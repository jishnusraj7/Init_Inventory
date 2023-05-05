package com.indocosmo.mrp.web.masters.saleitemchoices.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.saleitemchoices.dao.SaleItemChoicesDao;
import com.indocosmo.mrp.web.masters.saleitemchoices.model.SaleItemChoices;

public interface ISaleItemChoicesService  extends IGeneralService<SaleItemChoices, SaleItemChoicesDao>{
	public JsonArray getEditDetails(int id) throws Exception;
	public List<SaleItemChoices> getExcelData() throws Exception;
}
