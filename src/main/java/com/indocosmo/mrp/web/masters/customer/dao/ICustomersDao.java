package com.indocosmo.mrp.web.masters.customer.dao;




import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;



public interface ICustomersDao extends IGeneralDao<Customers>{
	public String getCustomerCode(Integer id) throws Exception;
}
