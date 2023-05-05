package com.indocosmo.mrp.web.masters.reasons.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.reasons.dao.ReasonsDao;
import com.indocosmo.mrp.web.masters.reasons.model.Reasons;

public class ReasonsService extends GeneralService<Reasons, ReasonsDao> implements IReasonsService {
	
	private ReasonsDao departmentDao;
	
	public ReasonsService(ApplicationContext context) {
	
		super(context);
		departmentDao = new ReasonsDao(getContext());
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ReasonsDao getDao() {
	
		return departmentDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = departmentDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		if (context.getCompanyInfo().getId() != 0) {
			/* referenceTables.add(usersDao.getTable()); */
			
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
		}
		return referenceTableDetails;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<Reasons> dao = (GeneralDao<Reasons>) getDao();
		
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
			
			departmentDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportList() throws Exception {
	
		return departmentDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Reasons> getDataToImport() throws Exception {
	
		return departmentDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Reasons> getUpdatedDataToImport() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowListToImport();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getProductionDepartment() */
	
}
