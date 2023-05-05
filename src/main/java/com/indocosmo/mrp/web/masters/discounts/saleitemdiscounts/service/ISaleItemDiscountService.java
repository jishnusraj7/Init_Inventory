package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao.SaleItemDiscountDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.supplier.dao.SupplierDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;

public interface ISaleItemDiscountService extends IGeneralService<SaleItemDiscount,SaleItemDiscountDao>{

	public Integer IsSaleItemExist(Integer discountId, Integer saleItemId) throws Exception;
	
}
