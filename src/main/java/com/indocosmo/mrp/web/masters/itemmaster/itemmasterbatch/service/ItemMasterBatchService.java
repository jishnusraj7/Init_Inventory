package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.ItemMasterBatchDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;

public class ItemMasterBatchService extends GeneralService<ItemMasterBatch, ItemMasterBatchDao> implements
		IItemMasterBatchService {
	
	private ItemMasterBatchDao itemmasterbatchDao;
	
	public ItemMasterBatchService(ApplicationContext context) {
	
		super(context);
		itemmasterbatchDao = new ItemMasterBatchDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public ItemMasterBatchDao getDao() {
	
		return itemmasterbatchDao;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.service.GeneralService#delete(com.indocosmo
	 * .mrp.web.core.base.model.GeneralModelBase) */
	@Override
	public void delete(ItemMasterBatch itemBatch) throws Exception {
	
		final String where = "stock_item_id=" + itemBatch.getStockItemId();
		
		super.delete(where);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.
	 * IItemMasterBatchService#upadteItemMasterBatch(java.util.ArrayList) */
	public void upadteItemMasterBatch(ArrayList<ItemMasterBatch> stockItembatchList) throws Exception {
	
		itemmasterbatchDao.upadteItemMasterBatch(stockItembatchList);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.
	 * IItemMasterBatchService
	 * #getCurrentStockInBatch(com.indocosmo.mrp.web.masters
	 * .itemmaster.itemmasterbatch.model.ItemMasterBatch) */
	@Override
	public Double getCurrentStockInBatch(ItemMasterBatch itemMasterBatch) throws Exception {
	
		return itemmasterbatchDao.getCurrentStockInBatch(itemMasterBatch);
	}
	
	public Double currentStock(ItemMasterBatch itemMasterBatch) throws Exception {
	
		return itemmasterbatchDao.currentStock(itemMasterBatch);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.
	 * IItemMasterBatchService#getItmBtchTableRowJson() */
	public JsonArray getBatchJsonArray(int stockItemId) throws Exception {
	
		return itemmasterbatchDao.getBatchJsonArray(stockItemId);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ItemMasterBatch> getDataToImport() throws Exception {
	
		return itemmasterbatchDao.getHqTableRowListToImport();
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemMasterBatch> getUpdatedBatchListToImport(int stockItemId) throws Exception {
	
		return itemmasterbatchDao.getUpdatedBatchListToImport(stockItemId);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.
	 * IItemMasterBatchService#deleteData(java.lang.Integer) */
	public void deleteData(Integer idss) throws Exception {
	
		itemmasterbatchDao.deleteData(idss);
	}
	
}
