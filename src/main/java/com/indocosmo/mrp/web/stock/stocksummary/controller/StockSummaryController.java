package com.indocosmo.mrp.web.stock.stocksummary.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.stocksummary.dao.StockSummaryDao;
import com.indocosmo.mrp.web.stock.stocksummary.model.StockSummary;
import com.indocosmo.mrp.web.stock.stocksummary.service.StockSummaryService;

/**
 * @author Mohsina
 *
 */
@Controller
@RequestMapping("stocksummary")
public class StockSummaryController extends ViewController<StockSummary, StockSummaryDao, StockSummaryService>{

	public static final Logger logger = Logger.getLogger(StockSummaryController.class);	
	private StockSummaryService stockSummaryService;

	@Override
	public StockSummaryService getService() {

		stockSummaryService = new StockSummaryService(getCurrentContext());
		return stockSummaryService;
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute StockSummary stockSummary, HttpSession session , Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_SUMMARY");

		model.addAttribute("permission", permission);
		model.addAttribute("Store",true);
		model.addAttribute("Stock",true);
		model.addAttribute("stocksummaryclass",true);

		return "/stocksummary/list";
	}

	@RequestMapping(value = "/formJsonData")
	public void getformJsonData(HttpServletRequest request, HttpServletResponse response) throws Exception{

		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		JsonArray departmentData = departmentService.getMastersRowJson();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("departmentData", departmentData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/*
	 * 
	 *  Done by anandu on 25-01-2020
	 *   
	 */	
	@RequestMapping(value = "/item_category", method = RequestMethod.GET)
	public void getStockItems(HttpServletRequest request,
			HttpServletResponse response, HttpSession seesion, Model model) throws Exception {

		JsonArray categoryData = new StockSummaryService(getCurrentContext()).getgetItemCtegoryJsonArray();

		JsonObject itemCategories = new JsonObject();
		itemCategories.add("categoryData", categoryData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(itemCategories.toString());
	}

	
	@RequestMapping(value = "/summary")
	public void getStockSummary(HttpServletRequest request, HttpServletResponse response) throws Exception{

		stockSummaryService = new StockSummaryService(getCurrentContext());
		String date_from = request.getParameter("start_date");
		String date_to = request.getParameter("end_date");
		String department_id = request.getParameter("department_id");
		String category_id = request.getParameter("category_id");
		if(category_id == null)
		{
			category_id = "0" ;
		}
	//	int category_id =Integer.parseInt("200");  
		JsonArray stockSummaryData = stockSummaryService.getStockSummary(date_from, date_to, department_id,category_id);

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stockSummaryData", stockSummaryData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/printSummary")
	public ModelAndView printStockSummaryDetails(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) 
			throws Exception{

		stockSummaryService = new StockSummaryService(getCurrentContext());
		String date_from = request.getParameter("start_date");
		String date_to = request.getParameter("end_date");
		String department_id = request.getParameter("department_id");
		String category_id = request.getParameter("category_id");
		
		String reportType=request.getParameter("pdf");
		String department_name = null;
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		JsonArray departmentData = departmentService.getMastersRowJson();
		for(int i=0; i < departmentData.size(); i++){
			JsonObject department = (JsonObject) departmentData.get(i);
			if(department.get("id").getAsString().equals(department_id)){
				department_name = department.get("name").getAsString();
				break;
			}				
		}
		
	
	
		
		JsonArray stockSummaryData = stockSummaryService.getStockSummary(date_from, date_to, department_id, category_id);

		try {

			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			if(reportType.equals("0")) {
				Report report = new Report();
				report.setReportName("STOCK SUMMARY REPORT");
				report.setCurrUserName(user.getName());
				report.setCompanyName("Ambikapupuram Road, 8th Cross ,Street B, Panampilly Nagar");
				report.setCompanyAddress(company.getAddress());
				report.setReportType(1);
				report.setDecimalPlace(systemSettings.getDecimal_places());
				report.setCurrency(systemSettings.getCurrencySymbol());
				report.setDateFormat(dateFormat);
				model.addAttribute("reportName", report);
				model.addAttribute("fromDate", date_from);
				model.addAttribute("toDate", date_to);
				model.addAttribute("department", department_name);
				
				return new ModelAndView("stockSummaryReportView", "stockSummaryData", stockSummaryData);
			}else {
              
				
				Report report = new Report();
				report.setReportName("STOCK SUMMARY REPORT EXCEL");
				report.setCurrUserName(user.getName());
				//report.setCompanyName(company.getCompany_name());
				report.setCompanyName(company.getCompany_name());
				report.setCompanyAddress(company.getAddress());
				/*report.setCompanyName("Central Hotel");
				report.setCompanyAddress("Thrissur, Kerala, India");*/
				report.setReportType(1);
				report.setDecimalPlace(systemSettings.getDecimal_places());
				report.setCurrency(systemSettings.getCurrencySymbol());
				report.setDateFormat(dateFormat);
				model.addAttribute("reportName", report);
				model.addAttribute("fromDate", date_from);
				model.addAttribute("toDate", date_to);
				model.addAttribute("department", department_name);
				
				return new ModelAndView("stockSummaryDataExcel", "stockSummaryDataExcel", stockSummaryData);
				
			}

		} catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();

		}
		
	}
}
