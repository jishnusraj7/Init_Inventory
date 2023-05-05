package com.indocosmo.mrp.web.masters.supplier.state.service;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.supplier.state.dao.StateDao;
import com.indocosmo.mrp.web.masters.supplier.state.model.State;

public class StateService extends GeneralService<State,StateDao> implements IStateService{
	
	private StateDao stateDao;

	public StateService(ApplicationContext context) {
		super(context);
		stateDao=new StateDao(getContext());
	}

	@Override
	public StateDao getDao() {
		return stateDao;
	}
	
	

}