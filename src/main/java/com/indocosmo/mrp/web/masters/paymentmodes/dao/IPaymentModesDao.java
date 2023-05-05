package com.indocosmo.mrp.web.masters.paymentmodes.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;

public interface IPaymentModesDao extends IGeneralDao<PaymentModes>{
	public JsonArray getEditData()throws Exception;

}
