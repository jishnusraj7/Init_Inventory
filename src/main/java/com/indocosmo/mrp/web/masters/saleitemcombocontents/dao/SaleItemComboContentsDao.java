package com.indocosmo.mrp.web.masters.saleitemcombocontents.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.model.SaleItemComboContents;


public class SaleItemComboContentsDao extends GeneralDao<SaleItemComboContents> implements ISaleItemComboContentsDao {

	/**
	 * @param context
	 */
	public SaleItemComboContentsDao(ApplicationContext context) {
		
		super(context);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	
	@Override
	public SaleItemComboContents getNewModelInstance() {

		return new SaleItemComboContents();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "sale_item_combo_contents";
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
				+ "left JOIN mrp_stock_item si on si.id=sicc.combo_sale_item_id WHERE sicc.combo_sale_item_id="+itemid+"";
		return getTableRowsAsJson(sql);
	}
	
	
	public JsonArray getComboContentdtlData(Integer itemid) throws Exception {
		
		String sql="SELECT  sicc.combo_sale_item_id, si.`name` as stock_item_name, si.`code` as  stock_item_code ,"
				+ "sicc.combo_content_item_id,sicc.max_items from  sale_item_combo_contents  sicc "
				+ "left JOIN mrp_stock_item si on si.id=sicc.combo_sale_item_id WHERE sicc.combo_sale_item_id="+itemid+"";
		return getTableRowsAsJson(sql);
	}
	

@Override
public Integer delete(String where) throws Exception {

	// TODO Auto-generated method stub
	return super.delete(where);
}

public Integer deleteSaleItem(SaleItemComboContents combo_content_item_id) throws Exception {
	final String sql="UPDATE " + getTable()+  " SET is_deleted=1 " +" WHERE  combo_content_item_id="+combo_content_item_id.getCombo_content_item_id();
	
	final String sql1="UPDATE  sale_item_combo_substitutions SET is_deleted=1 " +" WHERE  sale_item_combo_content_id="+combo_content_item_id.getId();
	
	
	
	
	Integer is_deleted = 0;
Integer is_deleted1=0;
	beginTrans();
	try{
		is_deleted1 = executeSQL(sql1);
		is_deleted = executeSQL(sql);
	

		endTrans();
	}catch (Exception e){

		rollbackTrans();
		throw e;
	}

	return is_deleted;
	
}


public Integer deleteSaleItemCombo(SaleItemComboContents combo_content_item_id) throws Exception {
	/*final String sql="UPDATE " + getTable()+  " SET is_deleted=1 " +" WHERE  combo_content_item_id="+combo_content_item_id.getCombo_content_item_id();
	
	final String sql1="UPDATE  sale_item_combo_substitutions SET is_deleted=1 " +" WHERE  sale_item_combo_content_id="+combo_content_item_id.getId();
	*/
	
	final String sql="UPDATE " + getTable()+  " SET is_deleted=1 " +" WHERE  combo_sale_item_id="+combo_content_item_id.getCombo_sale_item_id();
	
/*	final String sql1="UPDATE  sale_item_combo_substitutions SET is_deleted=1 " +" WHERE  sale_item_combo_content_id="+combo_content_item_id.getCombo_content_item_id();
*/	final String updateSql="UPDATE  sale_item_combo_substitutions ss  SET is_deleted=1 WHERE  ss.sale_item_combo_content_id in (select sic.id from sale_item_combo_contents sic  where sic.combo_sale_item_id="+combo_content_item_id.getCombo_sale_item_id()+")";
	
	
	Integer is_deleted = 0;
Integer is_deleted1=0;
	beginTrans();
	try{
		is_deleted1 = executeSQL(updateSql);
		is_deleted = executeSQL(sql);
	

		endTrans();
	}catch (Exception e){

		rollbackTrans();
		throw e;
	}

	return is_deleted;
	
}




	
}
