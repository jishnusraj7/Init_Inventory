package com.indocosmo.mrp.web.production.bomanalysis.controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.production.bomanalysis.dao.BomAnalysisDao;
import com.indocosmo.mrp.web.production.bomanalysis.model.BomAnalysis;
import com.indocosmo.mrp.web.production.bomanalysis.service.BomAnalysisService;
import com.indocosmo.mrp.web.production.production.dao.ProductionDao;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

@Controller
@RequestMapping("bomanalysis")
public class BomAnalysisController extends ViewController<BomAnalysis, BomAnalysisDao, BomAnalysisService> {

	@Override
	public BomAnalysisService getService() {

		// TODO Auto-generated method stub
		return new BomAnalysisService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute BomAnalysis bomanalyse, HttpSession session, Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "PRD_BOM");

		model.addAttribute("permission", permission);
		model.addAttribute("Production", true);
		model.addAttribute("bomanalysis", true);

		return "/bomanalysis/list";
	}

	@RequestMapping(value = "getItemData")
	public void getItemData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		final BomAnalysisService bomAnalysisService = new BomAnalysisService(getCurrentContext());
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String departmentId = request.getParameter("deptId");

		JsonObject jsonResponse = new JsonObject();

		JsonArray itemData = bomAnalysisService.getItemData(startDate, endDate, departmentId);
		jsonResponse.add("itemData", itemData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getBomConsumption")
	public void getBomConsumption(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		final BomAnalysisService bomAnalysisService = new BomAnalysisService(getCurrentContext());
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String departmentId = request.getParameter("department_id");
		String stockItemId = request.getParameter("stock_item_id");

		JsonObject jsonResponse = new JsonObject();

		JsonArray bomData = bomAnalysisService.getBomConsumption(startDate, endDate, departmentId, stockItemId);

		JsonArray bomList = null;
		ArrayList<String> stockItemIdList = new ArrayList<String>();
		String bom_item_id=null;
		for (int i = 0; i < bomData.size(); i++) {
			JsonObject jsonObj = (JsonObject) bomData.get(i);
			String stckID = (jsonObj.get("stock_item_id").toString());
			stockItemIdList.add(stckID);
			bom_item_id = (jsonObj.get("bom_item_id").toString());
		}
		
		bomList = bomAnalysisService.getStatusOfBomItem(stockItemIdList, bom_item_id);

		jsonResponse.add("bomData", bomData);

		jsonResponse.add("bomList", bomList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/updateadjust", method = RequestMethod.POST)
	public void save(@RequestBody String adjustDtl, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());

		final String DB_TXN_POINT = "stockRegisterService";
		StockRegister stockRegister = new StockRegister();
		final ArrayList<StockRegisterDetail> stockRegdtlItemsList = new ArrayList<StockRegisterDetail>();
		stockRegisterService.beginTrans(DB_TXN_POINT);
		Integer sourc_cmpny_id = (Integer) session.getAttribute("COMPANY_ID");

		String selectdate = request.getParameter("selectdate");

		try {

			JsonParser parser = new JsonParser();
			JsonArray adjustDtlList = (JsonArray) parser.parse(adjustDtl);

			stockRegister.setTransType(transactionType.BOMADJUSTMENT.gettransactionTypeId());
			stockRegister.setExtRefNo("null");
			stockRegister.setTxnDate(selectdate);
			stockRegister.setExtRefId(0);
			stockRegister.setSource_shop_id(sourc_cmpny_id);
			stockRegister.setDestination_shop_id(sourc_cmpny_id);
			stockRegister = stockRegisterService.saveStockRegData(stockRegister);

			for (int i = 0; i < adjustDtlList.size(); i++) {

				JsonObject json = (JsonObject) adjustDtlList.get(i);

				if (json.get("adjust").getAsBoolean() == true) {
					final StockRegisterDetail stockRegDtl = new StockRegisterDetail();

					stockRegDtl.setStockRegHdrid(stockRegister.getId());
					stockRegDtl.setExtRefDtlId(0);
					stockRegDtl.setDepartment_id(json.get("department_id").getAsInt());
					stockRegDtl.setStockItemId(json.get("stock_item_id").getAsInt());
					stockRegDtl.setStockItemCode(json.get("stock_item_code").getAsString());
					stockRegDtl.setStockItemName(json.get("stock_item_name").getAsString());
					/*
					 * if(json.get("difference").getAsDouble()<0) {
					 * stockRegDtl.setInQty(Math.abs(json.get("difference").getAsDouble())); } else
					 * { stockRegDtl.setOutQty(json.get("difference").getAsDouble()); }
					 */
					stockRegDtl.setOutQty(json.get("difference").getAsDouble());
					stockRegDtl.setCostPrice(json.get("cost_price").getAsDouble());
					stockRegDtl.setApproval_status(1);
					stockRegDtl.setApproval_qty(json.get("difference").getAsDouble());
					// stockRegDtl.setRemarks(json.get("remarks").getAsString());
					stockRegdtlItemsList.add(stockRegDtl);

				}

			}

			stockRegisterDetailService.save(stockRegdtlItemsList);
			stockRegisterService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		} catch (Exception e) {

			response.getWriter().print("0");
			stockRegisterService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: updateadjust" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	@RequestMapping(value = "/report")
	public ModelAndView PrintInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder, Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String departmentId = request.getParameter("department_id");
		String reportType = request.getParameter("pdfExcel");

		final BomAnalysisService bomAnalysisService = new BomAnalysisService(getCurrentContext());
		JsonArray itemData = bomAnalysisService.getItemData(startDate, endDate, departmentId);
		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());

			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("BOM ANALYSIS REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			if (reportType == "pdf")
				report.setReportType(1);
			else
				report.setReportType(2);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);

		} catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		if (reportType.equals("pdf"))
			return new ModelAndView("bomanalysisView", "bomanalysisView", itemData);
		else
			return new ModelAndView("bomanalysisExcelView", "bomanalysisView", itemData);
	}

	@RequestMapping(value = "dropdownprocessBom")
	public void getDropdownJsonDataProcess(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		final ProductionDao productionDao = new ProductionDao(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();

		JsonArray departments = productionDao.getDepList();

		jsonResponse.add("departments", departments);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "getBomData")
	public void getBomData(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)
			throws Exception {

		final BomAnalysisService bomanalysisservice = new BomAnalysisService(getCurrentContext());

		JsonObject jsonResponse = new JsonObject();

		JsonArray alreadyaddorder = null;

		alreadyaddorder = bomanalysisservice.getBomDates();
		jsonResponse.add("alreadyaddorder", alreadyaddorder);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

}
