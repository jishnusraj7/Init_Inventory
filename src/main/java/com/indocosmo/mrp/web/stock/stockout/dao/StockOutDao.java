package com.indocosmo.mrp.web.stock.stockout.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockOutStatus;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.ColumnCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.OrderCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;

public class StockOutDao extends GeneralDao<StockOut> implements IStockOutDao {

	/**
	 * @param context
	 */
	public StockOutDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockOut getNewModelInstance() {

		return new StockOut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_out_hdr";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		if (wherePart != null && wherePart != "") {

			wherePart = "WHERE  " 
						 + "(stock_transfer_no LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_request_date LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_transfer_date LIKE '%"+ searchCriteria + "%' "
						 + "OR stock_out_status LIKE '%"+ searchCriteria + "%')";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {
				
				wherePart += " AND " + adnlFilterPart;
				
			} 
			else {
				
				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sql = "SELECT * " 
						+ "FROM " 
						+ "(SELECT "
						+  "mrp_department.`name` as department_name , "
						+  getTable()
						+  ".id,stock_out_type,source_department_id,"
						+  "dest_department_id,dest_company_id,dest_company_code,dest_company_name,stock_transfer_no,stock_request_date,"
						+  "stock_transfer_date,stock_transfer_print_date,req_by,req_status,remarks,total_amount,"
						+  getTable()+ ".last_sync_at,"
						+  "CASE(req_status) "
						+   "WHEN 0 THEN '"
						+   stockOutStatus.NEW.getStockOutStatusName()+ "'  "
						+   "WHEN 1 THEN '"
						+   stockOutStatus.APPROVED.getStockOutStatusName()+ "'  "
						+   "WHEN 2 THEN '"
						+   stockOutStatus.REJECTED.getStockOutStatusName()+ "'  "
						+   "WHEN 3 THEN '"
						+   stockOutStatus.ISSUED.getStockOutStatusName()+ "'  "
						+   "WHEN 4 THEN '"
						+   stockOutStatus.PRINTED.getStockOutStatusName()+ "'  "
						+  "ELSE '' "
						+  "END AS stock_out_status  "
						+  "FROM "+ ""
						+   getTable()+ "   "
						+  "LEFT JOIN mrp_department on mrp_department.id="+ getTable()+ ".dest_department_id  ) "+ getTable()+ " "
						+  wherePart+ " "
						+  sortPart+ " "
						+ "LIMIT "+ limitRows+ " "
						+ "OFFSET "+ offset + "";
		
		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart, String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart != null && wherePart != "") {

			wherePart = "WHERE  " 
						 + "(stock_transfer_no LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_request_date LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_transfer_date LIKE '%" + searchCriteria + "%' " 
						 + "OR stock_out_status LIKE '%"+ searchCriteria + "%')";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sqlCount = "SELECT COUNT(id) as row_count "
				+ "FROM "
				+ "(SELECT mrp_stock_out_hdr.id, mrp_stock_out_hdr.req_status, stock_transfer_no, "
				+ "dest_department_id,stock_request_date,stock_transfer_date, "
				+ "CASE (req_status)"
				+ "WHEN 0 THEN 'NEW' "
				+ "WHEN 1 THEN 'APPROVED' "
				+ "WHEN 2 THEN 'REJECTED' "
				+ "WHEN 3 THEN 'PRINTED' "
				+ "WHEN 4 THEN 'RECEIVED' "
				+ "ELSE '' "
				+ "END AS stock_out_status FROM	" + getTable() + " "
				+ "ORDER BY stock_transfer_no DESC) mrp_stock_out_hdr " + wherePart + "";

		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.stock.stockout.dao.IStockOutDao#updateStockOutStatus
	 * (com.indocosmo.mrp.web.stock.stockout.model.StockOut)
	 */
	@Override
	public StockOut updateStockOutStatus(StockOut stockout) throws Exception {

		String updateSqlChangestatus = "";

		updateSqlChangestatus = "UPDATE " + getTable() + " SET req_status="
				+ stockout.getReqStatus() + " WHERE id=" + stockout.getId();

		PreparedStatement ps = dbHelper
				.buildPreparedStatement(updateSqlChangestatus);

		if (ps != null) {

			beginTrans();
			try {

				ps.execute();
				endTrans();
			} catch (Exception e) {

				rollbackTrans();
				throw e;

			}
		}
		return stockout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#delete(java
	 * .lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.stock.stockout.dao.IStockOutDao#
	 * getTableRequestDataRowsAsJson()
	 */
	@Override
	public JsonArray getTableRequestDataRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable() + " WHERE req_status="
				+ stockOutStatus.NEW.getStockOutStatusId() + "";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.stock.stockout.dao.IStockOutDao#getStockInDtlData
	 * (com.indocosmo.mrp.web.stock.stockout.model.StockOut)
	 */
	@Override
	public JsonArray getStockOutDtlData(StockOut stockOut) throws Exception {

		final String sql = "SELECT  "
				+ "stk.*,stk.uom_code as uomcode, "
				+ "IFNULL((SELECT IFNULL(SUM(dtl. in_qty)-SUM(dtl.out_qty),0) AS current_stock "
				+ "FROM mrp_stock_register_hdr hdr "
				+ "INNER JOIN mrp_stock_register_dtl dtl ON hdr.id = dtl.stock_register_hdr_id "
				+ "WHERE dtl.stock_item_id = stk.stock_item_id "
				+ "AND dtl.department_id = stkhd.source_department_id "
				//+ "AND hdr.txn_date <= stkhd.stock_transfer_date "
				+ "GROUP BY dtl.stock_item_id,dtl.department_id ),0) AS current_stock "
				+ "FROM "
				+ "mrp_stock_out_dtl stk "
				+ "INNER JOIN mrp_stock_out_hdr stkhd ON stkhd.id=stk.stock_out_hdr_id " 
				+ "LEFT JOIN mrp_stock_item stkitm on stk.stock_item_id = stkitm.id " 
				+ "WHERE "
				+ "stock_out_hdr_id=" + stockOut.getId() + "";

return getTableRowsAsJson(sql);
	}

	/**
	 * @param stockInDetail
	 * @return
	 * @throws Exception
	 */
	public StockInDetail getItemTaxDtl(StockInDetail stockInDetail)
			throws Exception {

		if (stockInDetail.getStockItemId() != null) {

			final String sql = "SELECT "
								+ "id,tax_pc,tax_id "
								+ "FROM "
								+ "vw_stockitem_tax "
								+ "WHERE "
								+ "vw_stockitem_tax.valid_from < NOW() AND vw_stockitem_tax.id="
								+  stockInDetail.getStockItemId() + " "
								+ "ORDER BY valid_from DESC "
								+ "LIMIT 1";

			try {

				final CachedRowSet srs = executeSQLForRowset(sql);
		
				if (srs != null) {

					if (srs.next()) {

						stockInDetail.setTaxId(srs.getInt("tax_id"));
						stockInDetail.setTaxPc(srs.getDouble("tax_pc"));
					}
				}

			} catch (Exception e) {

				throw e;
			}
		}

		return stockInDetail;
	}

	/**
	 * @param stockoutHdrId
	 * @return
	 * @throws Exception
	 */
	public StockOut getStockOutData(Integer stockoutHdrId) throws Exception {

		final String sql = "SELECT "
							+ "id,stock_transfer_no,stock_transfer_date,stock_request_date,req_status,total_amount,"
							+ " (SELECT  "
							+ " dep.`name` "
							+ "	FROM "
							+ "	mrp_department dep "
							+ "	WHERE "
							+ "	dep.is_deleted=0 AND dep.id = sohdr.source_department_id) AS srcDepartment,"
							+ "	 (SELECT dep.`name` "
							+ "  FROM "
							+ "  mrp_department dep "
							+ "  WHERE "
							+ "  dep.is_deleted=0 AND dep.id = sohdr.dest_department_id) AS destDepartment "
							+ " FROM " 
							+ " mrp_stock_out_hdr sohdr " 
							+ " WHERE "
							+ " sohdr.id ="+ stockoutHdrId;

		CachedRowSet rs = getRowSet(sql);
		StockOut stockOut = getNewModelInstance();
		if (rs != null) {

			while (rs.next()) {

				stockOut.setId(rs.getInt("id"));
				stockOut.setStockTransfeNo(rs.getString("stock_transfer_no"));
				stockOut.setStockTransferDate(rs
						.getString("stock_transfer_date"));
				stockOut.setStockRequestDate(rs.getString("stock_request_date"));
				stockOut.setSrcDepartmentName(rs.getString("srcDepartment"));
				stockOut.setDestDepartmentName(rs.getString("destDepartment"));
				stockOut.setReqStatus(rs.getInt("req_status"));
				stockOut.setTotal_amount(rs.getDouble("total_amount"));

			}
		}
		return stockOut;
	}

	/**
	 * @param stockOut
	 * @throws Exception
	 */
	public void updateStockOutPrintStatus(StockOut stockOut) throws Exception {

		String updateSqlChangestatus = "";

		/*updateSqlChangestatus = "UPDATE "
								+ getTable() + " SET remarks= '"+ stockOut.getRemarks() + "',"
								+ "req_status="+ stockOutStatus.PRINTED.getStockOutStatusId() + " "
								+ "WHERE "
								+ "id="+ stockOut.getId();*/
		updateSqlChangestatus = "UPDATE "
				+ getTable() + " SET "
				+ "req_status="+ stockOutStatus.PRINTED.getStockOutStatusId() + " "
				+ "WHERE "
				+ "id="+ stockOut.getId();

		PreparedStatement ps = dbHelper.buildPreparedStatement(updateSqlChangestatus);

		if (ps != null) {

			beginTrans();
			try {

				ps.execute();
				endTrans();
			} catch (Exception e) {

				rollbackTrans();
				throw e;

			}
		}
	}

	/**
	 * @param datatableParameters
	 * @return
	 * @throws Exception
	 */
	public JsonObject getStockOutRequestTableJsonArray(
			DataTableCriterias datatableParameters) throws Exception {
		JsonObject jobj = new JsonObject();
		List<DataTableColumns> columnList = new ArrayList<DataTableColumns>();
		String columns = "";
		try {
			List<Map<ColumnCriterias, String>> colList = datatableParameters
					.getColumns();
			for (Map<ColumnCriterias, String> cols : colList) {
				DataTableColumns column = new DataTableColumns();
				for (Entry<ColumnCriterias, String> entry : cols.entrySet()) {
					if (entry.getKey().toString().equals("data")) {
						column.setData(entry.getValue());
					} else if (entry.getKey().toString().equals("name")) {
						column.setName(entry.getValue());
					} else if (entry.getKey().toString().equals("searchable")) {
						column.setSearchable(Boolean.valueOf(entry.getValue()));
					} else if (entry.getKey().toString().equals("orderable")) {
						column.setOrderable(Boolean.valueOf(entry.getValue()));
					} else if (entry.getKey().toString().equals("searchValue")) {
						column.setSearchValue(entry.getValue());
					} else if (entry.getKey().toString().equals("searchRegex")) {
						column.setSearchRegex(Boolean.valueOf(entry.getValue()));
					}
				}
				columnList.add(column);
				columns += ((columns.isEmpty()) ? "" : ",") + column.getData();
			}
			String wherePart = "";
			String sortPart = "";
			String adnlFilterPart = "";
			
			int i = Integer.parseInt(datatableParameters.getOrder().get(0)
					.get(OrderCriterias.column));
			
			if (columnList.get(i).getOrderable()) {
				
				sortPart = "ORDER BY "
						+ columnList.get(i).getData()
						+ " "
						+ datatableParameters.getOrder().get(0)
								.get(OrderCriterias.dir) + "";
			}
			if (!datatableParameters.getSearch().get(SearchCriterias.value)
					.isEmpty()) {
				for (DataTableColumns col : columnList) {
					
					if (col.getSearchable()) {
						
						wherePart += ((wherePart.isEmpty()) ? "WHERE " : " OR ")
								+ ""
								+ getTable()
								+ "."
								+ col.getData()
								+ " LIKE '%"
								+ datatableParameters.getSearch().get(
										SearchCriterias.value) + "%'";
					}
				}

			}
			adnlFilterPart = getAdditionalFilter(columnList);
			int limitRows = datatableParameters.getLength();
			int offset = datatableParameters.getStart();

			int totalRecords = 0;
			CachedRowSet totalRec = getStockOutRequestTotalCount(wherePart,
					datatableParameters.getSearch().get(SearchCriterias.value),
					adnlFilterPart, columnList);
			if (totalRec.next()) {
				totalRecords = totalRec.getInt("row_count");
			}
			JsonArray jDataArray = getStockOutRequestRowsAsJsonForDataTable(
					columns, wherePart,
					datatableParameters.getSearch().get(SearchCriterias.value),
					sortPart, limitRows, offset, adnlFilterPart);
			jobj.addProperty("recordsTotal", totalRecords);
			jobj.addProperty("draw", datatableParameters.getDraw());
			jobj.addProperty("recordsFiltered", totalRecords);
			jobj.add("data", jDataArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj;
	}

	/**
	 * @param columns
	 * @param wherePart
	 * @param searchCriteria
	 * @param sortPart
	 * @param limitRows
	 * @param offset
	 * @param adnlFilterPart
	 * @return
	 * @throws Exception
	 */
	public JsonArray getStockOutRequestRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart) throws Exception {
		
		if (wherePart != null || wherePart != "") {
			
			wherePart = "WHERE  " 
						 + "(stock_transfer_no LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_request_date LIKE '%"+ searchCriteria + "%' " 
						 + "OR stock_transfer_date LIKE '%"+ searchCriteria + "%' " 
						 + "OR total_amount LIKE '%"+ searchCriteria + "%')";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} 
			else {
			
				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sql = "SELECT mrp_stock_out_hdr.*,mrp_department.`name` as department_name " 
					 + "FROM " 
					 +  getTable() + " LEFT JOIN mrp_department ON mrp_department.id = mrp_stock_out_hdr.dest_department_id " 
					 +  wherePart + " "
					 + "AND req_status=" + stockOutStatus.NEW.getStockOutStatusId()+ " " 
					 +  sortPart + " " 
					 + "LIMIT " + limitRows + " " 
					 + "OFFSET "+ offset + " ";
		
		return getTableRowsAsJson(sql);
	}

	/**
	 * @param wherePart
	 * @param searchCriteria
	 * @param adnlFilterPart
	 * @param columnList
	 * @return
	 * @throws Exception
	 */
	public CachedRowSet getStockOutRequestTotalCount(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		if (wherePart != null || wherePart != "") {
		
			wherePart = "WHERE  " 
						+ "(stock_transfer_no LIKE '%"+ searchCriteria + "%' " 
						+ "OR stock_request_date LIKE '%"+ searchCriteria + "%' " 
  					    + "OR stock_transfer_date LIKE '%"+ searchCriteria + "%' " 
						+ "OR total_amount LIKE '%"+ searchCriteria + "%')";
		}
		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} 
			else {
			
				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sqlCount = "SELECT " 
				           + "COUNT(id) as row_count " 
				           + "FROM " + ""
				           +  getTable() + " " 
				           +  wherePart + " "
				           + "AND req_status= "+ stockOutStatus.NEW.getStockOutStatusId() + "";
		
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.
	 * util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart = "";
		for (DataTableColumns col : columnList) {

			if (col.getSearchValue() != "" && col.getSearchValue() != null) {
				
				if (col.getData().equals("req_status")) {
				
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " "
							: " AND ")
							+ ""
//							+ getTable()
//							+ "."
							+ col.getData()
							+ " IN(" + col.getSearchValue() + ")";
				}
				else if (col.getData().equals("department_name")) {
					
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " "
							: " AND ")
							+ ""
							+ getTable()
							+ ".dest_department_id = "
							+ col.getSearchValue()
							+ "";
				} 
				else {
				
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " "
							: " AND ")
							+ ""
							+ getTable()
							+ "."
							+ col.getData()
							+ " LIKE '%" + col.getSearchValue() + "%'";
				}
			}
		}
		return adnlFilterPart;
	}
	
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(stock_transfer_no) AS  row_count FROM "+getTable()+" WHERE stock_transfer_no='"+code+"' LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	public Double getCurrstock(String itemId , String current_date , String department_id) throws Exception{
	
		// TODO Auto-generated method stub
		CachedRowSet resultSet;
		double currStock=0.0;

		final String sql="SELECT "
				+ " IFNULL(SUM(dtl. in_qty)-SUM(dtl.out_qty),0) AS current_stock "
				+ "FROM mrp_stock_register_hdr hdr INNER JOIN mrp_stock_register_dtl dtl ON hdr.id = dtl.stock_register_hdr_id "
				+ "WHERE dtl.stock_item_id = '"+itemId+"' AND dtl.department_id = '"+department_id+"' "
				//+ "AND hdr.txn_date <= '"+current_date+"'"
				+ " GROUP BY dtl.stock_item_id,dtl.department_id";
				
				
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			currStock=resultSet.getDouble("current_stock");
			
			
		}
		
		
		return currStock;
	}
	

	public JsonArray getPendingStockOutDtlData(StockOut stockOut,String deptId,String itmsIdArray) throws Exception {
	
		
		final String sql = "SELECT  "
							+ "IFNULL(SUM(stk.request_qty) - SUM(stk.issued_qty),0) AS pending_stock1 "
							+ "FROM mrp_stock_out_dtl stk "
							+ "INNER JOIN mrp_stock_out_hdr hdr ON hdr.id = stk.stock_out_hdr_id "
							+ " WHERE stk.stock_item_id IN (" + itmsIdArray + ") "
							+ "AND  hdr.dest_department_id = " + deptId+ " GROUP BY stk.stock_item_id";

		return getTableRowsAsJson(sql);
	}
	
	public Double getPendingStockByDeptAndItem(String itemId , String department_id) throws Exception{
		
		CachedRowSet resultSet;
		double currStock=0.0;
		
		final String sql = "SELECT  "
				+ "IFNULL(SUM(stk.request_qty) - SUM(stk.issued_qty),0) AS pendingStock "
				+ "FROM mrp_stock_out_dtl stk "
				+ "INNER JOIN mrp_stock_out_hdr hdr ON hdr.id = stk.stock_out_hdr_id "
				+ " WHERE stk.stock_item_id = "+itemId+" "
				+ "AND  hdr.dest_department_id = " + department_id+ " GROUP BY stk.stock_item_id";
				
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			currStock=resultSet.getDouble("pendingStock");
			
			
		}
		
		
		return currStock;
	}
	
public Double getPendingStockByDeptAndItemStockOut(String itemId , String department_id) throws Exception{
		
		CachedRowSet resultSet;
		double pendingStock=0.0;
		
		final String sql = "SELECT  "
				+ "IFNULL(SUM(stk.request_qty) - SUM(stk.issued_qty),0) AS pendingStock "
				+ "FROM mrp_stock_out_dtl stk "
				+ "INNER JOIN mrp_stock_out_hdr hdr ON hdr.id = stk.stock_out_hdr_id "
				+ " WHERE stk.stock_item_id = "+itemId+" "
				+ "AND  hdr.dest_department_id = " + department_id+ " GROUP BY stk.stock_item_id";
				
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			pendingStock=resultSet.getDouble("pendingStock");
			
			
		}
		
		
		return pendingStock;
	}

	/*@Override
	public Double getUnitPrice(String item_code) throws Exception {
		System.out.println("item_code is ==========================================================================>"+item_code);
		
		CachedRowSet resultSet;
		double unitPrice=0.0;	
		final String sql = "SELECT unit_price FROM stock_items WHERE CODE="+item_code+" ";
			
	resultSet = dbHelper.executeSQLForRowset(sql);
	if (resultSet.next()) {
		unitPrice=resultSet.getDouble("unit_price");
	
	}
	System.out.println("unitprice is ==========================================================================>"+unitPrice);
	
	return unitPrice;
	}
		*/


}
