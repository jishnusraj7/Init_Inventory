package com.indocosmo.mrp.web.report.purchasereturnreport.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.purchasereturnreport.model.PurchaseReturnReport;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;

public class PurchaseReturnReportDao extends GeneralDao<PurchaseReturnReport> implements IPurchaseReturnReportDao{

	public PurchaseReturnReportDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public PurchaseReturnReport getNewModelInstance() {
		return new PurchaseReturnReport();
	}

	@Override
	protected String getTable() {
		return "";
	}

	public List<PurchaseReturnReport> getStockReturn(PurchaseReturnReport purchaseReturn) throws Exception {
        PurchaseReturnReport purchaseReturnReport=null;
		ArrayList<PurchaseReturnReport> purchaseReturnReportList = null;
		String itemSql="";
		String sql="";
		if (purchaseReturn.getStock_item_id() != "" && purchaseReturn.getStock_item_id() != null) {
			itemSql = " stock_item_id IN(" + purchaseReturn.getStock_item_id() + ")";
			
			 sql=" SELECT * FROM mrp_stock_return_hdr hdr INNER JOIN mrp_stock_return_dtl dtl ON hdr.id = dtl.stock_return_hdr_id " + 
						" WHERE "+itemSql+" AND hdr.return_date BETWEEN '"+purchaseReturn.getStartDate()+"' AND '"+purchaseReturn.getEndDate()+"'";
		}else {
		 sql=" SELECT * FROM mrp_stock_return_hdr hdr INNER JOIN mrp_stock_return_dtl dtl ON hdr.id = dtl.stock_return_hdr_id " + 
				" WHERE hdr.return_date BETWEEN '"+purchaseReturn.getStartDate()+"' AND '"+purchaseReturn.getEndDate()+"'";
		}
		CachedRowSet resultSet= getRowSet(sql);
		if(resultSet!=null) {
			purchaseReturnReportList=new ArrayList<PurchaseReturnReport>();
			while(resultSet.next()) {
				purchaseReturnReport=getNewModelInstance();
				DBUtil.setModelFromSRS(resultSet, purchaseReturnReport);
				purchaseReturnReportList.add(purchaseReturnReport);
			}
		}
		return purchaseReturnReportList;
	}

}
