package com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.vouchertypes.dao.VouchersDao;
import com.indocosmo.mrp.web.masters.vouchertypes.model.Vouchers;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.dao.VouchersClassDao;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.model.VoucherClass;


public interface IVoucherClassService extends IGeneralService<VoucherClass,VouchersClassDao>{
	public JsonArray getDepartmentImportList() throws Exception ;
	public JsonArray getDepartmentImportUpdatedList() throws Exception ;
	public List<VoucherClass> getDataToImport()throws Exception;
	public List<VoucherClass> getUpdatedDataToImport()throws Exception;
	
	
}
