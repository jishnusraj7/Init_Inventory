package com.indocosmo.mrp.web.masters.vouchertypes.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.vouchertypes.dao.VouchersDao;
import com.indocosmo.mrp.web.masters.vouchertypes.model.Vouchers;


public interface IVouchersService extends IGeneralService<Vouchers,VouchersDao>{
	public JsonArray getDepartmentImportList() throws Exception ;
	public JsonArray getDepartmentImportUpdatedList() throws Exception ;
	public List<Vouchers> getDataToImport()throws Exception;
	public List<Vouchers> getUpdatedDataToImport()throws Exception;

}
