package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;

/**
 * @author jo
 *
 */
public class ItemProdCostService extends GeneralService<ItemProdCost, ItemProdCostDao> implements IItemProdCostService
 {
	
	private ItemProdCostDao itemmasterBomDao;
	
	/**
	 * @param context
	 */
	public ItemProdCostService(ApplicationContext context) {

		super(context);
		itemmasterBomDao = new ItemProdCostDao(getContext());
	}
	
	
	@Override
	public ItemProdCostDao getDao() {
	
		return itemmasterBomDao;
	}
	
	
	public JsonArray getProdCostArray(int id) throws Exception {
	
		return itemmasterBomDao.getProdCostArray(id);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	@Override
	public void delete(ItemProdCost itemBom) throws Exception {
	
		final String where = "stock_item_id=" + itemBom.getStockItemId();
		
		super.delete(where);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.
	 * IItemMasterBomService#getDataToImport() */
	public List<ItemProdCost> getDataToImport() throws Exception {
	
		return itemmasterBomDao.getHqTableRowListToImport();
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.
	 * IItemMasterBomService#getUpdatedBomListToImport(int) */
	public List<ItemProdCost> getUpdatedBomListToImport(int stockItemId) throws Exception {
	
		return itemmasterBomDao.getUpdatedBomListToImport(stockItemId);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemProdCost> getBomListForItem(int stockItemId) throws Exception {
	
		return itemmasterBomDao.getBomListForItem(stockItemId);
	}
	
	/**
	 * @param idss
	 * @throws Exception
	 */
	public void deleteData(Integer idss) throws Exception {
	
		itemmasterBomDao.deleteData(idss);
	}
	
}
