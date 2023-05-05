package com.indocosmo.mrp.web.masters.employeecategory.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.employee.dao.EmployeeDao;
import com.indocosmo.mrp.web.masters.employeecategory.dao.EmployeeCategoryDao;
import com.indocosmo.mrp.web.masters.employeecategory.model.EmployeeCategory;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;

public class EmployeeCategoryService extends MasterBaseService<EmployeeCategory, EmployeeCategoryDao> implements
		IEmployeeCategoryService {
	
	private EmployeeCategoryDao choicesDao;
	
	private ItemMasterDao itemmasterdao;
	private EmployeeDao employee;
	
	/**
	 * @param context
	 */
	public EmployeeCategoryService(ApplicationContext context) {
	
		super(context);
		choicesDao = new EmployeeCategoryDao(getContext());
		itemmasterdao = new ItemMasterDao(getContext());
		employee = new EmployeeDao(getContext());
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(employee.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("employee_category_id");
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
		GeneralDao<EmployeeCategory> dao = getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
			String 	wherePart = "" + table.getRefrenceKey() + "=" + id
						+ "  AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			choicesDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public EmployeeCategoryDao getDao() {
	
		return choicesDao;
	}
	
}
