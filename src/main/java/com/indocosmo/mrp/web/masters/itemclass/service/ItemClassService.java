package com.indocosmo.mrp.web.masters.itemclass.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemclass.dao.ItemClassDao;
import com.indocosmo.mrp.web.masters.itemclass.model.ItemClass;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;

/**
 * @author jo
 *
 */
public class ItemClassService extends MasterBaseService<ItemClass, ItemClassDao> implements IItemClassService {
	
	private ItemClassDao itemClassDao;
	
	private ItemMasterDao itemMasterDao;
	
	public ItemClassService(ApplicationContext context) {
	
		super(context);
		itemClassDao = new ItemClassDao(getContext());
		itemMasterDao = new ItemMasterDao(getContext());
		
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public ItemClassDao getDao() {
	
		return itemClassDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = itemClassDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		if (context.getCompanyInfo().getId() != 0) {
			/* referenceTables.add(usersDao.getTable()); */
			
			referenceTables.add(itemMasterDao.getTable());
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("sub_class_id");
			referenceTableDetails.add(referenceModel);
			
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.MasterBaseService#delete(java
	 * .lang.String) */
	
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<ItemClass> dao = (GeneralDao<ItemClass>) getDao();
		
		int rowCount = 0;
		String wherePart = "";
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
			
			itemClassDao.delete(where);
		}
		
		return is_deleted;
		
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.service.IItemClassService#
	 * getDepartmentImportList() */
	public JsonArray getDepartmentImportList() throws Exception {
	
		return itemClassDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.service.IItemClassService#
	 * getDepartmentImportUpdatedList() */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {
	
		return itemClassDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.service.IItemClassService#
	 * getDataToImport() */
	public List<ItemClass> getDataToImport() throws Exception {
	
		return itemClassDao.getHqTableRowListToImport();
		
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.service.IItemClassService#
	 * getUpdatedDataToImport() */
	public List<ItemClass> getUpdatedDataToImport() throws Exception {
	
		return itemClassDao.getUpdatedHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.service.IItemClassService#
	 * getSuperClassList() */
	public JsonArray getSuperClassList() throws Exception {
	
		// TODO Auto-generated method stub
		return itemClassDao.getSuperClassList();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getProductionDepartment() */
	public List<ItemClass> getItemClassData() throws Exception {
	
		return itemClassDao.getItemClassData();
	}
	
}
