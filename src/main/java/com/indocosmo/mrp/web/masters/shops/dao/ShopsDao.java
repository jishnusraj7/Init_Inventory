package com.indocosmo.mrp.web.masters.shops.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.menu.model.Menu;
import com.indocosmo.mrp.web.masters.shops.model.Shops;



public class ShopsDao extends GeneralDao<Shops> implements IShopsDao{


	public ShopsDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Shops getNewModelInstance() {
		
		return new Shops();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "shop";
	}
	
	

	
	
	
	@Override
	public Integer delete(String where) throws Exception {
		// TODO Auto-generated method stub
		return super.delete(where);
	}
	
	//getting the master shop details  
	@Override
	public JsonArray getMsterShopJson() throws Exception {

		final String sql="SELECT * FROM shop order by created_at ASC LIMIT  1";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {

		final String sql="SELECT id,code,name FROM "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(s.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(s.is_deleted,0) = 0) AND (s.code LIKE '%"+searchCriteria+"%' OR s.name LIKE '%"+searchCriteria+"%' "
					+ " OR s.description LIKE '%"+searchCriteria+"%' )";
		}
		
		
		/*String sql = "SELECT	id,`code`,`name`,description,dept_type,is_deleted,is_system,last_sync_at,CASE(dept_type) WHEN 0 THEN 'Material Department' WHEN 1 THEN 'Production Department'"
				+"WHEN 2 THEN 'Sales Department' ELSE '' END AS dept_type_name FROM "+getTable()+" "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
*/
		String sql = "SELECT "
					+ "s.id,s.code,s.name,s.description,s.address,s.city,s.country,s.state,s.zip_code,"
					+ "s.created_by,s.created_at,s.area_id,s.cst_no,s.company_license_no,"
					+ "s.company_tax_no,s.phone,s.email,s.email_subscribe, "
					+ "IFNULL(sync_queue.id ,'') AS quetableId FROM "+getTable()+" s "
					+ "LEFT JOIN sync_queue "
					+ "ON(s.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')"+wherePart+" "+sortPart+" "
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
			wherePart="WHERE (IFNULL(is_deleted,0) = 0) AND (code LIKE '%"+searchCriteria+"%' or name LIKE '%"+searchCriteria+"%' "
					+ " OR description LIKE '%"+searchCriteria+"%' )";
		}
		String sqlCount="SELECT count(id) as row_count FROM "+getTable()+" " +wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.shops.dao.IShopsDao#getShopDataById(int)
	 */
	@Override
	public JsonArray getShopDataById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	String sql = "SELECT "
				+ "s.id,s.code,s.name,s.description,s.address,s.city,s.country,s.state,s.zip_code,s.area_id,"
				+ "s.is_synchable,s.created_by,s.created_at,"
				+ "IFNULL(sync_queue.id ,'') AS quetableId FROM "+getTable()+" s LEFT JOIN sync_queue "
				+ "ON(s.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') where s.id="+id+" AND (IFNULL(s.is_deleted,0) = 0)";
	return getTableRowsAsJson(sql);
	
	}

	public int getShopDataByCode(String code) throws Exception {
		
		int shop_id = -1;
		String sql = "SELECT "
				+ "id from "+ getTable() +" where code = '"+ code +"' AND (IFNULL(is_deleted,0) = 0)";
		
		CachedRowSet shopRecord= executeSQLForRowset(sql);
		if(shopRecord.next()){
			shop_id = shopRecord.getInt("id");
		}
		return shop_id;

	}
	
	public JsonArray getAreaByShopId(int id) throws Exception {
		// TODO Auto-generated method stub
		String sql1= "SELECT "
				     + "s.area_id,a.code,a.name FROM shop s INNER JOIN area_codes a ON s.area_id=a.id WHERE s.id="+id+" AND (IFNULL(s.is_deleted,0) = 0)";
		return getTableRowsAsJson(sql1);
	}

	/**
	 * @return
	 */
	public JsonArray getImagePath(Integer id) {
		// TODO Auto-generated method stub
		JsonObject jObj=new JsonObject();
		JsonArray jArray=new JsonArray();
		
	//	String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/logos/";
		
		String imgPath=absolutePath + "logo_"+id+".jpg";
		jObj.addProperty("imgPath", imgPath);
		jArray.add(jObj);
		return jArray;
		
	}

	/**
	 * @return
	 */
	public List<Shops> getExcelData() throws Exception{
		List<Shops> shopsList=null;
		
		String SQL="SELECT "
				+ "mn.*,GROUP_CONCAT(mdp.department_id separator ',') as dptIds,"
				+ "GROUP_CONCAT(dp.`name` separator ',') as dptname "
				+ "FROM "
				+ "shop mn "
				+ "LEFT JOIN shop_departments mdp ON mdp.shop_id = mn.id "
				+ "AND mdp.is_deleted = 0 "
				+ "LEFT JOIN departments dp ON dp.id=mdp.department_id "
				+ "WHERE "
				+ "mn.is_deleted = 0 group by mn.id";
	CachedRowSet rs= getRowSet(SQL);
	Shops shops;
	if(rs!=null){
		shopsList=new ArrayList<Shops>();
		while(rs.next()){		
			shops=getNewModelInstance();
			DBUtil.setModelFromSRS(rs, shops);
			shops.setDeptName(rs.getString("dptname"));
			shopsList.add(shops);
		}
	}	

	return shopsList;
}
	
	
}
