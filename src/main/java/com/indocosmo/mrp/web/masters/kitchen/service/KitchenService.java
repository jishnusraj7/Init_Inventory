package com.indocosmo.mrp.web.masters.kitchen.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.kitchen.dao.KitchenDao;
import com.indocosmo.mrp.web.masters.kitchen.model.Kitchen;

/**
 * @author jo
 *
 */
public class KitchenService extends MasterBaseService<Kitchen, KitchenDao> implements IKitchenService {
	
	private ItemMasterDao itemmasterdao;
	
	private KitchenDao kitchenDao;
	
	/**
	 * @param context
	 */
	public KitchenService(ApplicationContext context) {
	
		super(context);
		itemmasterdao = new ItemMasterDao(getContext());
		kitchenDao = new KitchenDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#
	 * getHqCurrencyDetailsImportList() */
	public JsonArray getHqCurrencyDetailsImportList() throws Exception {
	
		return kitchenDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	public JsonArray getHqCurrencyDetailsImportUpdatedList() throws Exception {
	
		return kitchenDao.getUpdatedHqTableRowJson();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(itemmasterdao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("kitchen_id");
			referenceTableDetails.add(referenceModel);
		}
		
		return referenceTableDetails;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public KitchenDao getDao() {
	
		return kitchenDao;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Integer isBaseCurrencyExist() throws Exception {
	
		return kitchenDao.isBaseCurrencyExist();
	}
}
