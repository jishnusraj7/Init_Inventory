package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.ItemMasterBatchDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;


public interface IItemMasterBatchService extends IGeneralService<ItemMasterBatch,ItemMasterBatchDao> {

	public void upadteItemMasterBatch(ArrayList<ItemMasterBatch> stockItembatchList) throws Exception;

	public Double getCurrentStockInBatch(ItemMasterBatch itemMasterBatch) throws Exception;
	public Double currentStock(ItemMasterBatch itemMasterBatch) throws Exception;
	public JsonArray getBatchJsonArray(int stockItemId) throws Exception;
	public List<ItemMasterBatch> getDataToImport()throws Exception;
	public List<ItemMasterBatch> getUpdatedBatchListToImport(int stockItemId)throws Exception;
	public void deleteData(Integer idss) throws Exception;

}
