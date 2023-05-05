package com.indocosmo.mrp.web.masters.itemmaster.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

/**
 * @author jo
 *
 */
public interface IItemMasterDao extends IGeneralDao<ItemMaster> {
	
	public List<ItemMasterBom> getitemBomListToImport(String ids) throws Exception;
	
	public JsonArray getItemDetails(int itemId) throws Exception;
	
	public JsonArray getChoiceData() throws Exception;
	
	public JsonArray getGroupItem() throws Exception;
	
	public JsonArray getModalDetails(int itemId) throws Exception;
	
	public JsonArray getDataToEditChoice(String itemId) throws Exception;
	
	public List<ItemMaster> getItemData() throws Exception;
	
	public JsonArray getdpData(String table) throws Exception;
	
	public JsonArray getSaleItems() throws Exception;
	
}
