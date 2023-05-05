package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.ItemMasterBatchDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;

@Controller
@RequestMapping(value = "/batch")
public class ItemMasterBatchController extends
		ViewController<ItemMasterBatch, ItemMasterBatchDao, ItemMasterBatchService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ItemMasterBatchService getService() {
	
		return new ItemMasterBatchService(getCurrentContext());
	}
	
	public ItemMasterBatch saveItemBatchData(ItemMasterBatch itemBatch) throws Exception {
	
		final ItemMasterBatchService itemmasterBatchService = new ItemMasterBatchService(getCurrentContext());
		itemmasterBatchService.save(itemBatch);
		
		return itemBatch;
	}
	
}
