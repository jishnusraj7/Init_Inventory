package com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.model.RPR_hdr;


public class RemoteRequesthdrDao extends GeneralDao<RPR_hdr> implements IRemoteRequesthdrDao{


	/**
	 * @param context
	 */
	public RemoteRequesthdrDao(ApplicationContext context) {
		
		super(context);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public RPR_hdr getNewModelInstance() {
	
		return new RPR_hdr();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "mrp_hq_remote_request_hdr";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		
		final String sql="SELECT * FROM " + getTable();
		
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<RPR_hdr> itemList) throws Exception {
		
		String stockInItemList = "";
		String wherePart = "";

		final Integer stockInId = itemList.get(0).getId();

		for (RPR_hdr stockInItem : itemList) {

			if (stockInItem.getId() != null) {
				
				stockInItemList += ((stockInItemList.isEmpty()) ? "" : ",")
						+ stockInItem.getId();
				}
			}
		
		if (stockInItemList.length() > 0) {
			
			wherePart = " AND stock_item_id not in (" + stockInItemList + ")";
			}
		
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE id=" + stockInId	+ "" + wherePart + ";";
		beginTrans();
		try {
			
			executeSQL(markAsDeletedSQl);
			
			if (stockInItemList.length() > 0)
				
				super.save(itemList);
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ " WHERE id=" + where+	";";
		
		Integer is_deleted = 0;

		beginTrans();
		try {
			
			is_deleted =executeSQL(markAsDeletedSQl);

			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
		return is_deleted;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
	
		if(searchCriteria!="" && searchCriteria!=null){
			
			wherePart="WHERE "
					  + "(request_number LIKE '%"+searchCriteria+"%' "
					  + "OR request_company_name LIKE '%"+searchCriteria+"%' "
					  + "OR submit_date LIKE '%"+searchCriteria+"%' "
					  + "OR request_by LIKE '%"+searchCriteria+"%')";
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
			}
			else{
				
				wherePart=" WHERE "+adnlFilterPart;
			}
		}

		String sql = "SELECT id,"+columns+" FROM "+ getTable()+"  "+wherePart+" "+sortPart+" LIMIT "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{


		if(searchCriteria!="" && searchCriteria!=null){
			
			wherePart="WHERE "
					   + "(request_number LIKE '%"+searchCriteria+"%' "
					   + "OR request_company_name LIKE '%"+searchCriteria+"%' "
					   + "OR submit_date LIKE '%"+searchCriteria+"%')";
		}
		
		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
			}else{
				
				wherePart=" WHERE "+adnlFilterPart;
			}
		}

		String sqlCount="SELECT COUNT(id) as row_count FROM "+ getTable()+" "+wherePart+"";
	
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

}



