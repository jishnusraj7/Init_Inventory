package com.indocosmo.mrp.web.masters.department.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.stock.stockadjustments.dao.StockAdjustmentDao;
import com.indocosmo.mrp.web.stock.stockdisposal.dao.StockDisposalDao;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockout.dao.StockOutDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;

/**
 * @author jo
 *
 */
public class DepartmentService extends MasterBaseService<Department, DepartmentDao> implements IDepartmentService {
	
	private DepartmentDao departmentDao;
	
	private UsersDao usersDao;
	
	private StockRegisterDetailDao stockRegisterdtlDao;
	
	private StockAdjustmentDao stockAdjustmentDao;
	
	private StockInDao stockInDao;
	
	private StockOutDao stockOutDao;
	
	private StockDisposalDao stockDisposalDao;
	
	/**
	 * @param context
	 */
	public DepartmentService(ApplicationContext context) {
	
		super(context);
		departmentDao = new DepartmentDao(getContext());
		usersDao = new UsersDao(getContext());
		stockRegisterdtlDao = new StockRegisterDetailDao(getContext());
		stockInDao = new StockInDao(getContext());
		stockOutDao = new StockOutDao(getContext());
		stockAdjustmentDao = new StockAdjustmentDao(getContext());
		stockDisposalDao = new StockDisposalDao(getContext());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public DepartmentDao getDao() {
	
		return departmentDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getIsSystemDepartment() */
	@Override
	public Department getIsSystemDepartment() throws Exception {
	
		return departmentDao.getIsSystemDepartment();
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
			
			referenceTables.add(stockAdjustmentDao.getTable());
			referenceTables.add(stockDisposalDao.getTable());
			referenceTables.add(stockRegisterdtlDao.getTable());
			referenceTables.add(stockInDao.getTable());
			referenceTables.add(stockOutDao.getTable());
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			if (table == stockOutDao.getTable()) {
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("source_department_id");
				referenceTableDetails.add(referenceModel);
				referenceModel = new RefereneceModelBase();
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("dest_department_id");
				referenceTableDetails.add(referenceModel);
				
			}
			else {
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("department_id");
				referenceTableDetails.add(referenceModel);
				
			}
			
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.MasterBaseService#delete(java
	 * .lang.String) */
	@SuppressWarnings("unchecked")
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<Department> dao = (GeneralDao<Department>) getDao();
		
		int rowCount = 0;
		String wherePart = "";
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				if (table.getRefrenceTable() == usersDao.getTable()) {
					wherePart = "" + table.getRefrenceKey() + "=" + id
							+ " AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
					
				}
				else {
					wherePart = "" + table.getRefrenceKey() + "=" + id + " ;";
					
				}
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
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getDepartmentImportList() */
	public JsonArray getDepartmentImportList() throws Exception {
	
		return departmentDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getDepartmentImportUpdatedList() */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getDataToImport() */
	public List<Department> getDataToImport() throws Exception {
	
		return departmentDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Department> getUpdatedDataToImport() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowListToImport();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getProductionDepartment() */
	@Override
	public Department getProductionDepartment() throws Exception {
	
		return departmentDao.getProductionDepartment();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getDptData() */
	public List<Department> getDptData() throws Exception {
	
		return departmentDao.getDptData();
	}
	
	
public JsonArray getMastersRowJson() throws Exception {
		return departmentDao.getMastersRowJson();
	}

	
}
