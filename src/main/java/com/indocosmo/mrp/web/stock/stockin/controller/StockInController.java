package com.indocosmo.mrp.web.stock.stockin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.dao.ItemStockDao;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.currentstock.service.ItemStockService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service.PurchaseOrderdtlService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.service.PurchaseOrderhdrService;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;
import com.indocosmo.mrp.web.stock.stockin.service.StockInService;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.service.StockInDetailService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;
import com.indocosmo.mrp.web.stock.war.model.StockWarModel;
import com.indocosmo.mrp.web.stock.war.service.StockWarServiceImpl;
import com.indocosmo.mrp.web.stock.war.warhistory.model.WarHistoryModel;
import com.indocosmo.mrp.web.stock.war.warhistory.service.StockWarHistorySeviceImpl;

/**
 * @author anjur
 *
 */
@Controller
@RequestMapping("/stockin")
public class StockInController extends ViewController<StockIn, StockInDao, StockInService> {

	public static final Logger logger = Logger.getLogger(StockInController.class);

	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockInService getService() {

		return new StockInService(getCurrentContext());
	}

	/**
	 * @param stockIn
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute StockIn stockIn , HttpSession session , Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();		

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_IN");
		model.addAttribute("permission", permission);

		SysdefPermission finalizepermission = userPermissionService.getCurrentUserPermission(session,
				"EDIT_FINAL_STOCKIN");
		model.addAttribute("StockinFinalizepermission", finalizepermission);

		//String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_in_hdr", "stock_in_hdr");

		//model.addAttribute("GRNNO", counterWithPrefix);


		model.addAttribute("Store",true);
		model.addAttribute("stockinclass",true);
		model.addAttribute("Stock",true);



		return "/stock/stockin/list";
	}

	/**
	 * @param stockInDtl
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public void save(@RequestBody String stockInDtl , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {

		final String DB_TXN_POINT = "stockInService";
		final StockInDetailService stockInDetailService = new StockInDetailService(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());
		Integer cmbnmode=(Integer) session.getAttribute("combineMode");

		StockIn stockin = null;

		stockInService.beginTrans(DB_TXN_POINT);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			
			//Code corrected for fixing the issue found in purchase order module. The issue is duplicate entry 
		    //of GRN number. the reason is while save the edited data the same save function is called, this time 
			//the GRN number is incremented by one. this causes the duplication of GRN number. Actually there is no
			// need to increment the GRN number while edit the already entered data. 
			// modified date: 30-12-2020. Modified by: Ayana Dharman,Aslam
			
//			String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_in_hdr", "stock_in_hdr");
			stockin = mapper.readValue(stockInDtl, StockIn.class);
			if (stockin.getId() == null)
			{
//				if(cmbnmode==CombineMode.SEPARATE.getCombineModeId())
//				{
					stockin.setGrnNo(counterService.getNextCounterwithPrefix("stock_in_hdr", "stock_in_hdr"));
//				}
			}
			
//            stockin.setGrnNo(counterWithPrefix);
			
			stockin = stockInService.saveStockItem(stockin);
			String stockIndtl = stockin.getStockDetailLists();

			JsonParser parser = new JsonParser();
			JsonArray stockInDtlList = (JsonArray) parser.parse(stockIndtl);

			final ArrayList<StockInDetail> stockInItemsList = new ArrayList<StockInDetail>();

			String deletedPoDtlId = "";
			for (int i = 0; i < stockInDtlList.size(); i++) {

				JsonObject json = (JsonObject) stockInDtlList.get(i);
				if (json.get("isDeleted").getAsBoolean() && json.get("poDtl_id") != null) {

					final Integer poDtlId = (json.get("poDtl_id").getAsString() != null && json.get("poDtl_id")
							.getAsString().length() > 0) ? Integer.parseInt(json.get("poDtl_id").getAsString()) : null;

							if (poDtlId != null)
								deletedPoDtlId += ((deletedPoDtlId.isEmpty()) ? "" : ",") + poDtlId;
				}
			}

			//for inserting uomdata

			for (int i = 0; i < stockInDtlList.size(); i++) {

				final StockInDetail stockInItem = new StockInDetail();
				JsonObject json = (JsonObject) stockInDtlList.get(i);
				if (!json.get("isDeleted").getAsBoolean()) {

					//	for(int j=0;j<stockUomList.size();j++) {

					if (json.get("id").getAsString() != null && json.get("id").getAsString().length() > 0)
						stockInItem.setId(Integer.parseInt(json.get("id").getAsString()));

					stockInItem.setStockInHdrId(stockin.getId());
					stockInItem.setStockItemId(json.get("stock_item_id").getAsInt());
					stockInItem.setStockItemCode(json.get("stock_item_code").getAsString());
					stockInItem.setStockItemName(json.get("stock_item_name").getAsString());

					/*stockInItem.setPoQty((json.get("po_qty").getAsString() != null && json.get("po_qty").getAsString()
							.length() > 0) ? json.get("po_qty").getAsDouble() : 0.00);*/
					stockInItem.setReceivedQty((json.get("received_qty").getAsString() != null && json
							.get("received_qty").getAsString().length() > 0) ? json.get("received_qty").getAsDouble()
									: 0.00);			

					stockInItem.setUnitPrice((json.get("unit_price").getAsString() != null && json.get("unit_price")
							.getAsString().length() > 0) ? json.get("unit_price").getAsDouble() : 0.00);
					stockInItem.setAmount((json.get("amount").getAsString() != null && json.get("amount").getAsString()
							.length() > 0) ? json.get("amount").getAsDouble() : 0.00);

					if (json.get("po_id").getAsString() != null && json.get("po_id").getAsString().length() > 0)
						stockInItem.setPoId(json.get("po_id").getAsInt());
					stockInItem.setTaxId((json.get("tax_id").getAsString() != null && json.get("tax_id").getAsString()
							.length() > 0) ? json.get("tax_id").getAsInt() : 0);
					stockInItem.setTaxPc((json.get("tax_pc").getAsString() != null && json.get("tax_pc").getAsString()
							.length() > 0) ? json.get("tax_pc").getAsDouble() : 0);
					stockInItem.setTaxAmount((json.get("tax_amount").getAsString() != null && json.get("tax_amount")
							.getAsString().length() > 0) ? json.get("tax_amount").getAsDouble() : 0.00);
					stockInItem.setDeletedPOItems(deletedPoDtlId);
					stockInItem.setUomCode(json.get("uomcode").getAsString());
					stockInItem.setBaseUomCode(json.get("base_uom_code").getAsString());
					stockInItem.setCompoundUnit((json.get("compound_unit").getAsString() != null && json.get("compound_unit")
							.getAsString().length() > 0) ? json.get("compound_unit").getAsDouble() : 0.00);
					stockInItem.setPack_qty((json.get("pack_qty").getAsString() != null && json.get("pack_qty")
							.getAsString().length() > 0) ? json.get("pack_qty").getAsDouble() : 0.00);			

					stockInItemsList.add(stockInItem);
				}
			}

			stockInDetailService.save(stockInItemsList);
			/* By Suresh */
			/* stockin.setStockInDetails(stockInItemsList); */
			stockInService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		}
		catch (Exception e) {

			response.getWriter().print("0");
			stockInService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: save" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterPrefix(HttpServletResponse response , Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_in_hdr", "stock_in_hdr");
		response.getWriter().print(counterWithPrefix);
	}

	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.posellaweb.web.core.base.controller.ViewController#delete
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {

		Double cur_stock_in_batch = 0.00;

		final StockInService stockInService = new StockInService(getCurrentContext());
		final StockInDetailService stockInDetailService = new StockInDetailService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());

		final String DB_TXN_POINT = "stockInService";
		final String id = request.getParameter("id");
		final String[] batch = request.getParameterValues("batch");
		final String stockreg_id = request.getParameter("stockreg_id");
		final String ext_ref_no = request.getParameter("GRN_No");

		stockInService.beginTrans(DB_TXN_POINT);

		try {

			Integer is_deleted = 0;
			stockInService.updatePOStatus(id);
			is_deleted = stockInService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {

				StockInDetail stockInDtl = new StockInDetail();
				stockInDtl.setStockInHdrId(Integer.parseInt(id));
				stockInDetailService.delete(stockInDtl);

				if (stockreg_id != "") {

					StockRegister stockReg = new StockRegister();
					stockReg.setExtRefId(Integer.parseInt(id));
					stockReg.setExtRefNo(ext_ref_no);
					stockRegisterService.delete(stockReg);

					StockRegisterDetail stockRegDet = new StockRegisterDetail();
					stockRegDet.setStockRegHdrid(Integer.parseInt(stockreg_id));
					stockRegisterDetailService.delete(stockRegDet);
				}

				final ArrayList<ItemMasterBatch> itembatchList = new ArrayList<ItemMasterBatch>();

				for (int i = 0; i < batch.length; i++) {

					ItemMasterBatch itemMasterBatch = new ItemMasterBatch();

					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(batch[i]);
					if (json.get("stock_item_batch_id").toString() != null && json.get("stock_item_batch_id").toString().length() > 0)
					{
						itemMasterBatch.setId(Integer.valueOf(json.get("stock_item_batch_id").toString()));
					}

					cur_stock_in_batch = itemMasterBatchService.getCurrentStockInBatch(itemMasterBatch);

					itemMasterBatch.setStockItemId(Integer.valueOf(json.get("stock_item_id").toString()));
					itemMasterBatch.setCostPrice(Double.valueOf(json.get("cost_price").toString()));
					itemMasterBatch.setStock(cur_stock_in_batch - Double.valueOf(json.get("qty").toString()));

					itembatchList.add(itemMasterBatch);
				}

				itemMasterBatchService.upadteItemMasterBatch(itembatchList);
				response.getWriter().print("1");
			}

			stockInService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {

			stockInService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}
	}

	/**
	 * @param stockInDtl
	 * @param request
	 * @param stockin
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/finalize")
	public void finalize(@RequestBody String stockInDtl , HttpServletRequest request , StockIn stockin ,
			HttpServletResponse response , HttpSession session) throws Exception {
		System.out.println("finalize========= stock in==controller");
		final StockInService stockInService = new StockInService(getCurrentContext());
		final StockRegisterDetailService stockRegDetailService = new StockRegisterDetailService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final GettingCurrentDateTime currentDate = new GettingCurrentDateTime(getCurrentContext());
		Integer sourc_cmpny_id=(Integer) session.getAttribute("COMPANY_ID");

		final String DB_TXN_POINT = "stockInRegService";
		stockInService.beginTrans(DB_TXN_POINT);
		Double qty=0.0;
		Double unit_price=0.0;
		StockRegister stockRegItem = new StockRegister();
		StockWarModel stockWarModel = new StockWarModel();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		final StockWarServiceImpl stockWarService = new StockWarServiceImpl(getCurrentContext());
		final StockWarHistorySeviceImpl stockWarHistorySevice = new StockWarHistorySeviceImpl(getCurrentContext());

		try {
			stockin = mapper.readValue(stockInDtl, StockIn.class);

			String stockIndtl = stockin.getStockDetailLists();

			JsonParser parser = new JsonParser();
			JsonArray stockInDtlList = (JsonArray) parser.parse(stockIndtl);

			stockin.setFinalizedDate(currentDate.getCurrentDate());
			stockin = stockInService.saveStockItem(stockin);
			stockRegItem.setDepartmentId(stockin.getDepartmentId());
			stockRegItem.setExtRefNo(stockin.getGrnNo());
			stockRegItem.setTxnDate(stockin.getReceivedDate());
			stockRegItem.setTransType(transactionType.STOCKIN.gettransactionTypeId());
			stockRegItem.setExtRefId(stockin.getId());
			stockRegItem.setSource_shop_id(sourc_cmpny_id);
			stockRegItem.setDestination_shop_id(sourc_cmpny_id);

			stockRegItem = stockRegisterService.saveStockRegData(stockRegItem);

			final ArrayList<StockRegisterDetail> stockRegdtlItemsList = new ArrayList<StockRegisterDetail>();
			final ArrayList<ItemMasterBatch> stockItembatchList = new ArrayList<ItemMasterBatch>();
			for (int i = 0; i < stockInDtlList.size(); i++) {
				JsonObject json = (JsonObject) stockInDtlList.get(i);
				if (!json.get("isDeleted").getAsBoolean()) {
					
					Integer stockId=Integer.valueOf(json.get("stock_item_id").getAsString());

					final StockRegisterDetail stockRegDtl = new StockRegisterDetail();
					final ItemMasterBatch itemMasterBatch = new ItemMasterBatch();

					if (json.get("stock_item_batch_id").getAsString() != null
							&& json.get("stock_item_batch_id").getAsString().length() > 0) {

						final Integer itemMasterBatchId = Integer.parseInt(json.get("stock_item_batch_id")
								.getAsString());
						itemMasterBatch.setId(itemMasterBatchId);
					}

					stockRegDtl.setDepartment_id(stockRegItem.getDepartmentId());
					stockRegDtl.setStockRegHdrid(stockRegItem.getId());
					stockRegDtl.setStockItemId(stockId);
					stockRegDtl.setExtRefDtlId(stockin.getId());
					stockRegDtl.setStockItemCode(json.get("stock_item_code").getAsString());
					stockRegDtl.setStockItemName(json.get("stock_item_name").getAsString());
					qty=0.0;

					//qty=Double.valueOf(json.get("received_qty").getAsString())*Double.valueOf(json.get("pack_contains").getAsString())*Double.valueOf(json.get("pack_qty").getAsString());
					qty=Double.valueOf(json.get("pack_qty").getAsString());
					stockRegDtl.setInQty(Double.valueOf(qty));
					stockRegDtl.setApproval_qty(Double.valueOf(qty));
					stockRegDtl.setApproval_status(1);
					unit_price=0.0;
					unit_price=Double.valueOf(json.get("unit_price").getAsString())/(Double.valueOf(json.get("compound_unit").getAsString()));
					//added by Udhay for WAR on 01-Nov-2021
					Double warRate=0.00;
					List<StockWarModel> stockWarDetails=stockWarService.getWarDetails(stockId,stockRegItem.getDepartmentId());
					Double currStockCount=stockInService.getCurrentStockCount(stockId, stockRegItem.getDepartmentId());
					System.out.println("currStockCount==============================>"+currStockCount);
					if(stockWarDetails.size()>0){	
						
						for (StockWarModel stockWarDetail:stockWarDetails){
							
							//checking whether the current War rate is equal to requested war price or not
							if(stockWarDetail.getWarRate().equals(Double.valueOf(json.get("unit_price").getAsString()))){
								stockWarDetail.setPreviousQty(currStockCount);
								stockWarDetail.setPreviousRate(stockWarDetail.getWarRate());
								stockWarDetail.setInQty(Double.valueOf(json.get("received_qty").getAsString()));
								stockWarDetail.setInRate(Double.valueOf(json.get("unit_price").getAsString()));
								stockWarDetail.setUpdatedDdate(currentDate.getCurrentDate());
								warRate=stockWarDetail.getWarRate();
								stockWarService.save(stockWarDetail);	
							}else{
								//Store the war details in war history table if the current War rate diffrent from requested war rate
								Mapper objectMapper = new DozerBeanMapper();
								WarHistoryModel warHistoryModel = new WarHistoryModel();
								 objectMapper.map(stockWarDetail,warHistoryModel); // will copy all fields from stockWarDetail to warHistoryModel
								System.out.println("warHistoryModel=================>"+warHistoryModel.toString());
								stockWarHistorySevice.save(warHistoryModel);
								
								stockWarDetail.setPreviousQty(currStockCount);
								stockWarDetail.setPreviousRate(stockWarDetail.getWarRate());
								stockWarDetail.setInQty(Double.valueOf(json.get("received_qty").getAsString()));
								stockWarDetail.setInRate(Double.valueOf(json.get("unit_price").getAsString()));
								stockWarDetail.setUpdatedDdate(currentDate.getCurrentDate());	
								//calculate the WAR
								stockWarDetail.setWarRate(stockWarService.getWarRateExisting(stockWarDetail.getInQty(),
										stockWarDetail.getInRate(),stockWarDetail.getPreviousQty(),stockWarDetail.getPreviousRate()));
								warRate=stockWarDetail.getWarRate();
								stockWarService.save(stockWarDetail);
								
							}
							
													
						}
								
							
					}else{
						
						//if stock details not in  war table 
							Double previousCostPrice=stockWarService.getLatestCostPrice("cost_price",stockId, stockRegItem.getDepartmentId());
							stockWarModel.setDeptId(stockRegItem.getDepartmentId());
							stockWarModel.setStockId(stockId);
							stockWarModel.setInQty(Double.valueOf(json.get("received_qty").getAsString()));
							stockWarModel.setInRate(Double.valueOf(json.get("unit_price").getAsString()));
							stockWarModel.setCreatedDate(currentDate.getCurrentDate());
							//if stock rate exists in mrp_stock_register_dtl
							if(previousCostPrice > 0.00){
								stockWarModel.setPreviousQty(currStockCount);
								stockWarModel.setPreviousRate(previousCostPrice);
								stockWarModel.setWarRate(stockWarService.getWarRateExisting(stockWarModel.getInQty(),stockWarModel.getInRate(),stockWarModel.getPreviousQty(),stockWarModel.getPreviousRate()));
								warRate=stockWarModel.getWarRate();
								
							}else{  //if stock rate exists not in mrp_stock_register_dtl
								previousCostPrice= stockWarService.getLatestCostPriceStock("unit_price",stockId);
								stockWarModel.setPreviousQty(0.00);
								stockWarModel.setPreviousRate(previousCostPrice);
								stockWarModel.setWarRate(Double.valueOf(json.get("unit_price").getAsString()));
								warRate=stockWarModel.getWarRate();
							}
							//System.out.println("costPrice=========controller"+previousCostPrice);
							
							stockWarService.save(stockWarModel);
							
							
					}
					/*stockRegDtl.setCostPrice((json.get("unit_price").getAsString() != null && 
							json.get("unit_price").getAsString().length() != 0) ? Double.valueOf(unit_price) : 0.00);
					*/
					stockRegDtl.setCostPrice(warRate>0.00?warRate:Double.valueOf((json.get("unit_price").getAsString())));
					stockRegDtl.setPoId((json.get("po_id").getAsString() != null && json.get("po_id").getAsString()
							.length() != 0) ? Integer.valueOf(json.get("po_id").getAsString()) : null);

					stockRegdtlItemsList.add(stockRegDtl);

					itemMasterBatch.setStockItemId(Integer.valueOf(json.get("stock_item_id").getAsString()));
					itemMasterBatch.setStock(Double.valueOf(json.get("received_qty").getAsString()));
					/*itemMasterBatch.setCostPrice((json.get("unit_price").getAsString() != null && json
							.get("unit_price").getAsString().length() != 0) ? Double.valueOf(json.get("unit_price")
									.getAsString()) : 0.00);*/
					itemMasterBatch.setCostPrice(warRate>0.00?warRate:Double.valueOf((json.get("unit_price").getAsString())));

					stockItembatchList.add(itemMasterBatch);

				}
			}

			stockRegDetailService.save(stockRegdtlItemsList);
			itemMasterBatchService.upadteItemMasterBatch(stockItembatchList);
			response.getWriter().print("1");

			stockInService.endTrans(DB_TXN_POINT);

		}
		catch (Exception e) {

			stockInService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}
	}

	/**
	 * @param stockin
	 * @param model
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report")
	public ModelAndView printGoodsReceiptSlip(@ModelAttribute StockIn stockin , Model model ,
			HttpServletResponse response , HttpSession session) throws Exception {

		final CompanyProfile company = (CompanyProfile) session
				.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		Integer combine_pur=(Integer) session.getAttribute("combineMode");

		final StockInService stockInService = new StockInService(getCurrentContext());
		final StockInDetailService stockInDetailService = new StockInDetailService(getCurrentContext());
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());

		final String dateFormat = currentDateFormat.getSystemDateFormat();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		final Integer stockinHdrId = stockin.getId();

		Report report = new Report();

		report.setReportName("GOODS RECEIPT SLIP");

		report.setReportType(0);
		report.setCompanyName(company.getCompany_name());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		report.setCombine_mode(combine_pur);

		model.addAttribute("reportName", report);

		try {
			stockin = stockInService.getStockInData(stockinHdrId);
			stockin.setStockInDetails(stockInDetailService.getstockInDtlData(stockinHdrId));
			stockInService.updateAftrFinalize(stockin);

			Date finaliseDate = formatter.parse(stockin.getFinalizedDate());
			stockin.setFinalizedDate(currentDateFormat.getDateWithSystemFormat(finaliseDate));
		}
		catch (Exception e) {

			throw e;
		}
		return new ModelAndView("stockInView", "invoiceData", stockin);


	}
	
	/**
	 * @param stockin
	 * @param model
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/normalreport")
	public ModelAndView normalPrintGoodsReceiptSlip(@ModelAttribute StockIn stockin , Model model ,
			HttpServletResponse response , HttpSession session) throws Exception {

		final CompanyProfile company = (CompanyProfile) session
				.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		Integer combine_pur=(Integer) session.getAttribute("combineMode");

		final StockInService stockInService = new StockInService(getCurrentContext());
		final StockInDetailService stockInDetailService = new StockInDetailService(getCurrentContext());
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());

		final String dateFormat = currentDateFormat.getSystemDateFormat();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		final Integer stockinHdrId = stockin.getId();

		Report report = new Report();

		report.setReportName("CENTRAL HOTELS PURCHASE ORDER");

		report.setReportType(0);
		report.setCompanyName(company.getCompany_name());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		report.setCombine_mode(combine_pur);

		model.addAttribute("reportName", report);

		try {
			stockin = stockInService.getStockInData(stockinHdrId);
			stockin.setStockInDetails(stockInDetailService.getstockInDtlData(stockinHdrId));
			stockin.setGrnNo(stockin.getGrnNo().substring(4));
			//stockInService.updateAftrFinalize(stockin);

			Date finaliseDate = formatter.parse(stockin.getReceivedDate());
			stockin.setFinalizedDate(currentDateFormat.getDateWithSystemFormat(finaliseDate));
		}
		catch (Exception e) {

			throw e;
		}
		return new ModelAndView("purchaseView", "invoiceData", stockin);


	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());

		JsonArray depData = departmentService.getMastersRowJson();
		JsonArray supData = supplierService.getMastersRowJson();

		JsonArray stockItmData=itemMasterService.getMastersRowJson();
        JsonArray itemCategoryData=stockInService.getCategory();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("depData", depData);
		jsonResponse.add("supData", supData);
		jsonResponse.add("stockItmData", stockItmData);
        jsonResponse.add("itemCategoryData",itemCategoryData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "formJsonDataStkin")
	public void getFormJsonDataStkIn(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());

		JsonArray depData = departmentService.getMastersRowJson();
		JsonArray supData = supplierService.getMastersRowJson();

		JsonArray stockItmDatastkin = stockInService.getStockItemData();



		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("depData", depData);
		jsonResponse.add("supData", supData);
		jsonResponse.add("stockItmDatastkin", stockItmDatastkin);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}


	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "stockRegJsonData")
	public void getStockregJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final StockInDetailService stockInDetailService = new StockInDetailService(getCurrentContext());

		JsonArray stockInDtlData = stockInDetailService.getJsonArray();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("stockInDtlData", stockInDtlData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param item
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getItemMasterBatchId")
	public void getItemListJsonData(HttpServletRequest request , ItemMasterBatch item , HttpServletResponse response ,
			Model model) throws Exception {

		final ItemMasterBatchService itemmasterBatchservice = new ItemMasterBatchService(getCurrentContext());

		JsonArray itemsBatchData = itemmasterBatchservice.getBatchJsonArray(item.getId());
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("itemsBatchData", itemsBatchData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getstockredid")
	public void getStockRegHdrid(HttpServletRequest request , ItemMasterBatch item , HttpServletResponse response ,
			Model model) throws Exception {

		final StockInDao stockInService = new StockInDao(getCurrentContext());

		JsonArray stockregid = stockInService.getStockregData(item.getId());
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("stockregid", stockregid);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("depList")
	public void getDepartmentList(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		JsonArray data = departmentService.getMastersRowJson();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("data", data);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("supList")
	public void getSupplierList(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final SupplierService supplierService = new SupplierService(getCurrentContext());

		List<Supplier> list = supplierService.getList();
		String json = new Gson().toJson(list);

		response.getWriter().print(json);
	}

	/**
	 * @param stockIn
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getStockInDtlData")
	public void getStockInDtlData(@ModelAttribute StockIn stockIn , HttpServletResponse response) throws Exception {

		final StockInService stockInService = new StockInService(getCurrentContext());
		System.out.println("stock==========>");
		JsonArray stkInDtl = stockInService.getStockInDtlData(stockIn);
		System.out.println("stock==========>"+stkInDtl.toString());
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("stkInDtl", stkInDtl);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getPoDtlList")
	public void getPOrderDtlList(HttpServletRequest request , HttpServletResponse response) throws Exception {

		final Integer poId = Integer.parseInt(request.getParameter("poId"));

		final PurchaseOrderdtlService poDtlService = new PurchaseOrderdtlService(getCurrentContext());

		JsonArray poDtl = poDtlService.getpoDtlList(poId);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("poDtl", poDtl);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getPOlist")
	public void getPOrderList(HttpServletRequest request , HttpServletResponse response) throws Exception {

		final Integer supllierId = Integer.parseInt(request.getParameter("supllierId"));

		final PurchaseOrderhdrService poService = new PurchaseOrderhdrService(getCurrentContext());

		JsonArray poList = poService.getpoList(supllierId);
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("poList", poList);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}


	@RequestMapping(value = "getSupplierAddress")
	public void getSupplierAddress(HttpServletRequest request , HttpServletResponse response) throws Exception {
		System.out.println("request=========================>"+request.toString());
		System.out.println("supllierId=========================>"+request.getParameter("supllierId"));
	//	System.out.println("supllierId=========================>"+request.getParameter("supllierId"));
		final Integer supllierId = Integer.parseInt(request.getParameter("supllierId"));

		final StockInService stockInService = new StockInService(getCurrentContext());

		String address = stockInService.getSupplierAddress(supllierId);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(address);
	}

	// for item quick serch gana
	@RequestMapping(value = "formJsonItemData")
	public void getformJsonItemData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());

		JsonArray itemData = stockInService.getItemData();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stockItmDatastkin", itemData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	//for item quick search  gana
	@RequestMapping(value="/getItemData")
	public void getItemsDataOnSearch(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String itemCode=request.getParameter("itemCode");
		String itemName=request.getParameter("itemName");
		itemName=itemName.replace("'","''");
		//String departmentData=request.getParameter("departmentId");

		/*if(itemData==null) {
			itemData="";
		}*/
		final StockInService stockInservice=new StockInService(getCurrentContext());
		JsonArray itemDataOnDepartment=new JsonArray();
		itemDataOnDepartment=stockInservice.getItemStockData(itemCode,itemName);
		JsonObject jsonResponse=new JsonObject();
		jsonResponse.add("itemDataOnDepartment", itemDataOnDepartment);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}
	//gana
	@RequestMapping(value="/getPunit")
	public void getPunit(HttpServletRequest request,HttpServletResponse response) {

		String uomCode=request.getParameter("uomCode");
		final StockInService stockInService=new StockInService(getCurrentContext());
		JsonArray baseUom=new JsonArray();
		try {
			baseUom=stockInService.getPunitData(uomCode);	
			JsonObject jsonPunitResponse=new JsonObject();
			jsonPunitResponse.add("baseUom", baseUom);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonPunitResponse.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "formJsonDataReport")
	public void getFormJsonDataReport(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());

		JsonArray depData = departmentService.getMastersRowJson();
		JsonArray supData = supplierService.getMastersRowJson();

		//JsonArray stockItmData=itemMasterService.getMastersRowJson();
		JsonArray stockItmData=stockInService.getReportData();
        JsonArray itemCategoryData=stockInService.getCategory();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("depData", depData);
		jsonResponse.add("supData", supData);
		jsonResponse.add("stockItmData", stockItmData);
        jsonResponse.add("itemCategoryData",itemCategoryData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
}