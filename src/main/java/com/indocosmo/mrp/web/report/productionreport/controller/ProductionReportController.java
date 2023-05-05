package com.indocosmo.mrp.web.report.productionreport.controller;

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
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.productionreport.dao.ProductionReportDao;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.productionreport.service.ProductionReportService;

@Controller
@RequestMapping(value = "/productionreport")
public class ProductionReportController extends
		ViewController<ProductionReportModel, ProductionReportDao, ProductionReportService> {
	
	public static final Logger logger = Logger.getLogger(ProductionReportController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ProductionReportService getService() {
	
		return new ProductionReportService(getCurrentContext());
	}
	
	/**
	 * @param prod
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ProductionReportModel prod , HttpSession session , Model model)
			throws Exception {
	
		model.addAttribute("Report", true);
		
		return "/report/production/list";
	}
	
	@RequestMapping(value = "/Production Report")
	public ModelAndView PrintInvoice(@ModelAttribute ProductionReportModel prod , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception 
	{
		String[] startdate = request.getParameterValues("startdate");
		String[] enddate = request.getParameterValues("enddate");
		String[] option = request.getParameterValues("option");
		String companyId = request.getParameter("companyId");
		String stock_item_id = request.getParameter("stock_item_id");
		prod.setPdfExcel(request.getParameter("pdfExcel"));
		prod.setStartdate(startdate[0]);
		prod.setEnddate(enddate[0]);
		
		final ProductionReportDao productiondao = new ProductionReportDao(getCurrentContext());
		
		JsonArray orderhdrDateData = null;
		//JsonArray totalorderhdrDateData = null;
		
		orderhdrDateData = productiondao.getProductionData(stock_item_id, companyId, startdate[0], enddate[0],option[0], session);
		
		// totalorderhdrDateData=
		// productiondao.getProductionItemTotal(startdate[0],enddate[0],option[0],session);
		JsonArray orderdataList = new JsonArray();
		orderdataList.add(orderhdrDateData);
		// orderdataList.add(totalorderhdrDateData);
		
		try {
			
			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
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
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			report.setIs_customer(option[0]);
			model.addAttribute("reportName", report);
			model.addAttribute("prod", prod);
			
			
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		System.out.println(orderdataList);
		if(prod.getPdfExcel().equals("pdf"))
			return new ModelAndView("prodSummaryView", "prodSummaryView", orderdataList);
		else
			return new ModelAndView("prodSummaryExcelView", "prodSummaryExcelView", orderdataList);
		
		//return new ModelAndView("prodSummaryView", "prodSummaryView", orderdataList);
	}
	
	
	
	@RequestMapping(value = "/Prodbalance Report")
	public ModelAndView Prodbalance(@ModelAttribute ProductionReportModel prod , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception {
	
		String startdate = request.getParameter("startdate");
		String option = request.getParameter("option");
		String companyId = request.getParameter("companyId");
		String stock_item_id = request.getParameter("stock_item_id");
		prod.setStartdate(startdate);
		
		final ProductionReportDao productiondao = new ProductionReportDao(getCurrentContext());
		
		JsonArray orderhdrDateData = null;
		//JsonArray totalorderhdrDateData = null;
		
		orderhdrDateData = productiondao.getProductionBalanceData(stock_item_id, companyId, startdate,option, session);
		
		// totalorderhdrDateData=
		// productiondao.getProductionItemTotal(startdate[0],enddate[0],option[0],session);
		
		JsonArray orderdataList = new JsonArray();
		
		orderdataList.add(orderhdrDateData);
		// orderdataList.add(totalorderhdrDateData);
		
		try {
			
			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("PRODUCTION BALANCE REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			report.setIs_customer(option);
			model.addAttribute("reportName", report);
			model.addAttribute("prod", prod);
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		if(prod.getPdfExcel().equals("pdf")){
			return new ModelAndView("prodBalSummaryView", "prodBalSummaryView", orderdataList);
		}
			
		else{
			return new ModelAndView("prodBalSummaryViewExcelView", "prodBalSummaryViewExcelView", orderdataList);
		}
		
		
	}

	
}
