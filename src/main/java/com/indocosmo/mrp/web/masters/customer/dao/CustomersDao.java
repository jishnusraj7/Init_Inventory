package com.indocosmo.mrp.web.masters.customer.dao;


import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;



/**
 * @author jo
 *
 */
public class CustomersDao extends GeneralDao<Customers> implements ICustomersDao{


	public CustomersDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Customers getNewModelInstance() {

		return new Customers();
	}

	@Override
	public String getTable() {

		return "customers";
	}

	@Override
	public Integer delete(String where) throws Exception {
		// TODO Auto-generated method stub
		return super.delete(where);
	}
	
	public JsonArray getCustomerDataByShopId(Integer shop_id) throws Exception {

		final String sql="SELECT is_ar,ar_code,customer_type,card_no   from "+ getTable() + " WHERE shop_id="+shop_id+ " and (IFNULL(is_deleted,0) = 0)";

		return getTableRowsAsJson(sql);
	}
	
	



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {

		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(tbl1.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(tbl1.is_deleted,0) = 0) and (tbl1.code like '%"+searchCriteria+"%' or tbl1.name like '%"+searchCriteria+"%' or tbl1.card_no like '%"+searchCriteria+"%' )";
		}


		String sql="SELECT	tbl1.id,tbl1.code,tbl1.`name`,tbl1.customer_type,tbl1.is_valid,tbl1.card_no,tbl1.address,tbl1.cst_no,tbl1.license_no,tbl1.tin,"
				+ "tbl1.street,tbl1.city,tbl1.state,tbl1.country,tbl1.zip_code, IFNULL(sync_queue.id ,'') AS quetableId,"
				+ "tbl1.phone,tbl1.fax,tbl1.email,tbl1.is_ar,tbl1.ar_code,tbl1.accumulated_points,"
				+ "tbl1.redeemed_points,tbl1.is_system,tbl1.created_at,tbl1.created_by,tbl2.name as cust_type_name"
				+ " FROM "+getTable()+" tbl1 "
				+ " LEFT JOIN sync_queue  ON (tbl1.id=sync_queue.record_id AND sync_queue.table_name='"+ getTable()+ "')"
				+ "left join customer_types tbl2 on tbl1.customer_type = tbl2.id  " +wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) and (code like '%"+searchCriteria+"%' or name like '%"+searchCriteria+"%' or card_no like '%"+searchCriteria+"%' )";
		}
		String sqlCount="SELECT count(id) as row_count from  "+getTable()+" " +wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	public String getCustomerCode(Integer id) throws Exception
	{
		String sq="select * from "+getTable()+" where id="+id+"";
		List<Customers> customer=buildItemList(sq);
		//return customer.get(0).getCode();
		return customer.get(0).getName();

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getReferenceRowCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int  getReferenceRowCount(String tableName,String where) throws Exception {
		PlanningDao planningDao1=new PlanningDao(getContext());
		String sql="";
		if(tableName.equals(planningDao1.getTable()))
		{
			sql= 	"SELECT COUNT(order_id) AS  row_count FROM " + tableName + ((where!=null)? " WHERE " + where:"") + ";";
		}
		else
		{
			sql= "SELECT COUNT(id) AS  row_count FROM " + tableName + ((where!=null)? " WHERE " + where:"") + ";";
		}

		return excuteSQLForRowCount(sql);
	}

}