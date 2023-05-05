package com.indocosmo.mrp.web.masters.profitcategory.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.profitcategory.dao.ProfitCategoryDao;
import com.indocosmo.mrp.web.masters.profitcategory.model.ProfitCategory;

public class ProfitCategoryService extends MasterBaseService<ProfitCategory, ProfitCategoryDao> implements
		IProfitCategoryService {
	
	private ProfitCategoryDao profitCategoryDao;
	
	private ItemMasterDao itemMasterDao;
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ProfitCategoryDao getDao() {
	
		return profitCategoryDao;
	}
	
	/**
	 * @param context
	 */
	public ProfitCategoryService(ApplicationContext context) {
	
		super(context);
		profitCategoryDao = new ProfitCategoryDao(getContext());
		itemMasterDao = new ItemMasterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable()
	 */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(itemMasterDao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("profit_category_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqProfitcategoryImportList() throws Exception {
	
		return profitCategoryDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqProfitcategoryImportUpdatedList() throws Exception {
	
		return profitCategoryDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ProfitCategory> getDataToImport() throws Exception {
	
		return profitCategoryDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ProfitCategory> getUpdatedDataToImport() throws Exception {
	
		return profitCategoryDao.getUpdatedHqTableRowListToImport();
	}
	
}
