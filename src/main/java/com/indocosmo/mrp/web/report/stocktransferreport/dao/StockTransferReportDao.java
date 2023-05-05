package com.indocosmo.mrp.web.report.stocktransferreport.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stocktransferreport.model.StockTransferReportModel;


public class StockTransferReportDao extends GeneralDao<StockTransferReportModel>{

	public StockTransferReportDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StockTransferReportModel getNewModelInstance() {
	
		// TODO Auto-generated method stub
		return new StockTransferReportModel();
	}

	@Override
	protected String getTable() {
	
		// TODO Auto-generated method stub
		return "";
	}

	public JsonArray getItemData(String startdate , String enddate, String transferType,String itemId) throws Exception {
	
		String sql="";
		if(itemId!=null && itemId!=""){
			String condition=" AND dtl.stock_item_id IN ("+itemId+")";
			
			sql="SELECT hdr.dest_company_id,hdr.dest_company_code, hdr.dest_department_name, hdr.transfer_type,hdr.dest_department_code,"
					+ "hdr.dest_company_name,hdr.stock_transfer_date,dtl.stock_item_id,dtl.stock_item_code,"
					+ "dtl.stock_item_name,SUM(dtl.issued_qty) AS issued_qty,dtl.cost_price,SUM(dtl.amount) AS amount "
					+ "FROM mrp_stock_transfer_hdr hdr "
					+ "INNER JOIN mrp_stock_transfer_dtl dtl ON hdr.id = dtl.stock_transfer_hdr_id "
					+ "WHERE hdr.req_status = 1 AND  hdr.transfer_type = "+transferType+" AND stock_transfer_date BETWEEN '"+startdate+"' AND '"+enddate+"'"
					+condition+ "GROUP BY stock_item_id,stock_transfer_date,dest_company_id "
					+ "ORDER BY dest_company_name,stock_transfer_date";
		}else{
			sql="SELECT hdr.dest_company_id,hdr.dest_company_code, hdr.dest_department_name, hdr.transfer_type,hdr.dest_department_code,"
					+ "hdr.dest_company_name,hdr.stock_transfer_date,dtl.stock_item_id,dtl.stock_item_code,"
					+ "dtl.stock_item_name,SUM(dtl.issued_qty) AS issued_qty,dtl.cost_price,SUM(dtl.amount) AS amount "
					+ "FROM mrp_stock_transfer_hdr hdr "
					+ "INNER JOIN mrp_stock_transfer_dtl dtl ON hdr.id = dtl.stock_transfer_hdr_id "
					+ "WHERE hdr.req_status = 1 AND  hdr.transfer_type = "+transferType+"  AND stock_transfer_date BETWEEN '"+startdate+"' AND '"+enddate+"'"
					+ "GROUP BY stock_item_id,stock_transfer_date,dest_company_id "
					+ "ORDER BY dest_company_name,stock_transfer_date";
		}
		
	 
	
		return getTableRowsAsJson(sql);
	}
	
	
	
	
	
	
}
