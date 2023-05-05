package com.indocosmo.mrp.web.production.planning.planningdetail.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;



public class PlanningDetailDao extends GeneralDao<PlanningDetail> implements IPlanningDetailDao {



	public PlanningDetailDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public PlanningDetail getNewModelInstance() {

		return new PlanningDetail();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "order_dtls_booking";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		String Sql="Select * from "+getTable()+" WHERE (is_deleted=0 OR is_deleted=NULL) ";
		return getTableRowsAsJson(Sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.planningdetail.dao.IPlanningDetailDao#deletedtlId(java.lang.String)
	 */
	public void deletedtlId(String deletdtlId) throws Exception {
		JsonParser parser = new JsonParser();
		JsonArray deletedtlId = (JsonArray) parser.parse(deletdtlId);
		String deletdtl="";

		for (int i = 0; i < deletedtlId.size(); i++) {

			deletdtl += deletedtlId.get(i).getAsInt() + (((deletedtlId.size()-1) ==i) ? "" : ",");
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE id in (" + deletdtl+	 ");";

		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}else{
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%')";
		}
		String sql = "SELECT itm.id,itm.`code`,itm.`name` FROM "+getTable()+" itm "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}else{
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%')";
		}
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<PlanningDetail> itemList) throws Exception {

		final Integer stockOutId = itemList.get(0).getOrder_id();		
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE order_id = "+stockOutId+";";


		beginTrans();
		try {		
			executeSQL(markAsDeletedSQl);
			for (PlanningDetail stockOutItem : itemList) {

				if (stockOutItem.getId() != null && stockOutItem.getId() != null) {
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
