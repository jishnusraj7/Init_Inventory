package com.indocosmo.mrp.web.masters.paymentmodes.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.paymentmodes.dao.PaymentModesDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;

public class PaymentModesService extends GeneralService<PaymentModes, PaymentModesDao>{

	private PaymentModesDao paymentModesDao;
	public PaymentModesService(ApplicationContext context) {
		super(context);
		paymentModesDao=new PaymentModesDao(getContext());
	}
	@Override
	public PaymentModesDao getDao() {
		return paymentModesDao;
	}
	
	public PaymentModes saveItem(PaymentModes PaymentModes) throws Exception {
		paymentModesDao.save(PaymentModes);
		
		return PaymentModes;
	}

	public JsonArray getEditData()throws Exception{
		return paymentModesDao.getEditData();
	}
}
