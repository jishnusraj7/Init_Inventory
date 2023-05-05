package com.indocosmo.mrp.web.report.stockregisterdetailsreport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.model.StockRegisterDetailReport;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
import com.sun.rowset.CachedRowSetImpl;

public class StockRegisterDetailReportDao extends GeneralDao<StockRegisterDetailReport> implements IStockRegisterDetailReportDao {

	public StockRegisterDetailReportDao(ApplicationContext context) {

		super(context);
	}

	@Override
	public StockRegisterDetailReport getNewModelInstance() {
		// TODO Auto-generated method stub
		return new StockRegisterDetailReport();
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return "";
	}

	public List<StockRegisterDetailReport> getReportResult(StockRegisterDetailReport stockRegisterDetailReport) throws Exception {
		// TODO Auto-generated method stub

		List<StockRegisterDetailReport> stkregrptlist = null;

		String DptQuery = "";
		String ItemCatQuery = "";
		String StockItemQuery = "";
		String TransationQuery = "";
		String StockItemQueryMonth = "";
		String StockItemQuery2 = "";
		String StockItemQueryMonth1 = "";
		if (stockRegisterDetailReport.getDepartment_id() != -1) {

			DptQuery = "and (vs.department_id=" + stockRegisterDetailReport.getDepartment_id()
			+ ")";
		}
		if (stockRegisterDetailReport.getItem_category_id() != 0) {

			ItemCatQuery = "AND (item_category.id = "
					+ stockRegisterDetailReport.getItem_category_id()
					+ " OR item_category.parent_id = "
					+ stockRegisterDetailReport.getItem_category_id() + ")";
		}

		if (stockRegisterDetailReport.getStock_item_id() != "") {//not equal to null

			StockItemQuery2 = "and (stock_item.id IN("
					+ stockRegisterDetailReport.getStock_item_id() + "))";
			StockItemQuery = "and (vs.stock_item_id IN("
					+ stockRegisterDetailReport.getStock_item_id() + "))";

		}
		if (stockRegisterDetailReport.getOption() == 0) {

			if (stockRegisterDetailReport.getStock_item_id() != "0") {//not equal to null

				StockItemQueryMonth = "and (vs.stock_item_id="
						+ stockRegisterDetailReport.getStock_item_id() + ")";
				StockItemQueryMonth1 = "and (stock_item.id="
						+ stockRegisterDetailReport.getStock_item_id() + ")";
			}
		}

		if (stockRegisterDetailReport.getTrans_type() != 0) {

			TransationQuery = "and (vs.trans_type=" + stockRegisterDetailReport.getTrans_type()
			+ ")";
		}

		String SQL = "";

			Connection con = null;
			CallableStatement st = null;
			con = this.dbHelper.getConnection();


			st = con.prepareCall("{call usp_stockreg_date_report(?,?,?,?,?,?)}");
			//st.setInt(1, stkregrpt.getStock_item_id());
			st.setString(1, stockRegisterDetailReport.getStock_item_id());
			st.setInt(2, stockRegisterDetailReport.getItem_category_id());
			st.setInt(3, stockRegisterDetailReport.getDepartment_id());
			st.setInt(4, stockRegisterDetailReport.getTrans_type());
			st.setString(5, stockRegisterDetailReport.getStartdate());
			st.setString(6, stockRegisterDetailReport.getEnddate());

			ResultSet rs1 = st.executeQuery();
			CachedRowSet rs = executeSQLForStordProc(rs1);

			if (rs != null) {

				stkregrptlist = new ArrayList<StockRegisterDetailReport>();

				while (rs.next()) {

					StockRegisterDetailReport stockregreport = new StockRegisterDetailReport();
					stockregreport.setStock_item_id(rs.getString("stock_item_id"));
					stockregreport.setStock_item_code(rs
							.getString("stock_item_code"));
					stockregreport.setStockitemName(rs
							.getString("stock_item_name"));
					stockregreport.setItem_category_id(rs
							.getInt("item_category_id"));
					stockregreport.setUomcode(rs.getString("uomcode"));
					stockregreport.setCost_price(rs.getString("cost_price"));
					stockregreport.setOpening(rs.getString("opening"));
					stockregreport.setInQty(rs.getString("inqty"));
					stockregreport.setOutQty(rs.getString("outqty"));
					stockregreport.setTxnDate(rs.getString("txn_date"));

					stkregrptlist.add(stockregreport);
				}

			}

		
		
		return stkregrptlist;

	}

	/**
	 * @param rSet
	 * @return
	 * @throws Exception
	 */
	public CachedRowSet executeSQLForStordProc(ResultSet rSet) throws Exception {

		CachedRowSet crs = null;

		if (rSet != null) {

			crs = new CachedRowSetImpl();
			crs.populate(rSet);
			rSet.close();
		}

		return crs;
	}

}
