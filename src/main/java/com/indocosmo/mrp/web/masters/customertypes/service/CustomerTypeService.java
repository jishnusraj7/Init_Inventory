package com.indocosmo.mrp.web.masters.customertypes.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customertypes.dao.CustomerTypeDao;
import com.indocosmo.mrp.web.masters.customertypes.model.CustomerType;

/**
 * @author jo
 *
 */
public class CustomerTypeService extends MasterBaseService<CustomerType, CustomerTypeDao> implements
		ICustomerTypeService {
	
	private CustomerTypeDao customerTypeDao;
	
	private CustomersDao customersDao;
	
	/**
	 * @param context
	 */
	public CustomerTypeService(ApplicationContext context) {
	
		super(context);
		customerTypeDao = new CustomerTypeDao(getContext());
		customersDao = new CustomersDao(getContext());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public CustomerTypeDao getDao() {
	
		return customerTypeDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = customersDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		if (context.getCompanyInfo().getId() != 0) {
			/* referenceTables.add(usersDao.getTable()); */
			
			referenceTables.add(customersDao.getTable());
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("customer_type");
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
		
		GeneralDao<CustomerType> dao = (GeneralDao<CustomerType>) getDao();
		
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
			
			customerTypeDao.delete(where);
		}
		
		return is_deleted;
		
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportList() throws Exception {
	
		return customerTypeDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {
	
		return customerTypeDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CustomerType> getDataToImport() throws Exception {
	
		return customerTypeDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CustomerType> getUpdatedDataToImport() throws Exception {
	
		return customerTypeDao.getUpdatedHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getCustomerTypesAsJson() throws Exception {
	
		// TODO Auto-generated method stub
		return customerTypeDao.getCustomerTypeList();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.department.service.IDepartmentService#
	 * getProductionDepartment() */
	
}
