package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;


public interface IItemMasterBatchDao extends IGeneralDao<ItemMasterBatch>{

	public void upadteItemMasterBatch(List<ItemMasterBatch> itemList) throws Exception;
	public Double getCurrentStockInBatch(ItemMasterBatch itemMasterBatch) throws Exception;
	public JsonArray getBatchJsonArray(int stockItemId) throws Exception;
	public List<ItemMasterBatch> getUpdatedBatchListToImport(int stockItemId)throws Exception;
	public void deleteData(Integer idss) throws Exception;

}
