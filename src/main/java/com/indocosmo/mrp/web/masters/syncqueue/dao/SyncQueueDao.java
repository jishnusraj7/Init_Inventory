package com.indocosmo.mrp.web.masters.syncqueue.dao;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;

public class SyncQueueDao extends GeneralDao<SyncQueue> implements ISyncQueueDao{

	public SyncQueueDao(ApplicationContext context) {
		super(context);
		
	}

	@Override
	public SyncQueue getNewModelInstance() {
		// TODO Auto-generated method stub
		return new SyncQueue();
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return "sync_queue";
	}
	
	
	public JsonArray getQuetableDet(int itemId,String tableName) throws Exception{
		final String sql = "select * from "+ getTable() + " where record_id=" + itemId+ " and table_name='"+tableName+"' ";
		
		return getTableRowsAsJson(sql);
	}
	
	
	
	public Integer updateSyncQueue(String  where) throws Exception {
		
		final String sqlSelect="select * from " + getTable() +" WHERE "+where;
		CachedRowSet rs= getRowSet(sqlSelect);
		Integer is_sync = 0;
		if(rs!=null){


		final String sql="UPDATE " + getTable()+  " SET crud_action='D' " +" WHERE "+where;
		

		beginTrans();
		try{

			is_sync = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		
	}
		
		return is_sync;

	}
	
	public String fungetSynQueId(String tableName,String recordId)throws Exception
	{
		String id="";
		CachedRowSet resultSet;
		
		final String sql="select id from "+getTable()+ " WHERE record_id='"+recordId+"' and table_name='"+tableName+"'";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			
			id=resultSet.getString("id");
			
		}
		return id;
	}
	
	public Integer upDateUseSp(String tableName,Integer id)throws Exception {
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		/*st = con.prepareCall("{call usp_sync_update(?,?)}");*/
		st = con.prepareCall("{call usp_sync_delete(?,?)}");
		st.setString(1, tableName);
		st.setInt(2, id);
		
		
		return st.executeUpdate();
		
	}
}
