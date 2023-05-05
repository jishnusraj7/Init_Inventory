package com.indocosmo.mrp.web.masters.supplier.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.supplier.dao.SupplierDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.PurchaseOrderhdrDao;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;

/**
 * @author jo
 *
 */
public class SupplierService extends GeneralService<Supplier, SupplierDao> implements ISupplierService {
	
	private PurchaseOrderhdrDao purchaseorderdao;
	
	private StockInDao stockindao;
	
	private ItemMasterDao itemmasterdao;
	
	private SupplierDao supplierDao;
	
	/**
	 * @param context
	 */
	public SupplierService(ApplicationContext context) {
	
		super(context);
		supplierDao = new SupplierDao(getContext());
		purchaseorderdao = new PurchaseOrderhdrDao(getContext());
		stockindao = new StockInDao(getContext());
		itemmasterdao = new ItemMasterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.supplier.service.ISupplierService#
	 * getIsSystemSupplier() */
	@Override
	public Supplier getIsSystemSupplier() throws Exception {
	
		return supplierDao.getIsSystemSupplier();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = supplierDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(purchaseorderdao.getTable());
		referenceTables.add(itemmasterdao.getTable());
		if (context.getCompanyInfo().getId() != 0) {
			referenceTables.add(stockindao.getTable());
		}
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			if (table == itemmasterdao.getTable()) {
				
				referenceModel.setRefrenceKey("pref_supplier_id");
				
			}
			else {
				referenceModel.setRefrenceKey("supplier_id");
			}
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(java.lang
	 * .String) */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		String wherePart = "";
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<Supplier> dao = (GeneralDao<Supplier>) getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				if (itemmasterdao.getTable() == table.getRefrenceTable()) {
					wherePart = "" + table.getRefrenceKey() + "=" + id
							+ " AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
					
				}
				else {
					wherePart = "" + table.getRefrenceKey() + "=" + id + " ;";
					
				}
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
				
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			super.delete(where);
		}
		
		return is_deleted;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public SupplierDao getDao() {
	
		// TODO Auto-generated method stub
		return supplierDao;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getSupplierImportList() throws Exception {
	
		return supplierDao.getHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.supplier.service.ISupplierService#
	 * getSupplierImportUpdatedList() */
	public JsonArray getSupplierImportUpdatedList() throws Exception {
	
		return supplierDao.getUpdatedHqTableRowJson();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.supplier.service.ISupplierService#
	 * getDataToImport() */
	public List<Supplier> getDataToImport() throws Exception {
	
		return supplierDao.getHqTableRowListToImport();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.supplier.service.ISupplierService#
	 * getUpdatedDataToImport() */
	public List<Supplier> getUpdatedDataToImport() throws Exception {
	
		return supplierDao.getUpdatedHqTableRowListToImport();
	}
	
}
