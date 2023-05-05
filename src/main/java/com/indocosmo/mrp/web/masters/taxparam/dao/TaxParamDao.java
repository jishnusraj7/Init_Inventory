package com.indocosmo.mrp.web.masters.taxparam.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;

public class TaxParamDao extends GeneralDao<TaxParam> implements ITaxParamDao{

	public TaxParamDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public TaxParam getNewModelInstance() {
		return new TaxParam();
	}

	@Override
	protected String getTable() {
		return "tax_param";
	}
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
	public JsonArray getEditData() throws Exception
	{
  final String sql="SELECT id,tax1_name,tax2_name,tax3_name,default_taxation_method,default_purchase_taxation_method from "+getTable()+" where is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}

}
