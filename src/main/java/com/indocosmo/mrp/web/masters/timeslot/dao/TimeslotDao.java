package com.indocosmo.mrp.web.masters.timeslot.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.timeslot.model.Timeslot;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;

public class TimeslotDao extends MasterBaseDao<Timeslot> implements ITimeslotDao {
	private PlanningDao planningDao;
	public TimeslotDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
		 planningDao=new PlanningDao(getContext());
	}

	@Override
	public Timeslot getNewModelInstance() {
		// TODO Auto-generated method stub
		return new Timeslot();
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "mrp_time_slot";
	}

	public JsonArray getEditDetails(int id) throws Exception{
	
		final String sql="SELECT "
			      + "id,code,name,description,start_time,end_time ,"
			      + "created_by,created_at,updated_at,updated_by "
			      + "FROM "
			      +  getTable()+" "
			      + "WHERE "
			      + "id="+id+" AND is_deleted='0' ";

   return getTableRowsAsJson(sql);
	}
	
	@Override
	public int getReferenceRowCount(String tableName , String where) throws Exception {
	
		 String sql="";
		 if(tableName.equals(planningDao.getTable()))
		 {
			 sql= "SELECT COUNT(order_id) AS  row_count FROM " + tableName + ((where!=null)? " WHERE " + where:"") + ";"; 
		 }
		 else
		 {
		  sql= "SELECT COUNT(id) AS  row_count FROM " + tableName + ((where!=null)? " WHERE " + where:"") + ";";
		 }
		return excuteSQLForRowCount(sql);
	}
}
