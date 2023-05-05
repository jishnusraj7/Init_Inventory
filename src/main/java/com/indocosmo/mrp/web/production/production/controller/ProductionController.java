package com.indocosmo.mrp.web.production.production.controller;

import java.lang.reflect.Field;
import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumDepartmentType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service.ItemProdCostService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.timeslot.service.TimeslotService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;
import com.indocosmo.mrp.web.production.production.dao.ProductionDao;
import com.indocosmo.mrp.web.production.production.model.Production;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;
import com.indocosmo.mrp.web.production.production.productiondetail.service.ProductionDtlService;
import com.indocosmo.mrp.web.production.production.service.ProductionService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.service.ItemStockService;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.service.StockDisposalDetailService;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;
import com.indocosmo.mrp.web.stock.stockout.service.StockOutService;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.service.StockOutDetailService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

@Controller
@RequestMapping("/production")
public class ProductionController extends ViewController<Production, ProductionDao, ProductionService> {

	@Autowired
	AppConfig appConf;

	public static final Logger logger = Logger.getLogger(ProductionController.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ProductionService getService() {

		return new ProductionService(getCurrentContext());
	}

	/**
	 * @param stockdisposal
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute StockDisposal stockdisposal, HttpSession session, Model model)
			throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "PRD_DLY");
		model.addAttribute("permission", permission);
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("prod_hdr", "prod_hdr");
		model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("Production", true);
		model.addAttribute("dailyproductionclass", true);

		return "/production/list";
	}

	@RequestMapping(value = "/listP")
	public String getListProcess(@ModelAttribute StockDisposal stockdisposal, HttpSession session, Model model)
			throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "PRD_PROCESS");
		model.addAttribute("permission", permission);
		// String CounterWithPrefix=counterService.getNextCounterwithPrefix("prod_hdr",
		// "prod_hdr");
		// model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("Production", true);
		model.addAttribute("productionprocessingclass", true);

		return "/prodprocess/list";
	}

	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response, Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("prod_hdr", "prod_hdr");

		response.getWriter().print(CounterWithPrefix);
	}

	@RequestMapping(value = "/getStkrqstCounterPrefix")
	public void getStkrqstCounterPrefix(HttpServletResponse response, Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_out_hdr", "stock_out_hdr");

		response.getWriter().print(CounterWithPrefix);
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ItemMasterService itemmasterservice = new ItemMasterService(getCurrentContext());
		final ProductionDao dailyPlanningDao = new ProductionDao(getCurrentContext());
		final ProductionService planningService = new ProductionService(getCurrentContext());
		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();

		JsonArray inProductionItemsData = itemmasterservice.getJsonArray();
		JsonArray itemsData = itemmasterservice.getJsonArray();
		JsonArray salesOrders = dailyPlanningDao.getSalesOrder();
		JsonArray costData = dailyPlanningDao.getMaterialAndOtherCost();
		JsonArray itm = itemMasterBomService.getItemMastersRowJson();

		jsonResponse.add("itemsData", itemsData);
		jsonResponse.add("costData", costData);
		jsonResponse.add("itmData", itm);
		jsonResponse.add("salesOrders", salesOrders);
		jsonResponse.add("inProductionItemsData", inProductionItemsData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "dropdownData")
	public void getDropdownJsonData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		// final ProductionDao productionDao=new ProductionDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();

		JsonArray shops = planningDao.getCustomerDataAsJson();
		JsonArray customer = productionDao.getCustomerIdsAsJson();
		JsonArray departments = productionDao.getDepList();

		jsonResponse.add("shops", shops);
		jsonResponse.add("customer", customer);
		jsonResponse.add("departments", departments);

		// jsonResponse.add("balanceByDate", balanceDataByDate);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "dropdownprocess")
	public void getDropdownJsonDataProcess(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());
		// final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		final TimeslotService timeslotService = new TimeslotService(getCurrentContext());
		// final ItemMasterService itemmasterservice = new
		// ItemMasterService(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();
		JsonArray customer = productionDao.getAllCustomerIds();
		JsonArray timeSlot = timeslotService.getMastersRowJson();
		JsonArray itm = itemMasterBomService.getItemMastersRowJson();
		JsonArray departments = productionDao.getDepList();
		JsonArray shop = productionDao.getShopList();

		jsonResponse.add("customer", customer);
		jsonResponse.add("timeSlot", timeSlot);
		jsonResponse.add("departments", departments);
		jsonResponse.add("itmData", itm);
		jsonResponse.add("shop", shop);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getCurrStock")
	public void getCurrStock(@ModelAttribute StockOut stockOut, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		final StockOutService stockOutService = new StockOutService(getCurrentContext());
		Double currstock = 0.0;
		Double pendingStock = 0.0;

		String itemId = request.getParameter("itemId");
		String current_date = request.getParameter("current_date");
		String department_id = request.getParameter("department_id");
		currstock = stockOutService.getCurrstock(itemId, current_date, department_id);

		String des_department_id = request.getParameter("desDepartmentId");

		pendingStock = stockOutService.getPendingStockByDeptAndItem(itemId, des_department_id);

		List<String> resulData = new ArrayList<String>();

		// resulData.add(pendingStock.toString());
		resulData.add(currstock.toString());

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(resulData);

	}

	@RequestMapping(value = "getUpdateRate")
	public void getUpdateRate(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();

		JsonArray itm = itemMasterBomService.getItemMastersRowJson();

		jsonResponse.add("itmsData", itm);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getShopWiseSplitData")
	public void getShopWiseSplitData(HttpServletRequest request, @RequestParam(value = "data") String completeData,
			HttpServletResponse response, Model model, HttpSession session) throws Exception {

		final ProductionDao planningService = new ProductionDao(getCurrentContext());
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		Integer stockId = Integer.parseInt(request.getParameter("stkId"));
		String date = request.getParameter("date");
		JsonObject jsonResponse = new JsonObject();
		JsonArray orderSplitData = planningService.getShopWiseOrderData(stockId, date, session);
		JsonArray currentDayPrevBalance = null;

		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		String orderIdList = gson.toJson(jobject.get("orderData1"));
		JsonArray orderListData = (JsonArray) parser1.parse(orderIdList);

		String orderIds = "";
		if (orderListData.size() != 0) {
			for (int i = 0; i < orderListData.size(); i++) {
				JsonObject json = (JsonObject) orderListData.get(i);
				orderIds += (orderIds.isEmpty() ? " " : ",") + json.get("order_id").getAsInt();
			}
			currentDayPrevBalance = planningService.getCurrentDayPreviousBalance(orderIds, stockId, date, session);
		}

		jsonResponse.add("orderSplitData", orderSplitData);
		jsonResponse.add("currentdayPrevBalance", currentDayPrevBalance);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getShopWiseSplitDataShopWise")
	public void getShopWiseSplitDataShopWise(HttpServletRequest request,
			@RequestParam(value = "data") String completeData, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {

		final ProductionDao planningService = new ProductionDao(getCurrentContext());
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		Integer stockId = Integer.parseInt(request.getParameter("stkId"));
		String date = request.getParameter("productionDate");
		String prod_date = request.getParameter("prod_date");

		Integer shopId = Integer.parseInt(request.getParameter("customerID"));

		JsonObject jsonResponse = new JsonObject();
		JsonArray orderSplitData = planningService.getShopWiseOrderDataShopwise(stockId, shopId, date, prod_date,
				session);
		JsonArray currentDayPrevBalance = null;

		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		String orderIdList = gson.toJson(jobject.get("orderData1"));
		JsonArray orderListData = (JsonArray) parser1.parse(orderIdList);

		String orderIds = "";
		if (orderListData.size() != 0) {
			for (int i = 0; i < orderListData.size(); i++) {
				JsonObject json = (JsonObject) orderListData.get(i);
				orderIds += (orderIds.isEmpty() ? " " : ",") + json.get("order_id").getAsInt();
			}
			currentDayPrevBalance = planningService.getCurrentDayPreviousBalance(orderIds, stockId, date, session);
		}

		jsonResponse.add("orderSplitData", orderSplitData);
		jsonResponse.add("currentdayPrevBalance", currentDayPrevBalance);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping("depList")
	public void getDepartmentList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ProductionDao planningDao = new ProductionDao(getCurrentContext());

		JsonArray data = planningDao.getDepList();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("data", data);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getItemRates")
	public void getItemRates(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		try {
			final ProductionDao planningDao = new ProductionDao(getCurrentContext());
			JsonArray rateDetails = planningDao.getItemRates();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.add("rateDetails", rateDetails);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getItemRates in" + this + Throwables.getStackTraceAsString(e));
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "stockRegJsonData")
	public void getStockregJsonData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final StockDisposalDetailService stockDisposalDetailService = new StockDisposalDetailService(
				getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemStockService itemstock = new ItemStockService(getCurrentContext());

		JsonArray stockDispDtlData = stockDisposalDetailService.getJsonArray();
		JsonArray stockRegDtlData = stockRegisterDetailService.getJsonArray();
		JsonArray stockRegData = stockRegisterService.getJsonArray();
		JsonArray stockItmBatchData = itemMasterBatchService.getJsonArray();
		JsonArray stockItmBatchData1 = itemstock.getJsonArray();
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stockDispDtlData", stockDispDtlData);
		jsonResponse.add("stockRegDtlData", stockRegDtlData);
		jsonResponse.add("stockRegData", stockRegData);
		jsonResponse.add("stockItmBatchData", stockItmBatchData);
		jsonResponse.add("stockItmBatchData1", stockItmBatchData1);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#setModelItem
	 * (javax.servlet.http.HttpServletRequest,
	 * com.indocosmo.mrp.web.core.base.model.GeneralModelBase)
	 */
	@Override
	protected Production setModelItem(HttpServletRequest request, Production item) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {

			Column annotationColumn = (Column) field.getDeclaredAnnotation(Column.class);

			if (annotationColumn != null) {

				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);

				if (fieldName.equals("ref_no")) {
					if (request.getParameter("id") == null) {
						valueString = counterService.getNextCounterwithPrefix("stock_disposal_hdr",
								"stock_disposal_hdr");
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

	@RequestMapping(value = "getProductionOrderData")
	public void getProductionOrderData(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {
		JsonArray orderArray;
		JsonArray orderHdrIds;
		JsonArray is_pending;

		String[] currentDate = request.getParameterValues("productionDate");
		final ProductionService planningService = new ProductionService(getCurrentContext());
		orderHdrIds = planningService.getOnProductionIds(currentDate[0]);
		is_pending = planningService.getPendingStockItem(currentDate[0]);

		String orderIds = "";
		if (orderHdrIds.size() != 0) {
			for (int i = 0; i < orderHdrIds.size(); i++) {
				JsonObject json = (JsonObject) orderHdrIds.get(i);
				orderIds += (orderIds.isEmpty() ? " " : ",") + json.get("order_id").getAsInt();
			}

		}

		orderArray = planningService.getProductionOrderData(currentDate[0], orderIds, session);

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("orderArray", orderArray);
		jsonResponse.add("orderHdrIds", orderHdrIds);
		jsonResponse.add("is_pending", is_pending);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "getProductionOrderDataShopwise")
	public void getProductionOrderDataShopwise(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {
		JsonArray orderArray;
		JsonArray orderHdrIds;

		String productionuptoDate = request.getParameter("productionDate");
		String custId = request.getParameter("customerID");
		String prod_date = request.getParameter("prod_date");
		Integer request_type = Integer.parseInt(request.getParameter("request_type"));

		final ProductionService productionService = new ProductionService(getCurrentContext());
		orderHdrIds = productionService.getOnProductionIdsShopwise(prod_date, productionuptoDate, custId);

		JsonArray is_pending;
		is_pending = productionService.getPending(prod_date, custId);
		String orderIds = "";
		if (orderHdrIds.size() != 0) {
			for (int i = 0; i < orderHdrIds.size(); i++) {
				JsonObject json = (JsonObject) orderHdrIds.get(i);
				orderIds += (orderIds.isEmpty() ? " " : ",") + json.get("order_id").getAsInt();
			}

		}

		orderArray = productionService.getProductionOrderDataShopwise(prod_date, productionuptoDate, custId,
				request_type, session);

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("orderArray", orderArray);
		jsonResponse.add("orderHdrIds", orderHdrIds);
		jsonResponse.add("is_pending", is_pending);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	// production process
	@RequestMapping(value = "totalItemOrders")
	public void getTotalItemOrders(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {

		JsonArray totalItemList;
		// JsonArray orderHdrIds;
		String custId = request.getParameter("customerID");
		String delevery_date = request.getParameter("delevery_date");
		String deptId = request.getParameter("departmentID");
		String checkedItemArray = "";

		final ProductionService productionService = new ProductionService(getCurrentContext());
		// orderHdrIds=productionService.getOnProductionIdsShopwise(prod_date,productionuptoDate,custId);

		//// JsonArray is_pending;
		// is_pending=productionService.getPending(prod_date,custId);
		// String orderIds="";
		/*
		 * if(orderHdrIds.size() !=0) { for(int i=0;i<orderHdrIds.size();i++) {
		 * JsonObject json=(JsonObject)orderHdrIds.get(i); orderIds+=(orderIds.isEmpty()
		 * ? " ":",")+json.get("order_id").getAsInt(); }
		 * 
		 * }
		 */
		totalItemList = productionService.getVerifiedTotalItems(delevery_date, custId, deptId, checkedItemArray, "");

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("totalItemList", totalItemList);
		/*
		 * jsonResponse.add("orderHdrIds",orderHdrIds); jsonResponse.add("is_pending",
		 * is_pending);
		 */
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "orderDetailsWithDept")
	public void getTotalItemOrdersWithDepartment(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {

		JsonArray totalItemListData;
		// JsonArray orderHdrIds;
		String custId = request.getParameter("customerID");
		String delevery_date = request.getParameter("delevery_date");
		String deptId = request.getParameter("departmentID");
		String checkedItemArray = "";

		final ProductionService productionService = new ProductionService(getCurrentContext());
		totalItemListData = productionService.getVerifiedTotalItemsWithDepartment(delevery_date, custId, deptId,
				checkedItemArray, "");

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("totalItemListData", totalItemListData);
		/*
		 * jsonResponse.add("orderHdrIds",orderHdrIds); jsonResponse.add("is_pending",
		 * is_pending);
		 */
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "/saveDailyProduction", method = RequestMethod.POST)
	public void save(@RequestBody String productionItems, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		final String DB_TXN_POINT = "DailyPlanningService";
		Production dailyProd = null;

		final ProductionService dailyService = new ProductionService(getCurrentContext());
		final ProductionDtlService productionDetailService = new ProductionDtlService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());

		Integer dailyprodview = (Integer) session.getAttribute("dailyprodview");
		Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		Integer user_id = user.getId();

		Integer custId = Integer.parseInt(request.getParameter("custId"));
		dailyService.beginTrans(DB_TXN_POINT);
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {

			dailyProd = mapper.readValue(productionItems, Production.class);
			// dailyProd.setDepartmentId(department_id);
			if (dailyProd.getProd_time() == null || dailyProd.getProd_time().equals("")) {
				dailyProd.setProd_time(getProdTime(dailyProd.getSchedDate()));
			}

			String proddtl = dailyProd.getDailyPlanningDetailLists();
			String orderIds = dailyProd.getOrderHdrsIdsList();
			JsonParser parser = new JsonParser();
			JsonArray productionDtlList = (JsonArray) parser.parse(proddtl);
			JsonArray orderIdsList = (JsonArray) parser.parse(orderIds);

			if (dailyProd.getId() == null) {
				dailyProd.setProd_no(counterService.getNextCounterwithPrefix("prod_hdr", "prod_hdr"));
			}
			if (dailyprodview == 1) {
				dailyProd.setShop_id(custId);
			}
			dailyProd = dailyService.saveProdItem(dailyProd);

			final ArrayList<ProductionDetail> productionItemsList = new ArrayList<ProductionDetail>();

			for (int i = 0; i < productionDtlList.size(); i++) {
				final ProductionDetail productionItem = new ProductionDetail();
				JsonObject json = (JsonObject) productionDtlList.get(i);

				productionItem.setProdHdrId(dailyProd.getId());
				productionItem.setStockItemId(json.get("stock_item_id").getAsInt());
				productionItem.setStockItemCode(json.get("stock_item_code").getAsString());
				productionItem.setDepartment_id(json.get("department_id").getAsInt());
				productionItem.setScheduleQty((json.get("schedule_qty").getAsString() != null
						&& json.get("schedule_qty").getAsString().length() != 0)
								? json.get("schedule_qty").getAsDouble()
								: 0.00);
				productionItem.setOrderQty((json.get("order_qty").getAsString() != null
						&& json.get("order_qty").getAsString().length() != 0) ? json.get("order_qty").getAsDouble()
								: 0.00);
				productionItem.setProdQty(
						(json.get("prod_qty").getAsString() != null && json.get("prod_qty").getAsString().length() != 0)
								? json.get("prod_qty").getAsDouble()
								: 0.00);
				/*
				 * productionItem.setItemRate((json.get("itemRate").getAsString() != null &&
				 * json.get("itemRate").getAsString().length() != 0) ?
				 * json.get("itemRate").getAsDouble() : 0.00);
				 */
				productionItem.setSales_price(
						(json.get("saleRate").getAsString() != null && json.get("saleRate").getAsString().length() != 0)
								? json.get("saleRate").getAsDouble()
								: 0.00);
				productionItem.setMaterial_cost((json.get("itemMaterialCost").getAsString() != null
						&& json.get("itemMaterialCost").getAsString().length() != 0)
								? json.get("itemMaterialCost").getAsDouble()
								: 0.00);
				productionItem.setOther_cost((json.get("otherCost").getAsString() != null
						&& json.get("otherCost").getAsString().length() != 0) ? json.get("otherCost").getAsDouble()
								: 0.00);
				productionItem.setTotal_cost((json.get("totalCost").getAsString() != null
						&& json.get("totalCost").getAsString().length() != 0) ? json.get("totalCost").getAsDouble()
								: 0.00);
				productionItem.setDamage_qty((json.get("damageqty").getAsString() != null
						&& json.get("damageqty").getAsString().length() != 0) ? json.get("damageqty").getAsDouble()
								: 0.00);

				/*
				 * productionItem.setTotalAmt((json.get("totalAmt").getAsString() != null &&
				 * json.get("totalAmt").getAsString().length() != 0) ?
				 * json.get("totalAmt").getAsDouble() : 0.00);
				 */
				productionItem
						.setRemarks((json.get("remarks") != null && json.get("remarks").getAsString().length() != 0)
								? json.get("remarks").getAsString()
								: "");
				productionItem.setUser_id(user_id);
				productionItem.setProdnHdrId(json.get("prodnHdrId").getAsInt());
				productionItemsList.add(productionItem);

			}
			String orderIdsString = "";
			if (orderIdsList.size() != 0) {
				for (int i = 0; i < orderIdsList.size(); i++) {
					JsonObject json = (JsonObject) orderIdsList.get(i);
					orderIdsString += (orderIdsString.isEmpty() ? " " : ",") + json.get("order_id").getAsInt();
				}

				dailyService.updateOrderHdr(dailyProd.getId(), orderIdsString);
			}
			productionDetailService.save(productionItemsList);

			dailyService.endTrans(DB_TXN_POINT);
			JsonObject obj = new JsonObject();
			Gson gson = new Gson();
			obj.addProperty("prodId", dailyProd.getId());

			response.getWriter().print(obj);

		} catch (Exception e) {
			response.getWriter().print("0");
			dailyService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  save in" + this + Throwables.getStackTraceAsString(e));
		}
	}

	/**
	 * @param stockDisp
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getProductionDtl")
	public void getProductionDtlData(@ModelAttribute Production prodHdr, HttpServletResponse response,
			HttpSession session) throws Exception {

		final ProductionService prodService = new ProductionService(getCurrentContext());
		JsonArray prodDtl = prodService.getProductionDtlData(prodHdr);
		JsonArray orderHdrIds = prodService.getOrderHdrIdsByProdId(prodHdr.getId());
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("prodDtl", prodDtl);
		jsonResponse.add("orderHdrIds", orderHdrIds);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/getBomAndProdData")
	public void getDataToEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		JsonObject jsonResponse = new JsonObject();
		final ItemMasterBomService itemBomService = new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService prodCostService = new ItemProdCostService(getCurrentContext());
		final ProductionService dailyPlanService = new ProductionService(getCurrentContext());
		// final SyncQueueService syncQueueService=new
		// SyncQueueService(getCurrentContext());
		try {

			JsonArray bom = itemBomService.getBomJsonArray(itemId, 0);

			JsonArray prodcost = prodCostService.getProdCostArray(itemId);
			JsonArray bomQty = dailyPlanService.getBomQty(itemId);

			jsonResponse.add("bom", bom);
			jsonResponse.add("prd_costdata", prodcost);
			jsonResponse.add("bomQty", bomQty);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getBomAndProdData in" + this + Throwables.getStackTraceAsString(e));

		}
	}

	@RequestMapping(value = "/updateBomData")
	protected @ResponseBody void updateBomAndCost(MultipartHttpServletRequest request, HttpSession session,
			@RequestParam(value = "data", required = true) String completeData, HttpServletResponse response)
			throws Exception {

		Integer version = (Integer) session.getAttribute("version");
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		SyncQueue syncQueue = new SyncQueue();
		ObjectMapper mapper1 = new ObjectMapper();
		ItemMasterBom bom = null;
		ItemProdCost itmProdCost = null;
		SyncQueue syncQueueItem = null;
		final String DB_TXN_POINT = "dailyService";
		Boolean flag2 = true;
		Boolean prodCostFlag = true;
		Integer stockId = Integer.parseInt(request.getParameter("stkId"));
		Double bomQty = request.getParameter("bomQty") == "" ? 0.0 : Double.parseDouble(request.getParameter("bomQty"));

		final ProductionService dailyService = new ProductionService(getCurrentContext());
		ArrayList<ItemMasterBom> bomArrayList = new ArrayList<ItemMasterBom>();
		final ItemMasterBomService itemBomService = new ItemMasterBomService(getCurrentContext());
		final ItemMasterBomDao itemMasterBomDao = new ItemMasterBomDao(getCurrentContext());
		final SyncQueueService syncQueueService = new SyncQueueService(getCurrentContext());
		final ItemProdCostService prodCostService = new ItemProdCostService(getCurrentContext());
		final ItemProdCostDao itemProdCostDao = new ItemProdCostDao(getCurrentContext());

		final ArrayList<SyncQueue> SyncQueueArrayList = new ArrayList<SyncQueue>();
		ArrayList<ItemProdCost> prodCostArrayList = new ArrayList<ItemProdCost>();

		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		try {

			String bomList = gson.toJson(jobject.get("bomData1"));
			String Qutable = gson.toJson(jobject.get("Quetable"));
			String costCalcList = gson.toJson(jobject.get("prodCostList"));

			JsonArray bomListData = (JsonArray) parser1.parse(bomList);
			JsonArray prodCostData = (JsonArray) parser1.parse(costCalcList);
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);

			JsonObject obj = new JsonObject();
			dailyService.beginTrans(DB_TXN_POINT);
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

					bom.setStockItemId(stockId);
					if (json.get("id") != null && (json.get("id").getAsString()).length() != 0) {
						bom.setId(json.get("id").getAsInt());
					}
					bom.setBomItemId((json.get("bom_item_id").getAsInt()));
					bom.setQty(json.get("qty").getAsDouble());
					bom.setCost_price(json.get("unit_price").getAsDouble());
					bom.setStock_item_qty(bomQty);
					bomArrayList.add(bom);
				}
				if (flag2 == true) {
					itemBomService.save(bomArrayList);
					itemBomService.updateCostprice(bomArrayList, version);
					JsonArray bomArray1 = itemMasterBomDao.getBomJsonArray(stockId, 0);
					obj.add("bomData1", bomArray1);
				}

			}
			if (prodCostData.size() != 0) {

				for (int i = 0; i < prodCostData.size(); i++) {
					itmProdCost = new ItemProdCost();
					JsonObject json = (JsonObject) prodCostData.get(i);
					if (prodCostData.size() == 1 && json.get("id").getAsString().length() == 0
							&& json.get("prod_cost_id").getAsString().length() == 0
							&& json.get("rate").getAsString().length() == 0) {
						prodCostFlag = false;
						break;
					}

					itmProdCost.setStockItemId(stockId);
					if (json.get("id") != null && (json.get("id").getAsString()).length() != 0) {
						itmProdCost.setId(json.get("id").getAsInt());
					}
					if (json.get("isPercentage").getAsBoolean() == true) {
						itmProdCost.setIs_percentage(1);
					} else {
						itmProdCost.setIs_percentage(0);
					}
					itmProdCost.setCostcalc_param_id(json.get("prod_cost_id").getAsInt());
					itmProdCost.setRate(json.get("rate").getAsDouble());
					itmProdCost.setStock_item_qty(bomQty);

					prodCostArrayList.add(itmProdCost);
				}

				if (prodCostFlag == true) {
					prodCostService.save(prodCostArrayList);
					JsonArray prodCostArray1 = itemProdCostDao.getProdCostArray(stockId);
					obj.add("proddata1", prodCostArray1);

				}

			}
			if (flag2 == false) {
				itemBomService.deleteData(stockId);
				if (appConf.getsystemSettings().getImplementationMode()
						.getImplementationModeId() == ImplementationMode.FULL.getImplementationModeId()) {
					syncQueueService.upDateUseSp(itemMasterBomDao.getTable(), stockId);
				}
			}
			if (prodCostFlag == false) {
				prodCostService.deleteData(stockId);
				if (appConf.getsystemSettings().getImplementationMode()
						.getImplementationModeId() == ImplementationMode.FULL.getImplementationModeId()) {
					syncQueueService.upDateUseSp(itemProdCostDao.getTable(), stockId);
				}
			}
			if (appConf.getsystemSettings().getImplementationMode().getImplementationModeId() == ImplementationMode.FULL
					.getImplementationModeId()) {
				if (bomArrayList.size() != 0) {
					for (int i = 0; i < bomArrayList.size(); i++) {
						syncQueueItem = new SyncQueue();
						String tableName = itemMasterBomDao.getTable();

						String bomId = syncQueueService.fungetSynQueId(tableName,
								bomArrayList.get(i).getId().toString());
						if (bomId != "") {
							syncQueueItem.setId(Integer.parseInt(bomId));
							syncQueueItem.setCrudAction("U");
						} else {
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

					syncQueueService.save(SyncQueueArrayList);
				}
			}

			if (appConf.getsystemSettings().getImplementationMode().getImplementationModeId() == ImplementationMode.FULL
					.getImplementationModeId()) {
				if (prodCostArrayList.size() != 0) {
					for (int i = 0; i < prodCostArrayList.size(); i++) {
						syncQueueItem = new SyncQueue();
						String tableName = itemProdCostDao.getTable();

						String bomId = syncQueueService.fungetSynQueId(tableName,
								prodCostArrayList.get(i).getId().toString());
						if (bomId != "") {
							syncQueueItem.setId(Integer.parseInt(bomId));
							syncQueueItem.setCrudAction("U");
						} else {
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

					syncQueueService.save(SyncQueueArrayList);
				}
			}
			boolean isLiteVersion = appConf.getsystemSettings().getImplementationMode()
					.getImplementationModeId() == ImplementationMode.LITE.getImplementationModeId() ? true : false;
			Integer success = dailyService.updateBomQty(stockId, bomQty, isLiteVersion);
			dailyService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {
			response.getWriter().print("0");
			e.printStackTrace();
			dailyService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  updateBomAndCost in" + this + Throwables.getStackTraceAsString(e));
		}

	}

	@RequestMapping(value = "getUpdatedCost")
	public void getCostData(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		try {

			final ProductionDao planningDao = new ProductionDao(getCurrentContext());
			final ItemMasterService itemService = new ItemMasterService(getCurrentContext());

			JsonArray costData = planningDao.getMaterialAndOtherCost();
			JsonObject jsonResponse = new JsonObject();

			jsonResponse.add("costData", costData);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getUpdatedCost in" + this + Throwables.getStackTraceAsString(e));
		}
	}

	@RequestMapping(value = "/finalize", method = RequestMethod.POST)
	public void finalize(@RequestBody String productionItems, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		final String DB_TXN_POINT = "DailyPlanningService";

		final ProductionService dailyService = new ProductionService(getCurrentContext());
		final ProductionDtlService productionDetailService = new ProductionDtlService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		StockRegister stockReg = new StockRegister();
		Production dailyProd = null;
		Integer error = 0;

		final String id = request.getParameter("id");
		int dest_department_id = enumDepartmentType.DEP_PRODUCTION.getenumDepartmentTypeId();
		int roundVal = systemSettings.getDecimal_places();

		dailyService.beginTrans(DB_TXN_POINT);

		Integer sourc_cmpny_id = (Integer) session.getAttribute("COMPANY_ID");

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			dailyProd = mapper.readValue(productionItems, Production.class);
			dailyProd.setId(Integer.parseInt(id));
			dailyService.updateStatus(Integer.parseInt(id));
			JsonArray prodDtlList = productionDetailService.getTableRowsAsJson(Integer.parseInt(id));

			stockReg.setExtRefNo(dailyProd.getProd_no());
			stockReg.setExtRefId(Integer.parseInt(id));
			stockReg.setDepartmentId(dest_department_id);
			stockReg.setTxnDate(dailyProd.getSchedDate());
			stockReg.setTransType(transactionType.PRODUCTION.gettransactionTypeId());
			stockReg.setSource_shop_id(sourc_cmpny_id);
			stockReg.setDestination_shop_id(sourc_cmpny_id);
			stockReg = stockRegisterService.saveStockRegData(stockReg);

			for (JsonElement pa : prodDtlList) {
				JsonObject json = pa.getAsJsonObject();

				error = dailyService.updateProDBomItems(json.get("id").getAsInt(), roundVal,
						json.get("department_id").getAsInt(), dest_department_id, stockReg.getId());
				if (error == 1)
					break;

			}
			if (error == 1) {
				response.getWriter().print("0");
				logger.error("Method: finalize in" + this);
				dailyService.rollbackTrans(DB_TXN_POINT);
			} else {
				dailyService.endTrans(DB_TXN_POINT);
				response.getWriter().print("1");
			}
		} catch (Exception e) {

			response.getWriter().print("0");
			logger.error("Method: finalize in" + this + Throwables.getStackTraceAsString(e));
			dailyService.rollbackTrans(DB_TXN_POINT);
			throw new CustomException();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		final ProductionService productionService = new ProductionService(getCurrentContext());
		final ProductionDtlService prodDtlService = new ProductionDtlService(getCurrentContext());

		final String id = request.getParameter("id");
		final String prod_no = request.getParameter("prod_no");
		final String DB_TXN_POINT = "dailyPlangService";
		productionService.beginTrans(DB_TXN_POINT);
		try {

			Integer is_deleted = 0;
			is_deleted = productionService.delete(id);

			if (is_deleted == 0) {
				response.getWriter().print("0");
			} else {

				ProductionDetail productionDtl = new ProductionDetail();
				productionDtl.setProdHdrId(Integer.parseInt(id));
				prodDtlService.delete(productionDtl);
				productionService.upDateProductionOrderData(Integer.parseInt(id));
				response.getWriter().print(1);
				// response.getWriter().print("1");
			}

			productionService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {
			response.getWriter().print(0);
			productionService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}

	private String getProdTime(String prodDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date prd_date = (Date) formatter.parse(prodDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(prd_date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currentdate = sdf.format(cal.getTime());
		String hh = currentdate.split(":")[0];
		String mm = currentdate.split(":")[1];
		String ss = currentdate.split(":")[2];
		calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
		calendar.add(Calendar.MINUTE, Integer.parseInt(mm));
		calendar.add(Calendar.SECOND, Integer.parseInt(ss));
		Date newDate = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String finalString = dateFormat.format(newDate);
		return finalString;
	}

	@RequestMapping(value = "/updateStatusProcess", method = RequestMethod.POST)
	public void updateStatusProcess(@RequestBody String itemDtl, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		final ProductionService productionService = new ProductionService(getCurrentContext());
		final ProductionDao productionDao = new ProductionDao(getCurrentContext());

		final String DB_TXN_POINT = "productionService";
System.out.println("request======================="+request.toString());
System.out.println("customerID======================="+request.getParameter("customerID"));
System.out.println("itemDtl======================="+itemDtl);
		productionService.beginTrans(DB_TXN_POINT);

		String close_date = request.getParameter("close_date");
		String customerID = request.getParameter("customerID");
		String prod_date = request.getParameter("prod_date");

		try {

			JsonParser parser = new JsonParser();
			JsonArray itemDtllList = (JsonArray) parser.parse(itemDtl);

			if (itemDtllList.size() != 0) {

				for (int i = 0; i < itemDtllList.size(); i++) {
					JsonObject json = (JsonObject) itemDtllList.get(i);
					if (json.get("check_val").getAsBoolean() == true) {
						Integer itemid = json.get("sale_item_id").getAsInt();
						Integer timesltid = json.get("closing_time_slot_id").getAsInt();

						productionDao.updateStatusToProduction(itemid, timesltid, close_date, customerID, prod_date);
					}
				}

			}

			productionService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		} catch (Exception e) {

			response.getWriter().print("0");
			productionService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: updateStatusProcess" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	@RequestMapping(value = "getShopDataProcess")
	public void getBomConsumption(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		final ProductionService productionService = new ProductionService(getCurrentContext());
		String closing_date = request.getParameter("closing_date");
		String customer_id = request.getParameter("customer_id");
		String stock_item_id = request.getParameter("stock_item_id");
		String time_slot = request.getParameter("time_slot_id");
		JsonObject jsonResponse = new JsonObject();

		JsonArray shopOrder = productionService.getShopOrderProcess(closing_date, customer_id, stock_item_id,
				time_slot);

		jsonResponse.add("shopOrder", shopOrder);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "ProductionList")
	public void getProductionList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ProductionService productionService = new ProductionService(getCurrentContext());
		String production_date = request.getParameter("production_date");
		String time_slot_id = request.getParameter("time_slot_id");
		String department_id = request.getParameter("departmentId");
		String stock_item_id = request.getParameter("selected_item_id");

		JsonObject jsonResponse = new JsonObject();
		JsonArray ProductionList = new JsonArray();
		// JsonArray ProductionList =
		// productionService.getProductionList(production_date,time_slot_id,department_id,"");
		ProductionList = productionService.getProductionList(stock_item_id, time_slot_id, department_id,
				production_date);

		jsonResponse.add("ProductionList", ProductionList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "/saveProductionProcess", method = RequestMethod.POST)
	public void saveProduction(@RequestBody String productionItems, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		System.out.println("inside save production process");
		System.out.println("productionItems================================>"+productionItems);
		final String DB_TXN_POINT = "DailyPlanningService";
		Production dailyProd = null;

		final ProductionService dailyService = new ProductionService(getCurrentContext());
		final ProductionDtlService productionDetailService = new ProductionDtlService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		StockRegister stockReg = new StockRegister();

		Integer error = 0;
		Integer sourc_cmpny_id = (Integer) session.getAttribute("COMPANY_ID");

		int dest_department_id = enumDepartmentType.DEP_PRODUCTION.getenumDepartmentTypeId();
		int roundVal = systemSettings.getDecimal_places();

		Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		Integer user_id = user.getId();

		Integer timeslot_id = Integer.parseInt(request.getParameter("time_slot"));

		dailyService.beginTrans(DB_TXN_POINT);
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			dailyProd = mapper.readValue(productionItems, Production.class);
			if (dailyProd.getProd_time() == null || dailyProd.getProd_time().equals("")) {
				dailyProd.setProd_time(getProdTime(dailyProd.getSchedDate()));
			}

			String proddtl = dailyProd.getDailyPlanningDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray productionDtlList = (JsonArray) parser.parse(proddtl);

			if (dailyProd.getId() == null) {
				dailyProd.setProd_no(counterService.getNextCounterwithPrefix("prod_hdr", "prod_hdr"));
			}

			// save into mrp_prod_hdr
			dailyProd = dailyService.saveProdItem(dailyProd);

			stockReg.setExtRefNo(dailyProd.getProd_no());
			stockReg.setExtRefId(dailyProd.getId());
			stockReg.setDepartmentId(dailyProd.getDepartment_id());
			stockReg.setTxnDate(dailyProd.getSchedDate());
			stockReg.setTransType(transactionType.PRODUCTION.gettransactionTypeId());
			stockReg.setSource_shop_id(sourc_cmpny_id);
			stockReg.setDestination_shop_id(sourc_cmpny_id);
			stockReg = stockRegisterService.saveStockRegData(stockReg);

			final ArrayList<ProductionDetail> productionItemsList = new ArrayList<ProductionDetail>();
			for (int i = 0; i < productionDtlList.size(); i++) {
				final ProductionDetail productionItem = new ProductionDetail();
				JsonObject json = (JsonObject) productionDtlList.get(i);
				System.out.println("jsonObject==================>"+json.toString());

				productionItem.setProdHdrId(dailyProd.getId());
				productionItem.setStockItemId(json.get("stock_item_id").getAsInt());
				productionItem.setStockItemCode(json.get("stock_item_code").getAsString());
				productionItem.setDepartment_id(dailyProd.getDepartment_id());

				productionItem.setScheduleQty((json.get("schedule_qty").getAsString() != null
						&& json.get("schedule_qty").getAsString().length() != 0)
								? json.get("schedule_qty").getAsDouble()
								: 0.00);

				productionItem.setOrderQty((json.get("order_qty").getAsString() != null
						&& json.get("order_qty").getAsString().length() != 0) ? json.get("order_qty").getAsDouble()
								: 0.00);

				productionItem.setProdQty(
						(json.get("prod_qty").getAsString() != null && json.get("prod_qty").getAsString().length() != 0)
								? json.get("prod_qty").getAsDouble()
								: 0.00);

				/*
				 * productionItem.setSales_price((json.get("saleRate").getAsString() != null &&
				 * json.get("saleRate").getAsString().length() != 0) ?
				 * json.get("saleRate").getAsDouble() : 0.00);
				 */

				productionItem.setMaterial_cost((json.get("itemMaterialCost").getAsString() != null
						&& json.get("itemMaterialCost").getAsString().length() != 0)
								? json.get("itemMaterialCost").getAsDouble()
								: 0.00);

				productionItem.setOther_cost((json.get("otherCost").getAsString() != null
						&& json.get("otherCost").getAsString().length() != 0) ? json.get("otherCost").getAsDouble()
								: 0.00);

				productionItem.setTotal_cost((json.get("totalCost").getAsString() != null
						&& json.get("totalCost").getAsString().length() != 0) ? json.get("totalCost").getAsDouble()
								: 0.00);

				productionItem.setDamage_qty((json.get("damageqty").getAsString() != null
						&& json.get("damageqty").getAsString().length() != 0) ? json.get("damageqty").getAsDouble()
								: 0.00);

				productionItem
						.setRemarks((json.get("remarks") != null && json.get("remarks").getAsString().length() != 0)
								? json.get("remarks").getAsString()
								: "");
				productionItem.setUser_id(user_id);
				productionItemsList.add(productionItem);
				System.out.println("json details==================prod_date"+dailyProd.getSchedDate());
				System.out.println("stock_item_id========"+json.get("stock_item_id").getAsInt());			
				System.out.println("prod_qty======"+json.get("prod_qty").getAsString());
				
				dailyService.updateIssuedQty(dailyProd.getSchedDate(), json.get("prod_qty").getAsString(),json.get("stock_item_id").getAsInt());
			}
			// save into mrp_prod_dtl
			productionDetailService.save(productionItemsList);

			// update finished status on order_dtls_booking table
			dailyService.updateOrderDtl(productionItemsList, timeslot_id, dailyProd.getSchedDate());
		

			// stock update in stockin_hdr and stock_in_dtl -- commented on 12/02/2019
			/*
			 * Integer sale_stock_in_hdr_id = 0; sale_stock_in_hdr_id =
			 * dailyService.saveIntoSalesStockin(dailyProd);
			 * dailyService.saveSaleStockInDetail(productionItemsList,sale_stock_in_hdr_id);
			 */

			// stock register updates

			for (ProductionDetail proddet : productionItemsList) {
				error = dailyService.updateProDBomItems(proddet.getId(), roundVal, proddet.getDepartment_id(),
						dailyProd.getDepartment_id(), stockReg.getId());
				if (error == 1)
					break;
			}

			if (error == 1) {
				response.getWriter().print("0");
				logger.error("Method: finalize in" + this);
				dailyService.rollbackTrans(DB_TXN_POINT);

			} else {

				dailyService.endTrans(DB_TXN_POINT);
				response.getWriter().print("1");
			}
			/*
			 * dailyService.endTrans(DB_TXN_POINT); response.getWriter().print("1");
			 */
		} catch (Exception e) {
			response.getWriter().print("0");
			dailyService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  save in" + this + Throwables.getStackTraceAsString(e));
		}
	}

	@RequestMapping(value = "orderDetailsProd")
	public void getOrderDetailsProd(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		final ProductionService productionService = new ProductionService(getCurrentContext());
		String production_date = request.getParameter("production_date");
		String time_slot_id = request.getParameter("time_slot_id");
		String stock_item_id = request.getParameter("stock_item_id");

		JsonObject jsonResponse = new JsonObject();

		JsonArray orderSplitData = productionService.getOrderDetailsProd(production_date, time_slot_id, stock_item_id);

		jsonResponse.add("orderSplitData", orderSplitData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "orderDetailsdailyprod")
	public void getOrderDetailsDailyprd(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		final ProductionService productionService = new ProductionService(getCurrentContext());

		String dtl_id = request.getParameter("dtl_id");

		JsonObject jsonResponse = new JsonObject();

		JsonArray orderSplitData = productionService.getOrderDetailsDailyprd(dtl_id);

		jsonResponse.add("orderSplitData", orderSplitData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "/reportProd")
	public ModelAndView PrintInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		String production_date = request.getParameter("production_date");
		String dept_id = request.getParameter("dept_id");
		String time_slot_id = request.getParameter("time_slot_id");

		final ProductionService productionService = new ProductionService(getCurrentContext());
		JsonArray itemData = productionService.getReportData(production_date, dept_id, time_slot_id);
		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("RAW MATERIAL REQUISITION");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		return new ModelAndView("prodprocessreportView", "prodprocessreportView", itemData);
	}

	@RequestMapping(value = "/printAndSendToProduction")
	public ModelAndView printAndSendToProduction(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		String custId = request.getParameter("customerID");
		String delevery_date = request.getParameter("delevery_date");
		String deptId = request.getParameter("departmentID");
		String oderDtlArray = request.getParameter("orderDtlID");
		String checkedItemArray = request.getParameter("itemIdArray");
		String shopName = request.getParameter("shopName");

		final ProductionService productionService = new ProductionService(getCurrentContext());
		JsonArray itemData = productionService.getVerifiedTotalItems(delevery_date, custId, deptId, checkedItemArray,
				oderDtlArray);
		// JsonArray itemData1
		// =productionService.getVerifiedTotalItems1(delevery_date,custId,deptId,checkedItemArray)
		// ;
		System.out.println("1");

		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());

			String dateFormat = currentDateFormat.getSystemDateFormat();
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(delevery_date);
			DateFormat formatter = new SimpleDateFormat(dateFormat);
			String dateWithSystemFormat = formatter.format(date1);
			// Date headDate = new SimpleDateFormat(dateFormat).parse();

			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName(" VERIFIED ORDERS  From " + shopName + " - " + dateWithSystemFormat);
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());

			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}
		return new ModelAndView("printAndSendToProductionView", "printAndSendToProductionView", itemData);
	}

	@RequestMapping(value = "datecheck")
	public void getdatecheck(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		final ProductionService productionService = new ProductionService(getCurrentContext());
		String production_date = request.getParameter("production_date");
		String date_Exist = productionService.getDateCheckExistPrd(production_date);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(date_Exist);
	}

// Material Requisition

	@RequestMapping(value = "/printStore")
	public ModelAndView PrintMaterialInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		String req_no = request.getParameter("req_no");
		final ProductionService productionService = new ProductionService(getCurrentContext());
		JsonArray itemData = productionService.getmaterialReportData(req_no);
		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("RAW MATERIAL REQUISITION");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}
		return new ModelAndView("prodprocessreportView", "prodprocessreportView", itemData);

	}

	@RequestMapping(value = "getPendingMaterial")
	public void getPendingMaterial(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws Exception {

		JsonArray pendingList;

		String department = request.getParameter("department");
		final ProductionService planningService = new ProductionService(getCurrentContext());

		pendingList = planningService.getPendingMaterial(department);

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("pendingList", pendingList);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "getProductDeptName")
	public void getDeptNameDetails(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();
		JsonArray deptDetails = productionDao.getDeptDetails();
		JsonArray departments = productionDao.getDepList();

		jsonResponse.add("departmentDetils", deptDetails);
		jsonResponse.add("depList", departments);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "departmrntDataLoad")
	public void getDepartmentDropdownJsonData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();
		JsonArray departments = productionDao.getDepList();

		jsonResponse.add("departments", departments);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "markFinishedPrint")
	public ModelAndView getMarkFinishedReport(Model model, HttpServletResponse response, HttpServletRequest request,
			HttpSession session) throws Exception {
		final ProductionService productionService = new ProductionService(getCurrentContext());
//		String production_date = request.getParameter("production_date");
//		String time_slot_id = request.getParameter("time_slot_id");
//		String department_id = request.getParameter("department_id");
		String stock_item_array = request.getParameter("stock_itemArray");
		System.out.println("\n" + request.getParameter("stock_itemArray") + "\n");
		JsonParser parser = new JsonParser();
		JsonArray ProductionList = null;
//		JsonArray ProductionList = productionService.getProductionList(production_date,time_slot_id,department_id,stock_item_array);		
		try {

			ProductionList = (JsonArray) parser.parse(stock_item_array);
			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("ITEMS IN PRODUCTION");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return new ModelAndView("markFinishedView", "productionListView", ProductionList);
	}

	@RequestMapping(value = "getBomListOfCurrentProduct")
	public void getBomListOfCurrentProduct(@RequestBody ArrayList<String> stockItemIdList, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		System.out.println("inside getBomListOfCurrentProduct()");
		JsonArray bomListOfCurrentProduct = null;
		JsonArray bomCurrentStockList = null;
		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();
		bomListOfCurrentProduct = productionDao.getBomListOfCurrentProduct(stockItemIdList);
		ArrayList<String> bomIdList = new ArrayList<String>();
		for (int i = 0; i < bomListOfCurrentProduct.size(); i++) {
			JsonObject jsonObj = (JsonObject) bomListOfCurrentProduct.get(i);
			String bom_item_id = (jsonObj.get("bom_item_id").toString());
			bomIdList.add(bom_item_id);
		}

		System.out.println("bomIdList:" + bomIdList);

		String department_id = request.getParameter("department_id");
		System.out.println("department_id:" + department_id);

		bomCurrentStockList = productionDao.getCurrentBomStock(bomIdList, department_id);

		jsonResponse.add("bomListOfCurrentProduct", bomListOfCurrentProduct);
		jsonResponse.add("bomCurrentStockList", bomCurrentStockList);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

}