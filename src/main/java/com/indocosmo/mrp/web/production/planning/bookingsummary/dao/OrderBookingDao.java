package com.indocosmo.mrp.web.production.planning.bookingsummary.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;




public class OrderBookingDao extends GeneralDao<OrderBoonkingSummary> implements IOrderBookingDao {



	public OrderBookingDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public OrderBoonkingSummary getNewModelInstance() {

		return new OrderBoonkingSummary();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "order_booking_summary";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		String Sql="Select * from "+getTable()+" ";
		return getTableRowsAsJson(Sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.planning.planningdetail.dao.IPlanningDetailDao#deletedtlId(java.lang.String)
	 */
	public void deletedtlId(String deletdtlId) throws Exception {
		JsonParser parser = new JsonParser();
		JsonArray deletedtlId = (JsonArray) parser.parse(deletdtlId);
		String deletdtl="";

		for (int i = 0; i < deletedtlId.size(); i++) {

			deletdtl += deletedtlId.get(i).getAsInt() + (((deletedtlId.size()-1) ==i) ? "" : ",");
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE id in (" + deletdtl+	 ");";

		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}else{
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%')";
		}
		String sql = "SELECT itm.id,itm.`code`,itm.`name` FROM "+getTable()+" itm "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}else{
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%')";
		}
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	
	private boolean checkIfRecordExist(OrderBoonkingSummary orderSummary) throws Exception{
		// TODO Auto-generated method stub
		Boolean success=false;
		Integer count=0;
		final String sql="SELECT count(id) as id_count FROM "+getTable()+" WHERE delivery_date='"+orderSummary.getTrans_date()+"' "
				           + "AND stock_item_id="+orderSummary.getStock_item_id()+" AND shop_id="+orderSummary.getShop_id()+"";
		JsonArray record_num=getTableRowsAsJson(sql);
		if( record_num.size() !=0)
		{
			JsonObject json = (JsonObject) record_num.get(0);
			count=json.get("id_count").getAsInt();
		}
		if(count > 0)
		{
			success=true;
		}
		return success;
	}

	/**
	 * @param orderSummary
	 */
	/*private Double adjustBalanceQtyDifference(OrderBoonkingSummary orderSummary) throws Exception{
		// TODO Auto-generated method stub
		
		Double balnce=0.0;
		final String sql1="SELECT bk.stock_item_id as stock_item_id,bk.order_qty+bk.balance_qty+bk.adjustment_qty-bk.issued_qty as balanceqty "
		           + "FROM order_booking_summary bk "
		           + "LEFT JOIN stock_item s on s.id=bk.stock_item_id "
		           + "LEFT JOIN uoms on uoms.id=s.uom_id WHERE bk.id IN (SELECT max(bk1.id) FROM "
		           + "order_booking_summary bk1 GROUP BY bk1.shop_id,bk1.stock_item_id "
		           + "HAVING bk1.shop_id = "+orderSummary.getShop_id()+" AND bk.order_qty+bk.balance_qty+bk.adjustment_qty-bk.issued_qty>0 "
		           + "AND bk1.stock_item_id="+orderSummary.getStock_item_id()+")"
		           + " ORDER BY balanceqty DESC";
      JsonArray balanceData=getTableRowsAsJson(sql1);
    
		if( balanceData.size() !=0)
		{
			JsonObject json = (JsonObject) balanceData.get(0);
			balnce=json.get("balanceqty").getAsDouble();
		}
		
		
		return balnce;
		
	}*/

	/**
	 * @param order_date
	 * @param shop_code
	 * @param stock_item_id
	 * @return
	 */
	private Double getOrderQuantity(String order_date, Integer shop_id,
			Integer stock_item_id) throws Exception{
		// TODO Auto-generated method stub
		Double qty=0.0;
		
		final String sql="SELECT "
				           + " bk.stock_item_id,bk.shop_id,bk.order_qty AS orderqty,bk.delivery_date "
				           + "FROM order_booking_summary bk "
				           + "where bk.stock_item_id = "+stock_item_id+" "
				           + "AND bk.shop_id = "+shop_id+" "
				           + "AND bk.delivery_date = '"+order_date+"'";
		JsonArray orderArray=getTableRowsAsJson(sql);
		if( orderArray.size() !=0)
		{
			JsonObject json = (JsonObject) orderArray.get(0);
			qty=json.get("orderqty").getAsDouble();
		}
		
		
		return qty;
	}
	private Double getBalanceByPreviousDate(Integer shop_id,Integer stock_item_id) throws Exception{
	
	    Double qty=0.0;
	    final String sql="SELECT IFNULL((order_qty + balance_qty +adjustment_qty - issued_qty),0) AS balanc_qty "
			               + "FROM order_booking_summary WHERE id=(SELECT MAX(id) FROM "+getTable()+" "
			               + "WHERE stock_item_id="+stock_item_id+" AND shop_id="+shop_id+")";
	    JsonArray jArray=getTableRowsAsJson(sql);
	    if(jArray.size() !=0)
	    {
	    	JsonObject json=(JsonObject)jArray.get(0);
	    	qty=json.get("balanc_qty").getAsDouble();
	    }
	    return qty;
	}

	/**
	 * @return
	 */
	public JsonArray getBalanceQtyData() throws Exception{
		// TODO Auto-generated method stub
		/*final String sql1="SELECT sm.stock_item_id,	sm.order_qty+sm.balance_qty+sm.adjustment_qty-sm.issued_qty as balance_qty,sm.shop_id "
				          + "FROM "+getTable()+" sm WHERE id "
				         + "IN(SELECT max(sm1.id) FROM order_booking_summary sm1 GROUP BY sm1.shop_id,sm1.stock_item_id)";*/
		
		final String sql1="SELECT sm.stock_item_id,SUM(sm.order_qty)  - SUM(sm.issued_qty) AS balance_qty,sm.shop_id FROM order_booking_summary sm GROUP BY  stock_item_id,shop_id";
		return getTableRowsAsJson(sql1);
	}

	/**
	 * @param shopId
	 * @param closing_date 
	 * @return
	 */
	public JsonArray getShopBalanceQty(String shopId,String closing_date, HttpSession session) throws Exception{
		// TODO Auto-generated method stub
			SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			Date date = new Date();
			String today= new SimpleDateFormat("yyyy-MM-dd").format(date);
	
			
			
			
			final String sql1="SELECT * "
					+ "FROM(SELECT bk.stock_item_id AS stock_item_id,s.`name` AS "
					+ "stock_item_name,s.`code` AS stock_item_code,s.is_combo_item AS is_combo_item,"
					+ "uoms. CODE AS uomcode,uoms. NAME AS uomname,"
					+ "ROUND(SUM(bk.order_qty)-SUM(bk.issued_qty) "
					+ "-IFNULL((SELECT SUM(issued_qty) "
					+ " FROM order_booking_summary bk2 WHERE bk2.trans_type=3 AND "
					+ "bk2.shop_id='"+shopId+"' AND bk2.trans_date='"+today+"' "
					+ "AND bk.stock_item_id=bk2.stock_item_id),0)"
					+ ",1) AS balanceqty,bk.shop_id,"
					+ "round(0, 1) AS adjustqty,0 AS quantity,0 AS flag "
					+ "FROM order_booking_summary bk "
					+ "LEFT JOIN mrp_stock_item s ON s.id = bk.stock_item_id "
					+ "LEFT JOIN uoms ON uoms.id = s.uom_id "
					+ "WHERE bk.trans_date<'"+today+"' and shop_id= '"+shopId+"' "
					
					+ " GROUP BY stock_item_id ORDER BY balanceqty DESC) tbl "
					+ "WHERE tbl.balanceqty>0";
			
			
			
			
			
		
			
			
			return getTableRowsAsJson(sql1);
	}
	
	public JsonArray getBalanceQtyByDate(String closing_date) throws Exception{
		
		//final String sql1="select stock_item_id,shop_id,(order_qty+balance_qty+adjustment_qty-issued_qty) as balance,delivery_date as order_date from order_booking_summary";
		Date date = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		/*final String sql1="SELECT"
				               + " stock_item_id,shop_id,(SUM(order_qty) - SUM(issued_qty)) AS balance,"
				               + "trans_date AS order_date "
				               + "FROM "
				               + "order_booking_summary "
				               + "WHERE trans_date<'"+closing_date+"' "
				               + "GROUP BY stock_item_id,shop_id";*/
		
		
		final String sql1="SELECT tbl.stock_item_id,tbl.shop_id,(tbl.balance - adjust_qty) AS balance,tbl.order_date AS order_date "
				+ "FROM (SELECT sm.stock_item_id,sm.shop_id,(SUM(sm.order_qty) - SUM(sm.issued_qty)) AS balance,sm.trans_date AS order_date,"
				+ "IFNULL((SELECT "
				+ "SUM(obs.issued_qty) FROM "
				+ "order_booking_summary obs "
				+ "WHERE obs.trans_date = '"+today+"' AND obs.stock_item_id =sm.stock_item_id AND obs.shop_id = sm.shop_id "
				+ " AND obs.trans_type=3 ),0) AS adjust_qty "
				+ "FROM  order_booking_summary sm "
				+ "GROUP BY sm.stock_item_id,sm.shop_id) tbl";	
		
		
		
		
		return getTableRowsAsJson(sql1);
	}
	

	/**
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	/*public Double getPreviousIssuedQty(Integer shopId,Integer stockItemId,String orderDate) throws Exception{
		// TODO Auto-generated method stub
		
		Double issuedQty=0.0;
		final String sql1="SELECT issued_qty from order_booking_summary "
				           + "where stock_item_id="+stockItemId+" "
				           + "AND shop_id="+shopId+" "
				           + "AND order_date='"+orderDate+"'";
		
		 JsonArray jArray=getTableRowsAsJson(sql1);
		    if(jArray.size() !=0)
		    {
		    	JsonObject json=(JsonObject)jArray.get(0);
		    	issuedQty=json.get("issued_qty").getAsDouble();
		    }
		    return issuedQty;
	}
	
	public Double getPreviousBlnceQty(Integer shopId,Integer stockItemId,String orderDate) throws Exception{
		// TODO Auto-generated method stub
		
		Double issuedQty=0.0;
		final String sql1="SELECT balance_qty from order_booking_summary "
				           + "where stock_item_id="+stockItemId+" "
				           + "AND shop_id="+shopId+" "
				           + "AND order_date='"+orderDate+"'";
		
		 JsonArray jArray=getTableRowsAsJson(sql1);
		    if(jArray.size() !=0)
		    {
		    	JsonObject json=(JsonObject)jArray.get(0);
		    	issuedQty=json.get("balance_qty").getAsDouble();
		    }
		    return issuedQty;
	}*/
	public Double[] getPreviousRecordData(Integer shopId,Integer stockItemId,String orderDate) throws Exception{
		// TODO Auto-generated method stub
		Double[] prevsData=new Double[3];
		Double issuedQty=0.0;
		Double balQty=0.0;
		Double adjstQty=0.0;
		final String sql1="SELECT balance_qty,issued_qty,adjustment_qty from order_booking_summary "
				           + "where stock_item_id="+stockItemId+" "
				           + "AND shop_id="+shopId+" "
				           + "AND delivery_date='"+orderDate+"'";
		
		 JsonArray jArray=getTableRowsAsJson(sql1);
		    if(jArray.size() !=0)
		    {
		    	JsonObject json=(JsonObject)jArray.get(0);
		    	issuedQty=json.get("issued_qty").getAsDouble();
		    	balQty=json.get("balance_qty").getAsDouble();
		    	adjstQty=json.get("adjustment_qty").getAsDouble();
		    }
		    prevsData[0]=issuedQty;
		    prevsData[1]=balQty;
		    prevsData[2]=adjstQty;
		return prevsData;
	}
	
	public Double getAdjustedBalanceQuantity(OrderBoonkingSummary orderSummary) throws Exception
	{
		Double adjstedVal=0.0;
		//adjstedVal=orderSummary.getBalance_qty()-orderSummary.getOrder_qty()+orderSummary.getIssued_qty();
		return adjstedVal;
	}

	/**
	 * @param isWalkIn
	 * @return
	 */
	public Boolean checkIsWalkInCustomer(Integer isWalkIn) throws Exception{
		// TODO Auto-generated method stub
		Boolean isWakInCustomer=false;
		String sql="SELECT code FROM customers WHERE id="+isWalkIn;
		JsonArray resltArray=getTableRowsAsJson(sql);
		if(resltArray.size()!=0)
		{
			JsonObject json=(JsonObject)resltArray.get(0);
			isWakInCustomer=(json.get("code").getAsString().equals("WALKIN"))?true:false;
		}
		return isWakInCustomer;
	}

	public JsonArray getBalanceQtyDataDate(String closing_date) throws Exception{
		
		Date date = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(date);
		//insted of closing date use today
	
	/*	final String sql1="SELECT sm.stock_item_id,SUM(sm.order_qty)  - SUM(sm.issued_qty) AS balance_qty,sm.shop_id,IFNULL((SELECT COUNT(obkis.id) "
				+ "FROM order_booking_summary obkis WHERE sm.stock_item_id= obkis.stock_item_id AND sm.shop_id = obkis.shop_id "
				+ "AND obkis.trans_date = '"+closing_date+"'),0) AS is_adjst "
				+ " FROM order_booking_summary sm where sm.trans_date<'"+closing_date+"' GROUP BY  stock_item_id,shop_id ";*/
		
		final String sql1="SELECT tbl.*, (tbl.balance_qty1 - tbl.adjust_qty) AS balance_qty "
				+ "FROM(SELECT sm.stock_item_id,SUM(sm.order_qty) - SUM(sm.issued_qty) AS balance_qty1,sm.shop_id,IFNULL((SELECT SUM(obs.issued_qty) "
				+ "FROM order_booking_summary obs WHERE obs.trans_date = '"+today+"' AND obs.stock_item_id = sm.stock_item_id AND obs.shop_id = sm.shop_id "
				+ "AND obs.trans_type = 3),0) AS adjust_qty,IFNULL((SELECT "
				+ "COUNT(obkis.id) "
				+ "FROM order_booking_summary obkis "
				+ "WHERE sm.stock_item_id = obkis.stock_item_id AND sm.shop_id = obkis.shop_id AND obkis.trans_date = '"+today+"' "
				+ "AND obkis.trans_type = 3),0) AS is_adjst "
				+ "FROM order_booking_summary sm WHERE sm.trans_date < '"+today+"' GROUP BY stock_item_id, shop_id) tbl";
		
		return getTableRowsAsJson(sql1);

		
	}
	
	
	public JsonArray getPendingItemsByOrderNo(String order_no) throws Exception{
		
		String pendingItemQuery = "SELECT"
				+ " stock_item_id,order_pending AS pending"
				+ " FROM"
				+ " vw_order_pending_items"
				+ " WHERE"
				+ " ext_ref_id = '"+ order_no +"' AND order_pending > 0";
		return getTableRowsAsJson(pendingItemQuery);
	}
}
