package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao.SaleItemDiscountDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.supplier.dao.SupplierDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.PurchaseOrderhdrDao;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;

public class SaleItemDiscountService extends GeneralService<SaleItemDiscount,SaleItemDiscountDao> implements ISaleItemDiscountService{




	

	private SaleItemDiscountDao saleItemDiscountDao;

	public SaleItemDiscountService(ApplicationContext context) {
		super(context);
		saleItemDiscountDao=new SaleItemDiscountDao(getContext());
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */



	@Override
	public SaleItemDiscountDao getDao() {
		// TODO Auto-generated method stub
		return saleItemDiscountDao;
	}


	@Override
	public Integer IsSaleItemExist(Integer discountId, Integer saleItemId) throws Exception {
		// TODO Auto-generated method stub
		return saleItemDiscountDao.IsSaleItemExist(discountId, saleItemId);
	}


	public JsonArray getSaleItemJsonArray(int discountId) throws Exception{
		// TODO Auto-generated method stub
		return saleItemDiscountDao.getSaleItemJsonArray(discountId);
	}

	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "discount_id=" + id;
		
		return saleItemDiscountDao.delete(where);
	}

	public Integer deleteSyncQue(int departmentId) throws Exception{
		return saleItemDiscountDao.deleteSyncQueue(departmentId);
		
	}


	public void deleteSaleDiscounts(String saleItemIds, Integer id) throws Exception{
		// TODO Auto-generated method stub
		 saleItemDiscountDao.DeleteSaleItemDiscounts(saleItemIds, id);
		
	}

}
