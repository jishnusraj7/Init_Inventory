package com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.model.SaleItemComboContentsSubstituion;


public class SaleItemComboContentsSubstituionDao extends GeneralDao<SaleItemComboContentsSubstituion> implements ISaleItemComboContentsSubstituionDao {

	/**
	 * @param context
	 */
	public SaleItemComboContentsSubstituionDao(ApplicationContext context) {
		
		super(context);

	}
	
	@Override
	public JsonArray getComboContentList(String combo_content_id) throws Exception{

	final String sql="SELECT combo_content_substitutions.*,si.code as stock_item_code ,0 as flag,si.name as stock_item_name FROM combo_content_substitutions  "
			+ "LEFT join mrp_stock_item si  on si.id= combo_content_substitutions.substitution_sale_item_id  WHERE  combo_content_id ="+combo_content_id+" ";

		return getTableRowsAsJson(sql);
	}
	
	
	

	
	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	
	@Override
	public SaleItemComboContentsSubstituion getNewModelInstance() {

		return new SaleItemComboContentsSubstituion();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "sale_item_combo_substitutions";
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
	
	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<SaleItemComboContentsSubstituion> itemList) throws Exception {
		String stockODtlIdList = "";
		String wherePart = "";

		final Integer stockOutId = itemList.get(0).getSale_item_combo_content_id();
		for (SaleItemComboContentsSubstituion stockOutItem : itemList) {

			if (stockOutItem.getId() != null) {
				stockODtlIdList += ((stockODtlIdList.isEmpty()) ? "" : ",")
						+ stockOutItem.getId();
			}
		}
		if (stockODtlIdList.length() > 0) {
			if(!stockODtlIdList.equals("null")){
				wherePart = " AND id not in (" + stockODtlIdList + ")";
			}else{
				wherePart = "";}}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE sale_item_combo_content_id=" + stockOutId	+ "" + wherePart + ";";

		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);

			for (SaleItemComboContentsSubstituion stockOutItem : itemList) {

				if (stockOutItem.getSale_item_combo_content_id() != null && stockOutItem.getId() != null) {
					super.update(stockOutItem);
				}else{
					super.insert(stockOutItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}

	
	
}
