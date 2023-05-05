package com.indocosmo.mrp.web.production.planning.bookingsummary.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;



public interface IOrderBookingDao extends IGeneralDao<OrderBoonkingSummary>{

	public void deletedtlId(String deletdtlId) throws Exception;
}
