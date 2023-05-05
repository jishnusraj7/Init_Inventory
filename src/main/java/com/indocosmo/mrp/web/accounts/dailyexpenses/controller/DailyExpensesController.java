package com.indocosmo.mrp.web.accounts.dailyexpenses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.accounts.dailyexpenses.dao.DailyExpensesDao;
import com.indocosmo.mrp.web.accounts.dailyexpenses.model.DailyExpenses;
import com.indocosmo.mrp.web.accounts.dailyexpenses.service.DailyExpensesService;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;



@Controller
@RequestMapping("/dailyexpenses")

public class DailyExpensesController extends MasterBaseController<DailyExpenses,DailyExpensesDao,DailyExpensesService>{

	
	
	@Override
	public DailyExpensesService getService() {
		
		return new DailyExpensesService(getCurrentContext());
	}


	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute DailyExpenses DailyExpenses)
			throws Exception {

		return "/dailyexpenses/list";
	}
}
