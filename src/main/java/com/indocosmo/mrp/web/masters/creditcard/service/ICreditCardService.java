package com.indocosmo.mrp.web.masters.creditcard.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.creditcard.dao.CreditCardDao;
import com.indocosmo.mrp.web.masters.creditcard.model.CreditCard;

public interface ICreditCardService extends IMasterBaseService<CreditCard, CreditCardDao>{
	public JsonArray getEditDetails(int id) throws Exception;
}
