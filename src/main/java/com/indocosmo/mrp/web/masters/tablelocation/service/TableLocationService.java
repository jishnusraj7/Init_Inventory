package com.indocosmo.mrp.web.masters.tablelocation.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.tablelocation.dao.TableLocationDao;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;


public class TableLocationService extends GeneralService<TableLocation,TableLocationDao> implements ITableLocationService{
	
	
	//private IItemMasterDao itemMasterDao;
	
	private TableLocationDao itemmasterDao;
	

	
	public TableLocationService(ApplicationContext context) {
		super(context);
		itemmasterDao = new TableLocationDao(getContext());
		
	}

	
	@Override
	public TableLocationDao getDao() {

		return itemmasterDao;
	}
	
	/*@Override
	public Integer delete(String where) throws Exception {
		where = "id="+where;
		
		return super.delete(where);
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer delete(String id) throws Exception {
		
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<TableLocation> dao = (GeneralDao<TableLocation>) getDao();
		
		int rowCount = 0;
		 String wherePart="";
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				
					 wherePart = ""+table.getRefrenceKey()+"="+ id +" ;";

				
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			itemmasterDao.delete(where);
		}
		
		return is_deleted;
}


	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		
		ApplicationContext context = itemmasterDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		if(context.getCompanyInfo().getId()!=0){
	/*		referenceTables.add(usersDao.getTable());*/
			
			}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			
			
		}
			return referenceTableDetails;
	  }

	public TableLocation saveItem(TableLocation item) throws Exception {		
		GeneralDao<TableLocation> dao =  getDao();
		if (item.getId() == null || item.getId().equals(""))
			dao.insert(item);
		else
			dao.update(item);
		return item;
	}
	
	
	
public JsonArray getdpData(String table)  throws Exception{

	return itemmasterDao.getdpData(table);
}


/**
 * @param id
 * @param string
 * @return
 */
public JsonArray updateBgImage(int id, String imagePath) throws Exception {
	// TODO Auto-generated method stub
	return itemmasterDao.updateBgImage(id,imagePath);
}


/**
 * @param id
 */
public void deleteBgImage(int id) throws Exception {
	// TODO Auto-generated method stub
	 itemmasterDao.deleteBgImage(id);
}


public String getBgImage(int id) throws Exception {
	// TODO Auto-generated method stub
	return itemmasterDao.getBgImage(id);

}


/* (non-Javadoc)
 * @see com.indocosmo.mrp.web.masters.tablelocation.service.ITableLocationService#saveItem(com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster)
 */





	
}
