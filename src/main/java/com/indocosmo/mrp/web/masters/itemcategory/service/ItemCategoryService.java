package com.indocosmo.mrp.web.masters.itemcategory.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemcategory.dao.ItemCategoryDao;
import com.indocosmo.mrp.web.masters.itemcategory.model.ItemCategory;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;


public class ItemCategoryService extends MasterBaseService<ItemCategory,ItemCategoryDao> implements IItemCategoryService{
	
	private ItemCategoryDao itemCategoryDao;
	private ItemMasterDao itemMasterDao;
	
	/**
	 * @param context
	 */
	public ItemCategoryService(ApplicationContext context) {
		
		super(context);
		itemCategoryDao=new ItemCategoryDao(getContext());
		itemMasterDao = new ItemMasterDao(getContext());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ItemCategoryDao getDao() {
		
		return itemCategoryDao;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable()
	 */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(itemMasterDao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("item_category_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemcategory.service.IItemCategoryService#getHqItemCategoryImportUpdatedList()
	 */
	@Override
	public JsonArray getHqItemCategoryImportUpdatedList() throws Exception {
		
		return itemCategoryDao.getUpdatedHqTableRowJson();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemcategory.service.IItemCategoryService#getHqItemCategoryImportList()
	 */
	@Override
	public JsonArray getHqItemCategoryImportList() throws Exception {
		
		return itemCategoryDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getDataToImport()throws Exception{
		return itemCategoryDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ItemCategory> getUpdatedDataToImport()throws Exception{
		return itemCategoryDao.getUpdatedHqTableRowListToImport();
	}

}
