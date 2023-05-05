package com.indocosmo.mrp.web.masters.paymentmodes.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;

public class PaymentModesDao extends GeneralDao<PaymentModes> implements IPaymentModesDao{

	public PaymentModesDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public PaymentModes getNewModelInstance() {
		return new PaymentModes();
	}

	@Override
	public String getTable() {
		return "payment_modes";
	}
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable()+" where is_deleted='0'";

		return getTableRowsAsJson(sql);
	}
	public JsonArray getEditData() throws Exception
	{
  final String sql="SELECT id,can_pay_by_cash,can_pay_by_company,can_pay_by_vouchers,can_pay_by_card,can_cash_refundable,can_card_refundable,can_company_refundable,can_voucher_refundable,cash_account_no,cash_refund_account_no,can_cash_round,can_company_round,can_voucher_round,can_card_round,created_by,created_at from "+getTable()+" where is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}

	
}
