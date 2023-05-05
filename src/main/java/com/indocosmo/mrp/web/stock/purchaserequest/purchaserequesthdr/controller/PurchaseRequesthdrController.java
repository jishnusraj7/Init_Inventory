package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.utils.core.dbutils.MySQLDBHelper;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaserequestType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service.PurchaseOrderdtlService;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.service.PurchaseRequestdtlService;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.dao.PurchaseRequesthdrDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.model.PR_hdr;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.service.PurchaseRequesthdrService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.service.RemoteRequestdtlService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.model.RPR_hdr;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.service.RemoteRequesthdrService;

@Controller
@RequestMapping("/purchaserequesthdr")
public class PurchaseRequesthdrController
		extends
		ViewController<PR_hdr, PurchaseRequesthdrDao, PurchaseRequesthdrService> {

	public static final Logger logger = Logger
			.getLogger(PurchaseRequesthdrController.class);

	@Override
	public PurchaseRequesthdrService getService() {
		return new PurchaseRequesthdrService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PR_hdr PO_hdr, HttpSession session,
			Model model) throws Exception {
		final CounterService counterService = new CounterService(
				getCurrentContext());
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService
				.getCurrentUserPermission(session, "STR_PREQ");
		model.addAttribute("permission", permission);
		String CounterWithPrefix = counterService.getNextCounterwithPrefix(
				"pr_hdr", "pr_hdr");
		model.addAttribute("PONO", CounterWithPrefix);
		model.addAttribute("Store", true);
		model.addAttribute("Purchase", true);
		model.addAttribute("prclass", true);

		return "/purchaserequest/list";
	}

	MySQLDBHelper dbHelper;

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		final ItemMasterService itemmasterservice = new ItemMasterService(
				getCurrentContext());

		JsonArray itemsData = itemmasterservice.getMastersRowJson();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("itemsData", itemsData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public void save(@RequestBody String prDtl, HttpSession session,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		/*
		 * final CompanyService companyService=new
		 * CompanyService(getNewContext()); Company currentCompany = new
		 * Company(); currentCompany=companyService.getCompanyByID(0)
		 */;
		final ApplicationContext companyContext = getNewContext();

		final RemoteRequesthdrService remotepurchaseorderhdrService = new RemoteRequesthdrService(
				companyContext);
		final RemoteRequestdtlService remotepurchaseorderdtlService = new RemoteRequestdtlService(
				companyContext);
		final PurchaseRequesthdrService purchaseorderService = new PurchaseRequesthdrService(
				getCurrentContext());
		final PurchaseRequestdtlService purchaseorderdtlservice = new PurchaseRequestdtlService(
				getCurrentContext());
		final String DB_TXN_POINT = "purchaseorderService";
		PR_hdr prhdr = null;
		purchaseorderService.beginTrans(DB_TXN_POINT);
		ObjectMapper mapper = new ObjectMapper().configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			prhdr = mapper.readValue(prDtl, PR_hdr.class);

			prhdr = purchaseorderService.saveStockItem(prhdr);
			String prdtl = prhdr.getPrDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray prdtlList = (JsonArray) parser.parse(prdtl);

			final RPR_hdr rpr_hdr = new RPR_hdr();
			rpr_hdr.setRequest_number(prhdr.getPr_number());
			rpr_hdr.setRequest_company_id(prhdr.getCompany_id());
			rpr_hdr.setRequest_company_code(prhdr.getRequest_company_code());
			rpr_hdr.setRequest_company_name(prhdr.getRequest_company_name());
			rpr_hdr.setSubmit_date(prhdr.getSubmit_date());
			rpr_hdr.setRequest_by(prhdr.getRequest_by());
			rpr_hdr.setRequest_status(0);
			rpr_hdr.setRemarks(prhdr.getRemarks());
			if (prhdr.getStatus() == 1) {
				remotepurchaseorderhdrService.save(rpr_hdr);
			}
			final ArrayList<PR_dtl> stockInItemsList = new ArrayList<PR_dtl>();
			final ArrayList<RPR_dtl> rpr_dtlList = new ArrayList<RPR_dtl>();
			for (int i = 0; i < prdtlList.size(); i++) {
				JsonObject json = (JsonObject) prdtlList.get(i);

				final PR_dtl stockInItem = new PR_dtl();

				if (json.get("id").getAsString() != null
						&& json.get("id").getAsInt() != 0
						&& json.get("id").getAsString().length() != 0) {
					stockInItem.setId(Integer.parseInt(json.get("id")
							.getAsString()));
				} else {
					stockInItem.setId(null);
				}

				stockInItem.setPr_hdr_id(prhdr.getId());
				stockInItem.setStock_item_id(json.get("stock_item_id")
						.getAsInt());
				stockInItem.setStock_item_code(json.get("stock_item_code")
						.getAsString());
				stockInItem.setStock_item_name(json.get("stock_item_name")
						.getAsString());
				stockInItem.setQty(json.get("qty").getAsDouble());
				stockInItem.setExpected_date(json.get("expectedDate")
						.getAsString());
				stockInItem.setRequest_status(json.get("request_status")
						.getAsInt());
				stockInItemsList.add(stockInItem);
				final RPR_dtl rpr_dtl = new RPR_dtl();
				rpr_dtl.setRemote_request_hdr_id(rpr_hdr.getId());
				rpr_dtl.setStock_item_id(json.get("stock_item_id").getAsInt());
				rpr_dtl.setStock_item_code(json.get("stock_item_code")
						.getAsString());
				rpr_dtl.setStock_item_name(json.get("stock_item_name")
						.getAsString());
				rpr_dtl.setQty(json.get("qty").getAsDouble());
				rpr_dtl.setExpected_date(json.get("expectedDate").getAsString());
				rpr_dtl.setRequest_status(0);
				rpr_dtlList.add(rpr_dtl);
				if (prhdr.getStatus() == purchaserequestType.SUBMITTED
						.getPurchaserequestTypeId()) {
					remotepurchaseorderdtlService.save(rpr_dtlList);
				}
			}
			purchaseorderdtlservice.save(stockInItemsList);
			prhdr.setPr_dtl(stockInItemsList);
			purchaseorderService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");

		}

		catch (Exception e) {
			response.getWriter().print("0");
			e.printStackTrace();
			purchaseorderService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.posellaweb.web.core.base.controller.ViewController#delete
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final PurchaseRequesthdrService purchaseorderService = new PurchaseRequesthdrService(
				getCurrentContext());
		final PurchaseRequestdtlService purchaseorderdtlservice = new PurchaseRequestdtlService(
				getCurrentContext());
		final String DB_TXN_POINT = "purchaseorderService";
		final String id = request.getParameter("id");
		purchaseorderService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			is_deleted = purchaseorderService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			} else {
				is_deleted = 0;
				is_deleted = purchaseorderdtlservice.delete(id);
				response.getWriter().print("1");
			}
			purchaseorderService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {
			purchaseorderService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in " + this
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}

	}

	/**
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response, Model model)
			throws Exception {
		final CounterService counterService = new CounterService(
				getCurrentContext());
		String CounterWithPrefix = counterService.getNextCounterwithPrefix(
				"pr_hdr", "pr_hdr");
		response.getWriter().print(CounterWithPrefix);
	}

	@Override
	protected PR_hdr setModelItem(HttpServletRequest request, PR_hdr item)
			throws Exception {
		final CounterService counterService = new CounterService(
				getCurrentContext());
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {

			/**
			 * Column annotation Gets the column from bean
			 */
			Column annotationColumn = (Column) field
					.getDeclaredAnnotation(Column.class);

			if (annotationColumn != null) {

				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);

				if (fieldName.equals("pr_number")) {
					if (request.getParameter("id") == null) {
						valueString = counterService.getNextCounterwithPrefix(
								"pr_hdr", "pr_hdr");
					}
				}

				if (valueString != null) {

					Object value = WebUtil.pageFieldToModelValue(field,
							valueString);

					if (value != null) {

						field.setAccessible(true);
						field.set(item, value);
					}
				}

			}

		}

		return item;
	}

}
