package com.indocosmo.mrp.web.masters.itemslist.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemslist.dao.ItemsListDao;
import com.indocosmo.mrp.web.masters.itemslist.model.ItemsList;

@Service
@Qualifier("IItemListService")
public interface IItemListService extends IGeneralService<ItemsList,ItemsListDao>{

	public JsonArray getitemListDataList() throws Exception ;
}
