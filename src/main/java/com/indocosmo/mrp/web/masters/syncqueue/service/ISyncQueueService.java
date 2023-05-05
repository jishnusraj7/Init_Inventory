package com.indocosmo.mrp.web.masters.syncqueue.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.syncqueue.dao.SyncQueueDao;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;

public interface ISyncQueueService extends IGeneralService<SyncQueue, SyncQueueDao> {
	public JsonArray getQuetableDet(int itemId,String tableName) throws Exception;
	public String fungetSynQueId(String tableName,String recordId)throws Exception;
	public Integer upDateUseSp(String tableName,Integer id) throws Exception;
}
