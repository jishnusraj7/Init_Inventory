package com.indocosmo.mrp.web.masters.tablelocation.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.tablelocation.dao.TableLocationDao;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;

@Service
@Qualifier("IItemMasterService")
public interface ITableLocationService extends IGeneralService<TableLocation,TableLocationDao>{
	

	public TableLocation saveItem(TableLocation item) throws Exception;
	public JsonArray getdpData(String table)  throws Exception;
}
