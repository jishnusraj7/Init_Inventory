package com.indocosmo.mrp.web.production.planning.planningdetail.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;



public interface IPlanningDetailDao extends IGeneralDao<PlanningDetail>{

	public void deletedtlId(String deletdtlId) throws Exception;
}
