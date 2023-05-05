package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.controller;

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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service.PurchaseOrderdtlService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.PurchaseOrderhdrDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.service.PurchaseOrderhdrService;

@Controller
@RequestMapping("/purchaseorderhdr")
public class PurchaseOrderhdrController extends ViewController<PO_hdr, PurchaseOrderhdrDao, PurchaseOrderhdrService> {
	
	public static final Logger logger = Logger.getLogger(PurchaseOrderhdrController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public PurchaseOrderhdrService getService() {
	
		return new PurchaseOrderhdrService(getCurrentContext());
	}
	
	/**
	 * @param PO_hdr
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PO_hdr PO_hdr , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		final CounterService counterService = new CounterService(getCurrentContext());
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_PORD");
		
		String counterWithPrefix = counterService.getNextCounterwithPrefix("po_hdr", "po_hdr");
		
		model.addAttribute("permission", permission);
		model.addAttribute("PONO", counterWithPrefix);
		model.addAttribute("Store",true);
		model.addAttribute("Purchase",true);
		model.addAttribute("poclass",true);
		
		return "/purchaseorder/list";
	}
	
	/**
	 * @param po_hdr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remotepurchaserequest/list")
	public String getremotepurchaseRequestList(@ModelAttribute PO_hdr po_hdr , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String counterWithPrefix = counterService.getNextCounterwithPrefix("po_hdr", "po_hdr");
		
		model.addAttribute("PONO", counterWithPrefix);
		
		return "/remotepurchaserequest/list";
	}
	
	/**
	 * @param po_hdr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/purchaserequest/list")
	public String getpurchaseRequestList(@ModelAttribute PO_hdr po_hdr , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String counterWithPrefix = counterService.getNextCounterwithPrefix("po_hdr", "po_hdr");
		
		model.addAttribute("PONO", counterWithPrefix);
		
		return "/purchaserequest/list";
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "pr_hdrlist")
	public void getPR_hdrList(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception {
	
		final PurchaseOrderhdrService purchaseorderService = new PurchaseOrderhdrService(getCurrentContext());
		
		List<PO_hdr> list = purchaseorderService.getPurchaseRequestList(null);
		String json = new Gson().toJson(list);
		
		response.getWriter().print(json);
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "po_hdrlist")
	public void getPO_hdrList(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception {
	
		final PurchaseOrderhdrService purchaseorderService = new PurchaseOrderhdrService(getCurrentContext());
		
		List<PO_hdr> list = purchaseorderService.getList();
		String json = new Gson().toJson(list);
		
		response.getWriter().print(json);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#setModelItem
	 * (javax.servlet.http.HttpServletRequest,
	 * com.indocosmo.mrp.web.core.base.model.GeneralModelBase) */
	@Override
	protected PO_hdr setModelItem(HttpServletRequest request , PO_hdr item) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {
			
			/**
			 * Column annotation Gets the column from bean
			 */
			Column annotationColumn = (Column) field.getDeclaredAnnotation(Column.class);
			
			if (annotationColumn != null) {
				
				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);
				
				if (fieldName.equals("po_number")) {
					if (request.getParameter("id") == null) {
						
						valueString = counterService.getNextCounterwithPrefix("po_hdr", "po_hdr");
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
	 * @param poDtl
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public void save(@RequestBody String poDtl , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		PO_hdr pohdr = null;
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		pohdr = mapper.readValue(poDtl, PO_hdr.class);
		final PurchaseOrderdtlService purchaseorderdtlservice = new PurchaseOrderdtlService(getCurrentContext());
		final String DB_TXN_POINT = "purchaseorderService";
		final PurchaseOrderhdrService purchaseorderService = new PurchaseOrderhdrService(getCurrentContext());
		
		String remoterequestDtlid = pohdr.getDeleteremoterequestdtlid();
		
		purchaseorderService.beginTrans(DB_TXN_POINT);
		
		try {
			
			pohdr = purchaseorderService.saveStockItem(pohdr);
			
			String podtl = pohdr.getPoDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray poDtlList = (JsonArray) parser.parse(podtl);
			
			final ArrayList<PO_dtl> stockInItemsList = new ArrayList<PO_dtl>();
			
			for (int i = 0; i < poDtlList.size(); i++) {
				
				final PO_dtl stockInItem = new PO_dtl();
				
				JsonObject json = (JsonObject) poDtlList.get(i);
				
				stockInItem.setDeleteremotedtlid1(remoterequestDtlid);
				stockInItem.setId((!json.get("id").isJsonNull()) ? json.get("id").getAsInt() : null);
				stockInItem.setPo_dtl_status((!json.get("po_dtl_status").isJsonNull()) ? json.get("po_dtl_status")
						.getAsInt() : null);
				stockInItem.setRemote_request_status((!json.get("remote_request_status").isJsonNull()) ? json.get(
						"remote_request_status").getAsInt() : 0);
				stockInItem.setRemote_request_hdr_id((!json.get("remote_request_hdr_id").isJsonNull()) ? json.get(
						"remote_request_hdr_id").getAsInt() : null);
				stockInItem.setRequest_dtl_id((!json.get("request_dtl_id").isJsonNull() && json.get("request_dtl_id")
						.getAsString().length() != 0) ? json.get("request_dtl_id").getAsInt() : null);
				stockInItem
						.setQty((!json.get("qty").isJsonNull() && json.get("qty").getAsString().length() != 0) ? json
								.get("qty").getAsDouble() : 0.00);
				stockInItem.setUnit_price((!json.get("unit_price").isJsonNull() && json.get("unit_price").getAsString()
						.length() != 0) ? json.get("unit_price").getAsDouble() : 0.00);
				stockInItem
						.setAmount((!json.get("amount").isJsonNull() && json.get("amount").getAsString().length() != 0) ? json
								.get("amount").getAsDouble() : 0.00);
				stockInItem.setCompany_id(json.get("company_id").getAsInt());
				stockInItem.setCompany_code(json.get("company_code").getAsString());
				stockInItem.setCompany_name(json.get("company_name").getAsString());
				stockInItem.setPo_hdr_id(pohdr.getId());
				stockInItem.setStock_item_id(json.get("stock_item_id").getAsInt());
				stockInItem.setStock_item_code(json.get("stock_item_code").getAsString());
				stockInItem.setStock_item_name(json.get("stock_item_name").getAsString());
				stockInItem.setExpected_date(json.get("expectedDate").getAsString());
				stockInItem.setIs_tax_inclusive((json.get("is_tax_inclusive").getAsBoolean()) ? 1 : 0);
				
				stockInItemsList.add(stockInItem);
			}
			
			purchaseorderdtlservice.save(stockInItemsList);
			
			purchaseorderService.endTrans(DB_TXN_POINT);
			
			response.getWriter().print("1");
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			response.getWriter().print("0");
			purchaseorderService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
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
	
		final PurchaseOrderhdrService purchaseorderService = new PurchaseOrderhdrService(getCurrentContext());
		final PurchaseOrderdtlService purchaseorderdtlservice = new PurchaseOrderdtlService(getCurrentContext());
		
		final String DB_TXN_POINT = "purchaseorderService";
		final String id = request.getParameter("id");
		
		purchaseorderService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			is_deleted = purchaseorderService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_deleted = 0;
				is_deleted = purchaseorderdtlservice.delete(id);
				response.getWriter().print("1");
			}
			
			purchaseorderService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			purchaseorderService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("po_hdr", "po_hdr");
		
		response.getWriter().print(CounterWithPrefix);
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
	
		final PurchaseOrderdtlService purchaseorderdtlservice = new PurchaseOrderdtlService(getCurrentContext());
		final SupplierService supplierservice = new SupplierService(getCurrentContext());
		final ItemMasterService itemmasterservice = new ItemMasterService(getCurrentContext());
		
		JsonArray purchaseorderdtllist = purchaseorderdtlservice.getJsonArray();
		JsonArray supplier_data = supplierservice.getMastersRowJson();
		JsonArray itemsData = itemmasterservice.getMastersRowJson();
		
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("purchaseorderdtllist", purchaseorderdtllist);
		jsonResponse.add("supplier_data", supplier_data);
		jsonResponse.add("itemsData", itemsData);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "remote_pr_request")
	public void getremote_pr_requestRowsAsJson(HttpServletRequest request , HttpServletResponse response)
			throws Exception {
	
		final PurchaseOrderhdrService purchaseorderService = new PurchaseOrderhdrService(getCurrentContext());
		JsonArray data = purchaseorderService.getemote_pr_requestList();
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("data", data);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param pohdr
	 * @param model
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/PoReport")
	public ModelAndView PrintInvoice(@ModelAttribute PO_hdr pohdr , Model model , HttpServletResponse response ,
			HttpSession session) throws Exception {
	
		final PurchaseOrderdtlService purchaseorderdtlService = new PurchaseOrderdtlService(getCurrentContext());
		final PurchaseOrderhdrService purchaseorderhdrService = new PurchaseOrderhdrService(getCurrentContext());
		final Integer pohdr_id = pohdr.getId();
		Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		final String dateFormat = currentDateFormat.getSystemDateFormat();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Report report = new Report();
		report.setReportName("PURCHASE ORDER");
		report.setReportType(0);
		report.setCurrUserName(user.getName());
		report.setCompanyName(company.getCompany_name());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		model.addAttribute("reportName", report);
		pohdr = purchaseorderhdrService.getPohdrdata(pohdr_id.toString());
		pohdr.setPo_dtl(purchaseorderdtlService.getList(pohdr_id.toString()));
		pohdr.setCommpanyName(company.getCompany_name());
		Date printDate = formatter.parse(pohdr.getPo_date());
		pohdr.setPo_date(currentDateFormat.getDateWithSystemFormat(printDate));
		
		return new ModelAndView("poView", "listInvoice", pohdr);
		
	}
	
}