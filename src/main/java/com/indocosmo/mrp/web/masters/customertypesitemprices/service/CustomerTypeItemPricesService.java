package com.indocosmo.mrp.web.masters.customertypesitemprices.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.customertypesitemprices.dao.CustomerTypeItemPricesDao;
import com.indocosmo.mrp.web.masters.customertypesitemprices.model.CustomerTypeItemPrices;

/**
 * @author jo
 *
 */
public class CustomerTypeItemPricesService extends GeneralService<CustomerTypeItemPrices, CustomerTypeItemPricesDao> implements
		ICustomerTypeItemPricesService {
	
	private CustomerTypeItemPricesDao customerTypeDao;
	
	private CustomerTypeItemPricesDao customersDao;
	
	/**
	 * @param context
	 */
	public CustomerTypeItemPricesService(ApplicationContext context) {
	
		super(context);
		customerTypeDao = new CustomerTypeItemPricesDao(getContext());
		customersDao = new CustomerTypeItemPricesDao(getContext());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public CustomerTypeItemPricesDao getDao() {
	
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
	
		final String where = "customer_type_id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<CustomerTypeItemPrices> dao = (GeneralDao<CustomerTypeItemPrices>) getDao();
		
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
	public List<CustomerTypeItemPrices> getDataToImport() throws Exception {
	
		return customerTypeDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CustomerTypeItemPrices> getUpdatedDataToImport() throws Exception {
	
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
