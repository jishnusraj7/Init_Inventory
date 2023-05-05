package com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.masters.vouchertypes.dao.VouchersDao;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.dao.VouchersClassDao;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.model.VoucherClass;
import com.indocosmo.mrp.web.stock.stockadjustments.dao.StockAdjustmentDao;
import com.indocosmo.mrp.web.stock.stockdisposal.dao.StockDisposalDao;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockout.dao.StockOutDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;

public class VoucherClassService extends GeneralService<VoucherClass, VouchersClassDao> implements IVoucherClassService {
	
	private VouchersClassDao departmentDao;
	private VouchersDao vouchersDao;
	
	
	public VoucherClassService(ApplicationContext context) {
	
		super(context);
		departmentDao = new VouchersClassDao(getContext());
		vouchersDao=new VouchersDao(getContext());
		
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public VouchersClassDao getDao() {
	
		return departmentDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = departmentDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
	
			
			referenceTables.add(vouchersDao.getTable());
			
		
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("voucher_type");
				referenceTableDetails.add(referenceModel);
				
			
			
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<VoucherClass> dao = (GeneralDao<VoucherClass>) getDao();
		
		int rowCount = 0;
		String wherePart = "";
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				
					wherePart = "" + table.getRefrenceKey() + "=" + id + " ;";
					
				
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			departmentDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportList() throws Exception {
	
		return departmentDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDepartmentImportUpdatedList() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<VoucherClass> getDataToImport() throws Exception {
	
		return departmentDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<VoucherClass> getUpdatedDataToImport() throws Exception {
	
		return departmentDao.getUpdatedHqTableRowListToImport();
	}
	
	/**
	 * @param stockIn
	 * @return
	 * @throws Exception
	 */
	public VoucherClass saveVouchr(VoucherClass stockIn) throws Exception {
	
		departmentDao.save(stockIn);
		
		return stockIn;
	}
	
}
