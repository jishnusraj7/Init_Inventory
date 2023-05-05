package com.indocosmo.mrp.web.production.production.productiondetail.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.production.productiondetail.dao.ProductionDtlDao;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;




public class ProductionDtlService extends GeneralService<ProductionDetail,ProductionDtlDao> implements IProductionDtlService{


	private ProductionDtlDao stockInDtlDao;

	public ProductionDtlService(ApplicationContext context) {

		super(context);
		stockInDtlDao = new ProductionDtlDao(getContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public ProductionDtlDao getDao() {

		return stockInDtlDao;
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo.mrp.web.core.base.model.ModelBase)
	 */
	@Override
	public void delete(ProductionDetail item) throws Exception {
		final String where = "prod_hdr_id="+item.getProdHdrId();

		super.delete(where);
	}

	/**
	 * @param stockinHdrId
	 * @return
	 */
	public ArrayList<ProductionDetail> getstockInDtlData(Integer stockinHdrId) throws Exception{

		return stockInDtlDao.getstockInDtlData(stockinHdrId);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.dailyproduction.dailyproductiondetail.service.IProductionDtlService#getTableRowsAsJson(java.lang.Integer)
	 */
	@Override
	public JsonArray getTableRowsAsJson(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return stockInDtlDao.getTableRowsAsJson(id);
	}
}
