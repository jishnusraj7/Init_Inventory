package com.indocosmo.mrp.web.report.currentstock.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.currentstock.dao.ItemStockDao;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;


public class ItemStockService extends GeneralService<ItemStock,ItemStockDao> implements IItemStockService{
	
	private ItemStockDao itemStockDao;
	

	public ItemStockService(ApplicationContext context) {
		super(context);
		itemStockDao = new ItemStockDao(getContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ItemStockDao getDao() {

		return itemStockDao;
	}

	public List<ItemStock> getList(ItemStock itemstock) throws Exception {

		return itemStockDao.getList(itemstock);	}


/*	public ItemStock  getitemStockdata(String id) throws Exception 

	{

		return itemStockDao.getitemStockdata(id);

	
	}*/


}
