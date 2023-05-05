package com.indocosmo.mrp.web.masters.itemslist.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemslist.model.ItemsList;


public class ItemsListDao extends GeneralDao<ItemsList> implements IItemsListDao{

	public ItemsListDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/*@Override
	public List<ItemsList> getItems() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemsList getNewModelInstance() {
		// TODO Auto-generated method stub
		return new ItemsList();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return "mrp_stock_item";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_system,0) = 0) AND (IFNULL(is_deleted,0) = 0) AND is_active=1";

		return getTableRowsAsJson(sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(si.is_deleted,0) = 0)";
		}else{
			if(searchCriteria!="" && searchCriteria!=null)
				wherePart="where (IFNULL(si.is_deleted,0) = 0) and (si.code like '%"+searchCriteria+"%' or si.name like '%"+searchCriteria+"%' or si.is_manufactured like '%"+searchCriteria+"%' or ic.name like '%"+searchCriteria+"%' or si.item_category_id like '%"+searchCriteria+"%')";
		}
		String sql = "select  si.id,si.code,si.name,si.is_manufactured,ic.name as item_category_name , si.item_category_id,si.is_active,si.is_synchable "
				+ "from "+ getTable()+" si left join mrp_item_category ic on ic.id=si.item_category_id "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(si.is_deleted,0) = 0)";
		}else{		
			if(searchCriteria!="" && searchCriteria!=null)
				wherePart="where (IFNULL(si.is_deleted,0) = 0) and (si.code like '%"+searchCriteria+"%' or si.name like '%"+searchCriteria+"%' or si.is_manufactured like '%"+searchCriteria+"%' or ic.name like '%"+searchCriteria+"%' or si.item_category_id like '%"+searchCriteria+"%')";
		}
		String sqlCount="select count(si.id) as row_count from "+ getTable()+" si left join mrp_item_category ic on ic.id=si.item_category_id "+wherePart+"";

		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


}
