package com.indocosmo.mrp.web.masters.creditcard.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.creditcard.model.CreditCard;

public interface ICreditCardDao extends IMasterBaseDao<CreditCard>{
	public JsonArray getEditDetails(int id) throws Exception;
}
