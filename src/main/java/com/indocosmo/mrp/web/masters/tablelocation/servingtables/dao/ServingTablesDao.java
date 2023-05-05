package com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.model.ServingTables;



public class ServingTablesDao extends GeneralDao<ServingTables> implements IServingTablesDao{


	public ServingTablesDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public ServingTables getNewModelInstance() {

		return new ServingTables();
	}

	@Override
	public String getTable() {

		return "serving_tables";
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		return null;
	}

	
	
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		return null;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getUpdatedHqTableRowJson()
	 */
	

	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		return null;
	}

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		return null;
	}
	
	
	
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		for(DataTableColumns col:columnList){
			
			if(col.getSearchValue()!="" &&col.getSearchValue()!=null){
				
			
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ "si."+col.getData()+" like '%"+col.getSearchValue()+"%'";	
							
				
			}
		}
		return adnlFilterPart;
	}
	
	public JsonArray getdpData(String table)  throws Exception{
		
		 final String sql="SELECT id,code,name from " +table+ " WHERE (is_deleted=0 OR is_deleted IS NULL)";
			
			return getTableRowsAsJson(sql);
		
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.IServingTablesDao#getTableImages(java.lang.Integer)
	 */
	@Override
	public JsonArray getTableImages(Integer servingTableLocId) throws Exception {
		// TODO Auto-generated method stub
		final String sql1="SELECT "
				+ "s.id as id,s.code as code,s.name as name,s.covers as covers,s.row_position as row_position,"
				+ "s.column_position as column_position, s.layout_image as layout_image,img.image as image,"
				+ "img.height as height,img.width as width,"
				+ "s.serving_table_location_id as serving_table_location_id,"
				+ "s.created_by as created_by,s.created_at as created_at "
				+ "FROM "+getTable()+" s INNER JOIN serving_table_images"
				+ " img ON img.id=s.layout_image WHERE s.serving_table_location_id="+servingTableLocId+""
				+ " AND (IFNULL(is_deleted,0) = 0)";
		return getTableRowsAsJson(sql1);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.IServingTablesDao#deleteSyncQueueRecord(java.lang.Integer)
	 */
	@Override
	public Integer deleteSyncQueueRecord(Integer tbl_loc_id) throws Exception {
		Connection con = null;
		ResultSet rs=null;
		Statement stmt=null;
		Statement stmt1=null;
		Integer success=0;
		con =this.dbHelper.getConnection();
		stmt=con.createStatement();
		
		final String sql1="SELECT id FROM "+getTable()+" WHERE serving_table_location_id ="+tbl_loc_id+"";
		rs=stmt.executeQuery(sql1);
		while(rs.next())
		{
			stmt1=con.createStatement();
			String sql="Update sync_queue set crud_action='D' where record_id="+rs.getInt("id")+" and table_name='"+getTable()+"'";
			success=stmt1.executeUpdate(sql);
			stmt1.close();
		}
		
		return success;
	}


	/**
	 * @param id
	 * @return
	 */
	
	
	
	
	
	
}