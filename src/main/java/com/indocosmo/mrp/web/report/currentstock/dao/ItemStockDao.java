package com.indocosmo.mrp.web.report.currentstock.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;



public class ItemStockDao extends GeneralDao<ItemStock> implements IItemStockDao{

	public ItemStockDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemStock getNewModelInstance() {

		return new ItemStock();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "vw_itemstock";
	}

	/**
	 * @param itemstock
	 * @return
	 * @throws Exception
	 */
	public List<ItemStock> getList(ItemStock itemstock) throws Exception {

		String sql = "";
		String StkItmCon = "";
		String deptCon = "";
		if (itemstock.getStock_item_id() != "" && itemstock.getStock_item_id() != null) {
			StkItmCon = " stock_item_id IN(" + itemstock.getStock_item_id() + ")";
		}

		if (itemstock.getDepartment_id() != "" && itemstock.getDepartment_id() != null) {
			deptCon = " and (department_id IN(" + itemstock.getDepartment_id() + "))";
		} else {
			deptCon = " AND department_id IS NOT NULL";
		}

		if (itemstock.getItem_category_id() == null && itemstock.getStock_item_id() == null) {
			sql = "SELECT itm.*, uoms.`name` AS unit, mdp.`name` AS department_name " + "FROM "
					+ "vw_itemstock itm LEFT JOIN mrp_department mdp ON mdp.id=itm.department_id "
					+ "INNER JOIN uoms ON itm.uom_id =  uoms.id"
					+ " WHERE !ISNULL(itm.department_id) ORDER BY itm.department_id, itm.`name` ";
		} else if (itemstock.getItem_category_id() != 0) {
			/*
			 * sql="SELECT " + "* " + "FROM " + "vw_itemstock " + "WHERE " +
			 * "(department_id="+itemstock.getDepartment_id()+") " +
			 * "AND (item_category_id="+itemstock.getItem_category_id()+" " +
			 * "OR parent_id="+itemstock.getItem_category_id()+") " + "ORDER by (name)";
			 */

			sql = "SELECT itm.*, uoms.`name` AS unit, mdp.`name` AS department_name " + "FROM "
					+ "vw_itemstock itm LEFT JOIN mrp_department mdp ON mdp.id=itm.department_id "
					+ " INNER JOIN uoms ON itm.uom_id =  uoms.id " + "WHERE "
					+ " (item_category_id=" + itemstock.getItem_category_id() + " " + "OR parent_id="
					+ itemstock.getItem_category_id() + ") " + deptCon + " " + " ORDER BY mdp.`name` , itm.`name` ";

		} else if (itemstock.getStock_item_id() != "") {
			/*
			 * sql="SELECT * " + "FROM " + "vw_itemstock " + "WHERE "
			 * +StkItmCon+" and department_id="+itemstock.getDepartment_id()+" ";
			 */

			sql = "SELECT itm.*, uoms.`name` AS unit, mdp.`name` AS department_name " + "FROM "
					+ "vw_itemstock itm LEFT JOIN mrp_department mdp ON mdp.id=itm.department_id "
					+ " INNER JOIN uoms ON itm.uom_id =  uoms.id " + "WHERE "
					+ StkItmCon + " " + deptCon + " ORDER BY mdp.`name` , itm.`name` ";

		} else if (itemstock.getStock_item_id() == "") {
			/*
			 * sql="SELECT * " + "FROM " + "vw_itemstock " + "WHERE "
			 * +StkItmCon+" and department_id="+itemstock.getDepartment_id()+" ";
			 */

			sql = "SELECT itm.*, uoms.`name` AS unit, mdp.`name` AS department_name " + "FROM "
					+ "vw_itemstock itm LEFT JOIN mrp_department mdp ON mdp.id=itm.department_id "
					+ " INNER JOIN uoms ON itm.uom_id =  uoms.id " + "WHERE 1=1"
					+ deptCon + " ORDER BY mdp.`name` , itm.`name` ";

		} /*
			 * else { sql="SELECT itm.*,mdp.`name` as department_name " + "FROM " +
			 * "vw_itemstock itm LEFT JOIN mrp_department mdp ON mdp.id=itm.department_id ";
			 * 
			 * }
			 */

		//return buildItemList(sql);
  
  List<ItemStock> list = null;
  
  System.out.println(sql);

	CachedRowSet rs = getRowSet(sql);

	if (rs != null) {

		list = new ArrayList<ItemStock>();

		while (rs.next()) {

			ItemStock item = new ItemStock();
			DBUtil.setModelFromSRS(rs, item);
			item.setDepartment_name(rs.getString("department_name"));
			list.add(item);
		}
	}

	return list;

  
  
  
  
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.currentstock.dao.IItemStockDao#getCurrentStock(java.lang.String, java.lang.String)
	 */
	@Override
	public JsonArray getCurrentStock(String stockId,String department_id) throws Exception {

		final String sql = "SELECT " + "current_stock  " + "FROM " + getTable() + " " + "WHERE " + "stock_item_id="
				+ stockId + " and  department_id=" + department_id + " ";

		return getTableRowsAsJson(sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM "+getTable() ;

		return getTableRowsAsJson(sql);
	}

}
