package com.indocosmo.mrp.web.report.productionreport.dao;

import java.sql.ResultSet;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.sun.rowset.CachedRowSetImpl;


public class ProductionReportDao extends GeneralDao<ProductionReportModel> implements IProductionReportDao{

	public ProductionReportDao(ApplicationContext context) {

		super(context);
	
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ProductionReportModel getNewModelInstance() {

		return new ProductionReportModel();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.productionreport.dao.IProductionReportDao#getProductionItemTotal(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getProductionItemTotal(String startdate,String enddate,String option,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		String Sql="";		

		if(option.equals("3")){

			Sql="SELECT "
					+ "si.item_category_id ,order_dtls_booking.remarks,si.id as stock_item_id,ordhdr.order_date,"
					+ "si.`name` as stock_item_name,si.`code` as stock_item_code,    "
					+ "ROUND(SUM(qty),"+systemSettings.getDecimal_places()+") AS qty   "
					+ "FROM "
					+ "order_dtls_booking "
					+ "LEFT JOIN mrp_stock_item si on si.id=sale_item_id  "
					+ "LEFT JOIN order_hdrs_booking ordhdr on ordhdr.order_id=order_dtls_booking.order_id "
					+ "WHERE "
					+ "ordhdr.order_date Between '"+startdate+"'  and '"+enddate+"'    AND ordhdr.status = 1 "
					+ "GROUP BY sale_item_id ORDER BY sale_item_id";

		}else{

			Sql="SELECT "
					+ "si.item_category_id ,order_dtls_booking.remarks,si.id as stock_item_id,ordhdr.order_date,si.`name` AS stock_item_name,"
					+ "si.`code` as stock_item_code,    ROUND(sum(qty),"+systemSettings.getDecimal_places()+") AS qty   "
					+ "FROM "
					+ "order_dtls_booking "
					+ "LEFT JOIN mrp_stock_item si ON si.id=sale_item_id  "
					+ "LEFT JOIN order_hdrs_booking ordhdr ON ordhdr.order_id=order_dtls_booking.order_id "
					+ "WHERE "
					+ "ordhdr.order_date BETWEEN '"+startdate+"'  AND '"+enddate+"' AND ordhdr.is_ar_customer="+option+"   AND ordhdr.status = 1 "
					+ "GROUP BY sale_item_id "
					+ "ORDER BY sale_item_id";



		}
		return getTableRowsAsJson(Sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.productionreport.dao.IProductionReportDao#getProductionData(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getProductionData(String stock_item_id,String companyId,String startdate,String enddate,String option,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		Integer daily_prod_view= (Integer) context.getCurrentHttpSession().getAttribute("dailyprodview");
		String start_date=startdate;
		String end_date=enddate;
		String opt=option;
		String StkItmCon="";
		String StkItmCon1="";
		String CmpnyCon="";
		if(stock_item_id!=""){
			StkItmCon="and (stock_item_id IN("+stock_item_id+"))";
			StkItmCon1="and (dtl.sale_item_id IN("+stock_item_id+"))";
		}
		
		if(companyId!=""){
			//CmpnyCon="and (cus.id="+companyId+" )";
			
			if(companyId.contains(",")){
				CmpnyCon="and (cus.id IN("+companyId+"))";
			}
			
			else{
				if(Integer.parseInt(companyId)==101){
					CmpnyCon="and (cus.shop_id IN("+companyId+"))";
				}
				else{
					CmpnyCon="and (cus.id IN("+companyId+"))";
				}
				
			}
		}
		if(stock_item_id.equals(""))
		{
			stock_item_id=null;
			StkItmCon1=null;
		}else
		{
			stock_item_id="'"+StkItmCon+"'";
			StkItmCon1="'"+StkItmCon1+"'";
		}
		if(companyId.equals(""))
		{
			companyId=null;
		}else
			companyId="'"+CmpnyCon+"'";
		

		String sql="";
		if(option.equals("3")){
			
			if(daily_prod_view==1)
			{
			sql="CALL usp_prod_order_report_summary_1('"+start_date+"','"+end_date+"')";
			}
			else
			{
				sql="CALL usp_prod_order_report_summary('"+start_date+"','"+end_date+"')";	
			}

		}else if(option.equals("2")){
			if(daily_prod_view==1)
			{

			 sql="CALL usp_prod_order_shop_report_1("+stock_item_id+", "+companyId+" , '"+start_date+"','"+end_date+"')";
			}
			else
			{
				 sql="CALL usp_prod_order_shop_report("+stock_item_id+", "+companyId+" , '"+start_date+"','"+end_date+"')";
			}

		}
		else if(option.equals("1")){

			 sql="CALL usp_prod_customer_report('"+start_date+"','"+end_date+"',"+stock_item_id+", "+companyId+","+StkItmCon1+" )";

		}
		return getTableRowsAsJson(sql);
	}


	public CachedRowSet executeSQLForStordProc(ResultSet rSet) throws Exception {

		CachedRowSet crs = null;

		if (rSet != null) {

			crs = new CachedRowSetImpl();
			crs.populate(rSet);
			rSet.close();
		}

		return crs;
	}

	public JsonArray getProductionBalanceData(String stock_item_id , String companyId , String startdate ,
			String option , HttpSession session) throws Exception {
	
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		String start_date=startdate;
		String opt=option;
		String StkItmCon="";
		String CmpnyCon="";
		if(stock_item_id!=""){
			StkItmCon=" and (stock_item_id IN("+stock_item_id+"))";
		}
		
		if(companyId!=""){
			//CmpnyCon="and (cus.id="+companyId+" )";
			
			if(companyId.contains(",")){
				CmpnyCon="and (bk.shop_id IN("+companyId+"))";
			}
			
			else{
				if(Integer.parseInt(companyId)==101){
					CmpnyCon="and (shop_id IN("+companyId+"))";
				}
				else{
					CmpnyCon="and (bk.shop_id IN("+companyId+"))";
				}
				
			}
			

		}
		

		String sql="";
		if(option.equals("2")){
			
			sql="SELECT * "
					+ "FROM (SELECT bk.stock_item_id AS stock_item_id,s.`name` AS stock_item_name,cust.`name` AS orderfrom,"
					+ "s.`code` AS stock_item_code,uoms. CODE AS uomcode,uoms. NAME AS uomname,"
					+ "ROUND(SUM(bk.order_qty) - SUM(bk.issued_qty),"+systemSettings.getDecimal_places()+") AS balanceqty,bk.shop_id "
					+ "FROM order_booking_summary bk LEFT JOIN mrp_stock_item s ON s.id = bk.stock_item_id "
					+ "LEFT JOIN uoms ON uoms.id = s.uom_id INNER JOIN customers cust ON cust.id=bk.shop_id "
					+ "WHERE bk.trans_date <= '"+startdate+"' AND ISNULL(cust.shop_id)  "
					+ "GROUP BY stock_item_id,shop_id ORDER BY cust.name,balanceqty DESC) tbl "
					+ "WHERE tbl.balanceqty > 0  "+StkItmCon+" "+CmpnyCon+" ";
			System.out.println("if sql=================================>"+sql);
		}
		else if(option.equals("1"))
		{
			sql="SELECT * "
					+ "FROM (SELECT bk.stock_item_id AS stock_item_id,s.`name` AS stock_item_name,cust.`name` AS orderfrom,"
					+ "s.`code` AS stock_item_code,uoms. CODE AS uomcode,uoms. NAME AS uomname,"
					+ "ROUND(SUM(bk.order_qty) - SUM(bk.issued_qty),"+systemSettings.getDecimal_places()+") AS balanceqty,bk.shop_id "
					+ "FROM order_booking_summary bk LEFT JOIN mrp_stock_item s ON s.id = bk.stock_item_id "
					+ "LEFT JOIN uoms ON uoms.id = s.uom_id INNER JOIN customers cust ON cust.id=bk.shop_id "
					+ "WHERE bk.trans_date <= '"+startdate+"' "+CmpnyCon+" "
					+ "GROUP BY stock_item_id,shop_id ORDER BY cust.name,balanceqty DESC) tbl "
					+ "WHERE tbl.balanceqty > 0 "+StkItmCon+" ";
			System.out.println("else sql=================================>"+sql);

		}
		
		
		return getTableRowsAsJson(sql);
	}
}
