package com.indocosmo.mrp.web.accounts.dailyexpenses.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.indocosmo.mrp.web.accounts.dailyexpenses.dao.DailyExpensesDao;
import com.indocosmo.mrp.web.accounts.dailyexpenses.model.DailyExpenses;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;



public interface IDailyExpensesService extends IMasterBaseService<DailyExpenses,DailyExpensesDao>{

}
