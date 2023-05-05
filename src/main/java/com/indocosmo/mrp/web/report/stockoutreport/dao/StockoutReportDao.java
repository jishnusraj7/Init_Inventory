package com.indocosmo.mrp.web.report.stockoutreport.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;

public class StockoutReportDao extends GeneralDao<StockoutReportModel> implements IStockoutReportDao{

	public StockoutReportDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockoutReportModel getNewModelInstance() {
		return new StockoutReportModel();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		return "";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.stockoutreport.dao.IStockoutReportDao#getreportDetails(com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel)
	 */
	public List<StockoutReportModel>  getreportDetails( StockoutReportModel stkoutrpt) throws Exception 
	{

		List<StockoutReportModel> stkoutrptlist=null;

		String DestDptQuery="";
		String StockItemQuery="";
		String SrcDptQuery="";

		String sql="";

		if(stkoutrpt.getDestDepartmentId()!=-1){
			DestDptQuery="and (stkhdr.dest_department_id="+stkoutrpt.getDestDepartmentId()+")";
		}
		if(stkoutrpt.getStockItemId()!=""){
			StockItemQuery="and (stkdtl.stock_item_id in ("+stkoutrpt.getStockItemId()+") )";

		}

		if(stkoutrpt.getSourceDepartmentId()!=-1){
			SrcDptQuery="and (stkhdr.source_department_id="+stkoutrpt.getSourceDepartmentId()+")";

		}

		if(stkoutrpt.getOption()==1)
		{

			sql="SELECT "
					+ "itmcat.`name` AS itm_cat_name,uoms.`code` AS uomname,stkdtl.stock_item_code,stkdtl.issued_qty,"
					+ "stkdtl.stock_item_name,stkdtl.cost_price,stkhdr.stock_transfer_date,stkhdr.stock_transfer_no,"
					+ "stkdtl.amount,stkhdr.total_amount,srcdpt.`name` AS source_department_name,"
					+ "destdpt.`name` AS dest_department_name, "
					+ "mrpwarprice.war_rate " //added by udhay on 14 feb 2022
					+ "FROM "
					+ "mrp_stock_out_hdr stkhdr "
					+ "INNER JOIN mrp_department srcdpt ON stkhdr.source_department_id = srcdpt.id "
					+ "LEFT JOIN mrp_department destdpt ON stkhdr.dest_department_id = destdpt.id "
					+ "INNER JOIN mrp_stock_out_dtl stkdtl ON stkdtl.stock_out_hdr_id = stkhdr.id  "
					+ "INNER JOIN mrp_stock_item stkitm ON stkdtl.stock_item_id = stkitm.id "
					+ "INNER JOIN mrp_dept_stock_item mrpwarprice ON stkhdr.source_department_id = mrpwarprice.dept_id and stkdtl.stock_item_id = mrpwarprice.stock_id " //added by udhay on 14 feb 2022
					+ "LEFT JOIN uoms ON stkitm.uom_id = uoms.id "
					+ "INNER JOIN mrp_item_category itmcat ON itmcat.id=stkitm.item_category_id "
					+ "WHERE  "
					+ "(stkhdr.stock_transfer_date BETWEEN '"+stkoutrpt.getStartdate()+"' and '"+stkoutrpt.getEnddate()+"'  AND stkhdr.req_status >0) "
					+ ""+DestDptQuery+"  "+ StockItemQuery+" "+SrcDptQuery+"  "
					+ "ORDER BY (stkhdr.stock_transfer_no),itmcat.`name`,stkdtl.stock_item_name ";
			CachedRowSet rs= getRowSet(sql);
			StockoutReportModel stockoutreport;
			if(rs!=null){
				stkoutrptlist=new ArrayList<StockoutReportModel>();
				while(rs.next()){		

					stockoutreport=getNewModelInstance();
					DBUtil.setModelFromSRS(rs, stockoutreport);
					stkoutrptlist.add(stockoutreport);
				}
			}	


		}

		if(stkoutrpt.getOption()==0)
		{

			sql="SELECT "
					+ "itmcat.`name` AS itm_cat_name,uoms.`code` AS uomname,stkdtl.stock_item_id,stkdtl.issued_qty,"
					+ "stkdtl.stock_item_name,stkdtl.cost_price,stkhdr.stock_transfer_date,stkhdr.stock_transfer_no,"
					+ "srcdpt.`name` AS source_department_name,destdpt.`name` AS dest_department_name "
					+ "FROM "
					+ "mrp_stock_out_hdr stkhdr "
					+ "INNER JOIN mrp_department srcdpt ON stkhdr.source_department_id = srcdpt.id "
					+ "LEFT JOIN mrp_department destdpt ON stkhdr.dest_department_id = destdpt.id "
					+ "INNER JOIN mrp_stock_out_dtl stkdtl ON stkdtl.stock_out_hdr_id = stkhdr.id  "
					+ "INNER JOIN mrp_stock_item stkitm ON stkdtl.stock_item_id = stkitm.id "
					+ "LEFT JOIN uoms ON stkitm.uom_id = uoms.id "
					+ "INNER JOIN mrp_item_category itmcat ON itmcat.id=stkitm.item_category_id "
					+ "WHERE  "
					+ "(stkhdr.stock_transfer_date BETWEEN '"+stkoutrpt.getStartdate()+"' and '"+stkoutrpt.getEnddate()+"'  AND stkhdr.req_status=3 ) "+DestDptQuery+"  "+ StockItemQuery+" "+SrcDptQuery+"  "
					+ "ORDER BY stkdtl.stock_item_name ";
			CachedRowSet rs= getRowSet(sql);

			if(rs!=null){



				stkoutrptlist=new ArrayList<StockoutReportModel>();

				while(rs.next()){	
					StockoutReportModel stockoutreport=new StockoutReportModel();	
					stockoutreport.setStock_item_name(rs.getString("stock_item_name"));
					stockoutreport.setStockItemId(rs.getString("stock_item_id"));
					stockoutreport.setItm_cat_name(rs.getString("itm_cat_name"));
					stockoutreport.setStock_transfer_no(rs.getString("stock_transfer_no"));
					stockoutreport.setStock_transfer_date(rs.getString("stock_transfer_date"));
					stockoutreport.setCost_price(rs.getDouble("cost_price"));
					stockoutreport.setIssued_qty(rs.getDouble("issued_qty"));
					stockoutreport.setUomname(rs.getString("uomname"));
					stkoutrptlist.add(stockoutreport);


				}

			}
		}





		return stkoutrptlist;
	}



}
