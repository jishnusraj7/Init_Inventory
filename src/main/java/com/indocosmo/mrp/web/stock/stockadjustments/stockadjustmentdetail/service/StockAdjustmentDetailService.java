package com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.dao.StockAdjustmentDetailDao;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model.StockAdjustmentDetail;


public class StockAdjustmentDetailService  extends GeneralService<StockAdjustmentDetail,StockAdjustmentDetailDao> implements IStockAdjustmentDetailService{

	private StockAdjustmentDetailDao  stockDisposalDetailDao;

	public StockAdjustmentDetailService(ApplicationContext context) {
		super(context);
		stockDisposalDetailDao=new StockAdjustmentDetailDao(getContext());
	}


	
	
	@Override
	public void delete(StockAdjustmentDetail item) throws Exception {
       final String where = "stock_adjust_hdr_id="+item.getStock_adjust_hdr_id();
		
		super.delete(where);
	}




	@Override
	public StockAdjustmentDetailDao getDao() {
		// TODO Auto-generated method stub
		return stockDisposalDetailDao;
	}
	
	public List<StockAdjustmentDetail> saveStkDsplDtl(List<StockAdjustmentDetail> item) throws Exception{
		stockDisposalDetailDao.save(item);
		return item;
	}

}
