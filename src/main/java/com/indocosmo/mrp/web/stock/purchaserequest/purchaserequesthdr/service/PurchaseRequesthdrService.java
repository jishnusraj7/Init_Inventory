package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.dao.PurchaseRequesthdrDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.model.PR_hdr;

public class PurchaseRequesthdrService extends GeneralService<PR_hdr,PurchaseRequesthdrDao> implements IPurchaseRequesthdrService {



	private PurchaseRequesthdrDao purchaserequesthdrDao;

	public PurchaseRequesthdrService(ApplicationContext context) {
		super(context);
		purchaserequesthdrDao=new PurchaseRequesthdrDao(getContext());
	}


	@Override
	public PurchaseRequesthdrDao getDao() {
		return purchaserequesthdrDao;
	}
	public PR_hdr saveStockItem(PR_hdr prhdr) throws Exception {
		purchaserequesthdrDao.save(prhdr);
		
		return prhdr;
	}

}
