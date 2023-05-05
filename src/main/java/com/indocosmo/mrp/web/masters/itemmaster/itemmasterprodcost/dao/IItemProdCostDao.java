package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;


public interface IItemProdCostDao extends IGeneralDao<ItemProdCost>{
	public JsonArray getProdCostArray(int id) throws Exception;
	public List<ItemProdCost> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public void deleteData(Integer idss) throws Exception;
}
