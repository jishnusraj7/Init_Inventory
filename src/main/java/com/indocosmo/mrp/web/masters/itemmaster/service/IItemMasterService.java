package com.indocosmo.mrp.web.masters.itemmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

@Service
@Qualifier("IItemMasterService")
public interface IItemMasterService extends IGeneralService<ItemMaster,ItemMasterDao>{
	
	
	public JsonArray getHqitemMasterImportList()  throws Exception;
	public JsonArray getHqitemMasterImportUpdatedList()  throws Exception;
	public JsonArray getItemDetails(int itemId) throws Exception;
	public ItemMaster saveItem(ItemMaster item) throws Exception;
	public List<ItemMaster> getDataToImport()throws Exception;
	public List<ItemMaster> getUpdatedDataToImport()throws Exception;
	public JsonArray getChoiceData() throws Exception;
	public JsonArray getGroupItem() throws Exception;
	public JsonArray getModalDetails(int itemId) throws Exception;
	public JsonArray getDataToEditChoice(String itemId) throws Exception;
	public List<ItemMaster> getItemData() throws Exception;
	public JsonArray getdpData(String table)  throws Exception;
	public JsonArray getSaleItems() throws Exception;
}
