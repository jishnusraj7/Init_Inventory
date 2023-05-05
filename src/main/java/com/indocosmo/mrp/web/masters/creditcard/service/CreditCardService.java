package com.indocosmo.mrp.web.masters.creditcard.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.creditcard.dao.CreditCardDao;
import com.indocosmo.mrp.web.masters.creditcard.model.CreditCard;

public class CreditCardService  extends MasterBaseService<CreditCard, CreditCardDao> implements ICreditCardService{

	private CreditCardDao creditCardDao;
	
	public CreditCardService(ApplicationContext context) {
		super(context);
		creditCardDao=new CreditCardDao(getContext());
	}

	@Override
	public CreditCardDao getDao() {
		// TODO Auto-generated method stub
		return creditCardDao;
	}
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		/*referenceTables.add(customersDao.getTable());*/
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("ar_code");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<CreditCard> dao =  getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			creditCardDao.delete(where);
		}
		
		return is_deleted;
	}
	public JsonArray getEditDetails(int id) throws Exception{
		return creditCardDao.getEditDetails(id);
		
	}

}
