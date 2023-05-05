package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;

import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.dao.TableImageDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model.TableImage;

import com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.ServingTablesDao;




public class TableImageService extends GeneralService<TableImage,TableImageDao> implements ITableImageService{	
	
	//private IItemMasterDao itemMasterDao;
	
	private TableImageDao imageDao;
	private ServingTablesDao tableDao;
	
	
	
	public TableImageService(ApplicationContext context) {
		super(context);
		imageDao = new TableImageDao(getContext());
		tableDao=new ServingTablesDao(getContext());
	}

	
	@Override
	public TableImageDao getDao() {

		return imageDao;
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

		GeneralDao<TableImage> dao = (GeneralDao<TableImage>) getDao();

		int rowCount = 0;

		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {

			
					final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
					rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);

				

			}
		}

		if (rowCount != 0) {

			is_deleted = 0;
		}

		if (referenceTables == null || rowCount == 0) {

			super.delete(where);
		}

		return is_deleted;
	}





	


	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception{
	
				
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(tableDao.getTable());

		for (String table : referenceTables) {

			RefereneceModelBase referenceModel=new RefereneceModelBase();
			
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("layout_image");
			referenceTableDetails.add(referenceModel);
			
			
		}

		return referenceTableDetails;
	}
	
	
	
	public JsonArray getItemDetails(int itemId) throws Exception{
		return imageDao.getItemDetails(itemId);
	}

	public JsonArray getDataToEditChoice(String itemId) throws Exception{
		return imageDao.getDataToEditChoice(itemId);
	}
	

	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportList()  throws Exception{

		return imageDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportUpdatedList()  throws Exception{

		return imageDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<TableImage> getDataToImport()throws Exception{
		return imageDao.getHqTableRowListToImport();
	}
	
	

public JsonArray getdpData(String table)  throws Exception{

	return imageDao.getdpData(table);
}


/* (non-Javadoc)
 * @see com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service.ITableImageService#saveItem(com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation)
 */
@Override
public TableImage saveItem(TableImage item) throws Exception {		
	GeneralDao<TableImage> dao =  getDao();
	if (item.getId() == null || item.getId().equals(""))
		dao.insert(item);
	else
		dao.update(item);
	return item;
}


/* (non-Javadoc)
 * @see com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service.ITableImageService#saveItem(com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation)
 */



	
}
