package com.indocosmo.mrp.web.production.planning.planningdetail.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.planning.planningdetail.dao.PlanningDetailDao;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;


public class PlanningDetailService extends GeneralService<PlanningDetail,PlanningDetailDao> implements IPlanningDetailService{

	
	private PlanningDetailDao planningDao;

	public PlanningDetailService(ApplicationContext context) {
		super(context);
		planningDao=new PlanningDetailDao(getContext());
	}

	@Override
	public PlanningDetailDao getDao() {
		// TODO Auto-generated method stub
		return planningDao;
	}

}
