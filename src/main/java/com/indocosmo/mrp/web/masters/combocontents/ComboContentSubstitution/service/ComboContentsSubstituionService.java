package com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.dao.ComboContentsSubstituionDao;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.model.ComboContentsSubstituion;
import com.indocosmo.mrp.web.masters.shops.dao.ShopsDao;

public class ComboContentsSubstituionService  extends GeneralService<ComboContentsSubstituion, ComboContentsSubstituionDao> implements IComboContentsSubstituionService{

	private ComboContentsSubstituionDao areaCodeDao;
	private ShopsDao shopsDao;
	
	public ComboContentsSubstituionService(ApplicationContext context) {
		super(context);
		areaCodeDao=new ComboContentsSubstituionDao(getContext());
		shopsDao=new ShopsDao(getContext());
	}

	@Override
	public ComboContentsSubstituionDao getDao() {
		// TODO Auto-generated method stub
		return areaCodeDao;
	}
	
	

		
		
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(shopsDao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("area_id");
			referenceTableDetails.add(referenceModel);
		}
		return referenceTableDetails;
	}
	
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<ComboContentsSubstituion> dao =  getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			areaCodeDao.delete(where);
		}
		
		return is_deleted;
	}
	

}
