package com.indocosmo.mrp.web.accounts.dailyexpenses.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.indocosmo.mrp.web.accounts.dailyexpenses.model.DailyExpenses;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;


public class DailyExpensesDao extends MasterBaseDao<DailyExpenses> implements IDailyExpensesDao {

	
	public DailyExpensesDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public DailyExpenses getNewModelInstance() {
		
		return new DailyExpenses();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		
		return "uoms";
	}

}
