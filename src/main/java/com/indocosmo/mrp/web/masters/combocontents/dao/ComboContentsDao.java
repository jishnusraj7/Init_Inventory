package com.indocosmo.mrp.web.masters.combocontents.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.combocontents.model.ComboContents;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;


public class ComboContentsDao extends MasterBaseDao<ComboContents> implements IComboContentsDao {

	/**
	 * @param context
	 */
	public ComboContentsDao(ApplicationContext context) {
		
		super(context);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	
	@Override
	public ComboContents getNewModelInstance() {

		return new ComboContents();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "combo_contents";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(wherePart==null || wherePart==""){
			
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}
		else{
			
			wherePart="WHERE "
					   + "(IFNULL(itm.is_deleted,0) = 0) "
					   + "AND (itm.code LIKE '%"+searchCriteria+"%' "
					   + "OR itm.name LIKE '%"+searchCriteria+"%' "
					   + "OR itm.description like '%"+searchCriteria+"%' )";
		}
		
		String sql = "SELECT "
				     + "created_by,created_at,itm.id,itm.`code`,itm.`name`,itm.description,itm.uom_id,IFNULL(sync_queue.id ,'') AS quetableId "
				     + "FROM "
				     +  getTable()+" itm "
				     + "LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "
				     +  wherePart+" "
				     +  sortPart+" LIMIT "+limitRows+" "
				     + "OFFSET "+offset+"";
	
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(wherePart==null || wherePart==""){
		
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}
		else{

			wherePart="WHERE "
					   + "(IFNULL(itm.is_deleted,0) = 0) "
					   + "AND (itm.code LIKE '%"+searchCriteria+"%' "
					   + "OR itm.name LIKE '%"+searchCriteria+"%' "
					   + "OR itm.description LIKE '%"+searchCriteria+"%') ";
		}
		
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm  "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {
		
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}
	
	public JsonArray getComboContentData(Integer itemid) throws Exception {
		
		String sql="SELECT  sicc.combo_sale_item_id, si.`name` as stock_item_name, si.`code` as  stock_item_code ,"
				+ "sicc.combo_content_item_id,sicc.max_items from  sale_item_combo_contents  sicc "
				+ "left JOIN combo_contents si on si.id=sicc.combo_content_item_id WHERE sicc.combo_sale_item_id="+itemid+"  and sicc.is_deleted=0";
		return getTableRowsAsJson(sql);
	}
	
	
	public JsonArray getComboContentdtlData(Integer itemid) throws Exception {
		
		String sql="SELECT  sicc.combo_sale_item_id, si.`name` as stock_item_name, si.`code` as  stock_item_code ,"
				+ "sicc.combo_content_item_id,sicc.max_items from  sale_item_combo_contents  sicc "
				+ "left JOIN mrp_stock_item si on si.id=sicc.combo_sale_item_id WHERE sicc.combo_sale_item_id="+itemid+"";
		return getTableRowsAsJson(sql);
	}
	
	
}
