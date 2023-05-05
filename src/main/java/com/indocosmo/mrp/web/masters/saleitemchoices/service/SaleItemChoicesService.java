package com.indocosmo.mrp.web.masters.saleitemchoices.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.saleitemchoices.dao.SaleItemChoicesDao;
import com.indocosmo.mrp.web.masters.saleitemchoices.model.SaleItemChoices;

public class SaleItemChoicesService extends GeneralService<SaleItemChoices, SaleItemChoicesDao> implements
		ISaleItemChoicesService {
	
	private SaleItemChoicesDao saleItemChoicesDao;
	
	public SaleItemChoicesService(ApplicationContext context) {
	
		super(context);
		saleItemChoicesDao = new SaleItemChoicesDao(getContext());
	}
	
	@Override
	public SaleItemChoicesDao getDao() {
	
		return saleItemChoicesDao;
	}
	
	public JsonArray getEditDetails(int id) throws Exception {
	
		return saleItemChoicesDao.getEditDetails(id);
		
	}
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		/* referenceTables.add(usersDao.getTable()); */
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			/* referenceModel.setRefrenceKey("user_group_id"); */
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = "" + table.getRefrenceKey() + "=" + id
						+ " AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + saleItemChoicesDao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			saleItemChoicesDao.delete(where);
		}
		
		return is_deleted;
	}
	
	public List<SaleItemChoices> getExcelData() throws Exception {
	
		return saleItemChoicesDao.getExcelData();
	}
	
}
