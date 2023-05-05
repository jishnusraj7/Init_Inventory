package com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.dao.StockDisposalDetailDao;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model.StockDisposalDetail;


public class StockDisposalDetailService  extends GeneralService<StockDisposalDetail,StockDisposalDetailDao> implements IStockDisposalDetailService{

	private StockDisposalDetailDao  stockDisposalDetailDao;

	public StockDisposalDetailService(ApplicationContext context) {
		super(context);
		stockDisposalDetailDao=new StockDisposalDetailDao(getContext());
	}


	
	
	@Override
	public void delete(StockDisposalDetail item) throws Exception {
       final String where = "stock_disposal_hdr_id="+item.getStockDisposalHdrId();
		
		super.delete(where);
	}




	@Override
	public StockDisposalDetailDao getDao() {
		// TODO Auto-generated method stub
		return stockDisposalDetailDao;
	}
	
	public List<StockDisposalDetail> saveStkDsplDtl(List<StockDisposalDetail> item) throws Exception{
		stockDisposalDetailDao.save(item);
		return item;
	}

}
