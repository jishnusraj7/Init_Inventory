package com.indocosmo.mrp.web.masters.tax.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.tax.dao.TaxDao;
import com.indocosmo.mrp.web.masters.tax.model.Tax;

public class TaxService extends GeneralService<Tax, TaxDao> implements ITaxService{

		private TaxDao taxDao;
	public TaxService(ApplicationContext context) {
		super(context);
		taxDao=new TaxDao(getContext());
	}
	@Override
	public TaxDao getDao() {
		return taxDao;
	}
	
	public JsonArray getEditDetails(int id) throws Exception{
		return taxDao.getEditDetails(id);
		
	}
	public List<Tax> getTaxData() throws Exception{
		return taxDao.getTaxData();
	}
	
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = taxDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		//referenceTables.add(purchaseorderdao.getTable());
	//	referenceTables.add(itemmasterdao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("tax_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		String wherePart = "";
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<Tax> dao = (GeneralDao<Tax>) getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
			 wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
				
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			super.delete(where);
		}
		
		return is_deleted;
	}
	public JsonArray getTaxListForBom() throws Exception {
	
		// TODO Auto-generated method stub
		return taxDao.getTaxListForBom();
	}
	
	
	

}
