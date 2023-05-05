package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;


public interface IItemMasterBomService extends IGeneralService<ItemMasterBom,ItemMasterBomDao>{
	public JsonArray getBomJsonArray(int id , int isBase)throws Exception;
	public List<ItemMasterBom> getDataToImport()throws Exception;
	public List<ItemMasterBom> getUpdatedBomListToImport(int stockItemId) throws Exception;
	public void updateMrpStockItem(ItemMaster item , Integer version) throws Exception;
	public JsonArray getItemList() throws Exception;
}
