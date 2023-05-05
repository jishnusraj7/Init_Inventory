package com.indocosmo.mrp.web.stock.stocktransfer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.stock.stocktransfer.model.StockTransfer;
import java.sql.Statement;


public class StockTransferDao extends GeneralDao<StockTransfer>{

	public StockTransferDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StockTransfer getNewModelInstance() {
	
		// TODO Auto-generated method stub
		return new StockTransfer() ;
	}

	@Override
	protected String getTable() {
	
		// TODO Auto-generated method stub
		return "mrp_stock_transfer_hdr";
	}
	
	
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {

		String adnlFilterPart = "";

		for (DataTableColumns col : columnList) {

			if (col.getSearchValue() != "" && col.getSearchValue() != null) {
				adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ")
						+ "" + col.getData() + " = ('"
						+ col.getSearchValue() + "')";
			}
		}
		return adnlFilterPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	/*@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = " WHERE " + "(stock_transfer_no LIKE '%"
					+ searchCriteria + "%' "
					+ "OR stock_transfer_date LIKE '%"
					+ searchCriteria + "%' "
					+ "OR dest_company_name LIKE '%"
					+ searchCriteria + "%' "
					 + "OR transfer_status LIKE '%"+searchCriteria+"%'"
			
							+ ")";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sql = " SELECT * FROM(SELECT "
				+ "hdr.*, dpt.`name` AS source_department_name,dpt.`code` AS source_department_code, "
				+ " CASE (req_status) "
				+ "  WHEN 0 THEN 'PENDING' "
				+ "  WHEN 1 THEN 'FINALIZE' "
				+ "  ELSE '' "
				+ " END AS transfer_status "
				+ " FROM "+getTable()+" hdr "
				+ " LEFT JOIN mrp_department dpt ON hdr.source_department_id = dpt.id) tab " + wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";
		return getTableRowsAsJson(sql);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	/*@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = " WHERE " + "(stock_transfer_no LIKE '%"
					+ searchCriteria + "%' "
					+ "OR stock_transfer_date LIKE '%"
					+ searchCriteria + "%' "
					+ "OR dest_company_name LIKE '%"
					+ searchCriteria + "%' "
					 + "OR transfer_status LIKE '%"+searchCriteria+"%'"
			
							+ ")";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sqlCount = " SELECT COUNT(id) as row_count FROM (SELECT "
				+ "hdr.*, dpt.`name` AS source_department_name,dpt.`code` AS source_department_code, "
				+ " CASE (req_status) "
				+ "  WHEN 0 THEN 'PENDING' "
				+ "  WHEN 1 THEN 'FINALIZE' "
				+ "  ELSE '' "
				+ " END AS transfer_status "
				+ " FROM "+getTable()+" hdr "
				+ " LEFT JOIN mrp_department dpt ON hdr.source_department_id = dpt.id) tab " + wherePart + " " ;
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}*/


	@Override
	public Integer isCodeExist(String code) throws Exception {
	
		final String sql= "SELECT COUNT(stock_transfer_no) AS  row_count FROM "+getTable()+" WHERE stock_transfer_no='"+code+"' LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	public JsonArray getStockTransDtlData(StockTransfer stockTrans) throws Exception {
	
		// TODO Auto-generated method stub
		
		//SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");

		final String sql= "SELECT "
				+ "dtl.id,dtl.stock_item_id,dtl.stock_item_code,dtl.stock_item_name,hdr.order_no ,hdr.order_date,"
				+ "dtl.request_qty,dtl.issued_qty,dtl.cost_price,dtl.tax_pc,dtl.uom_code AS uomcode,dtl.base_uom_code, " + 
				"dtl.compound_unit, "
				+ "IFNULL((SELECT IFNULL(SUM(stkdtl.in_qty) - SUM(stkdtl.out_qty),0) AS current_stock "
				+ "FROM mrp_stock_register_hdr stkhdr "
				+ "INNER JOIN mrp_stock_register_dtl stkdtl ON stkhdr.id = stkdtl.stock_register_hdr_id "
				+ "WHERE stkdtl.stock_item_id = dtl.stock_item_id "
				+ "AND stkdtl.department_id = hdr.source_department_id "
				+ "GROUP BY "
				+ "stkdtl.stock_item_id,stkdtl.department_id),0) AS currentStock "
				+ " FROM "
				+ "mrp_stock_transfer_hdr hdr "
				+ " INNER JOIN mrp_stock_transfer_dtl dtl ON hdr.id = dtl.stock_transfer_hdr_id "
				+ " INNER JOIN mrp_stock_item stkItm ON stkItm.id = dtl.stock_item_id "
				+ "LEFT JOIN uoms um ON stkItm.uom_id = um.id "
				+ "WHERE hdr.id = '"+stockTrans.getId()+"'";
		return getTableRowsAsJson(sql);
	}

	public void upDateStatus(StockTransfer stockTransfer) throws Exception {
	
		// TODO Auto-generated method stub
		final String sqlUpdate="UPDATE "+getTable()+ " "
				+ " SET req_status=1 WHERE id='"+stockTransfer.getId()+"'";
		executeSQL(sqlUpdate);
		
		
	}
	
	@Override
	public Integer delete(String where) throws Exception {

		final String sql = "DELETE FROM " + getTable() + " WHERE " + where;

		Integer is_deleted = 0;

		beginTrans();
		try {

			is_deleted = executeSQL(sql);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	public JsonArray getOrderstoTransfer(String where_part) throws Exception{
	
		final String sql= "SELECT"
				+ " orderHdr.order_id, orderHdr.order_no, orderHdr.order_date,"
				+ " orderHdr.shop_code AS order_from,mrp_time_slot.`name` AS closing_time,"
				+ " CASE "
				+ " WHEN orderHdr.is_ar_customer = 0 THEN 'Department'"
				+ " WHEN orderHdr.is_ar_customer = 2 THEN 'Shop'"
				+ " END AS order_type,"
				+ " orderHdr.department_id AS source_department_id,"
				+ " department.`code` AS source_department_code,"
				+ " department.`name` AS source_department_name, orderHdr.remarks"
				+ " FROM"
				+ " order_hdrs_booking AS orderHdr"
				+ " INNER JOIN (SELECT DISTINCT"
				+ " ext_ref_id"
				+ " FROM "
				+ " vw_order_pending_items"
				+ " WHERE "
				+ " order_pending > 0) vpi ON orderHdr.order_no = vpi.ext_ref_id"
				+ " LEFT JOIN mrp_department AS department ON orderHdr.department_id = department.id"
				+ " INNER JOIN mrp_time_slot ON orderHdr.closing_time_slot_id = mrp_time_slot.id "
				+ where_part +" AND orderHdr.status IN (1, 2, 3) "
				+ " ORDER BY order_no";
		return getTableRowsAsJson(sql);
	}

	public JsonArray getOrderDtlData(String order_id) throws Exception{
	
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		String inner_part = "";
		String where_part = "WHERE orderDtl.order_id= "+ order_id +" ";
		
		String orderDtlQuery = "SELECT"
				+ " orderDtl.id, orderDtl.order_id, orderDtl.sale_item_id AS stock_item_id,"
				+ " orderDtl.sub_class_code AS stock_item_code, orderDtl.sub_class_name AS stock_item_name,"
				+ " orderDtl.uom_code AS uomcode, ROUND(vpi.order_pending, "+ systemSettings.getDecimal_places() 
				+ ") AS request_qty, ROUND(IFNULL((SELECT SUM(current_stock) FROM vw_itemstock WHERE "
				+ " stock_item_id = orderDtl.sale_item_id "+ inner_part +"), 0), "+ systemSettings.getDecimal_places() 
				+ " ) AS currentStock, ROUND(IFNULL(itemrate.cost_price, 0), 2 ) AS cost_price"
				+ " FROM"
				+ " order_dtls_booking AS orderDtl"
				+ " INNER JOIN (SELECT *"
				+ " FROM"
				+ " vw_order_pending_items"
				+ " WHERE "
				+ " order_pending > 0) vpi ON orderDtl.order_id = vpi.order_id"
				+ " AND orderDtl.sale_item_id = vpi.stock_item_id"
				+ " INNER JOIN vw_itemrate itemrate ON itemrate.stock_item_id = orderDtl.sale_item_id"
				+ " INNER JOIN mrp_stock_item items ON items.id = orderDtl.sale_item_id " 
				+ where_part +" GROUP BY sale_item_id";
		return getTableRowsAsJson(orderDtlQuery);
	}
	
	public JsonArray getStockTransferHdr(String transfer_date) throws Exception{
		
		String sql = "SELECT "
				+ "transferHdr.*, dpt.`name` AS source_department_name,"
				+ "dpt.`code` AS source_department_code, "
				+ "CASE (req_status) "
				+ "WHEN 0 THEN 'PENDING' "
				+ "WHEN 1 THEN 'FINALIZE' "
				+ "WHEN 2 THEN 'PENDING' "
				+ "ELSE '' "
				+ "END AS transfer_status "
				+ "FROM "
				+ getTable()+" transferHdr "
				+ "LEFT JOIN mrp_department dpt ON transferHdr.source_department_id = dpt.id "
				+ "WHERE transferHdr.stock_transfer_date= '"+transfer_date+"' "
				+ "ORDER BY stock_transfer_no desc";
		return getTableRowsAsJson(sql);
	}
	
	public String getDepartmentData(String department_id)throws Exception{
		
		Connection con = null;
		Statement statement=null;
		String departmentName=null;
		ResultSet rs=null;
		con=this.dbHelper.getConnection();	
		statement=con.createStatement();
	    String sql="SELECT name FROM mrp_department WHERE id='"+department_id+"'";
	    rs=statement.executeQuery(sql);
	    while(rs.next()) {
	    	
	        departmentName=rs.getString("name");
	    }
		return departmentName;
		
	}
}
