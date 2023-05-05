package com.indocosmo.mrp.web.masters.discounts.service;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.masters.discounts.dao.DiscountDao;
import com.indocosmo.mrp.web.masters.discounts.model.Discount;



@Service
@Qualifier("IDiscountService")
public interface IDiscountService extends IGeneralService<Discount,DiscountDao>{
	
	
	

}
