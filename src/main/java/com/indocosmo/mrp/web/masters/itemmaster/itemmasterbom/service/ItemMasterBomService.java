package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

/**
 * @author jo
 *
 */
public class ItemMasterBomService extends GeneralService<ItemMasterBom, ItemMasterBomDao> implements
		IItemMasterBomService {
	
	private ItemMasterBomDao itemmasterBomDao;
	
	/**
	 * @param context
	 */
	public ItemMasterBomService(ApplicationContext context) {

		super(context);
		itemmasterBomDao = new ItemMasterBomDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public ItemMasterBomDao getDao() {
	
		return itemmasterBomDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.
	 * IItemMasterBomService#getBomJsonArray(int) */
	public JsonArray getBomJsonArray(int id , int isBase) throws Exception {
	
		return itemmasterBomDao.getBomJsonArray(id,isBase);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	@Override
	public void delete(ItemMasterBom itemBom) throws Exception {
	
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
	public List<ItemMasterBom> getDataToImport() throws Exception {
	
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
	public List<ItemMasterBom> getUpdatedBomListToImport(int stockItemId) throws Exception {
	
		return itemmasterBomDao.getUpdatedBomListToImport(stockItemId);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemMasterBom> getBomListForItem(int stockItemId) throws Exception {
	
		return itemmasterBomDao.getBomListForItem(stockItemId);
	}
	
	/**
	 * @param idss
	 * @throws Exception
	 */
	public void deleteData(Integer idss) throws Exception {
	
		itemmasterBomDao.deleteData(idss);
	}

	public JsonArray getItemList() throws Exception {
	
		// TODO Auto-generated method stub
		return itemmasterBomDao.getItemList();
	}

	public void updateMrpStockItem(ItemMaster item, Integer version) throws Exception {
	
		// TODO Auto-generated method stub
		 itemmasterBomDao.updateMrpStockItem(item,version);
	}

	public JsonArray getItemMastersRowJson() throws Exception {
	
		// TODO Auto-generated method stub
		return itemmasterBomDao.getItemMastersRowJson();
	}

	public void updateCostprice(ArrayList<ItemMasterBom> bomArrayList, Integer version) throws Exception {
	
		// TODO Auto-generated method stub
		 itemmasterBomDao.updateCostprice(bomArrayList,version);
	}

	public void updateStockItemUnitPrice(Integer stkid, String unitprice) throws Exception {
		
		itemmasterBomDao.updateStockItemUnitPrice(stkid,unitprice);
	}

	public JsonArray getbasebomList() throws Exception {
		
		return itemmasterBomDao.getbasebomList();
	}

	public void updateQtyManufactured(Integer stock_item_id, double stock_item_qty, Integer version) throws Exception{
		
		itemmasterBomDao.updateQtyManufactured(stock_item_id,stock_item_qty, version);
	}

	
	
	
}
