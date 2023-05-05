package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.model.PR_hdr;


public class PurchaseRequesthdrDao extends GeneralDao<PR_hdr> implements IPurchaseRequesthdrDao{




	public PurchaseRequesthdrDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public PR_hdr getNewModelInstance() {
		return new PR_hdr();
	}

	@Override
	public String getTable() {
		return "mrp_pr_hdr";
	}

	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();
		return getTableRowsAsJson(sql);
	}

	@Override
	public void save(List<PR_hdr> itemList) throws Exception {
		String stockInItemList = "";
		String wherePart = "";
		final Integer stockInId = itemList.get(0).getId();
		for (PR_hdr stockInItem : itemList) {

			if (stockInItem.getId() != null) {
				stockInItemList += ((stockInItemList.isEmpty()) ? "" : ",")
						+ stockInItem.getId();}}
		if (stockInItemList.length() > 0) {
			wherePart = " AND stock_item_id not in (" + stockInItemList + ")";}
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId	+ "" + wherePart + ";";
		beginTrans();
		try {executeSQL(markAsDeletedSQl);
		if (stockInItemList.length() > 0)
			super.save(itemList);
		endTrans();} catch (Exception e) {
			rollbackTrans();
			throw e;}}


	@Override
	public Integer delete(String where) throws Exception {
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ " WHERE id=" + where+	";";
		Integer is_deleted = 0;

		beginTrans();
		try {
			is_deleted =executeSQL(markAsDeletedSQl);
			endTrans();} catch (Exception e) {
				rollbackTrans();
				throw e;
			}return is_deleted;
	}

	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		for(DataTableColumns col:columnList){
			if(col.getData().equals("pr_number") && col.getSearchValue()!="" && col.getSearchValue()!=null)
			{
				adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ ""+getTable()+"."+col.getData()+" like '%"+(col.getSearchValue()).trim()+"%'";	
			}
			else if(col.getSearchValue()!="" && col.getSearchValue()!=null ){
				adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ ""+getTable()+"."+col.getData()+" = ('"+(col.getSearchValue()).trim()+"')";
			}
		}
		return adnlFilterPart;
	}

	

	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		
		
			if(searchCriteria!="" && searchCriteria!=null){
			wherePart="WHERE (pr_number like '%"+searchCriteria+"%' or create_date like '%"+searchCriteria+"%' or submit_date like '%"+searchCriteria+"%' or status like '%"+searchCriteria+"%'  or request_by like '%"+searchCriteria+"%')";
		}
			
			if(adnlFilterPart!="" && adnlFilterPart!=null){

				if(wherePart!=null && wherePart!=""){
					wherePart+=" and "+adnlFilterPart;
				}else{
					wherePart=" where "+adnlFilterPart;
				}
			}
			
	String sql = "select id,"+columns+" from "+ getTable()+"  "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(searchCriteria!="" && searchCriteria!=null){
			wherePart="WHERE (pr_number like '%"+searchCriteria+"%' or create_date like '%"+searchCriteria+"%' or submit_date like '%"+searchCriteria+"%' or status like '%"+searchCriteria+"%'  or request_by like '%"+searchCriteria+"%')";
		}
		String sqlCount="select count(id) as row_count from "+ getTable()+" "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	
}



