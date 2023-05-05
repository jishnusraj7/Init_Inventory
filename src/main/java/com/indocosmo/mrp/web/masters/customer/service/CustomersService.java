package com.indocosmo.mrp.web.masters.customer.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.production.planning.bookingsummary.dao.OrderBookingDao;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;

public class CustomersService extends GeneralService<Customers, CustomersDao> implements ICustomersService {
	
	private CustomersDao customersDao;
	
	private PlanningDao planningDao;
	private OrderBookingDao orderBookingDao;
	
	public CustomersService(ApplicationContext context) {
	
		super(context);
		customersDao = new CustomersDao(getContext());
		planningDao = new PlanningDao(getContext());
		orderBookingDao=new OrderBookingDao(getContext());
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public CustomersDao getDao() {
	
		return customersDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = planningDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		if (context.getCompanyInfo().getId() != 0) {
			/* referenceTables.add(usersDao.getTable()); */
			
			referenceTables.add(planningDao.getTable());
			referenceTables.add(orderBookingDao.getTable());
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			if(table == planningDao.getTable())
			{
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("shop_code");
			referenceTableDetails.add(referenceModel);
			}else if(table == orderBookingDao.getTable())
			{
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("shop_id");
				referenceTableDetails.add(referenceModel);
			}
			
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang
	 * .String) */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		Integer custId = Integer.parseInt(id);
		String custCode = customersDao.getCustomerCode(custId);
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<Customers> dao = (GeneralDao<Customers>) getDao();
		
		int rowCount = 0;
		String wherePart = "";
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				if (table.getRefrenceTable().equals(planningDao.getTable())) {
					wherePart = "" + table.getRefrenceKey() + "= '" + custCode + "';";
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
			
			customersDao.delete(where);
		}
		
		return is_deleted;
		
	}
	
	public Customers saveItem(Customers cust) throws Exception {
		
		customersDao.save(cust);		
		return cust;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#save(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	public void save(Customers item) throws Exception {
	
		GeneralDao<Customers> dao = getDao();
		if (item.getId() == null || item.getId().equals(""))
			dao.insert(item);
		else
			dao.update(item);
	}
	
}
