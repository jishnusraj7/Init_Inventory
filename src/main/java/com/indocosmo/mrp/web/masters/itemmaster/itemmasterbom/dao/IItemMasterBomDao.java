package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;


public interface IItemMasterBomDao extends IGeneralDao<ItemMasterBom>{
	public JsonArray getBomJsonArray(int id , int isBase) throws Exception;
	public List<ItemMasterBom> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public void deleteData(Integer idss) throws Exception;
	public JsonArray getItemList() throws Exception;
	public void updateMrpStockItem(ItemMaster item , Integer version) throws Exception;
}
