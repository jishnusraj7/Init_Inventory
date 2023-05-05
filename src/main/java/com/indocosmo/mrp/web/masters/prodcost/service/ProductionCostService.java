package com.indocosmo.mrp.web.masters.prodcost.service;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.prodcost.dao.ProductionCostDao;
import com.indocosmo.mrp.web.masters.prodcost.model.ProductionCost;



/**
 * @author jo
 *
 */
public class ProductionCostService extends MasterBaseService<ProductionCost, ProductionCostDao> implements IProductionCostService {
	
	private ProductionCostDao productionCostDao;
	final ItemProdCostDao itemProdCostDao;
	
	
	/**
	 * @param context
	 */
	public ProductionCostService(ApplicationContext context) {
	
		super(context);
		productionCostDao = new ProductionCostDao(getContext());
		itemProdCostDao = new ItemProdCostDao(getContext());
		
		
	}
	
	
	public ProductionCostDao getDao() {
	
		return productionCostDao;
	}
	
	
	
	
	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
	
		ApplicationContext context = productionCostDao.getContext();
		
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(itemProdCostDao.getTable());
		if (context.getCompanyInfo().getId() != 0) {
			
		}
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel = new RefereneceModelBase();
			
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("costcalc_param_id");
				referenceTableDetails.add(referenceModel);
				
			
			
		}
		return referenceTableDetails;
	}
	
	 

	@SuppressWarnings("unchecked")
	@Override
	public Integer delete(String id) throws Exception {
	
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		
		GeneralDao<ProductionCost> dao = (GeneralDao<ProductionCost>) getDao();
		
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
			
			productionCostDao.delete(where);
		}
		
		return is_deleted;
	}
	
	
	

	public List<ProductionCost> getProdCostData() throws Exception {
	
		return productionCostDao.getProdCostData();
	}
	
	@Override
	public List<ProductionCost> getExcelData() throws Exception {
		
		return productionCostDao.getExcelData();
	}
	
}
