package com.indocosmo.mrp.web.report.stockadjustmentsummary.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;

public class StockAdjustmentReportDao extends GeneralDao<StockAdjustmentReport> implements IStockAdjustmentReportDao{

	public StockAdjustmentReportDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public StockAdjustmentReport getNewModelInstance() {
		
		return new StockAdjustmentReport();
	}

	@Override
	protected String getTable() {
		return null;
	}

	@Override
	public JsonArray getStockAdjustmentDetails(StockAdjustmentReport stockAdjustment) throws Exception {	

		String sql = "SELECT "
				+ "mrp_department.`name` AS department_name, mrp_stock_adjust_hdr.adjust_date, "
				+ "mrp_stock_adjust_dtl.stock_item_id, mrp_stock_adjust_dtl.stock_item_name, "
				+ "mrp_stock_adjust_dtl.system_qty, mrp_stock_adjust_dtl.actual_qty, mrp_stock_adjust_dtl.diff_qty "
				+ "FROM "
				+ "mrp_stock_adjust_dtl "
				+ "INNER JOIN mrp_stock_adjust_hdr ON mrp_stock_adjust_hdr.id = mrp_stock_adjust_dtl.stock_adjust_hdr_id "
				+ "INNER JOIN mrp_department ON mrp_stock_adjust_hdr.department_id = mrp_department.id "
				+ "WHERE "
				+ "adjust_date BETWEEN '"+ stockAdjustment.getStartdate()+"' AND '"+ stockAdjustment.getEnddate()
				+ "' AND (system_qty <> 0 OR actual_qty <> 0 ) "
				+ "ORDER BY mrp_stock_adjust_hdr.department_id, "
				+ "mrp_stock_adjust_hdr.adjust_date,"
				+ " mrp_stock_adjust_dtl.stock_item_name";
		return getTableRowsAsJson(sql);		
	}
}
