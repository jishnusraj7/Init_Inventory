package com.indocosmo.mrp.web.stock.stockout.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockOutStatus;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;
import com.indocosmo.mrp.web.stock.stockin.service.StockInService;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.service.StockInDetailService;
import com.indocosmo.mrp.web.stock.stockout.dao.IStockOutDao;
import com.indocosmo.mrp.web.stock.stockout.dao.StockOutDao;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;
import com.indocosmo.mrp.web.stock.stockout.service.StockOutService;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.service.StockOutDetailService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;
import com.indocosmo.mrp.web.stock.war.model.StockWarModel;
import com.indocosmo.mrp.web.stock.war.service.StockWarServiceImpl;
import com.indocosmo.mrp.web.stock.war.warhistory.model.WarHistoryModel;
import com.indocosmo.mrp.web.stock.war.warhistory.service.StockWarHistorySeviceImpl;

/**
 * @author jo
 *
 */
@Controller
@RequestMapping("/stockout")
public class StockOutController extends ViewController<StockOut, StockOutDao, StockOutService> {

	public static final Logger logger = Logger.getLogger(StockOutController.class);
	
	
	

	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockOutService getService() {

		return new StockOutService(getCurrentContext());
	}

	/**
	 * @param stockOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(Model model , HttpServletRequest request , HttpSession session) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		Integer isStockOut = Integer.parseInt(request.getParameter("isStockOut"));
		SysdefPermission permission = null;

		if (isStockOut == 0) {
			model.addAttribute("stockoutclass0",true);
			permission = userPermissionService.getCurrentUserPermission(session, "STR_REQ");
		}
		else {
			model.addAttribute("stockoutclass1",true);
			permission = userPermissionService.getCurrentUserPermission(session, "STR_OUT");
		}

		model.addAttribute("permission", permission);
		//String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");
	//	model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("isRequest", isStockOut);
		SysdefPermission finalizepermission = userPermissionService.getCurrentUserPermission(session,
				"EDIT_FINAL_STOCKOUT");
		model.addAttribute("stockoutFinalizepermission", finalizepermission);
		model.addAttribute("Store",true);


		model.addAttribute("Stock",true);

		return "/stock/stockout/list";
	}

	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterPrefix(HttpServletResponse response , Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");
		response.getWriter().print(counterWithPrefix);

	}

	/**
	 * @param stockOutDtl
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public void save(@RequestBody String stockOutDtl , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
		
		System.out.println("stockOutDtl====================================>"+stockOutDtl);
	final CounterService counterService = new CounterService(getCurrentContext());

		final String DB_TXN_POINT = "stockOutService";
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		final StockOutDetailService stockOutDetailService = new StockOutDetailService(getCurrentContext());
		StockOut stockout = null;
		stockOutService.beginTrans(DB_TXN_POINT);
		
		//Issue in stock out module: Duplicate entry of REF No. there is no need to increment the counter in edit mode
		//comment the unwanted line of code 
		//modified by: Ayana Dharman modified date: 12-01-2021
		
		
	//	String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");
		try {

			ObjectMapper mapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			stockout = mapper.readValue(stockOutDtl, StockOut.class);
			
		//	stockout.setStockTransfeNo(CounterWithPrefix);
			
			
			String stockOutdtl = stockout.getStockDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray stockInDtlList = (JsonArray) parser.parse(stockOutdtl);

			if (stockout.getId() == null) {

				stockout.setStockTransfeNo((counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr")));
			}
			
			stockout = stockOutService.saveStockItem(stockout);

			final ArrayList<StockOutDetail> stockOutItemsList = new ArrayList<StockOutDetail>();

			for (int i = 0; i < stockInDtlList.size(); i++) {

				final StockOutDetail stockOutItem = new StockOutDetail();
				JsonObject json = (JsonObject) stockInDtlList.get(i);
				
				System.out.println("json stockOutDtl====================================>"+json.toString());

				if (json.get("id").getAsString() != null && json.get("id").getAsString().length() != 0) {

					stockOutItem.setId(Integer.valueOf(json.get("id").getAsString()));
				}

				stockOutItem.setStockOutHdrId(stockout.getId());
				stockOutItem.setStockItemId((json.get("stock_item_id").getAsString() != null && json
						.get("stock_item_id").getAsString().length() != 0) ? Integer.valueOf(json.get("stock_item_id")
								.getAsString()) : 0);
				stockOutItem.setStockItemCode((json.get("stock_item_code").getAsString() != null && json
						.get("stock_item_code").getAsString().length() != 0) ? json.get("stock_item_code")
								.getAsString() : null);
				stockOutItem.setStockItemName((json.get("stock_item_name").getAsString() != null && json
						.get("stock_item_name").getAsString().length() != 0) ? json.get("stock_item_name")
								.getAsString() : null);
				stockOutItem.setRequestQty((json.get("request_qty").getAsString() != null && json.get("request_qty")
						.getAsString().length() != 0) ? Double.valueOf(json.get("request_qty").getAsString()) : 0.00);
				
				System.out.println("delivered_qty===========================>"+json.get("delivered_qty").getAsString());
				
				if(json.get("delivered_qty").getAsString().equalsIgnoreCase("0.000")||json.get("delivered_qty").getAsString().equalsIgnoreCase("0.00")||json.get("delivered_qty").getAsString().equalsIgnoreCase("0.0")){
					System.out.println("if===========================>"+json.get("delivered_qty").getAsString());
					stockOutItem.setDeliveredQty(Double.valueOf(json.get("request_qty").getAsString()));

				}else{
					System.out.println("else===========================>"+json.get("delivered_qty").getAsString());
					stockOutItem.setDeliveredQty((json.get("delivered_qty").getAsString() != null && json
							.get("delivered_qty").getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty")
									.getAsString()) : 0.00);
				}
				
				
				/*stockOutItem.setUnitPrice((json.get("unit_price").getAsString() != null && json.get("unit_price")
						.getAsString().length() != 0) ? Double.valueOf(json.get("unit_price").getAsString()) : 0.00);*/
				//Added By Udhay on  31st Aug 2021 for StockOut Data Issue
				Double unitPrice=stockOutDetailService.getUnitPrice(json.get("stock_item_code").toString());
				stockOutItem.setUnitPrice(unitPrice);
				System.out.println("stockOutItem.getUnitPrice====================================>"+stockOutItem.getUnitPrice());
				//Added By Udhay on  1st Sep 2021 for StockOut Data Issue
				stockOutItem.setAmount(Double.valueOf(json.get("delivered_qty").getAsString())*unitPrice);
				/*stockOutItem.setAmount((json.get("amount").getAsString() != null && json.get("amount").getAsString()
						.length() != 0) ? Double.valueOf(json.get("amount").getAsString()) : 0.00);*/
					stockOutItem.setBaseUomCode(json.get("base_uom_code").getAsString());
					stockOutItem.setUomCode(json.get("uomcode").getAsString());
				stockOutItem.setCompoundUnit((json.get("compound_unit").getAsString() != null && json.get("compound_unit")
						.getAsString().length() > 0) ? json.get("compound_unit").getAsDouble() : 0.00);
				stockOutItemsList.add(stockOutItem);
				
			}

			stockOutDetailService.save(stockOutItemsList);

			stockout.setStockOutDetails(stockOutItemsList);
			stockOutService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");

		}
		catch (Exception e) {

			e.printStackTrace();
			response.getWriter().print("0");
			stockOutService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in " + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}

	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#setModelItem
	 * (javax.servlet.http.HttpServletRequest,
	 * com.indocosmo.mrp.web.core.base.model.GeneralModelBase) */
	@Override
	protected StockOut setModelItem(HttpServletRequest request , StockOut item) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {

			Column annotationColumn = (Column) field.getDeclaredAnnotation(Column.class);

			if (annotationColumn != null) {

				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);

				if (fieldName.equals("stock_transfer_no")) {

					if (request.getParameter("id") == null) {

						valueString = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");
					}
				}

				if (valueString != null) {

					Object value = WebUtil.pageFieldToModelValue(field, valueString);

					if (value != null) {

						field.setAccessible(true);
						field.set(item, value);
					}
				}

			}

		}

		return item;
	}

	/**
	 * @param request
	 * @param stockout
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "status")
	public void updateStatus(@RequestBody String stockOutDtl , StockOut stockout , HttpSession session ,
			HttpServletResponse response , HttpServletRequest request) throws Exception {

		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		final StockRegisterDetailService stockRegDetailService = new StockRegisterDetailService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		Integer sourc_cmpny_id=(Integer) session.getAttribute("COMPANY_ID");
		final GettingCurrentDateTime currentDate = new GettingCurrentDateTime(getCurrentContext());
		final StockInService stockInService = new StockInService(getCurrentContext());
		StockWarModel stockWarModel = new StockWarModel();

		StockOut stockout1 = null;
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		stockout1 = mapper.readValue(stockOutDtl, StockOut.class);

		final String changeReason = stockout1.getChange_reason();

		Integer status = (stockout1.getReqStatus() != null) ? stockout1.getReqStatus() : 0;
		final Integer stockOutType = (stockout1.getStockOutType() != null) ? stockout1.getStockOutType() : 0;

		if (status == 1) {

			//status = stockOutStatus.APPROVED.getStockOutStatusId();
			status = stockOutStatus.ISSUED.getStockOutStatusId();
		}
		else if (status == 2) {

			status = stockOutStatus.REJECTED.getStockOutStatusId();
		}
		else if (status == 3) {

			status = stockOutStatus.ISSUED.getStockOutStatusId();
		}
		else if (status == 4) {

			status = stockOutStatus.PRINTED.getStockOutStatusId();
		}

		final String DB_TXN_POINT = "stockOutRegService";
		StockRegister stockRegItem = new StockRegister();
		Double curCtockInBatch = 0.00;

		String stockOutdtllist = stockout1.getStockDetailLists();

		JsonParser parser = new JsonParser();
		JsonArray stockInDtlList = (JsonArray) parser.parse(stockOutdtllist);
		stockOutService.beginTrans(DB_TXN_POINT);
System.out.println("stockInDtlList=================>"+stockInDtlList.toString());
		try {

			stockout.setId(stockout1.getId());
			stockout.setReqStatus(status);
			stockout.setChange_reason(changeReason);
			if (status == 3) {

				final Integer departmentId = (stockout1.getFromDepartmentId() != null) ? stockout1
						.getFromDepartmentId() : 0;
						stockout.setFromDepartmentId(departmentId);
						stockout.setStockTransfeNo(stockout1.getStockTransfeNo());
						stockout.setStockTransferDate(stockout1.getStockTransferDate());
			}

			if (stockout.getReqStatus() == 3) {

				final String stockregId = request.getParameter("stockreg_id");

				if (stockregId != null) {

					stockRegItem.setId(Integer.parseInt(stockregId));
				}

				stockRegItem.setExtRefId(stockout1.getId());
				stockRegItem.setDepartmentId(stockout.getFromDepartmentId());
				stockRegItem.setExtRefNo(stockout.getStockTransfeNo());
				stockRegItem.setTxnDate(stockout.getStockTransferDate());
				stockRegItem.setTransType(transactionType.STOCKOUT.gettransactionTypeId());
				stockRegItem.setSource_shop_id(sourc_cmpny_id);
				stockRegItem.setDestination_shop_id(sourc_cmpny_id);
				stockRegItem = stockRegisterService.saveStockRegData(stockRegItem);

				final ArrayList<StockRegisterDetail> stockRegdtlItemsList = new ArrayList<StockRegisterDetail>();
				final ArrayList<ItemMasterBatch> stockItembatchList = new ArrayList<ItemMasterBatch>();
				final StockWarServiceImpl stockWarService = new StockWarServiceImpl(getCurrentContext());
				final StockWarHistorySeviceImpl stockWarHistorySevice = new StockWarHistorySeviceImpl(getCurrentContext());

				for (int i = 0; i < stockInDtlList.size(); i++) {

					JsonObject json = (JsonObject) stockInDtlList.get(i);
					final StockOutDetail stockOutItem = new StockOutDetail();
					StockRegisterDetail stockRegDtl = new StockRegisterDetail();
					final ItemMasterBatch itemMasterBatch = new ItemMasterBatch();

					final Integer stockItemid = (json.get("stock_item_id").getAsString() != null && json
							.get("stock_item_id").getAsString().length() != 0) ? Integer.valueOf(json.get(
									"stock_item_id").getAsString()) : 0;

							if (json.get("id").getAsString() != null && json.get("id").getAsString().length() != 0) {

								final Integer stockOutDtlItemid = Integer.parseInt(json.get("id").getAsString());

								stockOutItem.setId(stockOutDtlItemid);
							}

							if (json.get("stock_item_id").getAsString() != null
									&& json.get("stock_item_id").getAsString().length() != 0) {

								final Integer itemMasterItemId = Integer.parseInt(json.get("stock_item_id").getAsString());
								itemMasterBatch.setStockItemId(itemMasterItemId);
								curCtockInBatch = itemMasterBatchService.currentStock(itemMasterBatch);

							}

							stockRegDtl.setStockRegHdrid(stockRegItem.getId());
							stockRegDtl.setStockItemId(stockItemid);
							stockRegDtl.setStockItemCode((json.get("stock_item_code").getAsString() != null && json
									.get("stock_item_code").getAsString().length() != 0) ? json.get("stock_item_code")
											.getAsString() : null);
							stockRegDtl.setStockItemName((json.get("stock_item_name").getAsString() != null && json
									.get("stock_item_name").getAsString().length() != 0) ? json.get("stock_item_name")
											.getAsString() : null);
							stockRegDtl.setOutQty((json.get("delivered_qty").getAsString() != null && json.get("delivered_qty")
									.getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty").getAsString())
											: 0.00);
							stockRegDtl
							.setCostPrice((json.get("unit_price").getAsString() != null && json.get("unit_price")
							.getAsString().length() != 0) ? Double
									.valueOf(json.get("unit_price").getAsString()) : 0.00);
							stockRegDtl.setExtRefDtlId(stockRegItem.getId());
							stockRegDtl.setDepartment_id(stockout1.getFromDepartmentId());
							stockRegDtl.setApproval_qty((json.get("delivered_qty").getAsString() != null && json.get("delivered_qty")
									.getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty").getAsString())
											: 0.00);;
											stockRegDtl.setApproval_status(1);

											stockRegdtlItemsList.add(stockRegDtl);

											stockRegDtl = new StockRegisterDetail();
											stockRegDtl.setStockRegHdrid(stockRegItem.getId());
											stockRegDtl.setStockItemId(stockItemid);
											stockRegDtl.setStockItemCode((json.get("stock_item_code").getAsString() != null && json
													.get("stock_item_code").getAsString().length() != 0) ? json.get("stock_item_code")
															.getAsString() : null);
											stockRegDtl.setStockItemName((json.get("stock_item_name").getAsString() != null && json
													.get("stock_item_name").getAsString().length() != 0) ? json.get("stock_item_name")
															.getAsString() : null);
											stockRegDtl.setInQty((json.get("delivered_qty").getAsString() != null && json.get("delivered_qty")
													.getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty").getAsString())
															: 0.00);
											stockRegDtl
											.setCostPrice((json.get("unit_price").getAsString() != null && json.get("unit_price")
											.getAsString().length() != 0) ? Double
													.valueOf(json.get("unit_price").getAsString()) : 0.00);
											stockRegDtl.setExtRefDtlId(stockRegItem.getId());
											stockRegDtl.setDepartment_id(stockout1.getToDepartmentId());
											stockRegDtl.setApproval_qty((json.get("delivered_qty").getAsString() != null && json.get("delivered_qty")
													.getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty").getAsString())
															: 0.00);;
															stockRegDtl.setApproval_status(1);
															
															
															
															
															//added by Udhayan on 19 Jan 2022for WAR Enhancement 
														//	StockWarModel stockWarModel = new StockWarModel();
															Double warRate=0.00;
															List<StockWarModel> stockWarDetailsFrom=stockWarService.getWarDetails(stockItemid,stockRegItem.getDepartmentId());
															List<StockWarModel> stockWarDetailsTo=stockWarService.getWarDetails(stockItemid,stockout1.getToDepartmentId());
															Double currStockCountFrom=stockInService.getCurrentStockCount(stockItemid, stockRegItem.getDepartmentId());
															Double currStockCountTo=stockInService.getCurrentStockCount(stockItemid, stockout1.getToDepartmentId());
															Double curtStockRegDeptPriceTo=stockWarService.getLatestCostPrice("cost_price",stockItemid, stockout1.getToDepartmentId());
															if(stockWarDetailsFrom.size()>0 && stockWarDetailsTo.size()>0){	
																
																for (StockWarModel stockWarDetailFrom:stockWarDetailsFrom){
																	
																	for (StockWarModel stockWarDetailTo:stockWarDetailsTo){
																		
																		//Store the war details in war history table if the current War rate diffrent from requested war rate
																		Mapper objectMapper = new DozerBeanMapper();
																		WarHistoryModel warHistoryModel = new WarHistoryModel();
																		 objectMapper.map(stockWarDetailTo,warHistoryModel); // will copy all fields from stockWarDetail to warHistoryModel
																		System.out.println("warHistoryModel=================>"+warHistoryModel.toString());
																		stockWarHistorySevice.save(warHistoryModel);
																		
																		stockWarDetailTo.setPreviousQty(currStockCountTo);
																		stockWarDetailTo.setPreviousRate(stockWarDetailTo.getWarRate());
																		stockWarDetailTo.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																		stockWarDetailTo.setInRate(stockWarDetailFrom.getWarRate());
																		stockWarDetailTo.setWarRate(stockWarService.getWarRateExisting(stockWarDetailTo.getInQty(),
																				stockWarDetailTo.getInRate(),stockWarDetailTo.getPreviousQty(),stockWarDetailTo.getPreviousRate()));
																		stockWarDetailTo.setUpdatedDdate(currentDate.getCurrentDate());
																		warRate=stockWarDetailTo.getWarRate();
																		stockWarService.save(stockWarDetailTo);	
																		///Double currStockCountFrom=stockInService.getCurrentStockCount(stockItemid, stockRegItem.getDepartmentId());
																		/*stockWarDetailFrom.setPreviousQty(currStockCountFrom);
																		stockWarService.save(stockWarDetailFrom);*/
																		
																	}
																	
																	
																}
																
															}else if(stockWarDetailsFrom.size()>0 && !(stockWarDetailsTo.size()>0)){
																for (StockWarModel stockWarDetailFrom:stockWarDetailsFrom){
																stockWarModel.setDeptId( stockout1.getToDepartmentId());
																stockWarModel.setStockId(stockItemid);
																stockWarModel.setPreviousQty(currStockCountTo);
																stockWarModel.setPreviousRate(curtStockRegDeptPriceTo);
																stockWarModel.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																stockWarModel.setInRate(stockWarDetailFrom.getWarRate());
																stockWarModel.setWarRate(stockWarService.getWarRateExisting(stockWarModel.getInQty(),
																		stockWarModel.getInRate(),stockWarModel.getPreviousQty(),stockWarModel.getPreviousRate()));
																stockWarModel.setUpdatedDdate(currentDate.getCurrentDate());
																warRate=stockWarModel.getWarRate();
																stockWarModel.setCreatedDate(currentDate.getCurrentDate());
																warRate=stockWarModel.getWarRate();
																stockWarService.save(stockWarModel);
																}
															}
															
															/*Double curtStockRegDeptPriceFrom=stockWarService.getLatestCostPrice("cost_price",stockItemid, stockRegItem.getDepartmentId());
															Double curtStockRegDeptPriceTo=stockWarService.getLatestCostPrice("cost_price",stockItemid, stockout1.getToDepartmentId());
															if(curtStockRegDeptPriceFrom>0.00 && curtStockRegDeptPriceTo>0.00){
																
																List<StockWarModel> stockWarDetailsFrom=stockWarService.getWarDetails(stockItemid,stockRegItem.getDepartmentId());
																List<StockWarModel> stockWarDetailsTo=stockWarService.getWarDetails(stockItemid,stockout1.getToDepartmentId());
																	
																if(stockWarDetailsFrom.size()>0 && stockWarDetailsTo.size()>0){
																	System.out.println("1111111111111111111111111111111111");
																		for (StockWarModel stockWarDetailfrom:stockWarDetailsFrom){	
																			
																			for (StockWarModel stockWarDetailsto:stockWarDetailsTo){
																				stockWarDetailsto.setPreviousQty(1.00);
																				//stockWarDetailsto.setPreviousQty(stockWarDetailsto.getInQty());
																				stockWarDetailsto.setPreviousRate(stockWarDetailsto.getWarRate());
																				stockWarDetailsto.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																				stockWarDetailsto.setInRate(stockWarDetailfrom.getWarRate());
																				stockWarDetailsto.setWarRate(stockWarService.getWarRateExisting(stockWarDetailsto.getInQty(),
																						stockWarDetailsto.getInRate(),stockWarDetailsto.getPreviousQty(),stockWarDetailsto.getPreviousRate()));
																				stockWarDetailsto.setUpdatedDdate(currentDate.getCurrentDate());
																				warRate=stockWarDetailsto.getWarRate();
																				stockWarService.save(stockWarDetailsto);
																			}
																			
																		}
																		
																}else if(stockWarDetailsFrom.size()>0){
																	System.out.println("2222222222222222222222222222");
																	for (StockWarModel stockWarDetailfrom:stockWarDetailsFrom){	
																		stockWarModel.setDeptId(stockout1.getToDepartmentId());
																		stockWarModel.setStockId(stockItemid);
																		stockWarModel.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																		stockWarModel.setInRate(stockWarDetailfrom.getWarRate());
																		stockWarModel.setCreatedDate(currentDate.getCurrentDate());
																		stockWarModel.setPreviousQty(0.00);
																		stockWarModel.setPreviousRate(0.00);
																		stockWarModel.setWarRate(stockWarDetailfrom.getWarRate());
																		warRate=stockWarModel.getWarRate();
																		stockWarService.save(stockWarModel);
																	}	
																}else if(stockWarDetailsTo.size()>0){
																	System.out.println("33333333333333333333333333333333");
																	for (StockWarModel stockWarDetailsto:stockWarDetailsTo){
																		stockWarDetailsto.setPreviousQty(stockWarDetailsto.getInQty());
																		stockWarDetailsto.setPreviousRate(stockWarDetailsto.getInRate());
																		stockWarDetailsto.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																		stockWarDetailsto.setInRate(curtStockRegDeptPriceFrom);
																		stockWarDetailsto.setWarRate(stockWarService.getWarRateExisting(stockWarDetailsto.getInQty(),
																				stockWarDetailsto.getInRate(),stockWarDetailsto.getPreviousQty(),stockWarDetailsto.getPreviousRate()));
																		 stockWarDetailsto.setUpdatedDdate(currentDate.getCurrentDate());
																		 stockWarService.save(stockWarDetailsto);
																	}
																	
																	
																}else{
																	System.out.println("4444444444444444444444444444444444");
																	stockWarModel.setDeptId(stockout1.getToDepartmentId());
																	stockWarModel.setStockId(stockItemid);
																	stockWarModel.setInQty(Double.valueOf(json.get("delivered_qty").getAsString()));
																	stockWarModel.setInRate(curtStockRegDeptPriceFrom);
																	stockWarModel.setCreatedDate(currentDate.getCurrentDate());
																	stockWarModel.setPreviousQty(0.00);
																	stockWarModel.setPreviousRate(0.00);
																	stockWarModel.setWarRate(curtStockRegDeptPriceFrom);
																	warRate=stockWarModel.getWarRate();
																	stockWarService.save(stockWarModel);
																}
																
																
															}else if(curtStockRegDeptPriceFrom>0.00){
																System.out.println("comming 111111111111111");
																
															}else if(curtStockRegDeptPriceTo>0.00){
																System.out.println("comming 2222222222222222");
																
															}else{
																
																System.out.println("comming here3333333");
																
															}*/
															
															stockRegDtl.setCostPrice(warRate);

															stockRegdtlItemsList.add(stockRegDtl);

															itemMasterBatch.setStockItemId(stockRegDtl.getStockItemId());
															itemMasterBatch.setStock(curCtockInBatch - stockRegDtl.getOutQty());
															itemMasterBatch.setCostPrice(stockRegDtl.getCostPrice());

															stockItembatchList.add(itemMasterBatch);
				}

				stockRegDetailService.save(stockRegdtlItemsList);
				itemMasterBatchService.upadteItemMasterBatch(stockItembatchList);

				// Stock Out Type = store to store
				if (stockOutType == 1) {

					saveStockInDataToStore(stockout1);
				}

			}
			stockout = stockOutService.updateStockOutStatus(stockout);
			stockOutService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		}
		catch (Exception e) {

			stockOutService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: updateStatus in " + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		response.getWriter().print("1");
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	public void saveStockInDataToStore(StockOut stockout) throws Exception {

		StockIn stockIn = new StockIn();
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		final Integer destCompanyId = (stockout.getDestCompanyId() != null && !stockout.getDestCompanyId().equals("")) ? stockout
				.getDestCompanyId() : 0;
				final CompanyService companyService = new CompanyService(getNewContext());
				Company currentCompany = new Company();
				currentCompany = companyService.getCompanyByID(destCompanyId);
				final ApplicationContext companyContext = getNewContext(currentCompany);
				final StockInService stockInService = new StockInService(companyContext);
				final StockInDetailService stockInDetailService = new StockInDetailService(companyContext);
				final CounterService counterService = new CounterService(companyContext);
				final GettingCurrentDateTime getCurrentDate = new GettingCurrentDateTime(getCurrentContext());
				final String DB_TXN_POINT = "stockInService";
				stockInService.beginTrans(DB_TXN_POINT);

				try {

					final Integer destDepartmentId = (stockout.getDestCompanyId() != null && !stockout.getDestCompanyId()
							.equals("")) ? stockout.getDestCompanyId() : 0;
							final String destCompanyCode = (stockout.getDestCompanyCode() != null && !stockout.getDestCompanyCode()
									.equals("")) ? stockout.getDestCompanyCode() : null;
									final String destCompanyName = (stockout.getDestCompanyName() != null && !stockout.getDestCompanyName()
											.equals("")) ? stockout.getDestCompanyName() : null;
											final String GRNNo = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");
											final String currentDate = getCurrentDate.getCurrentDate();
											stockIn.setDepartmentId(destDepartmentId);
											stockIn.setStockInType(2);
											stockIn.setStatus(0);
											stockIn.setCompanyId(destCompanyId);
											stockIn.setCompanyCode(destCompanyCode);
											stockIn.setCompanyName(destCompanyName);
											stockIn.setGrnNo(GRNNo);
											stockIn = stockInService.getInterStoreSupplier(stockIn);
											stockIn.setReceivedDate(currentDate);
											stockIn = stockInService.saveStockItem(stockIn);

											String stockOutdtllist = stockout.getStockDetailLists();

											JsonParser parser = new JsonParser();
											JsonArray stockInDtl = (JsonArray) parser.parse(stockOutdtllist);

											final ArrayList<StockInDetail> StockInDetailItemsList = new ArrayList<StockInDetail>();

											for (int i = 0; i < stockInDtl.size(); i++) {

												JsonObject json = (JsonObject) stockInDtl.get(i);

												StockInDetail stockInDetail = new StockInDetail();

												stockInDetail.setStockInHdrId(stockIn.getId());
												stockInDetail.setStockItemId((json.get("stock_item_id").getAsString() != null && json
														.get("stock_item_id").getAsString().length() != 0) ? Integer.valueOf(json.get("stock_item_id")
																.getAsString()) : null);
												stockInDetail.setStockItemCode((json.get("stock_item_code").getAsString() != null && json
														.get("stock_item_code").getAsString().length() != 0) ? json.get("stock_item_code")
																.getAsString() : null);
												stockInDetail.setStockItemName((json.get("stock_item_name").getAsString() != null && json
														.get("stock_item_name").getAsString().length() != 0) ? json.get("stock_item_name")
																.getAsString() : null);
												stockInDetail.setReceivedQty((json.get("delivered_qty").getAsString() != null && json
														.get("delivered_qty").getAsString().length() != 0) ? Double.valueOf(json.get("delivered_qty")
																.getAsString()) : 0.00);
												stockInDetail.setUnitPrice((json.get("unit_price").getAsString() != null && json.get("unit_price")
														.getAsString().length() != 0) ? Double.valueOf(json.get("unit_price").getAsString()) : 0.00);
												stockInDetail.setAmount((json.get("amount").getAsString() != null && json.get("amount").getAsString()
														.length() != 0) ? Double.valueOf(json.get("amount").getAsString()) : 0.00);
												stockInDetail = stockOutService.getItemTaxDtl(stockInDetail);
												StockInDetailItemsList.add(stockInDetail);
											}

											stockInDetailService.save(StockInDetailItemsList);
											stockInService.endTrans(DB_TXN_POINT);

				}
				catch (Exception e) {

					stockInService.rollbackTrans(DB_TXN_POINT);
					logger.error("Method: saveStockInDataToStore in " + this + Throwables.getStackTraceAsString(e));
					throw new CustomException();
				}
	}

	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.posellaweb.web.core.base.controller.ViewController#delete
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {

		final String DB_TXN_POINT = "stockOutService";
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		final StockOutDetailService stockOutDetailService = new StockOutDetailService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final String id = request.getParameter("id");
		final String stockreg_id = request.getParameter("stockreg_id");
		final String stock_transfer_no = request.getParameter("stock_transfer_no");

		stockOutService.beginTrans(DB_TXN_POINT);

		try {

			Integer is_deleted = 0;
			is_deleted = stockOutService.delete(id);

			if (is_deleted == 0) {

				response.getWriter().print("0");
			}
			else {

				StockOutDetail stockOutDtl = new StockOutDetail();
				stockOutDtl.setStockOutHdrId(Integer.parseInt(id));
				stockOutDetailService.delete(stockOutDtl);

				if (stockreg_id != "" && stockreg_id != null ) {

					StockRegister stockReg = new StockRegister();
					stockReg.setExtRefId(Integer.parseInt(id));
					stockReg.setExtRefNo(stock_transfer_no);
					stockRegisterService.delete(stockReg);

					StockRegisterDetail stockRegDet = new StockRegisterDetail();
					stockRegDet.setStockRegHdrid(Integer.parseInt(stockreg_id));
					stockRegisterDetailService.delete(stockRegDet);

				}
				response.getWriter().print("1");
			}

			stockOutService.endTrans(DB_TXN_POINT);

		}
		catch (Exception e) {

			stockOutService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in " + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}

	/**
	 * @param stockout
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report")
	public ModelAndView printInvoice(@ModelAttribute StockOut stockOut , Model model , HttpServletResponse response ,
			HttpSession session) throws Exception {

		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		final StockOutDetailService stockOutDetailService = new StockOutDetailService(getCurrentContext());
		final CompanyProfile companyProfile = (CompanyProfile) session
				.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		Integer combine_pur=(Integer) session.getAttribute("combineMode");


		final String dateFormat = currentDateFormat.getSystemDateFormat();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		final Integer stockoutHdrId = stockOut.getId();

		Report report = new Report();

		report.setReportName("GOODS ISSUE SLIP");
		report.setCompanyName(companyProfile.getCompany_name());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		report.setReportType(0);
		report.setCombine_mode(combine_pur);

		model.addAttribute("reportName", report);

		try {

			stockOut = stockOutService.getStockOutData(stockoutHdrId);
			stockOut.setStockOutDetails(stockOutDetailService.getStockOutDtlData(stockoutHdrId));
			stockOutService.updateStockOutPrintStatus(stockOut);
			Date deliveredDate = formatter.parse(stockOut.getStockTransferDate());
			stockOut.setStockTransferDate(currentDateFormat.getDateWithSystemFormat(deliveredDate));
			Date requestdDate = formatter.parse(stockOut.getStockRequestDate());
			stockOut.setStockRequestDate(currentDateFormat.getDateWithSystemFormat(requestdDate));
		}
		catch (Exception e) {

			logger.error("Method: printInvoice in " + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return new ModelAndView("stockOutView", "invoiceData", stockOut);
	}

	@RequestMapping(value = "requestJsonData")
	public void getRequestJsonData(@ModelAttribute DataTableCriterias datatableParameters , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {

		final StockOutService stockOutService = new StockOutService(getCurrentContext());

		JsonObject jsonResponse = stockOutService.getStockOutRequestTableJsonArray(datatableParameters);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param stockIn
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getStockOutDtlData")
	public void getStockOutDtlData(@ModelAttribute StockOut stockOut , HttpServletResponse response) throws Exception {

		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		JsonArray stkOutDtl = stockOutService.getStockOutDtlData(stockOut);



		String orderIds="";	
		if(stkOutDtl.size() !=0)
		{
			for(int i=0;i<stkOutDtl.size();i++)
			{
				JsonObject json=(JsonObject)stkOutDtl.get(i);
				orderIds+=(orderIds.isEmpty() ? " ":",")+json.get("stock_item_id").getAsInt();
			}	

		}

		/* String itmsIdArray [] = new String [stkOutDtl.size()];

		for (int i = 0; i < stkOutDtl.size(); i++) {
			JsonObject json = (JsonObject) stkOutDtl.get(i);
			if(json.has("stock_item_id")){
				itmsIdArray[i]=json.get("stock_item_id").getAsString();
			}




		}
		 */
		String deptId=stockOut.getDestDepartmentName();// taking as destination deptID



		JsonArray sktPendQty= stockOutService.getPendingStockOutDtlData(stockOut, deptId, orderIds);


		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stkOutDtl", stkOutDtl);
		jsonResponse.add("stkPendingOutDtl", sktPendQty);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value="getCurrStock")	
	public void getCurrStock(@ModelAttribute StockOut stockOut,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		int CHECK_RETURN=0;
		List<String> resulData= new ArrayList<String>();
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		Double currstock=0.0;
		Double pendingStock=0.0;
		int statusCheck=0;
		String itemId=request.getParameter("itemId");
		String current_date=request.getParameter("current_date");
		String department_id=request.getParameter("department_id");
	//	statusCheck=Boolean.parseBoolean("checkReturn")?"1":"0";
		statusCheck=Integer.parseInt(request.getParameter("checkReturn"));
         currstock=stockOutService.getCurrstock(itemId,current_date,department_id);


         if(statusCheck==CHECK_RETURN) {
		String des_department_id=request.getParameter("desDepartmentId");


		pendingStock=stockOutService.getPendingStockByDeptAndItem(itemId, des_department_id);

	

		resulData.add(pendingStock.toString());
         }
		resulData.add(currstock.toString());

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(resulData);

	}

	@RequestMapping(value="getPendingStock")	
	public void getPendingStockByDepAndItem(@ModelAttribute StockOut stockOut,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		Double pendingStock=0.0;
		String itemId=request.getParameter("itemId");
		String department_id=request.getParameter("department_id");



		pendingStock=stockOutService.getPendingStockByDeptAndItem(itemId, department_id);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(pendingStock.toString());

	}


}
