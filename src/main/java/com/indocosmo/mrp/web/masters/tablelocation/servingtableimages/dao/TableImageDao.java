package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.dao;


import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model.TableImage;



public class TableImageDao extends GeneralDao<TableImage> implements ITableImageDao{


	public TableImageDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public TableImage getNewModelInstance() {

		return new TableImage();
	}

	@Override
	public String getTable() {

		return "serving_table_images";
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "";

		return getTableRowsAsJson(sql);
	}

	public JsonArray getItemDetails(int itemId) throws Exception{
		final String sql = "select `id`, `code`, `name`, `description`, `item_thumb`, `uom_id`, `is_batch`, `is_manufactured`, `bom_qty`, `is_sellable`, `is_group_item`, `group_item_id`, `is_combo_item`, `is_open`, `is_require_weighing`,"
				+ " `sub_class_id`, `kitchen_id`, `choice_ids`, `profit_category_id`, `name_to_print`, `alternative_name`, `alternative_name_to_print`, `fg_color`, `bg_color`, `attrib1_name`, `attrib1_options`, `attrib2_name`, `attrib2_options`, `attrib3_name`,"
				+ "`attrib3_options`, `attrib4_name`, `attrib4_options`, `attrib5_name`, `attrib5_options`, `movement_method`, `valuation_method`, `pack_contains`, `shelf_life`, `input_tax_id`,`output_tax_id`, `tax_calculation_method`, `min_stock`, `max_stock`, `std_purchase_qty`,"
				+ " `pref_supplier_id`, `lead_time`,`is_active`,`is_synchable`,`item_category_id`,`output_tax_id_home_service`,`output_tax_id_table_service`,`output_tax_id_take_away_service`,`taxation_based_on`,`item_cost`,`fixed_price`,`sys_sale_flag` from "
				+ getTable() + " where id=" + itemId;
		
		return getTableRowsAsJson(sql);
	}
	
	
	public JsonArray getDataToEditChoice(String itemId) throws Exception{
		final String sql ="select id,code,name from choices where id in("+itemId+") and is_deleted=0";

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
		

		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getUpdatedHqTableRowListToImport()
	 */
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(si.is_deleted,0) = 0)";
		}else{
			if(searchCriteria!="" && searchCriteria!=null)
			wherePart="where (IFNULL(si.is_deleted,0) = 0) and (si.code like '%"+searchCriteria+"%' or si.name like '%"+searchCriteria+"%' )";
		}
		
		String sql = "select  "
					+ "si.id as id,si.code,si.name from "+ getTable()+" si "+wherePart+" "+sortPart+" LIMIT "+limitRows+" OFFSET "+offset+"";
			
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
		
		String sqlCount="select count(si.id) as row_count from "+ getTable()+" si "+wherePart+"";
		
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
		/*String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/serving_table_images/";
		String rootDirectory = rootPath  + "/webapps/" ;*/
		 final String sql="SELECT  id,image from " +getTable()+" ";
			
			return getTableRowsAsJson(sql);
		
	}

	
	public Integer delete(String  where) throws Exception {

		final String sql="DELETE FROM " + getTable()+ " WHERE "+where;
		Integer is_deleted = 0;

		beginTrans();
		try{

			is_deleted = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	
	
	
	
	
	
}