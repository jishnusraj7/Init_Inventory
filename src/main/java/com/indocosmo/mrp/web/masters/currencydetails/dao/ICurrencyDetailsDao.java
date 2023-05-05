package com.indocosmo.mrp.web.masters.currencydetails.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;


public interface ICurrencyDetailsDao extends IGeneralDao<CurrencyDetails> {
	public Integer isBaseCurrencyExist() throws Exception; 

}
