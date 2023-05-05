package com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.dao.RemoteRequesthdrDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.model.RPR_hdr;


public class RemoteRequesthdrService extends GeneralService<RPR_hdr,RemoteRequesthdrDao> implements IRemoteRequesthdrService {

	private RemoteRequesthdrDao remoterequesthdrDao;

	public RemoteRequesthdrService(ApplicationContext context) {
		super(context);
		remoterequesthdrDao=new RemoteRequesthdrDao(getContext());
	}



	@Override
	public RemoteRequesthdrDao getDao() {
		// TODO Auto-generated method stub
		return remoterequesthdrDao;
	}
	
	public RPR_hdr saveStockItem(RPR_hdr stockIn) throws Exception {
		remoterequesthdrDao.save(stockIn);
		
		return stockIn;
	}

}
