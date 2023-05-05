package com.indocosmo.mrp.web.masters.timeslot.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.timeslot.dao.TimeslotDao;
import com.indocosmo.mrp.web.masters.timeslot.model.Timeslot;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;

public class TimeslotService extends MasterBaseService<Timeslot, TimeslotDao>{
	private TimeslotDao timeslotDao;
	private PlanningDao planningDao;

	public TimeslotService(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
		timeslotDao=new TimeslotDao(getContext());
		planningDao=new PlanningDao(getContext());
	}

	@Override
	public TimeslotDao getDao() {
		// TODO Auto-generated method stub
		return timeslotDao;
	}

	public JsonArray getEditDetails(int id) throws Exception {
	
		// TODO Auto-generated method stub
		return timeslotDao.getEditDetails(id);
	}
	
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(planningDao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("closing_time_slot_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	
	
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		String wherePart = "";
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<Timeslot> dao = (GeneralDao<Timeslot>) getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				if (planningDao.getTable() == table.getRefrenceTable()) {
					wherePart = "" + table.getRefrenceKey() + "=" + id;
					
				}
				else {
					wherePart = "" + table.getRefrenceKey() + "=" + id
							+ " AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
					
				}
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
				
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			dao.delete(where);
		}
		
		return is_deleted;
	}
	
	

}
