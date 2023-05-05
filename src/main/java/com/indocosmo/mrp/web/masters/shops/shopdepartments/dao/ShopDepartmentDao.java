package com.indocosmo.mrp.web.masters.shops.shopdepartments.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;


public class ShopDepartmentDao extends GeneralDao<ShopDepartment> implements IShopDepartmentDao{
	
	public ShopDepartmentDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShopDepartment getNewModelInstance() {

		return new ShopDepartment();
	}
	
	@Override
	public List<ShopDepartment> getList() throws Exception {
		String SQL;
		List<ShopDepartment> testbom;
		try{
			SQL="SELECT * from "+getTable();
			testbom = buildItemList(SQL);
		}catch(Exception e){
			throw e;
		}
		return testbom;
		
	}

	@Override
	public String getTable() {

		return "shop_departments";
	}
	
	public JsonArray getBomJsonArray(int id) throws Exception{
		final String sql = "select uom.code as uomcode,stock_item_bom.id,Round(stock_item_bom.cost_price,2) as unit_price,stock_item_bom.bom_item_id,stock_item_bom.qty,mrp_stock_item.code as bom_item_code,mrp_stock_item.name as bom_item_name from "+getTable()+" LEFT JOIN uoms uom on uom.id=(select uom_id FROM mrp_stock_item WHERE mrp_stock_item.id=stock_item_bom.stock_item_id) inner join mrp_stock_item on mrp_stock_item.id=stock_item_bom.bom_item_id  where stock_item_id="+id;
		return getTableRowsAsJson(sql);
	}

	public void save(List<ShopDepartment> itemList) throws Exception {
		String itemBomItemList = "";
		String wherePart = "";

		final Integer itemBomstockId = itemList.get(0).getShop_id();

		for (ShopDepartment bomItem : itemList) {

			if (bomItem.getShop_id() != null) {
				if(bomItem.getId() != null){
					itemBomItemList += ((itemBomItemList.isEmpty()) ? "" : ",")
							+ bomItem.getId();
				}
				
			}
		}
		if (itemBomItemList.length() > 0) {
			//wherePart = " AND bom_item_id not in (" + itemBomItemList + ")";
			wherePart = " AND id not in (" + itemBomItemList + ")";
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()
				+ "  WHERE shop_id=" + itemBomstockId
				+ "" + wherePart + ";";
		beginTrans();
		try {

			//executeSQL(markAsDeletedSQl);
			/*if (itemBomItemList.length() > 0)
				super.save(itemList);*/
			for (ShopDepartment itemBomsItem : itemList) {

				if (itemBomsItem.getShop_id() != null && itemBomsItem.getId() != null) {
					super.update(itemBomsItem);
				}else{
					super.insert(itemBomsItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}

	
	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) and (code like '%"+searchCriteria+"%' or name like '%"+searchCriteria+"%'  )";
		}
		String sql ="";
		

		sql="SELECT id,code,name from departments" +wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
			
		
	    
		return getTableRowsAsJson(sql);
	}
	

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) and (code like '%"+searchCriteria+"%' or name like '%"+searchCriteria+"%' ) ";
		}
		String sqlCount="SELECT count(id) as row_count from departments "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.IShopDepartmentDao#getDepdataByShopId(java.lang.Integer)
	 */
	@Override
	public JsonArray getDepdataByShopId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		String sql1="SELECT * from "+getTable()+" where shop_id="+id+" and (IFNULL(is_deleted,0) = 0)";
		return getTableRowsAsJson(sql1);
	}

	/**
	 * @param id
	 * @return
	 */
	public void updateIsDelete(Integer id) throws Exception{
		// TODO Auto-generated method stub
		String sql="update "+getTable()+" set is_deleted=1 where shop_id="+id+"";
		executeSQL(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.IShopDepartmentDao#DepData(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer DepData(Integer depId, Integer shopId) throws Exception {
		// TODO Auto-generated method stub
		String sql="select id from shop_departments where shop_id="+shopId+" and department_id="+depId+"";
		JsonArray jsonarry=getTableRowsAsJson(sql);
		if(jsonarry.size() != 0)
		{
		JsonObject obj=(JsonObject)jsonarry.get(0);
		return obj.get("id").getAsInt();
		}else return null;
	}

	/**
	 * @param shopId
	 * @return
	 */
	public Object isServerExist(Integer shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.IShopDepartmentDao#DbData(java.lang.Integer)
	 */
	@Override
	public Integer DbData(Integer shopId) throws Exception {
		String sql="select id from shop_db_settings where shop_id="+shopId+" ";
		JsonArray jsonarry=getTableRowsAsJson(sql);
		if(jsonarry.size() != 0)
		{
		JsonObject obj=(JsonObject)jsonarry.get(0);
		return obj.get("id").getAsInt();
		}else return null;
	}
	
	public void deleteData(Integer idss) throws Exception{
		final String sql1="update "+getTable()+" set is_deleted=1 where shop_id="+idss+"";
		executeSQL(sql1);
	}
	public void DeleteDepartments(String deptIds,Integer shopId)throws Exception{
		Integer update=0;
		if(deptIds!=null && deptIds!=""){
		
	    final String upQueryMenuDpt="UPDATE shop_departments SET is_deleted=1 WHERE shop_id="+shopId+" and department_id  NOT IN("+deptIds+") and is_deleted=0";

		final String seleQuery="SELECT id FROM "+getTable()+" WHERE shop_id="+shopId+" and department_id  NOT IN("+deptIds+") and is_deleted=0";
		
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

	public Integer deleteSyncQueue(String tableName,int shopId) throws Exception{
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		st = con.prepareCall("{call usp_sync_delete(?,?)}");
		st.setString(1, tableName);
		
		st.setInt(2, shopId);
		
		
		return st.executeUpdate();
		
	}
	
	
	
	

	/**
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getDptJsonArray(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="SELECT id,code,name from departments where (IFNULL(is_deleted,0) = 0)";
		String sql1="SELECT dpt.id,dpt.code,dpt.name,ifnull(shp_dep.id,'')as shp_dep_id "
				+ "FROM departments dpt LEFT JOIN shop_departments shp_dep ON (shp_dep.department_id=dpt.id AND shp_dep.shop_id="+id+" "
				+ "AND (IFNULL(shp_dep.is_deleted,0)=0))WHERE (IFNULL(dpt.is_deleted,0) = 0)";
		return getTableRowsAsJson(sql1);
	}

	
	
}
