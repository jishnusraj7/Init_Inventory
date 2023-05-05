package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;


public interface IItemProdCostService extends IGeneralService<ItemProdCost,ItemProdCostDao>{
	public JsonArray getProdCostArray(int id)throws Exception;
	public List<ItemProdCost> getDataToImport()throws Exception;
	public List<ItemProdCost> getUpdatedBomListToImport(int stockItemId) throws Exception;
}
