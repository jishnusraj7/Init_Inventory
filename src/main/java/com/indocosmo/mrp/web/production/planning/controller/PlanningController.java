package com.indocosmo.mrp.web.production.planning.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.productionOrderStatusType;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.masters.customer.service.CustomersService;
import com.indocosmo.mrp.web.masters.customertypes.dao.CustomerTypeDao;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.itemcategory.service.ItemCategoryService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.shops.dao.ShopsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.timeslot.service.TimeslotService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.production.planning.bookingsummary.dao.OrderBookingDao;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;
import com.indocosmo.mrp.web.production.planning.bookingsummary.service.OrderBookingService;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;
import com.indocosmo.mrp.web.production.planning.model.Planning;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;
import com.indocosmo.mrp.web.production.planning.planningdetail.service.PlanningDetailService;
import com.indocosmo.mrp.web.production.planning.service.PlanningService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;

@Controller
@RequestMapping("/planning")
public class PlanningController extends
		ViewController<Planning, PlanningDao, PlanningService> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public PlanningService getService() {

		return new PlanningService(getCurrentContext());
	}

	/**
	 * @param uom
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Planning uom, HttpSession session,
			Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService
				.getCurrentUserPermission(session, "PRD_ORDR");

		model.addAttribute("permission", permission);
		model.addAttribute("Production",true);
		model.addAttribute("planningclass",true);

		
		return "/planning/list";
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "orderhdrDateData")
	public void getoerderDate(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model)
			throws Exception {

		String[] closing_date = request.getParameterValues("closing_date");
		String[] iscustomer = request.getParameterValues("iscustomer");

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());

		JsonArray orderhdrDateData = null;

		if (!iscustomer[0].equals("3")) {

			orderhdrDateData = planningDao.getOrderDataAsJsonByDate(
					closing_date[0], iscustomer[0], session);
		}

		JsonArray orderitemtotal = planningDao.getOrderItemTotal(closing_date[0],
				iscustomer[0], session);

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("orderhdrDateData", orderhdrDateData);
		jsonResponse.add("orderitemtotal", orderitemtotal);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/**
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response, Model model)
			throws Exception {

		final CounterService counterService = new CounterService(
				getCurrentContext());

		String CounterWithPrefix = counterService.getNextCounterwithPrefix(
				"order_hdrs_booking", "order_hdrs_booking");

		response.getWriter().print(CounterWithPrefix);
	}

	/**
	 * @param stockInDtl
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public void save(@RequestBody String productionOrder, HttpSession session,HttpServletResponse response, HttpServletRequest request)
					throws Exception 
	{
		final String DB_TXN_POINT = "planningService";
		
		System.out.println("productionOrder====================>"+productionOrder);
		System.out.println("prod request====================>"+request.toString());
		final PlanningDetailService planningDetailService = new PlanningDetailService(getCurrentContext());
		final PlanningService planningService = new PlanningService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());
		final CustomersService customersService = new CustomersService(getCurrentContext());
		final OrderBookingService orderBookngService = new OrderBookingService(getCurrentContext());
		final int HQ_ID = 0;

		Planning orderPlanning = null;
		Customers customers = null;
		planningService.beginTrans(DB_TXN_POINT);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//save to order_hdrs_booking
			orderPlanning = mapper.readValue(productionOrder, Planning.class);
			
			if(orderPlanning.getOrder_id() == null)
				orderPlanning.setOrder_no(counterService.getNextCounterwithPrefix("order_hdrs_booking", "order_hdrs_booking"));
			
			String customersList = orderPlanning.getCustomerList();
			customers = mapper.readValue(customersList, Customers.class);

			if(customers.getId()==null && customers.getCode()!=null && orderPlanning.getIs_ar_customer()==1 )
			{
				customers= customersService.saveItem(customers);
				orderPlanning.setCustomer_id(customers.getId());
				//added by udhayan for storing shop code in the order_hdrs_booking table on 26-Oct-2021
				orderPlanning.setShop_code(customers.getCode()!=null?customers.getCode():"");
			}
			
			System.out.println("shopcode========================>"+orderPlanning.getShop_code());
			
			orderPlanning = planningService.saveStockItem(orderPlanning);

			//save to order_dtls_booking
			String productionOrderdtl = orderPlanning.getPlanningDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray productionOrderdtlList = (JsonArray) parser.parse(productionOrderdtl);

			final ArrayList<PlanningDetail> planningDtlList = new ArrayList<PlanningDetail>();
			final ArrayList<OrderBoonkingSummary> orderBookingSummaryList = new ArrayList<OrderBoonkingSummary>();
			final ArrayList<OrderBoonkingSummary> orderBookingSummaryAdjustList = new ArrayList<OrderBoonkingSummary>();
			
			final CompanyService companyService = new CompanyService(getNewContext());
			Company hq = new Company();
			hq = companyService.getCompanyByID(HQ_ID);
			final ApplicationContext hqContext = getNewContext(hq);

			for (int i = 0; i < productionOrderdtlList.size(); i++) {

				final PlanningDetail productionOrderdtlItem = new PlanningDetail();
				JsonObject json = (JsonObject) productionOrderdtlList.get(i);

				productionOrderdtlItem.setOrder_id(orderPlanning.getOrder_id());
				productionOrderdtlItem.setSale_item_id(json.get("stock_item_id").getAsInt());
				productionOrderdtlItem.setSale_item_code(json.get("stock_item_code").getAsString());
				productionOrderdtlItem.setUom_code(json.get("uomcode").getAsString());
				productionOrderdtlItem.setUom_name(json.get("uomname").getAsString());
				productionOrderdtlItem.setSub_class_id(json.get("stock_item_id").getAsInt());
				productionOrderdtlItem.setSub_class_code(json.get("stock_item_code").getAsString());
				productionOrderdtlItem.setSub_class_name(json.get("stock_item_name").getAsString());
				productionOrderdtlItem.setOrder_date(json.get("dtlorder_date").getAsString());
				
				productionOrderdtlItem.setQuantity((json.get("quantity").getAsString() != null && json
						.get("quantity").getAsString().length() != 0) ? json.get("quantity").getAsDouble() : 0.00);	
				
				if((json.get("is_combo_item")).getAsString().equals(""))
					productionOrderdtlItem.setIs_combo_item(0);
				else
					productionOrderdtlItem.setIs_combo_item(json.get("is_combo_item").getAsInt());

				productionOrderdtlItem.setRemarks((json.get("dtlremarks") != null && json.get("dtlremarks").getAsString().length() != 0)
						? json.get("dtlremarks").getAsString() : "");

				productionOrderdtlItem.setStatus(1);
				planningDtlList.add(productionOrderdtlItem);

				//Save to order_booking summary
				final OrderBoonkingSummary orderSummary = new OrderBoonkingSummary();
				/*int shop_id = new ShopsDao(hqContext).getShopDataByCode(orderPlanning.getShop_code());
				if(shop_id != -1)
					orderSummary.setShop_id(shop_id);
				else
					//orderSummary.setShop_id((Integer) session.getAttribute("COMPANY_ID"));
					orderSummary.setShop_id(customers.getId());*/
				orderSummary.setShop_id(orderPlanning.getShopId1()!=null?orderPlanning.getShopId1():orderPlanning.getCustomer_id());
				System.out.println("shop_id:"+orderSummary.getShop_id());
				
				orderSummary.setShop_code(orderPlanning.getShop_code());
				orderSummary.setStock_item_id(json.get("stock_item_id").getAsInt());
				orderSummary.setTrans_date(orderPlanning.getClosing_date());		
				
				orderSummary.setOrder_qty((json.get("quantity").getAsString() != null && json.get("quantity")
						.getAsString().length() != 0) ? json.get("quantity").getAsDouble() : 0.00);				
				orderSummary.setCreated_by(orderPlanning.getCreated_by());
				orderSummary.setCreated_at(orderPlanning.getCreated_at());
				orderSummary.setUpdated_at(orderPlanning.getUpdated_at());
				orderSummary.setUpdated_by(orderPlanning.getUpdated_by());
				orderSummary.setExt_ref_id(orderPlanning.getOrder_no());
				orderSummary.setTrans_type(1);
				orderSummary.setOrder_date(orderPlanning.getOrder_date());
				orderBookingSummaryList.add(orderSummary);

				if(json.get("adjustqty").getAsString()!="" && json.get("adjustqty").getAsString()!=null 
						&& json.get("adjustqty").getAsDouble()!=0)
				{
					final OrderBoonkingSummary orderSummaryAdjust=new OrderBoonkingSummary();
					orderSummaryAdjust.setShop_id(orderPlanning.getCustomer_id());
					orderSummaryAdjust.setShop_code(orderPlanning.getShop_code());
					orderSummaryAdjust.setStock_item_id(json.get("stock_item_id").getAsInt());

					orderSummaryAdjust.setTrans_date(orderPlanning.getOrder_date());
					orderSummaryAdjust.setIssued_qty(json.get("adjustqty").getAsDouble());
					orderSummaryAdjust.setCreated_by(orderPlanning.getCreated_by());
					orderSummaryAdjust.setCreated_at(orderPlanning.getCreated_at());
					orderSummaryAdjust.setUpdated_at(orderPlanning.getUpdated_at());
					orderSummaryAdjust.setUpdated_by(orderPlanning.getUpdated_by());
					orderSummaryAdjust.setExt_ref_id(orderPlanning.getOrder_no());
					orderSummaryAdjust.setOrder_qty(0.0);
					orderSummaryAdjust.setTrans_type(3);
					orderSummaryAdjust.setOrder_date(orderPlanning.getOrder_date());
					orderBookingSummaryAdjustList.add(orderSummaryAdjust);
				}		
			}			
			orderBookngService.save(orderBookingSummaryList);
			
			if(orderBookingSummaryAdjustList.size() != 0)
				orderBookngService.save(orderBookingSummaryAdjustList);

			planningDetailService.save(planningDtlList);
			planningService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
			
		} catch (Exception e) {

			response.getWriter().print("0");
			planningService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: save" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "salesOrderData")
	public void getorderdat(HttpServletRequest request,HttpServletResponse response,
			Model model, HttpSession session) throws Exception 
	{
		String[] orderid = request.getParameterValues("orderhdrid");
		
		String[] closing_date=request.getParameterValues("closing_date");

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		final OrderBookingDao orderBookingDao=new OrderBookingDao(getCurrentContext());

		JsonArray list = planningDao.getOrderDataAsJsonByDateCat(orderid[0],
				session);
		JsonArray balanceDataByDate=orderBookingDao.getBalanceQtyByDate(closing_date[0]);
		

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("orderhdrData", list);
		jsonResponse.add("balanceByDate", balanceDataByDate);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	/**
	 * @param puchaseorder
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report")
	public ModelAndView PrintInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception 
	{
		String[] orderdate = request.getParameterValues("selectedDate");
		String[] iscustomer = request.getParameterValues("iscustomer");
		String item_cate_id=request.getParameter("item_category");
		final PlanningDao planningDao = new PlanningDao(getCurrentContext());

		JsonArray orderhdrDateData = null;
		JsonArray totalorderhdrDateData = null;

		if (!iscustomer[0].equals("3")) {

			orderhdrDateData = planningDao.getOrderDataAsJsonByDateReport(
					orderdate[0], iscustomer[0],item_cate_id, session);

			totalorderhdrDateData = planningDao.getOrderItemTotalReport(orderdate[0],
					iscustomer[0], item_cate_id,session);

		} else {

			orderhdrDateData = planningDao.getOrderItemTotalReport(orderdate[0],
					iscustomer[0], item_cate_id,session);
			totalorderhdrDateData = planningDao.getOrderItemTotalReport(orderdate[0],
					iscustomer[0], item_cate_id,session);
		}

		JsonArray orderdataList = new JsonArray();
		orderdataList.add(orderhdrDateData);
		orderdataList.add(totalorderhdrDateData);

		try {

			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("PRODUCTION ORDER REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setIs_customer(iscustomer[0]);
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}
		return new ModelAndView("prodView", "prodView", orderdataList);
	}
	
	
	@RequestMapping(value = "/reportRaw")
	public ModelAndView PrintRawInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request,HttpSession session) throws Exception 
	{
		String[] orderdate = request.getParameterValues("selectedDate");
		String[] iscustomer = request.getParameterValues("iscustomer");
		String item_cate_id=request.getParameter("item_category");

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());

		JsonArray totalorderhdrDateData = null;
		totalorderhdrDateData = planningDao.getOrderRawItemTotal(orderdate[0],iscustomer[0], item_cate_id,session);

		JsonArray orderdataList = new JsonArray();
		orderdataList.add(totalorderhdrDateData);

		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(
					getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session
					.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("RAW MATERIAL REQUEST LIST");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setIs_customer(iscustomer[0]);
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

		} catch (Exception e) {

			logger.error("Method: report in" + this
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}
		return new ModelAndView("prodViewRaw", "prodViewRaw", orderdataList);
	}
	

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getPlanningData")
	public void getPlanningData(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();

		JsonArray noforder = planningDao.getOrderDataAsJson();
		JsonArray customerorder = planningDao.getCustomerOrderNo();
		JsonArray shoporder = planningDao.getShopOrderNo();
		JsonArray orderData = new JsonArray();

		orderData = shoporder;
		orderData.addAll(customerorder);
		orderData.addAll(noforder);

		jsonResponse.add("orderDataList", orderData);
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
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final ItemCategoryService itmcatservice = new ItemCategoryService(
				getCurrentContext());
		final ItemMasterService itemmasterservice = new ItemMasterService(
				getCurrentContext());
		final OrderBookingService bookingSummaryService=new OrderBookingService(
				getCurrentContext());
		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		final OrderBookingDao orderBookingDao=new OrderBookingDao(getCurrentContext());
		final CustomerTypeDao customerTypeDao=new CustomerTypeDao(getCurrentContext());
		final TimeslotService timeslotService=new TimeslotService(getCurrentContext());
		final DepartmentDao departmentDao=new DepartmentDao(getCurrentContext());
		
		JsonObject jsonResponse = new JsonObject();

		JsonArray customerData = planningDao.getCustomerDataAsJson();
		JsonArray customerIds=planningDao.getCustomerIdsAsJson();
		JsonArray itemsData = itemmasterservice.getJsonArray();
		JsonArray itmcatData = itmcatservice.getJsonArray();
		JsonArray balanceQtyData=bookingSummaryService.getBalanceQtyData();
		JsonArray customerTypes=customerTypeDao.getCustomerTypes();
		JsonArray timeSlot=timeslotService.getMastersRowJson();
		JsonArray departmentData=departmentDao.getMastersRowJson();
		//JsonArray balanceDataByDate=orderBookingDao.getBalanceQtyByDate();

		jsonResponse.add("customerData", customerData);
		jsonResponse.add("itmcatData", itmcatData);
		jsonResponse.add("itemsData", itemsData);
		jsonResponse.add("balanceQtyData", balanceQtyData);
		jsonResponse.add("customerIds", customerIds);
		jsonResponse.add("customerTypes", customerTypes);
		jsonResponse.add("timeSlot", timeSlot);
		jsonResponse.add("departmentData", departmentData);

		//jsonResponse.add("balanceByDate", balanceDataByDate);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	@RequestMapping(value = "getcustomers")
	public void getCustomers(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();

		JsonArray customerIds=planningDao.getCustomerIdsAsJson();

		jsonResponse.add("customerIds", customerIds);

		
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
	@RequestMapping(value = "calendarViewList")
	public void getcalendarViewList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();

		JsonArray noforder = planningDao.getOrderDataAsJson();
		JsonArray customerorder = planningDao.getCustomerOrderNo();
		JsonArray shoporder = planningDao.getShopOrderNo();

		JsonArray orderData = new JsonArray();

		orderData = shoporder;
		orderData.addAll(customerorder);
		orderData.addAll(noforder);

		jsonResponse.add("orderDataList", orderData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@RequestMapping(value = "getShopBalanceData")
	public void getShopBalanceData(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model)
			throws Exception {

		String[] shopId = request.getParameterValues("selectedShop");
		String[] closing_date = request.getParameterValues("closing_date");

		final OrderBookingDao orderBokkingDao=new OrderBookingDao(getCurrentContext());
		
		JsonArray shopBalanceData = null;

		shopBalanceData=orderBokkingDao.getShopBalanceQty(shopId[0],closing_date[0],session);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("shopBalanceData", shopBalanceData);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	@RequestMapping(value = "getBalnceQty")
	public void getBalnceQtyForItem(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		

		final OrderBookingDao orderBokkingDao=new OrderBookingDao(getCurrentContext());
		
		JsonArray balanceQtyForItem = null;
        String closing_date=request.getParameter("closing_date");
		balanceQtyForItem=orderBokkingDao.getBalanceQtyDataDate(closing_date);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("balanceQtyForItem", balanceQtyForItem);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@RequestMapping(value="checkDateValidity")
	public void checkValidity(HttpServletRequest request,
			HttpServletResponse response,Model model,HttpSession session)
			throws Exception
	{
		
		String[] currentDate=request.getParameterValues("dateCurrent") ;
		boolean isValid=true;
		boolean isDateValid=true;
		final PlanningService planningService=new PlanningService(getCurrentContext());
		isValid=planningService.isOrderDateValid(currentDate[0]) ;
		isDateValid=planningService.isOrderDateValidAdd(currentDate[0]) ;
		//System.out.println(isValid); 
		//response.getWriter().print(isValid);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.addProperty("isValid", isValid);
		jsonResponse.addProperty("isDateValid", isDateValid);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		
		
	}
	
	
	@RequestMapping(value="GotoProduction")
	public void gotoProduction(@ModelAttribute Planning planning,HttpServletRequest request,
			HttpServletResponse response,Model model,HttpSession session)
			throws Exception
	{
	
		String productionIds=planning.getProductionIds() ;
		final PlanningService planningService=new PlanningService(getCurrentContext());
		try {
			
			Integer update = 0;
			update = planningService.gotoProduction(productionIds, productionOrderStatusType.ON_PRODUCTION.getProductionOrderStatusId());
			
			if (update == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				response.getWriter().print("1");
			}
		}
		catch (Exception e) {
		
			logger.error("Method: GotoProduction in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
		
	@RequestMapping(value="getDetailsplan")
	public void getDetailsplan(HttpServletRequest request,
			HttpServletResponse response,Model model,HttpSession session)
			throws Exception
	{
		
		String currentDate=request.getParameter("closingdate") ;
		
		final PlanningService planningService=new PlanningService(getCurrentContext());
		
	
		JsonObject jsonResponse = new JsonObject();

		JsonArray totalorderList = null;
		JsonArray alreadyaddorder = null;
		
		totalorderList=planningService.getOrdertotalList(currentDate);
		alreadyaddorder=planningService.getaddorderdate();
		jsonResponse.add("totalorderList", totalorderList);
		jsonResponse.add("alreadyaddorder", alreadyaddorder);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@RequestMapping(value = "getDepartments")
	public void getDepartments(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();

		JsonArray departments=planningDao.getDepartmentsAsJson();

		jsonResponse.add("departments", departments);

		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
}