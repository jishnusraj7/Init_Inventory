package com.indocosmo.mrp.web.production.planning.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.productionOrderStatusType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.production.planning.model.Planning;



public class PlanningDao extends GeneralDao<Planning> implements IPlanningDao {

	/**
	 * @param context
	 */
	public PlanningDao(ApplicationContext context) {
		
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Planning getNewModelInstance() {

		return new Planning();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "order_hdrs_booking";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getOrderDataAsJsonByDateCat(java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getOrderDataAsJsonByDateCat(String Orderid,HttpSession session) throws Exception {
		
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		Date date = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(date);

	/*	String sql="SELECT "
					+ "uom.`code` as uomcode,ordhdr.status,uom.name AS uomname,si.is_combo_item,  "
					+ "ROUND(orddtl.qty,"+systemSettings.getDecimal_places()+") as quantity,ordhdr.is_ar_customer,ordhdr.order_id ,0 as flag,"
					+ " round(ifnull(SUM(bk.order_qty)  - SUM(bk.issued_qty),0),"
					+ ""+systemSettings.getDecimal_places()+") as balanceqty, "
					+ "round(0,"+systemSettings.getDecimal_places()+") as adjustqty,"
					+ "CASE WHEN ordhdr.is_ar_customer=1 "
					+ " THEN IFNULL(ordhdr.customer_name, ordhdr.shop_code) "
					+ " ELSE ordhdr.shop_code "
					+ "END as orderfrom ,"
					+ "ordhdr.closing_date,  ordhdr.closing_time, ordhdr.order_no,ordhdr.order_date ,ordhdr.order_time,ordhdr.customer_name,"
					+ "ordhdr.customer_id as customerIds1,"
					+ "ordhdr.customer_address,ordhdr.customer_contact1,ordhdr.customer_contact2, si.id as stock_item_id,si.`code` as "
					+ "stock_item_code,si.name as stock_item_name  ,orddtl.order_date as dtlorder_date, orddtl.`name` as orderby ,"
					+ "orddtl.remarks as dtlremarks ,ordhdr.remarks  "
					+ "FROM "
					+ "order_dtls_booking orddtl "
					+ "LEFT JOIN order_hdrs_booking ordhdr on ordhdr.order_id=orddtl.order_id "
					+ "LEFT JOIN mrp_stock_item si on si.id=orddtl.sale_item_id "
					+ "LEFT JOIN uoms uom on uom.id= si.uom_id "
					+ "LEFT JOIN customers c ON c.`code`=ordhdr.shop_code "
					+ "LEFT JOIN order_booking_summary bk "
					+ "ON (bk.stock_item_id=orddtl.sale_item_id "
					+ "AND bk.shop_id=c.id "
					+ "AND bk.id IN(SELECT max(bk1.id) FROM order_booking_summary bk1 GROUP BY bk1.shop_id,bk1.stock_item_id))"
					+ "WHERE "
					+ "ordhdr.order_id='"+Orderid+"' ";*/
		
		
		String sql="SELECT uom.`code` AS uomcode,ordhdr. status,uom. NAME "
				+ "AS uomname,si.is_combo_item,ROUND(orddtl.qty, 1) AS quantity,"
				+ "ordhdr.is_ar_customer,ordhdr.order_id,0 AS flag,ordhdr.department_id,"
				+ "round(ifnull((SELECT SUM(order_qty) - SUM(issued_qty) "
				+ "FROM order_booking_summary obk  WHERE "
				+ "trans_date <= ordhdr.closing_date "
				+ "AND stock_item_id=si.id   AND c.`code`=ordhdr.shop_code GROUP BY stock_item_id LIMIT 1),0),1) "
				+ "AS balanceqty,"
				+"ROUND(IFNULL((SELECT obks.issued_qty FROM order_booking_summary obks WHERE obks.ext_ref_id=ordhdr.order_no   "
				+ "AND orddtl.sale_item_id=obks.stock_item_id AND obks.trans_type=3 ),0),1)  AS adjustqty, "
				+ " IFNULL((SELECT "
				+ "COUNT(phdr.id) "
				+ "FROM "
				+ "mrp_prod_hdr phdr INNER JOIN mrp_prod_dtl pdtl ON phdr.id=pdtl.prod_hdr_id "
				+ "WHERE pdtl.stock_item_id =  orddtl.sale_item_id "
				/*+ "AND phdr.shop_id = ordhdr.customer_id "*/
				+ "AND phdr.prod_date = '"+today+"'),0) AS is_adjst,"

				+ "CASE WHEN ordhdr.is_ar_customer = 1"
				+ " THEN IFNULL(ordhdr.customer_name,ordhdr.shop_code) "
				+ "ELSE ordhdr.shop_code END AS orderfrom,ordhdr.closing_date,ordhdr.closing_time_slot_id,ordhdr.order_no,ordhdr.order_date,ordhdr.order_time,"
				+ "ordhdr.customer_name, c.customer_type AS customer_type, ordhdr.customer_id AS customerIds1,ordhdr.customer_address, ordhdr.customer_contact1, ordhdr.customer_contact2,"
				+ " si.id AS stock_item_id, si.`code` AS stock_item_code, si. NAME AS stock_item_name, orddtl.order_date AS dtlorder_date, orddtl.`name`"
				+ " AS orderby, orddtl.remarks AS dtlremarks, ordhdr.remarks FROM order_dtls_booking orddtl LEFT JOIN order_hdrs_booking ordhdr ON ordhdr.order_id = orddtl.order_id "
				+"LEFT JOIN customers c ON ordhdr.customer_id=c.id"
				+ " LEFT JOIN mrp_stock_item si ON si.id = orddtl.sale_item_id LEFT JOIN uoms uom ON uom.id = si.uom_id "
			//	+ "LEFT JOIN customers c ON c.`code` = ordhdr.shop_code "
				+ "WHERE "
				+ "ordhdr.order_id = '"+Orderid+"'";


		

		return getTableRowsAsJson(sql);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		String Sql="SELECT * FROM "+getTable()+" ";
		return getTableRowsAsJson(Sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getOrderDataAsJson()
	 */
	@Override
	public JsonArray getOrderDataAsJson() throws Exception {
		
		String Sql="SELECT "
					+ "CONCAT('Total Orders:',CAST(COUNT(1) as CHAR)) AS title, DATE(closing_date) as start, '#' as url "
					+ ",(case(min(order_hdrs_booking.`status`)) when 0 then 1 else 0 END  )as isNotFinalized FROM "
					+ "order_hdrs_booking "
					+ "GROUP BY DATE(closing_date) "
					+ "LIMIT 0 , 30";
		return getTableRowsAsJson(Sql);
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getCustomerDataAsJson() throws Exception {

		/*String Sql="SELECT id,name,code FROM customers WHERE is_ar=1";*/
		String Sql="SELECT id,name,code,shop_id  FROM vw_production_customers WHERE shop_id IS NOT NULL";
		return getTableRowsAsJson(Sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getShopOrderNo()
	 */
	@Override
	public JsonArray getShopOrderNo() throws Exception {

		String Sql="SELECT "
					+ "CONCAT('Shop Orders:',CAST(COUNT(1) as CHAR)) AS title, DATE(closing_date) as start ,'#' as url "
					+ ",(case(min(order_hdrs_booking.`status`)) when 0 then 1 else 0 END  )as isNotFinalized FROM "
					+ "order_hdrs_booking WHERE is_ar_customer=2 "
					+ "GROUP BY DATE(closing_date) "
					+ "LIMIT 0 , 30";

		return getTableRowsAsJson(Sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getCustomerOrderNo()
	 */
	@Override
	public JsonArray getCustomerOrderNo() throws Exception {
		
		String sql="SELECT "
					+ "CONCAT('Customer Orders:',CAST(COUNT(1) as CHAR)) AS title, DATE(closing_date) as start ,  '#' as url   "
			     	+ ",(case(min(order_hdrs_booking.`status`)) when 0 then 1 else 0 END  )as isNotFinalized FROM "
			     	+ "order_hdrs_booking "
			     	+ "WHERE "
			     	+ "is_ar_customer=1 "
			     	+ "GROUP BY DATE(closing_date) "
			     	+ "LIMIT 0 , 30";
		return getTableRowsAsJson(sql);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getRowWount(com.indocosmo.mrp.web.core.base.model.GeneralModelBase)
	 */
	@Override
	protected int getRowWount(Planning item) throws Exception{


		final String tableName=getTable(item);
		final String wherePart=getWhere(item);

		final String sql= "SELECT COUNT(order_id) AS  row_count FROM " + tableName + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";

		return excuteSQLForRowCount(sql);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getOrderDataAsJsonByDate(java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getOrderDataAsJsonByDate(String closing_date,String iscustomer,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
String custQuery="";
if(!iscustomer.equals("0"))
{
	custQuery="AND ordhdr.is_ar_customer="+iscustomer+"";	
}


		String Sql="SELECT  "
				    + "si.item_category_id, si.id as stock_item_id,si.`name` AS stock_item_name,"
				    + "si.`code` as stock_item_code ,   "
				    + "CASE WHEN ordhdr.is_ar_customer=1 "
				    +  "THEN ordhdr.customer_name "
				  //  +  "ELSE ordhdr.shop_code "
				    +  "ELSE c.name "
				    + "END AS orderfrom , "
				    + "ordhdr.order_date,ordhdr.closing_date,ordhdr.is_ar_customer,ordhdr.shop_code,ordhdr.customer_name,"
				    + "ROUND(SUM(ordtl.qty),"+systemSettings.getDecimal_places()+") AS qty     "
				    + "FROM "
				    + "order_dtls_booking ordtl "
				    + "LEFT JOIN order_hdrs_booking ordhdr on ordtl.order_id=ordhdr.order_id   "
				    + "LEFT JOIN mrp_stock_item si on si.id=ordtl.sale_item_id "
				    + "LEFT JOIN customers c ON c.`code`=ordhdr.shop_code "
				    + "WHERE "
				    + "ordhdr.closing_date='"+closing_date+"'  "+custQuery+"   "
				    + " AND ordhdr.status!=0 "
				    + "GROUP BY ordtl.sale_item_id,ordhdr.shop_code,orderfrom  "
				    + "ORDER BY orderfrom,stock_item_name";	

		return getTableRowsAsJson(Sql);
	}
	
	
	
	public JsonArray getOrderDataAsJsonByDateReport(String closing_date,String iscustomer,String item_cate_id,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
String custQuery="";
if(!iscustomer.equals("0"))
{
	custQuery="AND ordhdr.is_ar_customer="+iscustomer+"";	
}
String itemcatQuery="";
if(!item_cate_id.equals("0"))
{
	itemcatQuery="AND si.item_category_id="+item_cate_id+"";	
}


		String Sql="SELECT  "
				    + "si.item_category_id, si.id as stock_item_id,si.`name` AS stock_item_name,"
				    + "si.`code` as stock_item_code ,   "
				    + "CASE WHEN ordhdr.is_ar_customer=1 "
				    +  "THEN ordhdr.customer_name "
				  //  +  "ELSE ordhdr.shop_code "
				    +  "ELSE c.name "
				    + "END AS orderfrom , "
				    + "ordhdr.order_date,ordhdr.closing_date,ordhdr.is_ar_customer,ordhdr.shop_code,ordhdr.customer_name,"
				    + "ROUND(SUM(ordtl.qty),"+systemSettings.getDecimal_places()+") AS qty     "
				    + "FROM "
				    + "order_dtls_booking ordtl "
				    + "LEFT JOIN order_hdrs_booking ordhdr on ordtl.order_id=ordhdr.order_id   "
				    + "LEFT JOIN mrp_stock_item si on si.id=ordtl.sale_item_id "
				    + "LEFT JOIN customers c ON c.`code`=ordhdr.shop_code "
				    + "WHERE "
				    + "ordhdr.closing_date='"+closing_date+"'  "+custQuery+" "+itemcatQuery+"  "
				    + " AND ordhdr.status!=0 "
				    + "GROUP BY ordtl.sale_item_id,ordhdr.shop_code,orderfrom  "
				    + "ORDER BY orderfrom,stock_item_name";	

		return getTableRowsAsJson(Sql);
	}
	
	
	
	
	


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#updateStatus(java.lang.Integer)
	 */
	public int updateStatus(Integer id) throws Exception{
		
		int res;

		try{

			beginTrans();
			final String sql= "UPDATE "+getTable()+" SET status = 1 WHERE order_id="+id+"";
			res=executeSQL(sql);
			endTrans();

		}catch(Exception e){

			throw e;
		}

		return res;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getOrderAsJsonByDate(java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	public JsonArray getOrderAsJsonByDate(String selectedDate,String iscustomer,HttpSession session) throws Exception {

		String sql="CALL GetSalesReport("+iscustomer+", '"+selectedDate+"')";

		return getTableRowsAsJson(sql);
	}





	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		
		String adnlFilterPart="";
		
		for(DataTableColumns col:columnList){

			if(col.getSearchValue()!="" && col.getSearchValue()!=null){

				 if(col.getData().equals("order_no")) {
						adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" LIKE '%"+col.getSearchValue()+"%'";	
					}else if(col.getData().equals("status")) {
						adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" IN("+col.getSearchValue()+")";	
					}
					else
					{
				adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" = ('"+col.getSearchValue()+"')";
					}
			
			
			}
		}
		return adnlFilterPart;
	}	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart!=null || wherePart!=""){

			wherePart="WHERE  "
   					   + "("+getTable()+".order_no LIKE '%"+searchCriteria+"%' "
   					   + "OR "+getTable()+".shop_code LIKE '%"+searchCriteria+"%' "
   					   + "OR "+getTable()+".customer_name LIKE '%"+searchCriteria+"%' )";
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
			}
			else{
				
				wherePart=" WHERE "+adnlFilterPart;
			}
		}
		
		/*String sql="SELECT  "
			    + getTable()+".order_id ,"+getTable()+".is_ar_customer,"+getTable()+".status ,"
			    + "CASE WHEN "+getTable()+".is_ar_customer=1 "
			    + "THEN IFNULL("+getTable()+".customer_name,"+getTable()+".shop_code) "
			    + "ELSE "+getTable()+".shop_code  "
			    + "END AS orderfrom, "
			    +  getTable()+".closing_date,"+getTable()+".closing_time,"+getTable()+".order_no,"+getTable()+".remarks"
			    + "(CASE "
			    + "WHEN TIMEDIFF(order_hdrs_booking.closing_time, NOW()) > 0 THEN "
			    + "TIMEDIFF(order_hdrs_booking.closing_time, NOW()) "
			    + "ELSE '00:00:00' END)AS hrs_to_left "
			    + "FROM "
			    + getTable()+"  "
			    + wherePart+ "   "
			    + sortPart+" "
			    + "LIMIT "+limitRows+" "
			    + "OFFSET "+offset+"";*/
		

		String sql="SELECT  "
				    + getTable()+".order_id ,"+getTable()+".is_ar_customer,"+getTable()+".status ,"
				    + "CASE WHEN "+getTable()+".is_ar_customer=1 "
				    + "THEN IFNULL("+getTable()+".customer_name,"+getTable()+".shop_code) "
				    + "ELSE "+getTable()+".shop_code  "
				    + "END AS orderfrom, "
				    +  getTable()+".closing_date,"+getTable()+".closing_time_slot_id,timeslot.`name` AS delevery_time, "+getTable()+".order_no,"+getTable()+".remarks  "
				    + "FROM "
				    + getTable()+" LEFT JOIN mrp_time_slot timeslot ON timeslot.id=order_hdrs_booking.closing_time_slot_id "
				    + wherePart+ "   "
				    + sortPart+" "
				    + "LIMIT "+limitRows+" "
				    + "OFFSET "+offset+"";


		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart!=null || wherePart!=""){

			wherePart="WHERE  "
   					   + "("+getTable()+".order_no LIKE '%"+searchCriteria+"%' "
   					   + "OR "+getTable()+".shop_code LIKE '%"+searchCriteria+"%' "
   					   + "OR "+getTable()+".customer_name LIKE '%"+searchCriteria+"%' )";
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
			}
			else{
				
				wherePart=" WHERE "+adnlFilterPart;
			}
		}
		String sqlCount="SELECT COUNT(order_id) AS row_count FROM "+getTable()+" "+wherePart;
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.dao.IPlanningDao#getOrderItemTotal(java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getOrderItemTotal(String closingDate,String iscustomer, HttpSession session) throws Exception {
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		String sql="";
		if(iscustomer.equals("3") || iscustomer.equals("0")){
			sql="SELECT "
				 + "si.item_category_id ,si.id as stock_item_id,ordhdr.order_date,ordhdr.closing_date,si.`name` as stock_item_name,si.`code` as stock_item_code,    "
				 + "ROUND(SUM(qty),"+systemSettings.getDecimal_places()+") AS qty   "
				 + "FROM "
				 + "order_dtls_booking "
				 + "LEFT JOIN mrp_stock_item si on si.id=sale_item_id  "
				 + "LEFT JOIN order_hdrs_booking ordhdr on ordhdr.order_id=order_dtls_booking.order_id "
				 + "WHERE "
				 + "ordhdr.closing_date='"+closingDate+"'  AND ordhdr.status!=0 "
				 + "GROUP BY sale_item_id "
				 + "ORDER BY stock_item_name";

		}else{

			sql="SELECT "
				+ "si.item_category_id ,si.id as stock_item_id,si.`name` as stock_item_name,si.`code` as stock_item_code,    "
				+ "ROUND(sum(qty),"+systemSettings.getDecimal_places()+") AS qty   "
				+ "FROM "
				+ "order_dtls_booking "
				+ "LEFT JOIN mrp_stock_item si ON si.id=sale_item_id  "
				+ "LEFT JOIN order_hdrs_booking ordhdr ON ordhdr.order_id=order_dtls_booking.order_id "
				+ "WHERE "
				+ "ordhdr.closing_date='"+closingDate+"'  AND ordhdr.is_ar_customer="+iscustomer+"  "
				+ " AND ordhdr.status!=0 "
				+ "GROUP BY sale_item_id "
				+ "ORDER BY stock_item_name";
		}
		return getTableRowsAsJson(sql);
	}
	
	
	
	public JsonArray getOrderItemTotalReport(String closingDate,String iscustomer, String item_cate_id,HttpSession session) throws Exception {
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		String sql="";
		String itemcatQuery="";
		if(!item_cate_id.equals("0"))
		{
			itemcatQuery="AND si.item_category_id="+item_cate_id+"";	
		}
		
		
		if(iscustomer.equals("3") || iscustomer.equals("0")){
			sql="SELECT "
				 + "si.item_category_id ,si.id as stock_item_id,ordhdr.order_date,ordhdr.closing_date,si.`name` as stock_item_name,si.`code` as stock_item_code,    "
				 + "ROUND(SUM(qty),"+systemSettings.getDecimal_places()+") AS qty   "
				 + "FROM "
				 + "order_dtls_booking "
				 + "LEFT JOIN mrp_stock_item si on si.id=sale_item_id  "
				 + "LEFT JOIN order_hdrs_booking ordhdr on ordhdr.order_id=order_dtls_booking.order_id "
				 + "WHERE "
				 + "ordhdr.closing_date='"+closingDate+"'  AND ordhdr.status!=0  "+itemcatQuery+" "
				 + "GROUP BY sale_item_id "
				 + "ORDER BY stock_item_name";

		}else{

			sql="SELECT "
				+ "si.item_category_id ,si.id as stock_item_id,si.`name` as stock_item_name,si.`code` as stock_item_code,    "
				+ "ROUND(sum(qty),"+systemSettings.getDecimal_places()+") AS qty   "
				+ "FROM "
				+ "order_dtls_booking "
				+ "LEFT JOIN mrp_stock_item si ON si.id=sale_item_id  "
				+ "LEFT JOIN order_hdrs_booking ordhdr ON ordhdr.order_id=order_dtls_booking.order_id "
				+ "WHERE "
				+ "ordhdr.closing_date='"+closingDate+"'  AND ordhdr.is_ar_customer="+iscustomer+" "+itemcatQuery+" "
				+ " AND ordhdr.status!=0 "
				+ "GROUP BY sale_item_id "
				+ "ORDER BY stock_item_name";
		}
		return getTableRowsAsJson(sql);
	}
	
	
	

	/**
	 * @return
	 */
	public JsonArray getCustomerIdsAsJson() throws Exception{
		// TODO Auto-generated method stub
		final String Sql1="SELECT id,code,name,address,phone,customer_type from vw_production_customers WHERE shop_id IS NULL";
		return getTableRowsAsJson(Sql1);
	}

	/**
	 * @param orderDate
	 */
	public boolean isOrderDateValid(String closingDate) throws Exception{
		
		boolean isValid=true;
		//final String sql="SELECT count(*) as row_count FROM order_booking_summary WHERE order_date>'"+orderDate+"'";
		
		final String sql="SELECT COUNT(order_id) as row_count FROM order_hdrs_booking WHERE closing_date<'"+closingDate+"' AND status=0";
		final CachedRowSet rs=executeSQLForRowset(sql);
		while(rs.next())
		{
			if(rs.getInt("row_count") >0)
			{
				isValid=false;
				break;
			}
		}
		return isValid;
	}

	public boolean isOrderDateValidAdd(String orderDate) throws Exception{
	
		// TODO Auto-generated method stub
		boolean isDateValid=true;
		final String sql="SELECT count(*) as row_count FROM order_booking_summary WHERE trans_date>'"+orderDate+"'";
		final CachedRowSet rs=executeSQLForRowset(sql);
		while(rs.next())
		{
			if(rs.getInt("row_count") >0)
			{
				isDateValid=false;
				break;
			}
		}
		return isDateValid;
	}

	public Integer updateOrderStatus(String productionId, int status) throws Exception {
		
		Integer update=0;
		try{
			final String sql="UPDATE order_hdrs_booking SET status = "
					+ status +" WHERE order_no IN('"+productionId+"')";
			update = executeSQL(sql);
		}catch(Exception e){

			throw e;
		}
		return update;
	}

	public JsonArray getOrderRawItemTotal(String orderdate , String customer , String item_cate_id, HttpSession session) throws Exception{
	
		// TODO Auto-generated method stub
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		String custQuery="";
		String itemcatQuery="";
		if(!customer.equals("0") && !customer.equals("3"))
		{
			custQuery="AND ordhdr.is_ar_customer="+customer+"";	
		}
		if(!item_cate_id.equals("0"))
		{
			itemcatQuery="AND item.item_category_id="+item_cate_id+"";	
		}
		
		final String sql="SELECT "
			                 + "C.closing_date AS closing_date,"
				             + "C.bomItemId,C.bomItemName AS Item_name,"
				             + "ROUND(IFNULL(SUM(C.total_qty),0),"+systemSettings.getDecimal_places()+") AS total_required_qty "
				             + "FROM"
				             + "(SELECT A.closing_date,B.bomItemId,B.bomItemName,B.bom_qty, A.prodItemName,"
				             + "A.qty,A.prodItemId,(A.qty * B.bom_qty)/stock_item_qty AS total_qty "
				             + "FROM "
				             + "(SELECT ordtl.sale_item_id AS prodItemId,item.`name` prodItemName,"
				             + "ordhdr.closing_date,ordhdr.is_ar_customer,ROUND(SUM(ordtl.qty), 2) AS qty "
				             + "FROM "
				             + "order_dtls_booking ordtl "
				             + "LEFT JOIN "
				             + "order_hdrs_booking ordhdr ON ordtl.order_id = ordhdr.order_id "
				             + "INNER JOIN mrp_stock_item item ON item.id = ordtl.sale_item_id "
				             + "WHERE ordhdr.closing_date = '"+orderdate+"'  "+itemcatQuery+" "+custQuery+" "
				             + "AND ordhdr. STATUS != 0 "
				             + "GROUP BY "
				             + "ordtl.sale_item_id) A "
				             + "LEFT JOIN "
				             + "(SELECT bom.bom_item_id AS bomItemId,bom.bom_item_qty AS bom_qty,"
				             + "bom.stock_item_qty AS stock_item_qty,si.`name` AS bomItemName,"
				             + "bom.stock_item_id AS stock_item_id "
				             + "FROM stock_item_bom bom "
				             + "INNER JOIN "
				             + "mrp_stock_item si ON bom.bom_item_id = si.id "
				             + "WHERE "
				             + "bom.is_deleted = 0 "
				             + "ORDER BY "
				             + "bom.stock_item_id) B "
				             + "ON A.prodItemId = B.stock_item_id) C "
				             + "GROUP BY "
				             + "C.bomItemId "
				             + "Order by C.bomItemName";
		
		
		return getTableRowsAsJson(sql);
	}

	public JsonArray getOrdertotalList(String currentDate) throws Exception{
	
		// TODO Auto-generated method stub
		
		final String sql="SELECT "
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE is_ar_customer=2 and closing_date='"+currentDate+"') AS shop_order,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE is_ar_customer=1 and closing_date='"+currentDate+"')AS cust_order,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE   closing_date='"+currentDate+"')AS total_order,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE `status`=0 and closing_date='"+currentDate+"')AS pending,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE `status`=1 and closing_date='"+currentDate+"')AS finalized,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE `status`=2 and closing_date='"+currentDate+"')AS production,"
				+ "(SELECT count(order_id) FROM order_hdrs_booking WHERE `status`=3 and closing_date='"+currentDate+"')AS finished";
		return  getTableRowsAsJson(sql);
	}

	public JsonArray getaddorderdate() throws Exception{
	
		// TODO Auto-generated method stub
		final String sql="SELECT "
				+ "DISTINCT (closing_date) AS date,"
				+ "(CASE "
				+ "WHEN closing_date = Date(NOW())THEN 'curdate' ELSE 'prodadd' END) AS css,'' AS title,"
						+ "1 AS selectable FROM order_hdrs_booking";
		return  getTableRowsAsJson(sql);
	}

	public JsonArray getDepartmentsAsJson() throws Exception{
		// TODO Auto-generated method stub
		final String Sql="SELECT id,code,name from mrp_department";
		return getTableRowsAsJson(Sql);
	}
	
	/*public void updateOrderStatusInDtl(String order_no,  String stock_item_id) throws Exception {
		
		Integer update=0;
		try{
			final String sql="UPDATE order_hdrs_booking SET status = "
					+ status +" WHERE order_id IN("+productionIds+")";
			executeSQL(sql);
		}catch(Exception e){

			throw e;
		}
	}*/
	
}
