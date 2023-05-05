package com.indocosmo.mrp.web.masters.menu.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.menu.dao.MenuDao;
import com.indocosmo.mrp.web.masters.menu.model.Menu;

public class MenuService  extends MasterBaseService<Menu, MenuDao> implements IMenuService{

	private MenuDao menuDao;
	
	public MenuService(ApplicationContext context) {
		super(context);
		menuDao=new MenuDao(getContext());
	}

	@Override
	public MenuDao getDao() {
		// TODO Auto-generated method stub
		return menuDao;
	}
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		/*referenceTables.add(customersDao.getTable());*/
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("ar_code");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<Menu> dao =  getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			menuDao.delete(where);
		}
		
		return is_deleted;
	}
	public JsonArray getEditDetails(int id) throws Exception{
		return menuDao.getEditDetails(id);
		
	}
	
	public List<Menu> getExcelData() throws Exception {
	
	return menuDao.getExcelData();
}


}
