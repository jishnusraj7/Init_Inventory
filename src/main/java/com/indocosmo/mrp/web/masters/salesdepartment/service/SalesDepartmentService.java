package com.indocosmo.mrp.web.masters.salesdepartment.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.menu.menudepartment.dao.MenuDepartmentDao;
import com.indocosmo.mrp.web.masters.salesdepartment.dao.SalesDepartmentDao;
import com.indocosmo.mrp.web.masters.salesdepartment.model.SalesDepartment;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.ShopDepartmentDao;

public class SalesDepartmentService extends MasterBaseService<SalesDepartment, SalesDepartmentDao> implements ISalesDepartmentService{
	
	private SalesDepartmentDao salesDepartmentDao;
	private MenuDepartmentDao menuDepartmentDao;
	private ShopDepartmentDao shopDepartmentDao;
	
	public SalesDepartmentService(ApplicationContext context) {
		super(context);
		salesDepartmentDao=new SalesDepartmentDao(getContext());
		menuDepartmentDao=new MenuDepartmentDao(getContext());
		shopDepartmentDao=new ShopDepartmentDao(getContext());
	
	}
	
	
	@Override
	public SalesDepartmentDao getDao() {
	
		return salesDepartmentDao ;
	}

	public JsonArray getEditDetails(int id) throws Exception{
		return salesDepartmentDao.getEditDetails(id);
		
	}

	@Override
	public JsonArray getMastersRowJson() throws Exception {
		// TODO Auto-generated method stub
		return salesDepartmentDao.getMastersRowJson();
	}
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(menuDepartmentDao.getTable());
		referenceTables.add(shopDepartmentDao.getTable());
		
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("department_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<SalesDepartment> dao =  getDao();
		
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
			
			salesDepartmentDao.delete(where);
		}
		
		return is_deleted;
	}
}
