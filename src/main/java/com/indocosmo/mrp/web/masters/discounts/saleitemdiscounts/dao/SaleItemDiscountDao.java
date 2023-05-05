package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;

public class SaleItemDiscountDao extends GeneralDao<SaleItemDiscount> implements ISaleItemDiscountDao {

	public SaleItemDiscountDao(ApplicationContext context) {
		super(context);

	}

	@Override
	public SaleItemDiscount getNewModelInstance() {

		return new SaleItemDiscount();
	}

	@Override
	public String getTable() {

		return "sale_item_discounts";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.masters.supplier.dao.ISupplierDao#getIsSystemSupplier
	 * ()
	 */

	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getsupplierdataList() throws Exception {

		final String sql = "SELECT * FROM "+getTable()+"  WHERE  is_deleted=0";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		
		final String sql = "SELECT id,code,name "
							+ "FROM " 
							+  getTable()+" "
							+ "WHERE "
							+ "(IFNULL(is_deleted,0) = 0) ";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		
		final String sql = "SELECT * "
							+ "FROM "
							+  getTable()+" "
							+ "WHERE (IFNULL(is_system,0) = 0) AND  (IFNULL(is_deleted,0) = 0)";
		
		return super.getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList(java.lang.String)
	 */
	@Override
	public List<SaleItemDiscount> getList(String where) throws Exception {
		
		String SQL = "SELECT * FROM " + getTable() + " WHERE id=" + where;

		return buildItemList(SQL);

	}
	
	@Override
	public Integer IsSaleItemExist(Integer discountId, Integer saleItemId) throws Exception {
		// TODO Auto-generated method stub
		String sql="select id from sale_item_discounts where sale_item_id="+saleItemId+" and discount_id="+discountId+"";
		JsonArray jsonarry=getTableRowsAsJson(sql);
		if(jsonarry.size() != 0)
		{
		JsonObject obj=(JsonObject)jsonarry.get(0);
		return obj.get("id").getAsInt();
		}else return null;
	}

	public JsonArray getSaleItemJsonArray(int discountId) throws Exception {
		// TODO Auto-generated method stub
		final String sql1="SELECT  s.id,s.code,s.name,IFNULL(sid.id,'') AS sale_item_id,ifnull(sid.price,'' )as sale_item_discount "
				+ "FROM mrp_stock_item s  LEFT JOIN sale_item_discounts sid ON (s.id=sid.sale_item_id AND sid.discount_id="+discountId+" "
				+ "AND (IFNULL(sid.is_deleted,0)=0)) WHERE (s.is_sellable=1 AND (IFNULL(s.is_deleted,0)=0))";
		return getTableRowsAsJson(sql1);
	}
	
	public Integer deleteSyncQueue(int departmentId) throws Exception{
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		st = con.prepareCall("{call usp_sale_item_discount_sync(?)}");
		// st=con.prepareCall("{call usp_production_finalize(?,?,?,?,?,?)}");
		
		st.setInt(1, departmentId);
		
		
		return st.executeUpdate();
		
	}
	
	public void DeleteSaleItemDiscounts(String saleItemids,Integer discountid)throws Exception{
		Integer update=0;
		if(discountid!=null && saleItemids!=""){
		
	    final String upQueryMenuDpt="UPDATE sale_item_discounts SET is_deleted=1 WHERE discount_id="+discountid+" and sale_item_id  NOT IN("+saleItemids+") and is_deleted=0";

		final String seleQuery="SELECT id FROM "+getTable()+" WHERE discount_id="+discountid+" and sale_item_id  NOT IN("+saleItemids+") and is_deleted=0";
		
		beginTrans();
		try{
		
				final CachedRowSet rs=executeSQLForRowset(seleQuery);
				update=executeSQL(upQueryMenuDpt);
		if(update!=0){
				
				while(rs.next()){
					
			 String upQuerySync="UPDATE sync_queue SET crud_action='D' WHERE  table_name='"+getTable()+"' and record_id="+rs.getInt("id")+"";
			executeSQL(upQuerySync);
				
				
				
			}
			
		}
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		
		}	
		
		
	}

}
