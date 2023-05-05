package com.indocosmo.mrp.web.masters.discounts.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.discounts.dao.DiscountDao;
import com.indocosmo.mrp.web.masters.discounts.model.Discount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;

/**
 * @author jo
 *
 */
public class DiscountService extends GeneralService<Discount, DiscountDao> implements IDiscountService {
	
	private DiscountDao discountDao;
	
	public DiscountService(ApplicationContext context) {
	
		super(context);
		discountDao = new DiscountDao(getContext());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public DiscountDao getDao() {
	
		return discountDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
			referenceModel.setRefrenceTable(table);
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
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<Discount> dao = (GeneralDao<Discount>) getDao();
		
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
			
			discountDao.delete(where);
		}
		
		return is_deleted;
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#save(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	public void save(Discount item) throws Exception {
	
		GeneralDao<Discount> dao = getDao();
		if (item.getId() == null || item.getId().equals(""))
			
			dao.insert(item);
		else
			
			dao.update(item);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getSaleItemJsonArray() throws Exception {
	
		return discountDao.getSaleItemJsonArray();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<SaleItemDiscount> getExcelData() throws Exception {
	
		return discountDao.getExcelData();
	}
	
}
