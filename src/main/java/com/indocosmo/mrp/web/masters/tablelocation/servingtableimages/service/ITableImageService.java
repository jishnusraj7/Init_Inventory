package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.dao.TableImageDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model.TableImage;

@Service
@Qualifier("IItemMasterService")
public interface ITableImageService extends IGeneralService<TableImage,TableImageDao>{
	
	
	
	public TableImage saveItem(TableImage item) throws Exception;
	public JsonArray getdpData(String table)  throws Exception;
}
