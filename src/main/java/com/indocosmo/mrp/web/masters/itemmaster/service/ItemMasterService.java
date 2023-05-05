package com.indocosmo.mrp.web.masters.itemmaster.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.PurchaseOrderdtlDao;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.dao.StockInDetailDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao.StockOutDetailDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;


public class ItemMasterService extends GeneralService<ItemMaster,ItemMasterDao> implements IItemMasterService{


	//private IItemMasterDao itemMasterDao;

	private ItemMasterDao itemmasterDao;

	private PurchaseOrderdtlDao purchaseorderdtlDao;


	private StockInDetailDao stockindetailDao;


	private StockOutDetailDao stockoutdetailDao;


	private StockRegisterDetailDao stockregisterdetailDao;

	public ItemMasterService(ApplicationContext context) {
		super(context);
		itemmasterDao = new ItemMasterDao(getContext());
		purchaseorderdtlDao = new PurchaseOrderdtlDao(getContext());
		stockindetailDao = new StockInDetailDao(getContext());
		stockoutdetailDao = new StockOutDetailDao(getContext());
		stockregisterdetailDao = new StockRegisterDetailDao(getContext());
	}


	@Override
	public ItemMasterDao getDao() {

		return itemmasterDao;
	}

	/*@Override
	public Integer delete(String where) throws Exception {
		where = "id="+where;

		return super.delete(where);
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public Integer delete(String id) throws Exception {
		final String where = "id=" + id;

		Integer is_deleted = 1;

		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();

		GeneralDao<ItemMaster> dao = (GeneralDao<ItemMaster>) getDao();

		int rowCount = 0;

		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {

				if(table.getRefrenceTable() == purchaseorderdtlDao.getTable()  || table.getRefrenceTable() == stockindetailDao.getTable() || table.getRefrenceTable() == stockoutdetailDao.getTable() || table.getRefrenceTable() == stockregisterdetailDao.getTable()){

					final String wherePart = ""+table.getRefrenceKey()+"="+ id;
					rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);

				}else{

					final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
					rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);

				}

			}
		}

		if (rowCount != 0) {

			is_deleted = 0;
		}

		if (referenceTables == null || rowCount == 0) {

			super.delete(where);
		}

		return is_deleted;
	}


	@Override
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception{



		ApplicationContext context = super.getContext();



		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(purchaseorderdtlDao.getTable());

		if(context.getCompanyInfo().getId()!=0){
			referenceTables.add(stockindetailDao.getTable());
			referenceTables.add(stockoutdetailDao.getTable());
			referenceTables.add(stockregisterdetailDao.getTable());
			referenceTables.add(itemmasterDao.getTable());
		}


		for (String table : referenceTables) {

			RefereneceModelBase referenceModel=new RefereneceModelBase();

			if(table==itemmasterDao.getTable())
			{
				referenceModel.setRefrenceTable(table);
				referenceModel.setRefrenceKey("group_item_id");
				referenceTableDetails.add(referenceModel);
			}
			else
			{
				referenceModel.setRefrenceTable(table);

				referenceModel.setRefrenceKey("stock_item_id");

				referenceTableDetails.add(referenceModel);	
			}

		}

		return referenceTableDetails;
	}



	public JsonArray getItemDetails(int itemId) throws Exception{
		return itemmasterDao.getItemDetails(itemId);
	}

	public JsonArray getDataToEditChoice(String itemId) throws Exception{
		return itemmasterDao.getDataToEditChoice(itemId);
	}


	public ItemMaster saveItem(ItemMaster item) throws Exception {		
		itemmasterDao.save(item);
		return item;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportList()  throws Exception{

		return itemmasterDao.getHqTableRowJson();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqitemMasterImportUpdatedList()  throws Exception{

		return itemmasterDao.getUpdatedHqTableRowJson();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<ItemMaster> getDataToImport()throws Exception{
		return itemmasterDao.getHqTableRowListToImport();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<ItemMaster> getUpdatedDataToImport()throws Exception{
		return itemmasterDao.getUpdatedHqTableRowListToImport();
	}

	public boolean updateIsActive(JsonArray activeId,JsonArray inactiveId) throws Exception
	{
		boolean success=itemmasterDao.updateIsActive(activeId,inactiveId);
		return success;

	}

	public boolean updateIsSynch(JsonArray synchId,JsonArray nonSyncId) throws Exception
	{
		boolean success=itemmasterDao.updateIsSynch(synchId,nonSyncId);
		return success;

	}

	public JsonArray getChoiceData() throws Exception{
		return itemmasterDao.getChoiceData();

	}

	public JsonArray getGroupItem() throws Exception{
		return itemmasterDao.getGroupItem();

	}

	public JsonArray getModalDetails(int itemId) throws Exception{
		return itemmasterDao.getModalDetails(itemId);

	}
	public Integer getIsManufactured(Integer id) throws Exception
	{
		return itemmasterDao.getIsManufactured(id);
	}


	public List<ItemMaster> getItemData() throws Exception {

		return itemmasterDao.getItemData();
	}

	public JsonArray getdpData(String table)  throws Exception{

		return itemmasterDao.getdpData(table);
	}


	public JsonArray getSaleItems() throws Exception{
		return itemmasterDao.getSaleItems();

	}

	public JsonArray getTransferItems() throws Exception{
		return itemmasterDao.getTransferItems();
	}


}
