package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;


public interface ISaleItemDiscountDao extends IGeneralDao<SaleItemDiscount> {

	Integer IsSaleItemExist(Integer discountId, Integer saleItemId) throws Exception;

	


}
