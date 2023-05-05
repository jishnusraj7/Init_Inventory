package com.indocosmo.mrp.web.report.purchaseorderreport.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;


public class PurchaseOrderReportDao extends GeneralDao<PurchaseOrderReportModel> implements IPurchaseOrderReportDao{

	public PurchaseOrderReportDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public PurchaseOrderReportModel getNewModelInstance() {

		return new PurchaseOrderReportModel();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.purchaseorderreport.dao.IPurchaseOrderReportDao#getreportDetails(com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel)
	 */
	public List<PurchaseOrderReportModel>  getreportDetails( PurchaseOrderReportModel pohdr) throws Exception 
	{

		List<PurchaseOrderReportModel> pohdrreportList=null;

		String SuppplierQuery="and (pohdr.supplier_id ="+pohdr.getSupplier_id()+")";
		String PoStatusQuery="and (pohdr.po_status = "+pohdr.getPo_status()+" )";
		String StockItemQuery="and (podtl.stock_item_id in("+pohdr.getStock_item_id()+"))";

		if(pohdr.getSupplier_id()==0){
			SuppplierQuery="";
		}
		if(pohdr.getPo_status()==-1){
			PoStatusQuery="";
		}

		if(pohdr.getStock_item_id()==""){
			StockItemQuery="";
		}

		String SQL="SELECT   "
				+ "itmcat.`name` AS itm_cat_name,pohdr.po_date, pohdr.supplier_name, pohdr.po_number, "
				+ "pohdr.po_status,pohdr.total_amount,podtl.qty,podtl.unit_price, podtl.stock_item_name "
				+ "FROM "
				+ "mrp_po_hdr pohdr,mrp_po_dtl podtl "
				+ "INNER JOIN mrp_stock_item stkitm ON podtl.stock_item_id=stkitm.id "
				+ "INNER JOIN mrp_item_category itmcat ON itmcat.id=stkitm.item_category_id "
				+ "WHERE "
				+ "(pohdr.po_date BETWEEN '"+pohdr.getStartdate()+"' and '"+pohdr.getEnddate()+"')   "
				+ ""+SuppplierQuery+"  "+ PoStatusQuery+" "+StockItemQuery+" and (pohdr.id=podtl.po_hdr_id)  "
				+ "ORDER BY (pohdr.po_number),itmcat.`name`,podtl.stock_item_name";

		CachedRowSet rs= getRowSet(SQL);
		PurchaseOrderReportModel pohdrreport;
		if(rs!=null){
			pohdrreportList=new ArrayList<PurchaseOrderReportModel>();
			while(rs.next()){		

				pohdrreport=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, pohdrreport);
				pohdrreportList.add(pohdrreport);
			}
		}	
		return pohdrreportList;
	}




}
