package com.indocosmo.mrp.web.masters.currencydetails.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.currencydetails.dao.CurrencyDetailsDao;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;

public class CurrencyDetailsService extends GeneralService<CurrencyDetails, CurrencyDetailsDao> implements
		ICurrencyDetailsService {
	
	private CurrencyDetailsDao currencyDetailsDao;
	
	/**
	 * @param context
	 */
	public CurrencyDetailsService(ApplicationContext context) {
	
		super(context);
		currencyDetailsDao = new CurrencyDetailsDao(getContext());
	}
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = currencyDetailsDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
	/*	referenceTables.add(purchaseorderdao.getTable());
		referenceTables.add(itemmasterdao.getTable());*/
		/*if (context.getCompanyInfo().getId() != 0) {
			referenceTables.add(stockindao.getTable());
		}*/
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			
			referenceModel.setRefrenceKey("currency");
			
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang
	 * .String) */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		String wherePart = "";
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<CurrencyDetails> dao = (GeneralDao<CurrencyDetails>) getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
			
				wherePart = "" + table.getRefrenceKey() + "=" + id + " ;";	
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
				
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
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#
	 * getHqCurrencyDetailsImportList() */
	public JsonArray getHqCurrencyDetailsImportList() throws Exception {
	
		return currencyDetailsDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqCurrencyDetailsImportUpdatedList() throws Exception {
	
		return currencyDetailsDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CurrencyDetails> getDataToImport() throws Exception {
	
		return currencyDetailsDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CurrencyDetails> getUpdatedDataToImport() throws Exception {
	
		return currencyDetailsDao.getUpdatedHqTableRowListToImport();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public CurrencyDetailsDao getDao() {
	
		return currencyDetailsDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.currencydetails.service.ICurrencyDetailsService
	 * #isBaseCurrencyExist() */
	public Integer isBaseCurrencyExist() throws Exception {
	
		return currencyDetailsDao.isBaseCurrencyExist();
	}
}
