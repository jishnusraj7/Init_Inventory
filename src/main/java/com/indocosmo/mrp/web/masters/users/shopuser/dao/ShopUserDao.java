package com.indocosmo.mrp.web.masters.users.shopuser.dao;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;


public class ShopUserDao  extends GeneralDao<ShopUser> implements IShopUserDao{

	public ShopUserDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShopUser getNewModelInstance() {
	
		// TODO Auto-generated method stub
		return new ShopUser();
	}

	@Override
	public String getTable() {
	
		// TODO Auto-generated method stub
		return "shop_users";
	}
	
	public JsonArray getShpusrJsonArray(int id) throws Exception{
		String sql="SELECT DISTINCT shp.id,shp.`code`,shp.`name`,IFNULL(shusr.id,'') as spusrId "
				+ " FROM shop shp LEFT JOIN shop_users shusr ON (shusr.shop_id=shp.id  AND shusr.user_id="+id +" AND "
				+ " (IFNULL(shusr.is_deleted, 0) = 0))  WHERE (IFNULL(shp.is_deleted,0) = 0)";
						
						return getTableRowsAsJson(sql);
	}
	public JsonArray getEditDetails(int id) throws Exception
	{
  final String sql="SELECT * from "+getTable()+" WHERE user_id="+id+"  and is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}
	
	
	public void DeleteShops(String shopIds,Integer userId)throws Exception{
		Integer update=0;
		if(shopIds!=null && shopIds!=""){
		
	    final String upQueryMenuDpt="UPDATE shop_users SET is_deleted=1 WHERE user_id="+userId+" and shop_id  NOT IN("+shopIds+") and is_deleted=0";

		final String seleQuery="SELECT id FROM "+getTable()+" WHERE user_id="+userId+" and shop_id  NOT IN("+shopIds+") and is_deleted=0";
		
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
	
	
	
	public String getShopUserId(String userId,String shopId)throws Exception
	{
		String id="";
		CachedRowSet resultSet;
		
		final String sql="select id from "+getTable()+ " WHERE user_id='"+userId+"' and shop_id='"+shopId+"' and is_deleted=0";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			
			id=resultSet.getString("id");
			
		}
		return id;
	}
	
	
	
	public Integer deleteSyncQue(Integer id) throws Exception {
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		st = con.prepareCall("{call usp_shpusr_syn_delete(?)}");
		// st=con.prepareCall("{call usp_production_finalize(?,?,?,?,?,?)}");
		
		st.setInt(1, id);
		
		
		return st.executeUpdate();
		
	}
	
	
}
