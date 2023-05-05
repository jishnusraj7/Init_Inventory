package com.indocosmo.mrp.web.masters.itemmaster.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.dao.ComboContentsSubstituionDao;
import com.indocosmo.mrp.web.masters.combocontents.dao.ComboContentsDao;
import com.indocosmo.mrp.web.masters.combocontents.model.ComboContents;
import com.indocosmo.mrp.web.masters.combocontents.service.ComboContentsService;
import com.indocosmo.mrp.web.masters.customertypesitemprices.dao.CustomerTypeItemPricesDao;
import com.indocosmo.mrp.web.masters.customertypesitemprices.model.CustomerTypeItemPrices;
import com.indocosmo.mrp.web.masters.customertypesitemprices.service.CustomerTypeItemPricesService;
import com.indocosmo.mrp.web.masters.itemclass.service.ItemClassService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.ItemMasterBatchDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service.ItemProdCostService;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.dao.SaleItemComboContentsDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.model.SaleItemComboContents;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.service.SaleItemComboContentsService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value = "/itemmaster")
public class ItemMasterController extends ViewController<ItemMaster, ItemMasterDao, ItemMasterService> {
	
	public static final Logger logger = Logger.getLogger(ItemMasterController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Autowired
	AppConfig appConf;
	
	@Override
	public ItemMasterService getService() {
	
		return new ItemMasterService(getCurrentContext());
	}
	
	/**
	 * @param itemMaster
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMaster itemMaster , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ITM");
		final ComboContentsService combocontentservice=new ComboContentsService(getCurrentContext());
		
		List<ComboContents> combocontent=combocontentservice.getList();
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Products",true);
		model.addAttribute("itemmaster",true);
		model.addAttribute("combocontent",combocontent);
		
		if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
				== ImplementationMode.LITE.getImplementationModeId())
		{
		model.addAttribute("lite_version",true);
		}else
		{
			model.addAttribute("lite_version",false);	
		}
		
		return "/itemmaster/list";
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropDownData")
	public void getDropdownData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());

		final ItemClassService itemClassService = new ItemClassService(getCurrentContext());
		
		JsonArray profitCat = itemMasterService.getdpData("mrp_profit_category");
		
		JsonArray itemasterclass =itemClassService.getJsonArray();
		JsonArray itemCategory = itemMasterService.getdpData("mrp_item_category");
		JsonArray kitchen = itemMasterService.getdpData("kitchens");
		JsonArray uom = itemMasterDao.getUOmData("uoms");
		JsonArray supplier = itemMasterService.getdpData("mrp_supplier");
		JsonArray tax = itemMasterService.getdpData("taxes");
		
		JsonArray superClassList = itemClassService.getSuperClassList();
		JsonArray item = itemMasterService.getMastersRowJson();
		JsonArray choices = itemMasterService.getChoiceData();
		JsonArray groupItem = itemMasterService.getGroupItem();
		jsonResponse.add("superClassList", superClassList);
		jsonResponse.add("kitchen", kitchen);
		jsonResponse.add("profitCategory", profitCat);
		jsonResponse.add("itemCategory", itemCategory);
		jsonResponse.add("uom", uom);
		jsonResponse.add("uom1", uom);
		jsonResponse.add("supplier", supplier);
		jsonResponse.add("tax", tax);
		jsonResponse.add("item", item);
		jsonResponse.add("choices", choices);
		jsonResponse.add("groupItem", groupItem);
		jsonResponse.add("itemasterclass", itemasterclass);
		
		
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/editModal")
	public void getModalData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		int itemId = Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		JsonArray modalDetails = itemMasterService.getModalDetails(itemId);
		jsonResponse.add("modalDetails", modalDetails.get(0));
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGroupItmData")
	public void getGroupItmData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		JsonArray groupItem = itemMasterService.getGroupItem();
		jsonResponse.add("groupItem", groupItem);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDataToEditChoice")
	public void getDataToEditChoice(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		String itemId = request.getParameter("itemId");
		JsonObject jsonResponse = new JsonObject();
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		
		try {
			
			JsonArray choiceDet = itemMasterService.getDataToEditChoice(itemId);
			
			jsonResponse.add("choiceDet", choiceDet);
			
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getDataToEditChoice in" + this + Throwables.getStackTraceAsString(e));
			
		}
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDataQue")
	public void getDataQue(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		JsonObject jsonResponse = new JsonObject();
		final SyncQueueService syncQueueService = new SyncQueueService(getCurrentContext());
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());
		
		try {
			
			JsonArray Quetable = syncQueueService.getQuetableDet(itemId, itemMasterDao.getTable());
			
			if (Quetable.size() != 0) {
				jsonResponse.add("Quetable", Quetable.get(0));
				
			}
			else
			
			{
				JsonObject Quetab = new JsonObject();
				Quetab.addProperty("id", "");
				Quetab.addProperty("dateTime", "");
				Quetab.addProperty("sysSaleFlag", true);
				Quetab.addProperty("syncNow", 0);
				Quetab.addProperty("shopId", "");
				Quetab.addProperty("origin", "");
				Quetab.addProperty("curdAction", "");
				
				jsonResponse.add("Quetable", Quetab);
			}
			
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getDataQue in" + this + Throwables.getStackTraceAsString(e));
			
		}
		
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getComboContent")
	public void getComboContent(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		String itemId = request.getParameter("itemId");
		JsonObject jsonResponse = new JsonObject();
		final ComboContentsSubstituionDao combocontentsservice = new ComboContentsSubstituionDao(
				getCurrentContext());
	
	
		
		
		try {
			
			JsonArray list = combocontentsservice.getComboContentList(itemId);
	

	
			jsonResponse.add("comboContentData", list);
	
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getComboContent in" + this + Throwables.getStackTraceAsString(e));
			
		}
		
		
		
	}
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDataToEdit")
	public void getDataToEdit(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		JsonObject jsonResponse = new JsonObject();
		final ItemMasterBomService itemBomService = new ItemMasterBomService(getCurrentContext());
		final ItemMasterBatchService itemBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemProdCostService prodCostService=new ItemProdCostService(getCurrentContext());
		final CustomerTypeItemPricesDao customertypeitemservice=new CustomerTypeItemPricesDao(getCurrentContext());
		final ComboContentsDao cobocontentdao = new ComboContentsDao(getCurrentContext());
		
		
		// final SyncQueueService syncQueueService=new
		// SyncQueueService(getCurrentContext());
		try {
			JsonArray CustomerType=customertypeitemservice.getCustomerDataById(itemId);
			JsonArray bom = itemBomService.getBomJsonArray(itemId,0);
			JsonArray batch = itemBatchService.getBatchJsonArray(itemId);
			JsonArray item = itemMasterService.getItemDetails(itemId);
			JsonArray prodcost=prodCostService.getProdCostArray(itemId);
			JsonArray comboContentData = cobocontentdao.getComboContentData(itemId);
			// JsonArray Quetable=syncQueueService.getQuetableDet(itemId);
			
			
			
			jsonResponse.add("bom", bom);
			if (batch.size() != 0) {
				jsonResponse.add("batch", batch.get(0));
				
			}
			
			
			jsonResponse.add("customerTypeList", CustomerType);
			jsonResponse.add("comboContentData", comboContentData);
			jsonResponse.add("itemDetails", item.get(0));
			jsonResponse.add("prd_costdata",prodcost);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getDataToEdit in" + this + Throwables.getStackTraceAsString(e));
			
		}
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveModal", method = RequestMethod.POST)
	public void saveModal(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "itemMasterService";
		
		Gson gson = new Gson();
		ItemMaster itemMaster = null;
		SyncQueue syncQueue = null;
		itemMasterService.beginTrans(DB_TXN_POINT);
		
		try {
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId())
			{
			itemMaster = super.saveData(request);
			}
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			syncQueue = new SyncQueue();
			if (QutableData.get("id") != null && (QutableData.get("id").getAsString()).length() != 0) {
				syncQueue.setId(QutableData.get("id").getAsInt());
			}
			syncQueue.setTableName(itemMasterDao.getTable());
			syncQueue.setRecordId(itemMaster.getId());
			syncQueue.setShopId(QutableData.get("shopId").getAsInt());
			syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
			syncQueue.setOrigin(QutableData.get("origin").getAsString());
			syncQueue.setPublishingDate(QutableData.get("dateTime").getAsString());
			syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId())
			{
			SyncQueueService.save(syncQueue);
			}
			response.getWriter().print("1");
			
			itemMasterService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			response.getWriter().print("0");
			itemMasterService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveModal" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * @param request
	 * @param file1
	 * @param completeData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveItem")
	protected @ResponseBody void saveItem(MultipartHttpServletRequest request ,
			@RequestParam(value = "imgFile", required = false) MultipartFile file1 ,
			@RequestParam(value = "data", required = true) String completeData,HttpServletResponse response) throws Exception {
	
		String status = "success";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper = new ObjectMapper();
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemMasterBatchService itemBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemMasterBomService itemBomService = new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService prodCostService=new ItemProdCostService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());
		final ItemMasterBatchDao temMasterBatchDao = new ItemMasterBatchDao(getCurrentContext());
		final CustomerTypeItemPricesService customertypeitempriceservice=new CustomerTypeItemPricesService(getCurrentContext());
		final SaleItemComboContentsService saleitemservice =new SaleItemComboContentsService(getCurrentContext()); 
		final ItemMasterBomDao itemMasterBomDao = new ItemMasterBomDao(getCurrentContext());
		final ItemProdCostDao itemProdCostDao=new ItemProdCostDao(getCurrentContext());
		final SaleItemComboContentsDao saleitemdao=new SaleItemComboContentsDao(getCurrentContext());
		
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/item_images/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
        File directory = new File(rootDirectory);
		
		if (! directory.exists()){
	        directory.mkdirs();
	       
	    }
		
		final String DB_TXN_POINT = "itemMasterService";
		ItemMasterBom bom = null;
		ItemProdCost itmProdCost=null;
		SyncQueue syncQueueItem = null;
		
		final ArrayList<SyncQueue> SyncQueueArrayList = new ArrayList<SyncQueue>();
		ArrayList<ItemMasterBom> bomArrayList = new ArrayList<ItemMasterBom>();
		
		ArrayList<CustomerTypeItemPrices>  customertypeitemPrice =new ArrayList<CustomerTypeItemPrices>();
		ArrayList<SaleItemComboContents>  saleItemcombocont =new ArrayList<SaleItemComboContents>();

		
		ArrayList<ItemProdCost> prodCostArrayList=new ArrayList<ItemProdCost>();
		// String itemList=request.getParameter("itemData");
		String bomList = gson.toJson(jobject.get("bom"));
		String costCalcList=gson.toJson(jobject.get("prodCostList"));
		String customerTypeList=gson.toJson(jobject.get("customerTypeList"));
		String saleitemcombocontentList=gson.toJson(jobject.get("saleitemcombocontent"));
		JsonArray customerTypeListData = (JsonArray) parser1.parse(customerTypeList);
		
		
		
		JsonArray saleitemcombocontent = (JsonArray) parser1.parse(saleitemcombocontentList);
		JsonArray bomListData = (JsonArray) parser1.parse(bomList);
		JsonArray prodCostData = (JsonArray) parser1.parse(costCalcList);
		
		String batchItem = gson.toJson(jobject.get("batchItem"));
		
		try {
			// ObjectMapper mapper = new ObjectMapper();
			ItemMaster item = mapper.readValue(gson.toJson(jobject.get("itemData")), ItemMaster.class);
			Integer idss = 0;
			idss = item.getId();
			String code = item.getCode();
			if (file1 != null) {
				file1.transferTo(new File(rootDirectory + code + "_img1.jpg"));
				item.setItemThumb(absolutePath + code + "_img1.jpg");
			}
			
			itemMasterService.beginTrans(DB_TXN_POINT);
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId())
			{
			item = itemMasterService.saveItem(item);
			}
			 JsonObject obj=new JsonObject();
			 Gson gson1 = new Gson();
		     String json1 = gson1.toJson(item);
			obj.addProperty("itemData1",json1);
			
			
			Boolean flag2 = true;
			Boolean prodCostFlag=true;
			if (item.getIsManufactured()) {
				
				if (bomListData.size() != 0) {
					for (int i = 0; i < bomListData.size(); i++) {
						bom = new ItemMasterBom();
						JsonObject json = (JsonObject) bomListData.get(i);
						if (bomListData.size() == 1 && json.get("id").getAsString().length() == 0
								&& json.get("bom_item_id").getAsString().length() == 0
								&& json.get("qty").getAsString().length() == 0) {
							flag2 = false;
							break;
						}
						
						bom.setStockItemId(item.getId());
						if (json.get("id") != null && (json.get("id").getAsString()).length() != 0) {
							bom.setId(json.get("id").getAsInt());
						}
						bom.setBomItemId((json.get("bom_item_id").getAsInt()));
						bom.setQty(json.get("qty").getAsDouble());
						bom.setCost_price(json.get("unit_price").getAsDouble());
						bom.setStock_item_qty(item.getBomQty()!=""?Double.parseDouble(item.getBomQty()):0.0);
						bomArrayList.add(bom);
					}
					if (flag2 == true) {
						itemBomService.save(bomArrayList);
						JsonArray bomArray1=itemMasterBomDao.getBomJsonArray(item.getId(),0);
						/* JsonObject obj1=new JsonObject();
						 Gson gson2 = new Gson();
					     String json2 = gson2.toJson(obj1);
						obj.addProperty("bomData1",json2);*/
						obj.add("bomData1", bomArray1);
					}
					
				}
				if(prodCostData.size() != 0)
				{

			        	for (int i = 0; i < prodCostData.size(); i++) {
						itmProdCost = new ItemProdCost();
						JsonObject json = (JsonObject) prodCostData.get(i);
						if (prodCostData.size() == 1 && json.get("id").getAsString().length() == 0
								&& json.get("prod_cost_id").getAsString().length() == 0
								&& json.get("rate").getAsString().length() == 0) {
							prodCostFlag = false;
							break;
						}
						
						itmProdCost.setStockItemId(item.getId());
						if (json.get("id") != null && (json.get("id").getAsString()).length() != 0) {
							itmProdCost.setId(json.get("id").getAsInt());
						}
						if(json.get("isPercentage").getAsBoolean() == true)
						{
							itmProdCost.setIs_percentage(1);
						}else
						{
							itmProdCost.setIs_percentage(0);
						}
						itmProdCost.setCostcalc_param_id(json.get("prod_cost_id").getAsInt());
						itmProdCost.setRate(json.get("rate").getAsDouble());
						itmProdCost.setStock_item_qty(item.getBomQty()!=""?Double.parseDouble(item.getBomQty()):0.0);

						prodCostArrayList.add(itmProdCost);
					}
			        	
					if (prodCostFlag == true) {
						prodCostService.save(prodCostArrayList);
						JsonArray prodCostArray1=itemProdCostDao.getProdCostArray(item.getId());
						obj.add("proddata1", prodCostArray1);
						
					}
					
				
				}
			}
			
			else {
				flag2 = false;
				prodCostFlag=false;
			}
			
			if (flag2 == false && idss != null) {
				 itemBomService.deleteData(idss);
				 if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
							== ImplementationMode.FULL.getImplementationModeId())
					{
				 SyncQueueService.upDateUseSp(itemMasterBomDao.getTable(), item.getId());
					}
				}
			if(prodCostFlag == false && idss != null)
			{
				prodCostService.deleteData(idss);
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				SyncQueueService.upDateUseSp(itemProdCostDao.getTable(),item.getId());
				}
			}
			
			ItemMasterBatch batch = mapper.readValue(batchItem, ItemMasterBatch.class);
			batch.setStockItemId(item.getId());
			itemBatchService.save(batch);
			
			String Qutable = gson.toJson(jobject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			// quetable save stock_item
			
			if (item.getId().toString() != "")
			
			{
				syncQueueItem = new SyncQueue();
				String tableName = itemMasterDao.getTable();
				
				String stkItmId = SyncQueueService.fungetSynQueId(tableName, item.getId().toString());
				if (stkItmId != "") {
					syncQueueItem.setId(Integer.parseInt(stkItmId));
				}
				syncQueueItem.setShopId(QutableData.get("shopId").getAsInt());
				syncQueueItem.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueueItem.setOrigin(QutableData.get("origin").getAsString());
				syncQueueItem.setPublishingDate(QutableData.get("dateTime").getAsString());
				syncQueueItem.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueueItem.setTableName(itemMasterDao.getTable());
				syncQueueItem.setRecordId(item.getId());
				
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				SyncQueueService.save(syncQueueItem);
				}
			}
			// quetable save item batch
			
			if (batch.getId().toString() != "") {
				syncQueueItem = new SyncQueue();
				String tableName = temMasterBatchDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, batch.getId().toString());
				if (bathId != "") {
					syncQueueItem.setId(Integer.parseInt(bathId));
				}
				syncQueueItem.setShopId(QutableData.get("shopId").getAsInt());
				syncQueueItem.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueueItem.setOrigin(QutableData.get("origin").getAsString());
				syncQueueItem.setPublishingDate(QutableData.get("dateTime").getAsString());
				syncQueueItem.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueueItem.setTableName(tableName);
				syncQueueItem.setRecordId(batch.getId());
				
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				SyncQueueService.save(syncQueueItem);
				}
			}
			
			
			if(customerTypeListData.size()!=0){
			
				for (int i = 0; i < customerTypeListData.size(); i++) {
					
					
				
				final CustomerTypeItemPrices customertypePrices=new CustomerTypeItemPrices();
				
				JsonObject json = (JsonObject) customerTypeListData.get(i);
				
				
				customertypePrices.setPrice_variance_pc(json.get("price_variance_pc").getAsDouble());
				customertypePrices.setCustomer_type_id(json.get("customer_type_id").getAsInt());
				customertypePrices.setIs_percentage(json.get("is_percentage").getAsInt());
				customertypePrices.setSale_item_id(item.getId());
				
				customertypeitemPrice.add(customertypePrices);
		
				
				}
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				customertypeitempriceservice.save(customertypeitemPrice);
				}
			}
			
			if(saleitemcombocontent.size()!=0){
				
				for (int i = 0; i < saleitemcombocontent.size(); i++) {
					
					
				
				final SaleItemComboContents saleItemcombocontents=new SaleItemComboContents();
				
				JsonObject json = (JsonObject) saleitemcombocontent.get(i);
				
				
				saleItemcombocontents.setCombo_content_item_id(json.get("combo_content_item_id").getAsInt());
				saleItemcombocontents.setCombo_sale_item_id(json.get("combo_sale_item_id").getAsInt());
				saleItemcombocontents.setMax_items(json.get("max_items").getAsDouble());
			
				
			saleitemdao.deleteSaleItemCombo(saleItemcombocontents);
				saleItemcombocont.add(saleItemcombocontents);
		
				
				}
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				saleitemservice.save(saleItemcombocont);
				}
			}
			
			
			
			
			if (bomArrayList.size() != 0) {
				for (int i = 0; i < bomArrayList.size(); i++) {
					syncQueueItem = new SyncQueue();
					String tableName = itemMasterBomDao.getTable();
					String bomId = SyncQueueService.fungetSynQueId(tableName, bomArrayList.get(i).getId().toString());
					if (bomId != "") {
						syncQueueItem.setId(Integer.parseInt(bomId));
						syncQueueItem.setCrudAction("U");
					}else
					{
						syncQueueItem.setCrudAction("C");
					}	
					syncQueueItem.setShopId(QutableData.get("shopId").getAsInt());
					syncQueueItem.setOrigin(QutableData.get("origin").getAsString());
					syncQueueItem.setPublishingDate(QutableData.get("dateTime").getAsString());
					syncQueueItem.setSyncStatus(QutableData.get("syncNow").getAsInt());
					
					syncQueueItem.setTableName(itemMasterBomDao.getTable());
					syncQueueItem.setRecordId(bomArrayList.get(i).getId());
					SyncQueueArrayList.add(syncQueueItem);
					
				}
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				SyncQueueService.save(SyncQueueArrayList);
				}
			}
			if (prodCostArrayList.size() != 0) {
				for (int i = 0; i < prodCostArrayList.size(); i++) {
					syncQueueItem = new SyncQueue();
					String tableName = itemProdCostDao.getTable();
					String bomId = SyncQueueService.fungetSynQueId(tableName, prodCostArrayList.get(i).getId().toString());
					if (bomId != "") {
						syncQueueItem.setId(Integer.parseInt(bomId));
						syncQueueItem.setCrudAction("U");
					}else
					{
						syncQueueItem.setCrudAction("C");
					}
					
					syncQueueItem.setShopId(QutableData.get("shopId").getAsInt());
				
					syncQueueItem.setOrigin(QutableData.get("origin").getAsString());
					syncQueueItem.setPublishingDate(QutableData.get("dateTime").getAsString());
					syncQueueItem.setSyncStatus(QutableData.get("syncNow").getAsInt());
					
					syncQueueItem.setTableName(itemProdCostDao.getTable());
					syncQueueItem.setRecordId(prodCostArrayList.get(i).getId());
					SyncQueueArrayList.add(syncQueueItem);
					
				}
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId())
				{
				SyncQueueService.save(SyncQueueArrayList);
				}
			}
			itemMasterService.endTrans(DB_TXN_POINT);
			response.getWriter().print(obj);
		}
		catch (Exception e) {
			response.getWriter().print("0");
			e.printStackTrace();
			itemMasterService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		

	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());
		final ItemMasterBatchDao itemMasterBatchDao = new ItemMasterBatchDao(getCurrentContext());
		final ItemMasterBomDao itemMasterBomDao = new ItemMasterBomDao(getCurrentContext());
		final ItemMasterBatchService ItemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService itmProdCostService=new ItemProdCostService(getCurrentContext());
		final ItemProdCostDao itmProdCostDao=new ItemProdCostDao(getCurrentContext());
		final CustomerTypeItemPricesService customertypeprices =new CustomerTypeItemPricesService(getCurrentContext());
		final String DB_TXN_POINT = "itemMasterService";
		final String id = request.getParameter("id");
		final String batchId = request.getParameter("batchId");
		itemMasterService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_syncI = 0;
			Integer is_syncBat = 0;
			Integer is_syncBom = 0;
			Integer is_syncProdCost=0;
			is_deleted = itemMasterService.delete(id);
			
			if (is_deleted == 0) {
				response.getWriter().print("0");
				customertypeprices.delete(id);
			}
			
			else {
				is_syncI = SyncQueueService.updateSyncQueue(id, itemMasterDao.getTable());
				is_syncBat = SyncQueueService.updateSyncQueue(batchId, itemMasterBatchDao.getTable());
				is_syncBom = SyncQueueService.upDateUseSp(itemMasterBomDao.getTable(), Integer.parseInt(id));
				is_syncProdCost=SyncQueueService.upDateUseSp(itmProdCostDao.getTable(), Integer.parseInt(id));
				
				ItemMasterBatchService.deleteData(Integer.parseInt(id));
				itemMasterBomService.deleteData(Integer.parseInt(id));
				itmProdCostService.deleteData(Integer.parseInt(id));
				
				response.getWriter().print("1");
			}
			
			itemMasterService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			itemMasterService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("item", "item");
		
		response.getWriter().print(CounterWithPrefix);
	}
	

	
}
