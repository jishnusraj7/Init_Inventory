package com.indocosmo.mrp.web.masters.choices.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.choices.dao.ChoicesDao;
import com.indocosmo.mrp.web.masters.choices.model.Choices;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;

public class ChoicesService extends MasterBaseService<Choices, ChoicesDao> implements IChoicesService {
	
	private ChoicesDao choicesDao;
	
	private ItemMasterDao itemmasterdao;
	
	/**
	 * @param context
	 */
	public ChoicesService(ApplicationContext context) {
	
		super(context);
		choicesDao = new ChoicesDao(getContext());
		itemmasterdao = new ItemMasterDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#getReferenceTable
	 * () */
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(itemmasterdao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("choice_ids");
			referenceTableDetails.add(referenceModel);
		}
		
		return referenceTableDetails;
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
		GeneralDao<Choices> dao = getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = " find_in_set(" + id + "," + table.getRefrenceKey()
						+ ") <> 0  AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL)";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(), wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			choicesDao.delete(where);
		}
		
		return is_deleted;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public ChoicesDao getDao() {
	
		return choicesDao;
	}
	
}
