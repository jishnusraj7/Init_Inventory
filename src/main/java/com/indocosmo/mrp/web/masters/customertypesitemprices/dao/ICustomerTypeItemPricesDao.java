package com.indocosmo.mrp.web.masters.customertypesitemprices.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.customertypesitemprices.model.CustomerTypeItemPrices;



public interface ICustomerTypeItemPricesDao extends IGeneralDao<CustomerTypeItemPrices>{
	public JsonArray getCustomerDataById(Integer itemId) throws Exception;
	

}
