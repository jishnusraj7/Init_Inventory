package com.indocosmo.mrp.web.masters.syncqueue.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;

public interface ISyncQueueDao extends IGeneralDao<SyncQueue>{
	public JsonArray getQuetableDet(int itemId,String tableName) throws Exception;
	public String fungetSynQueId(String tableName,String recordId)throws Exception;
	public Integer upDateUseSp(String tableName,Integer id) throws Exception;
}
