package com.indocosmo.mrp.web.stock.stocktransfer.service;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stocktransfer.dao.StockTransferDao;
import com.indocosmo.mrp.web.stock.stocktransfer.model.StockTransfer;


public class StockTransferService extends GeneralService<StockTransfer, StockTransferDao>{

	private StockTransferDao stockTransferDao;

	public StockTransferService(ApplicationContext context) {

		super(context);
		// TODO Auto-generated constructor stub
		stockTransferDao=new StockTransferDao(getContext());
	}

	@Override
	public StockTransferDao getDao() {

		// TODO Auto-generated method stub
		return stockTransferDao;
	}

	public StockTransfer saveStockItem(StockTransfer stockTransfer) throws Exception {

		stockTransferDao.save(stockTransfer);		
		return stockTransfer;
	}

	public JsonArray getStockTransDtlData(StockTransfer stockTrans) throws Exception {

		// TODO Auto-generated method stub
		return stockTransferDao.getStockTransDtlData(stockTrans);
	}

	public void upDateStatus(StockTransfer stockTransfer) throws Exception {

		// TODO Auto-generated method stub
		stockTransferDao.upDateStatus(stockTransfer);

	}

	@Override
	public Integer delete(String  where) throws Exception {
		where = "id="+where;

		return super.delete(where);

	}

	public JsonArray getOrderstoTransfer(String where_part) throws Exception{

		return stockTransferDao.getOrderstoTransfer(where_part);
	}

	public JsonArray getOrderDtlData(String order_id) throws Exception {

		return stockTransferDao.getOrderDtlData(order_id);
	}

	public JsonArray getStockTransferHdr(String transfer_date) throws Exception{
		return stockTransferDao.getStockTransferHdr(transfer_date);
	}

	public String getDepartmentData(String department_id) throws Exception{
		return stockTransferDao.getDepartmentData(department_id);
	}
}
