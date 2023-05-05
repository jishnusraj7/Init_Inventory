package com.indocosmo.mrp.web.report.nonmoving.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.nonmoving.dao.NonMovingStockDao;
import com.indocosmo.mrp.web.report.nonmoving.model.NonMovingStock;


public class NonMovingStockService extends GeneralService<NonMovingStock,NonMovingStockDao> implements INonMovingStockService{
	
	private NonMovingStockDao itemStockDao;
	

	public NonMovingStockService(ApplicationContext context) {
		super(context);
		itemStockDao = new NonMovingStockDao(getContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public NonMovingStockDao getDao() {

		return itemStockDao;
	}

	public List<NonMovingStock> getList(NonMovingStock itemstock) throws Exception {

		return itemStockDao.getList(itemstock);	}


/*	public ItemStock  getitemStockdata(String id) throws Exception 

	{

		return itemStockDao.getitemStockdata(id);

	
	}*/


}
