package com.indocosmo.mrp.web.report.stockinreport.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;


public class StockinReportDao extends GeneralDao<StockinReportModel> implements IStockinReportDao{

	public StockinReportDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockinReportModel getNewModelInstance() {

		return new StockinReportModel();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.stockinreport.dao.IStockinReportDao#getreportDetails(com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel)
	 */
	public List<StockinReportModel>  getreportDetails( StockinReportModel pohdr) throws Exception 
	{

		List<StockinReportModel> pohdrreportList=null;
		String SuppplierQuery="and (stockinhdr.supplier_id ="+pohdr.getSupplier_id()+")";
		String PoStatusQuery="and (stockinhdr.status = "+pohdr.getStatus()+" )";
		String StockItemQuery="and (stockindtl.stock_item_id in("+pohdr.getStock_item_id()+"))";

		String sql="";
		if(pohdr.getSupplier_id()==0){
			SuppplierQuery="";
		}
		if(pohdr.getStatus()==-1){
			//PoStatusQuery="";
			 PoStatusQuery="and (stockinhdr.status !="+stockInStatus.PENDING.getStockInStatusId()+" )";
		}

		if(pohdr.getStock_item_id()==""){
			StockItemQuery="";
		}

		if(pohdr.getOption()==1)
		{

			sql=	"SELECT "
					+ "uoms.`code` AS uomname,stockindtl.received_qty,stockindtl.unit_price,stockindtl.amount,"
					+ "stockinhdr.received_date,stockinhdr.supplier_name,"
					+ "stockinhdr.grn_no,stockinhdr. STATUS,stockinhdr.stock_in_type,stockindtl.stock_item_name, "
					+ "itmcat.`name` AS itm_cat_name,"
					+ "itmcat.id AS itm_cat_id "
					+ "FROM "
					+ "mrp_stock_in_hdr stockinhdr "
					+ "INNER JOIN mrp_stock_in_dtl stockindtl ON stockinhdr.id = stockindtl.stock_in_hdr_id "
					+ "INNER JOIN mrp_stock_item stkitm ON stockindtl.stock_item_id=stkitm.id  "
					+ "LEFT JOIN uoms ON stkitm.uom_id = uoms.id "
					+ "INNER JOIN mrp_item_category itmcat ON itmcat.id=stkitm.item_category_id "
					+ "WHERE "
					+ "(stockinhdr.received_date BETWEEN '"+pohdr.getStartdate()+"' and '"+pohdr.getEnddate()+"')    "+SuppplierQuery+"  "+ PoStatusQuery+" "+StockItemQuery+"  "
					+ "ORDER BY(stockinhdr.grn_no),itmcat.`name`,stockindtl.stock_item_name";


			CachedRowSet rs= getRowSet(sql);
			StockinReportModel pohdrreport;
			if(rs!=null){
				pohdrreportList=new ArrayList<StockinReportModel>();
				while(rs.next()){		

					pohdrreport=getNewModelInstance();
					DBUtil.setModelFromSRS(rs, pohdrreport);
					pohdrreportList.add(pohdrreport);
				}
			}	

		}


		if(pohdr.getOption()==0)
		{



			sql="SELECT "
					+ "uoms.`code` AS uomname,stockindtl.received_qty,stockindtl.unit_price,stockinhdr.received_date,stockinhdr.supplier_name,"
					+ "stockinhdr.grn_no,stockindtl.stock_item_id,stockindtl.stock_item_name, itmcat.`name` AS itm_cat_name,itmcat.id AS itm_cat_id "
					+ "FROM "
					+ "mrp_stock_in_hdr stockinhdr "
					+ "INNER JOIN mrp_stock_in_dtl stockindtl ON stockinhdr.id = stockindtl.stock_in_hdr_id "
					+ "INNER JOIN mrp_stock_item stkitm ON stockindtl.stock_item_id=stkitm.id  "
					+ "LEFT JOIN uoms ON stkitm.uom_id = uoms.id "
					+ "INNER JOIN mrp_item_category itmcat ON itmcat.id=stkitm.item_category_id "
					+ "WHERE "
					+ "(stockinhdr.received_date BETWEEN '"+pohdr.getStartdate()+"' and '"+pohdr.getEnddate()+"')    "+SuppplierQuery+" "
					+ " "+ PoStatusQuery+" "+StockItemQuery+"  ORDER BY stockindtl.stock_item_name";

			CachedRowSet rs= getRowSet(sql);

			if(rs!=null){

				pohdrreportList=new ArrayList<StockinReportModel>();

				while(rs.next()){	
					StockinReportModel pohdrreport=new StockinReportModel();	
					pohdrreport.setItm_cat_id(rs.getInt("itm_cat_id"));
					pohdrreport.setStock_item_name(rs.getString("stock_item_name"));
					pohdrreport.setStock_item_id(rs.getString("stock_item_id"));
					pohdrreport.setItm_cat_name(rs.getString("itm_cat_name"));
					pohdrreport.setGrn_no(rs.getString("grn_no"));
					pohdrreport.setReceived_date(rs.getString("received_date"));
					pohdrreport.setUnit_price(rs.getDouble("unit_price"));
					pohdrreport.setReceived_qty(rs.getDouble("received_qty"));
					pohdrreport.setUomname(rs.getString("uomname"));
					pohdrreport.setSupplier_name(rs.getString("supplier_name"));
					pohdrreportList.add(pohdrreport);
				}

			}
		}

		return pohdrreportList;
	}




}
