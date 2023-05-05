package com.indocosmo.mrp.web.report.prodbomreport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.prodbomreport.dao.ProdBomReportDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.prodbomreport.service.ProdBomReportService;

@Controller
@RequestMapping(value = "/prodbomreport")
public class ProdBomReportController extends ViewController<ProdBomReportModel, ProdBomReportDao, ProdBomReportService> {
	
	public static final Logger logger = Logger.getLogger(ProdBomReportController.class);
	
	@Override
	public ProdBomReportService getService() {
	
		return new ProdBomReportService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ProdBomReportModel prod , HttpSession session , Model model) throws Exception {
		model.addAttribute("Report",true);
		
		return "/report/production/list";
	}
	
	@RequestMapping(value = "/bommonthreport")
	public ModelAndView PrintReport(@ModelAttribute ProdBomReportModel prod , HttpServletRequest request , Model model ,
			HttpServletResponse response , HttpSession session) throws Exception {
	
		// System.out.println("Hello");
		
		String[] startdate = request.getParameterValues("startdate");
/*		String[] enddate = request.getParameterValues("enddate");
*/		String[] depId = request.getParameterValues("department_id");
		int department_id = Integer.parseInt(depId[0]);
		
		prod.setStartdate(startdate[0]);
/*		prod.setEnddate(enddate[0]);
*/		prod.setDepartment_id(department_id);
		
		final ProdBomReportDao prodBomDao = new ProdBomReportDao(getCurrentContext());
		
		JsonArray prodBomDateData = null;
		
		prodBomDateData = prodBomDao.getProductionMonthData(prod, session);
		
		JsonArray orderdataList = new JsonArray();
		orderdataList.add(prodBomDateData);
		
		try {
			
			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("PRODUCTION BOM  MONTH REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			// report.setIs_customer(option[0]);
			model.addAttribute("reportName", report);
			model.addAttribute("prodbom", prod);
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}
		
		if(prod.getPdfExcel().equals("pdf")){
			return new ModelAndView("ProdBomMonthClassView", "ProdBomMonthClassView", orderdataList);
		}
			
		else{
			return new ModelAndView("prodBomMonthSummaryExcelView", "prodBomMonthSummaryExcelView", orderdataList);
		}
		
		
		
		
	}
	
	@RequestMapping(value = "/BOM Report")
	public ModelAndView PrintInvoice(@ModelAttribute ProdBomReportModel prod , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception
	{
		String[] startdate = request.getParameterValues("startdate");
		String[] enddate = request.getParameterValues("enddate");
		String[] depId = request.getParameterValues("department_id");
		int department_id = Integer.parseInt(depId[0]);
		
		prod.setStartdate(startdate[0]);
		prod.setEnddate(enddate[0]);
		prod.setDepartment_id(department_id);
		
		final ProdBomReportDao prodBomDao = new ProdBomReportDao(getCurrentContext());
		
		JsonArray prodBomDateData = null;
		prodBomDateData = prodBomDao.getProductionData(prod, session);
		
		JsonArray orderdataList = new JsonArray();
		orderdataList.add(prodBomDateData);
		
		try 
		{
			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("PRODUCTION BOM REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			// report.setIs_customer(option[0]);
			model.addAttribute("reportName", report);
			model.addAttribute("prodbom", prod);
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}
		if(prod.getPdfExcel().equals("pdf")){
			return new ModelAndView("prodBomSummaryView", "prodBomSummaryView", orderdataList);
		}
			
		else{
			return new ModelAndView("prodBomSummaryExcelView", "prodBomSummaryExcelView", orderdataList);
		}
	
			
	}
	
	@RequestMapping("getDepList")
	public void getDepartmentList(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final ProdBomReportDao prodBomDao = new ProdBomReportDao(getCurrentContext());
		JsonArray data = prodBomDao.getDepartmentJson();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("data", data);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
}
