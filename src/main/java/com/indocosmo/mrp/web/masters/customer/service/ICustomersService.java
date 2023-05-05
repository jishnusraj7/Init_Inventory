package com.indocosmo.mrp.web.masters.customer.service;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;


@Service
@Qualifier("ICustomersService")
public interface ICustomersService extends IGeneralService<Customers,CustomersDao>{
	
	
	

}
