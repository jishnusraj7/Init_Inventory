package com.indocosmo.mrp.web.masters.menu.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.menu.model.Menu;

/**
 * @author jo
 *
 */
public class MenuDao extends MasterBaseDao<Menu> implements IMenuDao {

	public MenuDao(ApplicationContext context) {
		super(context);

	}

	@Override
	public Menu getNewModelInstance() {

		return new Menu();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "menus";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%' or itm.description like '%"+searchCriteria+"%' )";
		}
		String sql = "SELECT created_by,created_at,itm.id,itm.`code`,itm.`name`,itm.description,IFNULL(sync_queue.id ,'') AS quetableId "
				+ "FROM "+getTable()+" itm LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%' or itm.description like '%"+searchCriteria+"%') ";
		}
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm  "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	public JsonArray getEditDetails(int id) throws Exception
	{
		final String sql="SELECT * from "+getTable()+" WHERE id="+id+"  and is_deleted='0' ";

		return getTableRowsAsJson(sql);
	}
	
	
	public List<Menu> getExcelData() throws Exception {
		List<Menu> menuList=null;
	
			String SQL="SELECT "
					+ "mn.*,GROUP_CONCAT(mdp.department_id separator ',') as dptIds,"
					+ "GROUP_CONCAT(dp.`name` separator ',') as dptname "
					+ "FROM "
					+ "menus mn "
					+ "INNER JOIN menu_departments mdp ON mdp.menu_id = mn.id "
					+ "AND mdp.is_deleted = 0 "
					+ "LEFT JOIN departments dp ON dp.id=mdp.department_id "
					+ "WHERE "
					+ "mn.is_deleted = 0 group by mn.id";
		CachedRowSet rs= getRowSet(SQL);
		Menu menu;
		if(rs!=null){
			menuList=new ArrayList<Menu>();
			while(rs.next()){		
				menu=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, menu);
				menu.setDeptName(rs.getString("dptname"));
				menuList.add(menu);
			}
		}	

		return menuList;
	}
	

}
