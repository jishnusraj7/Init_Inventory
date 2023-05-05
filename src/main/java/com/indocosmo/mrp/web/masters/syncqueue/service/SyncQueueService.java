package com.indocosmo.mrp.web.masters.syncqueue.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.syncqueue.dao.SyncQueueDao;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;

public class SyncQueueService extends GeneralService<SyncQueue, SyncQueueDao> implements ISyncQueueService{

	public SyncQueueDao syncQueueDao=null;
	
	public SyncQueueService(ApplicationContext context) {
		super(context);
		 syncQueueDao=new SyncQueueDao(getContext());
	}

	@Override
	public SyncQueueDao getDao() {
		// TODO Auto-generated method stub
		return syncQueueDao;
	}
	
	public JsonArray getQuetableDet(int itemId,String tableName) throws Exception{
		return syncQueueDao.getQuetableDet(itemId,tableName);
	}
	
	
	public Integer updateSyncQueue(String id,String tableName) throws Exception {
		final String where = "record_id='" + id+"' and table_name='"+tableName+"'";

		Integer is_sync = 1;
		
		is_sync=syncQueueDao.updateSyncQueue(where);
				
				return is_sync;
	}
	
	
	public String fungetSynQueId(String tableName,String recordId)throws Exception {
		
		return syncQueueDao.fungetSynQueId(tableName,recordId);
	}

	public Integer upDateUseSp(String tableName,Integer id) throws Exception {
		// TODO Auto-generated method stub
		return syncQueueDao.upDateUseSp(tableName,id);
	}

	


}
