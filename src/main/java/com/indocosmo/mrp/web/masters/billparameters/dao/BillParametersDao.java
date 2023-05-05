package com.indocosmo.mrp.web.masters.billparameters.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;

public class BillParametersDao extends GeneralDao<BillParameters> implements IBillParametersDao{

	public BillParametersDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public BillParameters getNewModelInstance() {
		return new BillParameters();
	}

	@Override
	public String getTable() {
		return "bill_params";
	}
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
	public JsonArray getEditData() throws Exception
	{
  final String sql="SELECT id,show_tax_summary,bill_hdr1,bill_hdr2,bill_hdr3,bill_hdr4,bill_hdr5,bill_hdr6,bill_hdr7,bill_hdr8,bill_hdr9,bill_hdr10,bill_footer1,bill_footer2,bill_footer3,bill_footer4,bill_footer5,bill_footer6,bill_footer7,bill_footer8,bill_footer9,bill_footer10,created_by,created_at from "+getTable()+" where is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}

	
}
