package com.indocosmo.mrp.web.masters.saleitemchoices.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.saleitemchoices.model.SaleItemChoices;

public class SaleItemChoicesDao  extends GeneralDao<SaleItemChoices> implements ISaleItemChoicesDao{

	public SaleItemChoicesDao(ApplicationContext context) {
		super(context);
		
	}

	@Override
	public SaleItemChoices getNewModelInstance() {
		// TODO Auto-generated method stub
		return new SaleItemChoices();
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "sale_item_choices";
	}
	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(TBL.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(TBL.is_deleted,0) = 0) and (TBL.saleItemName like '%"+searchCriteria+"%' or TBL.choiceItemName like '%"+searchCriteria+"%' or TBL.free_items like '%"+searchCriteria+"%' )";
		}
		//String sql = "SELECT created_by,created_at,itm.id,itm.`sale_item_id`,itm.`choice_id`,itm.free_items,IFNULL(sync_queue.id ,'') AS quetableId FROM "+getTable()+" itm LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		
		String sql = " SELECT "
				+ "TBL.free_items,TBL.id,TBL.choiceItemName,TBL.saleItemName,TBL.`is_deleted`"
				+ " FROM "
				+ "(SELECT "
				+ "	itm.free_items,	itm.id as id, ch.`name` AS  choiceItemName, itm.is_deleted,"
				+ "stk.`name` as saleItemName ,IFNULL(sync_queue.id ,'') AS quetableId "
				+ "FROM "+getTable()+" itm "
				+ "INNER JOIN "
				+ "choices ch ON itm.choice_id=ch.id  INNER JOIN mrp_stock_item stk ON stk.id=itm.sale_item_id "
				+ "LEFT JOIN "
				+ "sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')) TBL "+wherePart+"  limit "+limitRows+" OFFSET "+offset+"";

		return getTableRowsAsJson(sql);
	}

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(TBL.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(TBL.is_deleted,0) = 0) and (TBL.saleItemName like '%"+searchCriteria+"%' or TBL.choiceItemName like '%"+searchCriteria+"%' or TBL.free_items like '%"+searchCriteria+"%') ";
		}
		
		
		String sqlCount=" SELECT "
				+ "count(TBL.id) as row_count,TBL.free_items,TBL.id,TBL.choiceItemName,TBL.saleItemName,TBL.`is_deleted`"
				+ " FROM "
				+ "(SELECT 	itm.free_items,	itm.id as id, ch.`name` AS  choiceItemName, itm.is_deleted,"
				+ "stk.`name` as saleItemName ,IFNULL(sync_queue.id ,'') AS quetableId "
				+ "FROM "+getTable()+" itm "
				+ "INNER JOIN "
				+ "choices ch ON itm.choice_id=ch.id  INNER JOIN mrp_stock_item stk ON stk.id=itm.sale_item_id "
				+ "LEFT JOIN "
				+ "sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')) TBL "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	
	public JsonArray getEditDetails(int id) throws Exception
	{
  final String sql="SELECT * from "+getTable()+" WHERE id="+id+"  and is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}
	
	
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}
	
	
	public List<SaleItemChoices> getExcelData() throws Exception {
		List<SaleItemChoices> saleItemChoicesList=null;
	
			String SQL="SELECT "
					+ "sic.*,ch.`name` as choice_name,si.`name` as item_name "
					+ "FROM sale_item_choices sic "
					+ "INNER JOIN choices ch ON ch.id=sic.choice_id "
					+ "INNER JOIN mrp_stock_item si ON si.id=sic.sale_item_id "
					+ "WHERE sic.is_deleted=0";
		CachedRowSet rs= getRowSet(SQL);
		SaleItemChoices saleItemChoices;
		if(rs!=null){
			saleItemChoicesList=new ArrayList<SaleItemChoices>();
			while(rs.next()){		
				saleItemChoices=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, saleItemChoices);
				saleItemChoices.setSaleItemName(rs.getString("item_name"));
				saleItemChoices.setChoiceName(rs.getString("choice_name"));
				saleItemChoicesList.add(saleItemChoices);
			}
		}	

		return saleItemChoicesList;
	}
	
	
	

}
