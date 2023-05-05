package com.indocosmo.mrp.web.production.bomanalysis.dao;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.production.bomanalysis.model.BomAnalysis;


public class BomAnalysisDao extends GeneralDao<BomAnalysis> implements IBomAnalysisDao{

	public BomAnalysisDao(ApplicationContext context) {

		super(context);
	}

	@Override
	public BomAnalysis getNewModelInstance() {

		// TODO Auto-generated method stub
		return new BomAnalysis();
	}

	@Override
	protected String getTable() {

		// TODO Auto-generated method stub
		return "stock_item_bom";
	}

	public JsonArray getItemData(String startDate,String endDate, String departmentId) throws Exception {

		String sql = "";		
		if(departmentId == null)
			departmentId = "2";

		sql="CALL usp_bom_analysis('"+ startDate +"','"+ endDate +"','"+ departmentId +"')";
		return getTableRowsAsJson(sql);
	}

	public JsonArray getBomConsumption(String startDate , String endDate, String departmentId , String stockItemId) throws Exception{

		String sql="SELECT phdr.prod_date,pdtl.department_id,pdtl.stock_item_id,SUM(pdtl.prod_qty) AS prod_qty,"
				+ "mstk.name AS stock_item_name,mstk.code stock_item_code,mpbm.stock_item_id AS bom_item_id,"
				+" msk.`name`as bom_item_name,"
				+ "	SUM(mpbm.prod_qty) AS bom_quantity "
				+ "FROM mrp_prod_hdr phdr "
				+ "INNER JOIN mrp_prod_dtl pdtl ON phdr.id = pdtl.prod_hdr_id "
				+ "INNER JOIN mrp_stock_item mstk ON pdtl.stock_item_id = mstk.id "
				+ "INNER JOIN mrp_prod_bom mpbm ON pdtl.id = mpbm.prod_dtl_id "
				+" INNER  JOIN mrp_stock_item msk ON mpbm.stock_item_id=msk.id"
				+ " WHERE "
				+ "phdr.prod_date between '"+ startDate +"' and '"+ endDate +"' "
				+ "AND department_id = '"+departmentId+"' "
				+ "AND mpbm.stock_item_id = '"+stockItemId+"' GROUP BY stock_item_id ";


		return getTableRowsAsJson(sql);
	}


	public JsonArray getBomDates() throws Exception{

		final String sql = "SELECT DISTINCT	(prod_date) AS date, "
				+ "(CASE	WHEN prod_date = Date(NOW()) "
				+ "THEN	'curdate' "
				+ "ELSE	'prodadd' "
				+ "END) AS css,'' AS title, "
				+ "1 AS selectable "
				+ "FROM	"
				+ "mrp_prod_hdr";

		return  getTableRowsAsJson(sql);
	}

	public JsonArray getStatusOfBomItem(ArrayList<String> stockItemIdList, String bom_item_id) throws Exception {
		
		String wherePart=null;
		for(int i=0;i<stockItemIdList.size();i++) {
			if(wherePart==null||wherePart=="") {
				wherePart= stockItemIdList.get(i);
			}
			else {
				wherePart+= ","+stockItemIdList.get(i);
			}
			
		}
		wherePart+=")";
		
		String sql = "SELECT stock_item_bom.id,stock_item_bom.stock_item_id,stock_item_bom.bom_item_id,stock_item_bom.is_deleted FROM stock_item_bom WHERE\n" + 
				"stock_item_bom.bom_item_id="+bom_item_id+ " AND stock_item_bom.stock_item_id IN(" +wherePart+ " ORDER  BY stock_item_bom.id DESC";

		return  getTableRowsAsJson(sql);
	}





}
