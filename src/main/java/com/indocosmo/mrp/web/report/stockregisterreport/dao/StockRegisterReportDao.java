package com.indocosmo.mrp.web.report.stockregisterreport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
import com.sun.rowset.CachedRowSetImpl;

public class StockRegisterReportDao extends GeneralDao<StockRegisterReport>
		implements IStockRegisterReportDao {

	public StockRegisterReportDao(ApplicationContext context) {

		super(context);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockRegisterReport getNewModelInstance() {

		return new StockRegisterReport();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#buildItemList(java.lang
	 * .String)
	 */
	protected List<StockRegisterReport> buildItemList(String sql)
			throws Exception {

		List<StockRegisterReport> list = null;
		CachedRowSet rs = getRowSet(sql);

		if (rs != null) {

			list = new ArrayList<StockRegisterReport>();

			while (rs.next()) {

				StockRegisterReport item = getNewModelInstance();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * @param stkregrpt
	 * @return
	 * @throws Exception
	 */
	public List<StockRegisterReport> getReportResult(
			StockRegisterReport stkregrpt) throws Exception {

		List<StockRegisterReport> stkregrptlist = null;

		String DptQuery = "";
		String ItemCatQuery = "";
		String StockItemQuery = "";
		String TransationQuery = "";
		String StockItemQueryMonth = "";
		String StockItemQuery2 = "";
		String StockItemQueryMonth1 = "";
		if (stkregrpt.getDepartment_id() != -1) {

			DptQuery = "and (vs.department_id=" + stkregrpt.getDepartment_id()
					+ ")";
		}
		if (stkregrpt.getItem_category_id() != 0) {

			ItemCatQuery = "AND (item_category.id = "
					+ stkregrpt.getItem_category_id()
					+ " OR item_category.parent_id = "
					+ stkregrpt.getItem_category_id() + ")";
		}

		if (stkregrpt.getStock_item_id() != "") {//not equal to null

			StockItemQuery2 = "and (stock_item.id IN("
					+ stkregrpt.getStock_item_id() + "))";
			StockItemQuery = "and (vs.stock_item_id IN("
					+ stkregrpt.getStock_item_id() + "))";

		}
		if (stkregrpt.getOption() == 0) {

			if (stkregrpt.getStock_item_id() != "0") {//not equal to null

				StockItemQueryMonth = "and (vs.stock_item_id="
						+ stkregrpt.getStock_item_id() + ")";
				StockItemQueryMonth1 = "and (stock_item.id="
						+ stkregrpt.getStock_item_id() + ")";
			}
		}

		if (stkregrpt.getTrans_type() != 0) {

			TransationQuery = "and (vs.trans_type=" + stkregrpt.getTrans_type()
					+ ")";
		}

		String SQL = "";
		if (stkregrpt.getOption() == 1) {
			/*
			 * SQL=
			 * "SELECT A.stock_item_id,A.stock_item_code,A.stock_item_name,IFNULL(C.txn_date,'"
			 * +stkregrpt.getStartdate()+"')" +
			 * " AS txn_date,A.item_category_id,	A.uomcode,IFNULL(B.cost_price,IFNULL(C.cost_price, 0)) "
			 * +
			 * "AS cost_price,IFNULL(C.opening, IFNULL(B.opening, 0)) AS opening,IFNULL(C.inqty, 0) AS inqty"
			 * +
			 * ",IFNULL(C.outqty, 0) AS outqty,C.department_id FROM(SELECT stock_item.id AS stock_item_id,"
			 * +
			 * "stock_item.`code` AS stock_item_code,stock_item.`name` AS stock_item_name,stock_item.item_category_id,"
			 * +
			 * "uoms.`code` AS uomcode FROM stock_item INNER JOIN uoms ON stock_item.uom_id = uoms.id "
			 * +
			 * "INNER JOIN item_category ON stock_item.item_category_id = item_category.id WHERE "
			 * + "stock_item.is_deleted = 0 "+StockItemQuery+" "+ItemCatQuery+
			 * ") A LEFT JOIN (SELECT vs.stock_item_id,vs.cost_price," +
			 * "(SUM(vs.in_qty) - SUM(vs.out_qty)) opening FROM vw_stock_register vs WHERE"
			 * + " vs.txn_date < '"+stkregrpt.getStartdate()+
			 * "' GROUP BY vs.stock_item_id) B ON A.stock_item_id = B.stock_item_id "
			 * +
			 * "LEFT JOIN (SELECT vs.txn_date,vs.department_id,vs.trans_type,vs.cost_price,vs.stock_item_id,"
			 * +
			 * "SUM(vs.in_qty) AS inqty,SUM(vs.out_qty) AS outqty,(SELECT SUM(vo.in_qty) - SUM(vo.out_qty)"
			 * +
			 * " FROM vw_stock_register vo WHERE vo.txn_date <vs.txn_date AND vo.stock_item_id=vs.stock_item_id) opening"
			 * +
			 * "SUM(vs.in_qty) AS inqty,SUM(vs.out_qty) AS outqty,0 as opening"
			 * +
			 * " FROM vw_stock_register vs WHERE vs.txn_date BETWEEN '"+stkregrpt
			 * .
			 * getStartdate()+"' AND '"+stkregrpt.getEnddate()+"'  "+DptQuery+" "
			 * +TransationQuery+"  GROUP BY " +
			 * "vs.stock_item_id,vs.txn_date) C ON A.stock_item_id = C.stock_item_id   WHERE  1=1 AND (IFNULL(C.opening,IFNULL(B.opening,0)) > 0 OR IFNULL(C.inqty, 0)>0 OR IFNULL(C.outqty, 0)>0) ORDER BY A.stock_item_name, C.txn_date"
			 * ;
			 */

			/*
			 * SQL="SELECT " + "A.stock_item_id," + "A.stock_item_code," +
			 * "A.stock_item_name," +
			 * "IFNULL(C.txn_date, '"+stkregrpt.getStartdate()+"') AS txn_date,"
			 * + "A.item_category_id," + "A.uomcode," + "IFNULL(" +
			 * "	B.cost_price," + "	IFNULL(C.cost_price, 0)" +
			 * "	) AS cost_price,	" + "	IFNULL(" + "	C.opening," +
			 * "	IFNULL(B.opening, 0)" + ") AS opening," +
			 * "IFNULL(C.inqty, 0) AS inqty," +
			 * "	IFNULL(C.outqty, 0) AS outqty," +
			 * "	IFNULL(C.department_id, B.department_id) AS department_id" +
			 * "	FROM" + "	(" + "		SELECT" + "		stock_item.id AS stock_item_id,"
			 * + "	    stock_item.`code` AS stock_item_code," +
			 * "		stock_item.`name` AS stock_item_name," +
			 * "		stock_item.item_category_id," + "		uoms.`code` AS uomcode" +
			 * "	FROM" + "	stock_item" +
			 * "	INNER JOIN uoms ON stock_item.uom_id = uoms.id" +
			 * "	INNER JOIN item_category ON stock_item.item_category_id = item_category.id"
			 * + "	WHERE" +
			 * "		stock_item.is_deleted = 0  "+StockItemQuery2+"  "+
			 * ItemCatQuery+"" + "	) A " + "LEFT JOIN (" + "	SELECT" +
			 * "		vs.department_id," + "	vs.stock_item_id," + "	vs.cost_price,"
			 * + "	(" + "		SUM(vs.in_qty) - SUM(vs.out_qty)" + "	) opening" +
			 * " FROM" + "	vw_stock_register vs " + "WHERE" +
			 * "	vs.txn_date < '"+
			 * stkregrpt.getStartdate()+"'  "+DptQuery+"   "+StockItemQuery+"" +
			 * "GROUP BY		" + "	vs.stock_item_id," + "	vs.department_id" +
			 * ") B ON A.stock_item_id = B.stock_item_id" + " LEFT JOIN (" +
			 * "	SELECT" + "		vs.txn_date," + "		vs.department_id," +
			 * "	vs.trans_type," + "		vs.cost_price," + "	vs.stock_item_id," +
			 * "	SUM(vs.in_qty) AS inqty," + "	SUM(vs.out_qty) AS outqty, " +
			 * "	(" + "	SELECT" + "		SUM(vo.in_qty) - SUM(vo.out_qty)" +
			 * "	FROM " + "		vw_stock_register vo" + "	WHERE" +
			 * "			vo.txn_date < vs.txn_date AND vo.department_id = vs.department_id  AND vo.stock_item_id = vs.stock_item_id"
			 * + "		AND vo.stock_item_id = vs.stock_item_id " + "		) opening " +
			 * "	FROM" + "	vw_stock_register vs " + "	WHERE " +
			 * "		vs.txn_date BETWEEN '"
			 * +stkregrpt.getStartdate()+"' AND '"+stkregrpt
			 * .getEnddate()+"'  "+DptQuery
			 * +" "+TransationQuery+" "+StockItemQuery+"" + "	GROUP BY " +
			 * "		vs.department_id," + "		vs.stock_item_id," + "		vs.txn_date" +
			 * " ) C ON A.stock_item_id = C.stock_item_id " + "WHERE " +
			 * "	1 = 1 " + "AND ( " + "	IFNULL(" + "	C.opening, " +
			 * "	IFNULL(B.opening, 0) " + ") > 0" +
			 * " OR IFNULL(C.inqty, 0) > 0 " + "	OR IFNULL(C.outqty, 0) > 0 " +
			 * ")" + "ORDER BY " + "	A.stock_item_name," + "	C.txn_date";
			 */

			// CachedRowSet rs= getRowSet(SQL);
			Connection con = null;
			CallableStatement st = null;
			con = this.dbHelper.getConnection();
			
			
			st = con.prepareCall("{call usp_stockreg_date_report(?,?,?,?,?,?)}");
			//st.setInt(1, stkregrpt.getStock_item_id());
			st.setString(1, stkregrpt.getStock_item_id());
			st.setInt(2, stkregrpt.getItem_category_id());
			st.setInt(3, stkregrpt.getDepartment_id());
			st.setInt(4, stkregrpt.getTrans_type());
			st.setString(5, stkregrpt.getStartdate());
			st.setString(6, stkregrpt.getEnddate());

			ResultSet rs1 = st.executeQuery();
			CachedRowSet rs = executeSQLForStordProc(rs1);

			if (rs != null) {

				stkregrptlist = new ArrayList<StockRegisterReport>();

				while (rs.next()) {

					StockRegisterReport stockregreport = new StockRegisterReport();
					stockregreport.setStock_item_id(rs.getString("stock_item_id"));
					//stockregreport.setStock_item_id("stock_item_id");
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

		}
		if (stkregrpt.getOption() == 0) {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			date = df.parse(stkregrpt.getStartdate());

			LocalDate date1 = date.toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			int year = date1.getYear();

			/*
			 * SQL=
			 * "SELECT A.stock_item_id,A.stock_item_code,A.stock_item_name,A.item_category_id,	"
			 * +
			 * "A.uomcode,IFNULL(B.cost_price,IFNULL(C.cost_price, 0)) AS cost_price,IFNULL(B.opening, 0)"
			 * +
			 * " AS opening,IFNULL(C.inqty, 0) AS inqty,IFNULL(C.outqty, 0) AS outqty,C.department_id "
			 * +
			 * "FROM(SELECT stock_item.id AS stock_item_id,stock_item.`code` AS stock_item_code,"
			 * +
			 * "stock_item.`name` AS stock_item_name,stock_item.item_category_id,uoms.`code` AS uomcode "
			 * +
			 * "FROM stock_item INNER JOIN uoms ON stock_item.uom_id = uoms.id "
			 * +
			 * "INNER JOIN item_category ON stock_item.item_category_id = item_category.id WHERE "
			 * +
			 * "stock_item.is_deleted = 0 "+StockItemQuery+" "+ItemCatQuery+")"
			 * +
			 * " A LEFT JOIN (SELECT vs.stock_item_id,vs.cost_price,(SUM(vs.in_qty) - SUM(vs.out_qty)) opening "
			 * + "FROM vw_stock_register vs WHERE vs.txn_date < '"+stkregrpt.
			 * getStartdate()+"' GROUP BY vs.stock_item_id) B " +
			 * "ON A.stock_item_id = B.stock_item_id LEFT JOIN (SELECT vs.department_id,"
			 * +
			 * "vs.trans_type,vs.cost_price,vs.stock_item_id,SUM(vs.in_qty) AS inqty,SUM(vs.out_qty) AS outqty "
			 * +
			 * "FROM vw_stock_register vs WHERE MONTH (vs.txn_date) = "+stkregrpt
			 * .getEnddate()+" AND YEAR (vs.txn_date)="+year+"  "+DptQuery+" "+
			 * TransationQuery+"" +
			 * " GROUP BY vs.stock_item_id,MONTH (vs.txn_date),YEAR(vs.txn_date)) C"
			 * +
			 * " ON A.stock_item_id = C.stock_item_id  WHERE  1=1 AND (IFNULL(B.opening,0) > 0 OR IFNULL(C.inqty, 0)>0 OR IFNULL(C.outqty, 0)>0)  ORDER BY A.stock_item_name"
			 * ;
			 */
			/*
			 * SQL=
			 * "SELECT A.stock_item_id,A.stock_item_code,A.stock_item_name,A.item_category_id,"
			 * +
			 * " A.uomcode,IFNULL(B.cost_price,IFNULL(C.cost_price, 0)) AS cost_price,IFNULL(B.opening, 0)"
			 * +
			 * " AS opening,IFNULL(C.inqty, 0) AS inqty,IFNULL(C.outqty, 0) AS outqty,IFNULL(B.department_id, C.department_id) AS department_id "
			 * +
			 * " FROM (SELECT stock_item.id AS stock_item_id,stock_item.`code` AS stock_item_code,"
			 * +
			 * " stock_item.`name` AS stock_item_name,stock_item.item_category_id,uoms.`code` AS uomcode FROM "
			 * + " stock_item INNER JOIN uoms ON stock_item.uom_id = uoms.id " +
			 * " INNER JOIN item_category ON stock_item.item_category_id = item_category.id WHERE "
			 * +
			 * " stock_item.is_deleted = 0 "+StockItemQueryMonth1+" "+ItemCatQuery
			 * +")" +
			 * " A LEFT JOIN (SELECT vs.stock_item_id, vs.department_id,vs.cost_price,(SUM(vs.in_qty) - SUM(vs.out_qty)) opening"
			 * + "	FROM vw_stock_register vs WHERE vs.txn_date < '"+stkregrpt.
			 * getStartdate()+"' "+DptQuery+" " +
			 * " GROUP BY vs.department_id,vs.stock_item_id) B ON A.stock_item_id = B.stock_item_id LEFT JOIN (SELECT vs.department_id,"
			 * +
			 * " vs.trans_type,vs.cost_price,vs.stock_item_id,SUM(vs.in_qty) AS inqty,SUM(vs.out_qty) AS outqty"
			 * +
			 * " FROM vw_stock_register vs WHERE  MONTH (vs.txn_date) = "+stkregrpt
			 * .getEnddate()+" AND YEAR (vs.txn_date)="+year+"  "+DptQuery+" "+
			 * TransationQuery+"" +
			 * " GROUP BY vs.department_id,vs.stock_item_id,MONTH (vs.txn_date),YEAR (vs.txn_date)) C "
			 * +
			 * " ON A.stock_item_id = C.stock_item_id WHERE 1 = 1 AND (IFNULL(B.opening, 0) > 0 OR IFNULL(C.inqty, 0) > 0 OR IFNULL(C.outqty, 0) > 0) ORDER BY A.stock_item_name"
			 * ;
			 */

			// CachedRowSet rs= getRowSet(SQL);

			Connection con1 = null;
			CallableStatement st1 = null;
			con1 = this.dbHelper.getConnection();
			st1 = con1
					.prepareCall("{call usp_stockreg_month_report(?,?,?,?,?,?,?)}");
			st1.setString(1, stkregrpt.getStock_item_id());
			st1.setInt(2, stkregrpt.getItem_category_id());
			st1.setInt(3, stkregrpt.getDepartment_id());
			st1.setInt(4, stkregrpt.getTrans_type());
			st1.setString(5, stkregrpt.getStartdate());
			st1.setInt(6, Integer.parseInt(stkregrpt.getEnddate()));
			st1.setInt(7, year);
			ResultSet rs1 = st1.executeQuery();
			CachedRowSet rs3 = executeSQLForStordProc(rs1);

			if (rs3 != null) {
				stkregrptlist = new ArrayList<StockRegisterReport>();

				while (rs3.next()) {
					StockRegisterReport stockregreport = new StockRegisterReport();
					stockregreport
							.setStock_item_id(rs3.getString("stock_item_id"));
					stockregreport.setStock_item_code(rs3
							.getString("stock_item_code"));
					stockregreport.setStockitemName(rs3
							.getString("stock_item_name"));
					stockregreport.setItem_category_id(rs3
							.getInt("item_category_id"));
					stockregreport.setUomcode(rs3.getString("uomcode"));
					stockregreport.setCost_price(rs3.getString("cost_price"));
					stockregreport.setOpening(rs3.getString("opening"));
					stockregreport.setInQty(rs3.getString("inqty"));
					stockregreport.setOutQty(rs3.getString("outqty"));
					// stockregreport.setTxnDate(rs.getString("txn_date"));
					stkregrptlist.add(stockregreport);
				}

			}

		}
		return stkregrptlist;
	}

	/**
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<ItemMaster> getItembyCategory(String category) throws Exception {

		final String sql = "SELECT * FROM stock_item WHERE item_category_id = "
				+ category;

		List<ItemMaster> list = null;

		CachedRowSet rs = getRowSet(sql);

		if (rs != null) {

			list = new ArrayList<ItemMaster>();

			while (rs.next()) {

				ItemMaster item = new ItemMaster();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}

		return list;

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