package com.indocosmo.mrp.web.production.planning.bookingsummary.service;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.planning.bookingsummary.dao.OrderBookingDao;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;


public class OrderBookingService extends GeneralService<OrderBoonkingSummary,OrderBookingDao> implements IOrderBookingService{

	
	private OrderBookingDao orderBookingDao;

	public OrderBookingService(ApplicationContext context) {
		super(context);
		orderBookingDao = new OrderBookingDao(getContext());
	}

	@Override
	public OrderBookingDao getDao() {
		
		return orderBookingDao;
	}

	/**
	 * @return
	 */
	public JsonArray getBalanceQtyData() throws Exception{
		
		return orderBookingDao.getBalanceQtyData();
	}

	/**
	 * @param isWalkIn
	 * @return
	 */
	public Boolean checkIsWalkInCustomer(Integer isWalkIn) throws Exception{
		
		return orderBookingDao.checkIsWalkInCustomer(isWalkIn);
	}
	
	/**
	 * @param order_no
	 * @return
	 * @throws Exception
	 */
	public JsonArray getPendingItemsByOrderNo(String order_no) throws Exception{
		return orderBookingDao.getPendingItemsByOrderNo(order_no);
	}

}
