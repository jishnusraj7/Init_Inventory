package com.indocosmo.mrp.web.masters.menu.menudepartment.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;

public class MenuDepartmentDao extends GeneralDao<MenuDepartment> implements IMenuDepartmentDao{

	public MenuDepartmentDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MenuDepartment getNewModelInstance() {
		// TODO Auto-generated method stub
		return new MenuDepartment();
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "menu_departments";
	}
	
	public String getShopDptId(String menuId,String dptId)throws Exception
	{
		String id="";
		CachedRowSet resultSet;
		
		final String sql="select id from "+getTable()+ " WHERE menu_id='"+menuId+"' and department_id='"+dptId+"' and is_deleted=0";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			
			id=resultSet.getString("id");
			
		}
		return id;
	}
	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (departments.code like '%"+searchCriteria+"%' or departments.name like '%"+searchCriteria+"%'  )";
		}
		String sql ="";
		if(adnlFilterPart=="")
		{
			// sql = "SELECT DISTINCT departments.id,departments.`code`,departments.`name`,IFNULL(sync_queue.id ,'') AS quetableId FROM item_classes itm   INNER JOIN departments ON departments.id = itm.department_id LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";

			sql="SELECT DISTINCT departments.id,departments.`code`,departments.`name`,IFNULL(mdp.id,'') as mdpId ,"
					+ "IFNULL(sync_queue.id, '') AS quetableId FROM item_classes itm INNER JOIN departments "
					+ "ON departments.id = itm.department_id LEFT JOIN menu_departments mdp ON (mdp.department_id=departments.id AND mdp.menu_id='' AND "
					+ " (IFNULL(mdp.is_deleted, 0) = 0))  LEFT JOIN sync_queue ON (mdp.id = sync_queue.record_id AND"
					+ " sync_queue.table_name = 'menu_departments')" +wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
			
		}
		else
		{
	     sql="SELECT DISTINCT departments.id,departments.`code`,departments.`name`,IFNULL(mdp.id,'') as mdpId ,"
				+ "IFNULL(sync_queue.id, '') AS quetableId FROM item_classes itm INNER JOIN departments "
				+ "ON departments.id = itm.department_id LEFT JOIN menu_departments mdp ON (mdp.department_id=departments.id AND mdp.menu_id="+adnlFilterPart +" AND "
				+ " (IFNULL(mdp.is_deleted, 0) = 0))  LEFT JOIN sync_queue ON (mdp.id = sync_queue.record_id AND"
				+ " sync_queue.table_name = 'menu_departments')" +wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		}
		return getTableRowsAsJson(sql);
	}

	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		for(DataTableColumns col:columnList){
			if(col.getData().toString().equals("name"))
			{
				adnlFilterPart=col.getSearchValue();
			}
			/*if(col.getSearchValue()!="" &&col.getSearchValue()!=null){
				adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ ""+getTable()+"."+col.getData()+" in ("+(col.getSearchValue()).trim()+")";
			}*/
		}
		return adnlFilterPart;
	}
	
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (departments.code like '%"+searchCriteria+"%' or departments.name like '%"+searchCriteria+"%' ) ";
		}
		String sqlCount="SELECT count(DISTINCT departments.id) as row_count from item_classes itm INNER JOIN departments ON  itm.department_id = departments.id "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	public JsonArray getEditDetails(int id) throws Exception
	{
  final String sql="SELECT * from "+getTable()+" WHERE menu_id="+id+"  and is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}
	
	public void DeleteDepartments(String deptIds,Integer menuId)throws Exception{
		Integer update=0;
		if(deptIds!=null && deptIds!=""){
		
	    final String upQueryMenuDpt="UPDATE menu_departments SET is_deleted=1 WHERE menu_id="+menuId+" and department_id  NOT IN("+deptIds+") and is_deleted=0";

		final String seleQuery="SELECT id FROM "+getTable()+" WHERE menu_id="+menuId+" and department_id  NOT IN("+deptIds+") and is_deleted=0";
		
		beginTrans();
		try{
		
				final CachedRowSet rs=executeSQLForRowset(seleQuery);
				update=executeSQL(upQueryMenuDpt);
		if(update!=0){
				
				while(rs.next()){
					
			 String upQuerySync="UPDATE sync_queue SET crud_action='D' WHERE  table_name='"+getTable()+"' and record_id="+rs.getInt("id")+"";
			executeSQL(upQuerySync);
				
				
				
			}
			
		}
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		
		}	
		
		
	}
	
	public Integer deleteSyncQue(Integer id) throws Exception {
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		st = con.prepareCall("{call usp_menu_syn_delete(?)}");
		// st=con.prepareCall("{call usp_production_finalize(?,?,?,?,?,?)}");
		
		st.setInt(1, id);
		
		
		return st.executeUpdate();
		
	}
	

	

	public JsonArray getmdpJsonArray(int id) throws Exception{
		String sql="SELECT DISTINCT departments.id,departments.`code`,departments.`name`,IFNULL(mdp.id,'') as mdpId ,"
				+ "IFNULL(sync_queue.id, '') AS quetableId FROM item_classes itm INNER JOIN departments "
				+ "ON departments.id = itm.department_id LEFT JOIN menu_departments mdp ON (mdp.department_id=departments.id AND mdp.menu_id="+id +" AND "
				+ " (IFNULL(mdp.is_deleted, 0) = 0))  LEFT JOIN sync_queue ON (mdp.id = sync_queue.record_id AND"
				+ " sync_queue.table_name = 'menu_departments') WHERE (IFNULL(itm.is_deleted,0) = 0)";
						
						return getTableRowsAsJson(sql);
	}

	
}
