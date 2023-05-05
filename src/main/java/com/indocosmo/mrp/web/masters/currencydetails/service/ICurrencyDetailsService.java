package com.indocosmo.mrp.web.masters.currencydetails.service;



import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.currencydetails.dao.CurrencyDetailsDao;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;


public interface ICurrencyDetailsService  extends IGeneralService<CurrencyDetails,CurrencyDetailsDao>{
	/*public JsonArray getHqCurrencyDetailsImportList() throws Exception ;
	public JsonArray getHqCurrencyDetailsImportUpdatedList() throws Exception ;*/
	/*public List<CurrencyDetails> getDataToImport()throws Exception;
	public List<CurrencyDetails> getUpdatedDataToImport()throws Exception;*/
	public Integer isBaseCurrencyExist() throws Exception; 

}
