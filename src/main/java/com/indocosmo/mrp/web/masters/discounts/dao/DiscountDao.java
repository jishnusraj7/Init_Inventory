package com.indocosmo.mrp.web.masters.discounts.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.discounts.model.Discount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.service.SaleItemDiscountService;





public class DiscountDao extends GeneralDao<Discount> implements IDiscountDao{


	public DiscountDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Discount getNewModelInstance() {

		return new Discount();
	}

	@Override
	public String getTable() {

		return "discounts";
	}

	@Override
	public Integer delete(String where) throws Exception {
		// TODO Auto-generated method stub
		return super.delete(where);
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
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) and (code like '%"+searchCriteria+"%' or name like '%"+searchCriteria+"%' or price like '%"+searchCriteria+"%' )";
		}


		String sql = "SELECT "
					+ "dis.id as id,dis.code as code,dis.name as name,dis.description as description,"
					+ "dis.account_code as account_code ,dis.is_deleted as is_deleted,ifnull(dis.price,'') as price,dis.is_system as is_system, "
					+ "dis.is_percentage as is_percentage,dis.is_promotion as is_promotion,dis.is_overridable as is_overridable,"
					+ "dis.is_item_specific as is_item_specific,dis.grouping_quantity as grouping_quantity,"
					+ "dis.allow_editing as allow_editing,dis.is_valid as is_valid,dis.date_from as date_from,"
					+ "dis.date_to as date_to,IFNULL(sync_queue.id ,'') AS quetableId,"
					+ "dis.time_from as time_from,dis.time_to as time_to,dis.week_days as week_days,dis.created_by as created_by"
					+ ",dis.created_at as created_at,ifnull(dis.disc_password,'') as disc_password,dis.permitted_for as permitted_for,"
					+ "("
					+ "CASE(allow_editing) "
					+ " WHEN 0 THEN 'UNIT PRICE' "
					+ " WHEN 1 THEN 'NET PRICE'"
					+"  ELSE '' "
					+ "END) AS editing_type "
					+ "FROM "+getTable()+" dis LEFT JOIN sync_queue  "
					+ "ON (dis.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')"+wherePart+" "+sortPart+" "
					+ "LIMIT "+limitRows+" OFFSET "+offset+"";

		
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
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) and (code like '%"+searchCriteria+"%' or name like '%"+searchCriteria+"%' or price like '%"+searchCriteria+"%' )";
		}
		String sqlCount="SELECT COUNT(id) as row_count from  "+getTable()+" " +wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	/**
	 * @return
	 */
	public JsonArray getSaleItemJsonArray() throws Exception {
		// TODO Auto-generated method stub
		final String sql1="SELECT id,code,name FROM mrp_stock_item WHERE is_sellable=1 AND IFNULL(is_deleted,0) = 0";
		return getTableRowsAsJson(sql1);
	}

	public List<SaleItemDiscount> getExcelData() throws Exception{
		List<SaleItemDiscount> salediscountsList=null;
		final SaleItemDiscountService saleService=new SaleItemDiscountService(getContext());
		
		String SQL="SELECT dis.code AS code,s.*,si.name AS sale_item_name FROM sale_item_discounts s "
				+ "LEFT JOIN discounts dis ON s.discount_id=dis.id LEFT JOIN mrp_stock_item si ON si.id=s.sale_item_id "
				+ " WHERE s.is_deleted=0 ";
	CachedRowSet rs= getRowSet(SQL);
	SaleItemDiscount salediscounts;
	if(rs!=null){
		salediscountsList=new ArrayList<SaleItemDiscount>();
		while(rs.next()){		
			salediscounts=saleService.getDao().getNewModelInstance();
			DBUtil.setModelFromSRS(rs, salediscounts);
			salediscounts.setDiscnt_code(rs.getString("code"));
			salediscounts.setSaleItem_name(rs.getString("sale_item_name"));
			salediscountsList.add(salediscounts);
		}
	}	

	return salediscountsList;
}
	

	
	

}