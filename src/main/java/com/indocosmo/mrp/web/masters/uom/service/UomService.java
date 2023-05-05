package com.indocosmo.mrp.web.masters.uom.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.uom.dao.UomDao;
import com.indocosmo.mrp.web.masters.uom.model.Uom;

public class UomService extends MasterBaseService<Uom, UomDao> implements IUomService {
	
	private ItemMasterDao itemmasterdao;
	
	private UomDao uomDao;
	
	/**
	 * @param context
	 */
	public UomService(ApplicationContext context) {
	
		super(context);
		uomDao = new UomDao(getContext());
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
			referenceModel.setRefrenceKey("uom_id");
			referenceTableDetails.add(referenceModel);
		}
		
		return referenceTableDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public UomDao getDao() {
	
		return uomDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.uom.service.IUomService#getUomImportList() */
	public JsonArray getUomImportList() throws Exception {
	
		return uomDao.getHqTableRowJson();
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.uom.service.IUomService#getBaseUom() */
	public JsonArray getBaseUom() throws Exception {
	
		return uomDao.getBaseUom();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.uom.service.IUomService#getUomImportUpdatedList
	 * () */
	public JsonArray getUomImportUpdatedList() throws Exception {
	
		return uomDao.getUpdatedHqTableRowJson();
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.uom.service.IUomService#getDataToImport() */
	public List<Uom> getDataToImport() throws Exception {
	
		return uomDao.getHqTableRowListToImport();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.uom.service.IUomService#getUpdatedDataToImport
	 * () */
	public List<Uom> getUpdatedDataToImport() throws Exception {
	
		return uomDao.getUpdatedHqTableRowListToImport();
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.uom.service.IUomService#getEditDetails(int) */
	public JsonArray getEditDetails(int id) throws Exception {
	
		return uomDao.getEditDetails(id);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.uom.service.IUomService#getUomData() */
	public List<Uom> getUomData() throws Exception {
	
		return uomDao.getUomData();
	}
}
