package com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.model.ServingTables;


public interface IServingTablesDao extends IGeneralDao<ServingTables>{
	
	
	public JsonArray getdpData(String table)  throws Exception;
	public JsonArray getTableImages(Integer servingTableLocId) throws Exception;
	public Integer deleteSyncQueueRecord(Integer tbl_loc_id) throws Exception;
}
