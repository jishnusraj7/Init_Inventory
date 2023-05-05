package com.indocosmo.mrp.web.accounts.dailyexpenses.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.indocosmo.mrp.web.accounts.dailyexpenses.dao.DailyExpensesDao;
import com.indocosmo.mrp.web.accounts.dailyexpenses.model.DailyExpenses;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;


public class DailyExpensesService extends MasterBaseService<DailyExpenses,DailyExpensesDao> implements IDailyExpensesService{

	
	
	private DailyExpensesDao uomDao;

	public DailyExpensesService(ApplicationContext context) {
		super(context);
		uomDao=new DailyExpensesDao(getContext());
	}

	@Override
	public DailyExpensesDao getDao() {
		// TODO Auto-generated method stub
		return uomDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.service.IBaseService#getDao()
	 */

}
