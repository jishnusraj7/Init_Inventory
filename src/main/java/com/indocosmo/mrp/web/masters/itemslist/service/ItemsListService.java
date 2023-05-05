package com.indocosmo.mrp.web.masters.itemslist.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemslist.dao.ItemsListDao;
import com.indocosmo.mrp.web.masters.itemslist.model.ItemsList;

public class ItemsListService extends GeneralService<ItemsList,ItemsListDao> implements IItemListService{
	private ItemsListDao itemsListDao;

	public ItemsListService(ApplicationContext context) {
		super(context);
		itemsListDao = new ItemsListDao(getContext());
		
		}
	
	
	
	@Override
	public ItemsListDao getDao() {
		
		return itemsListDao;
	}

	@Override
	public JsonArray getitemListDataList() throws Exception {
		// TODO Auto-generated method stub
		return itemsListDao.getTableRowsAsJson();
	}

	
}
