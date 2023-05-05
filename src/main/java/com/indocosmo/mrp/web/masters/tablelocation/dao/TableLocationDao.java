package com.indocosmo.mrp.web.masters.tablelocation.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;



public class TableLocationDao extends GeneralDao<TableLocation> implements ITableLocationDao{


	public TableLocationDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public TableLocation getNewModelInstance() {

		return new TableLocation();
	}

	@Override
	public String getTable() {

		return "serving_table_location";
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";

		return getTableRowsAsJson(sql);
	}

	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
/*	@Override
	public JsonArray getMastersRowJson() throws Exception {
		final String sql = "SELECT	si.id,si.code,si.name,si.input_tax_id,vw.tax_pc AS tax_percentage,si.valuation_method,si.is_active,si.is_manufactured ,uoms.`code` as uomcode "
				+"FROM stock_item si  LEFT JOIN (SELECT id,tax_pc,valid_from FROM vw_stockitem_tax Where vw_stockitem_tax.valid_from < NOW() "
				+" ORDER BY valid_from DESC) vw ON si.id = vw.id  LEFT JOIN uoms ON si.uom_id=uoms.`id`  WHERE si.is_system = 0 AND si.is_deleted = 0 AND si.is_active = 1 AND si.is_manufactured = 0 ";

		return getTableRowsAsJson(sql);
	}*/

	
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";

		return getTableRowsAsJson(sql);
	}
	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(si.is_deleted,0) = 0)";
		}else{
			if(searchCriteria!="" && searchCriteria!=null)
			wherePart="where (IFNULL(si.is_deleted,0) = 0) and (si.code like '%"+searchCriteria+"%' or si.name like '%"+searchCriteria+"%' )";
		}
		
		
		String sql = "SELECT "
					  + "si.id as id,si.code,si.name,si.sc_amount,si.is_sc_percentage,si.is_auto_layout,si.created_by,"
					  + "si.created_at,si.publish_status,si.apply_service_charge,si.bg_image "
					  + "FROM "+ getTable()+" si "+wherePart+" "+sortPart+" "
					  + "LIMIT "+limitRows+" OFFSET "+offset+"";
		
		return getTableRowsAsJson(sql);
	}

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(si.is_deleted,0) = 0)";
		}else{		
			if(searchCriteria!="" && searchCriteria!=null)
			wherePart="where (IFNULL(si.is_deleted,0) = 0) and (si.code like '%"+searchCriteria+"%' or si.name like '%"+searchCriteria+"%' )";
		}
		
		String sqlCount="SELECT COUNT(si.id) AS row_count FROM "+ getTable()+" si "+wherePart+"";
		
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
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
		
		 final String sql="SELECT id,code,name FROM " +table+ " WHERE (is_deleted=0 OR is_deleted IS NULL)";
			
			return getTableRowsAsJson(sql);
		
	}


	/**
	 * @param id
	 * @param imagePath
	 * @return
	 * @throws Exception 
	 */
	public JsonArray updateBgImage(int id, String imagePath) throws Exception {
		int flag;
		final String sql="UPDATE "+getTable()+" SET bg_image='"+imagePath+"' WHERE id="+id+"";
		final String sql1="select * from "+getTable()+" where id="+id+"";
		
		flag=executeSQL(sql);
		if (flag !=0)
		{
			return getTableRowsAsJson(sql1);
		}else
			return null;
	}


	/**
	 * @param id
	 * @return
	 */
	public void deleteBgImage(int id) throws Exception{
		// TODO Auto-generated method stub
		final String sql="UPDATE "+getTable()+" SET bg_image=NULL WHERE id="+id+"";
		executeSQL(sql);
	}


	public String getBgImage(int id) throws Exception {
		// TODO Auto-generated method stub
		String bg_img=null;
		final String sql1="SELECT bg_image from "+getTable()+" where id="+id+"";
		
		JsonArray jArray=getTableRowsAsJson(sql1);
		if( jArray.size() !=0)
		{
			JsonObject json = (JsonObject) jArray.get(0);
			bg_img=json.get("bg_image").getAsString();
			bg_img=bg_img.split("/")[bg_img.split("/").length-1];
		}
		
		return bg_img;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.tablelocation.dao.ITableLocationDao#getItemData()
	 */
	
	
	
	
	
	
}