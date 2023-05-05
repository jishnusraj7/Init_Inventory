package com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.dao.ViewStockDetailsDao;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.modal.ViewStockDetails;

public class ViewStockDetailsService extends GeneralService<ViewStockDetails,ViewStockDetailsDao> implements IViewStockDetailsService  {

	private ViewStockDetailsDao viewstockdetailsDao;

	public ViewStockDetailsService(ApplicationContext context) {
		super(context);
		viewstockdetailsDao=new ViewStockDetailsDao(getContext());
	}

	@Override
	public ViewStockDetailsDao getDao() {
		// TODO Auto-generated method stub
		return viewstockdetailsDao;
	}


}
