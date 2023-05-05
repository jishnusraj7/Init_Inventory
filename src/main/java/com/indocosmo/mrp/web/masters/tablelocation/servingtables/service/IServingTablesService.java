package com.indocosmo.mrp.web.masters.tablelocation.servingtables.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.ServingTablesDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.model.ServingTables;

@Service
@Qualifier("IServingTablesService")
public interface IServingTablesService extends IGeneralService<ServingTables,ServingTablesDao>{
	
	public ServingTables saveItem(ServingTables item) throws Exception;
	public JsonArray getdpData(String table)  throws Exception;
	public JsonArray getTableImages(Integer servingTableLocId) throws Exception;
}
