package com.indocosmo.mrp.web.stock.stockregister.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;

public class StockRegisterDao extends GeneralDao<StockRegister> implements
		IStockRegisterDao {

	/**
	 * @param context
	 */
	public StockRegisterDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockRegister getNewModelInstance() {

		return new StockRegister();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_register_hdr";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {

		final String sql = "DELETE FROM " + getTable() + " WHERE " + where;
		Integer is_deleted = 0;

		beginTrans();
		try {

			is_deleted = executeSQL(sql);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(com.indocosmo.mrp
	 * .web.core.base.model.GeneralModelBase)
	 */
	public void save(StockRegister item) throws Exception {
		
		System.out.println(item);
	
		
		
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getContext());
		final String dateTime = currentDateFormat.getCurrentDateTime(); 
		Integer userid=0;
		if(context.getUser()==null)
		{
			 userid=0;
		}
		else
		{
			 userid=context.getUser().getId();
		}
		//Users userDtls = (Users) httpSession.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		
		if(item.getId() == null ){
			item.setCreated_at(dateTime);
			item.setCreated_by(userid);
		}else{
			item.setUpdated_at(dateTime);
			item.setUpdated_by(userid);
		}
		
		
		
		
		
		
		
		
		if (item.getTransType() == transactionType.PRODUCTION
				.gettransactionTypeId()
				|| item.getTransType() == transactionType.STOCKIN
						.gettransactionTypeId()
				|| item.getTransType() == transactionType.STOCKOUT
						.gettransactionTypeId()) {
			final String sql1 = "DELETE " 
								 + "FROM " 
								 + "mrp_stock_register_dtl "
								 + "WHERE " 
								 + "stock_register_hdr_id IN" 
								 +  "(SELECT "
								 +  "id " 
								 +  "FROM " 
								 +  "mrp_stock_register_hdr " 
								 +  "WHERE "
								 +  "ext_ref_no= '" + item.getExtRefNo() + "' )";

			final String sql2 = "DELETE " 
						         + "FROM " 
						         + "mrp_stock_register_dtl "
						         + "WHERE " 
						         + "stock_register_hdr_id IN " 
						         + "(SELECT "
						         +  "id " 
						         +  "FROM " 
						         +  "mrp_stock_register_hdr " 
						         +  "WHERE "
						         +  "ext_ref_id= '" + item.getExtRefId()
						         +  "' and trans_type=" + item.getTransType() + " )";

			final String sql = "DELETE FROM mrp_stock_register_hdr WHERE ext_ref_no = '"
					+ item.getExtRefNo() + "'";

			Integer is_deleted1 = 0;
			Integer is_deleted = 0;

			beginTrans();
			try {

				if (item.getTransType() != transactionType.PRODUCTION
						.gettransactionTypeId()) {

					is_deleted1 = executeSQL(sql1);
					is_deleted = executeSQL(sql);
				} else if (item.getTransType() == transactionType.PRODUCTION
						.gettransactionTypeId()) {

					is_deleted = executeSQL(sql2);
				}

				endTrans();
			} catch (Exception e) {

				rollbackTrans();
				throw e;
			}
		}

		if (!isExist(item))
			insert(item);
		else
			
			update(item);
	}

}
