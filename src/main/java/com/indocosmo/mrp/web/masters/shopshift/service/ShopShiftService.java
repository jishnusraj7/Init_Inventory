package com.indocosmo.mrp.web.masters.shopshift.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.shopshift.dao.ShopShiftDao;
import com.indocosmo.mrp.web.masters.shopshift.model.ShopShift;

public class ShopShiftService extends MasterBaseService<ShopShift, ShopShiftDao> implements IShopShiftService {
	
	private ShopShiftDao shopshiftDao;
	
	private ItemMasterDao itemmasterdao;
	
	/**
	 * @param context
	 */
	public ShopShiftService(ApplicationContext context) {
	
		super(context);
		shopshiftDao = new ShopShiftDao(getContext());
		itemmasterdao = new ItemMasterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.MasterBaseService#delete(java
	 * .lang.String) */
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<ShopShift> dao = getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = "" + table.getRefrenceKey() + "=" + id
						+ " AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
				
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			shopshiftDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public ShopShiftDao getDao() {
	
		return shopshiftDao;
	}
	
}
