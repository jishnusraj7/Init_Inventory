package com.indocosmo.mrp.web.masters.employee.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.employee.dao.EmployeeDao;
import com.indocosmo.mrp.web.masters.employee.model.Employee;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;

/**
 * @author jo
 *
 */
public class EmployeeService extends GeneralService<Employee, EmployeeDao> implements IEmployeeService {
	
	private EmployeeDao employeeDao;
	
	private ItemMasterDao itemmasterdao;
	
	/**
	 * @param context
	 */
	public EmployeeService(ApplicationContext context) {
	
		super(context);
		employeeDao = new EmployeeDao(getContext());
		itemmasterdao = new ItemMasterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang
	 * .String) */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<Employee> dao = getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = "" + table.getRefrenceKey() + "=" + id
						+ " AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
				
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			employeeDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public EmployeeDao getDao() {
	
		return employeeDao;
	}
	
}
