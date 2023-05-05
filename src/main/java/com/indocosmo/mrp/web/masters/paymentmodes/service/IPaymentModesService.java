package com.indocosmo.mrp.web.masters.paymentmodes.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.paymentmodes.dao.PaymentModesDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;

public interface IPaymentModesService extends IGeneralService<PaymentModes, PaymentModesDao> {

	public JsonArray getEditData()throws Exception;
	
}
