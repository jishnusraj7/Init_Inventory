package com.indocosmo.mrp.web.report.stockdisposalreport.controller;

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
import com.indocosmo.mrp.web.report.prodbomreport.dao.ProdBomReportDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.productionreport.dao.ProductionReportDao;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.productionreport.service.ProductionReportService;
import com.indocosmo.mrp.web.report.stockdisposalreport.dao.DisposalReportDao;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;
import com.indocosmo.mrp.web.report.stockdisposalreport.service.DisposalReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value = "/stockdisposalreport")
public class DisposalReportController extends
		ViewController<DisposalReportModel, DisposalReportDao, DisposalReportService> {
	
	public static final Logger logger = Logger.getLogger(StockDisposalController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public DisposalReportService getService() {
	
		return new DisposalReportService(getCurrentContext());
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
	
	@RequestMapping(value = "/Disposal Report")
	public ModelAndView PrintInvoice(@ModelAttribute DisposalReportModel prod , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception 
	{
		String[] startdate = request.getParameterValues("startdate");
		String[] enddate = request.getParameterValues("enddate");
		String[] option = request.getParameterValues("option");
		String companyId = request.getParameter("companyId");
		String stock_item_id = request.getParameter("stock_item_id");
		String departmentId=request.getParameterValues("department_id")[0];
		String transType=request.getParameterValues("trans_type")[0];
		prod.setStartdate(startdate[0]);
		prod.setEnddate(enddate[0]);
		prod.setDeptId(departmentId);
		prod.setTransType(transType);
		
		final DisposalReportDao productiondao = new DisposalReportDao(getCurrentContext());
		
		JsonArray orderhdrDateData = null;
		//JsonArray totalorderhdrDateData = null;
		orderhdrDateData = productiondao.getDisposalDateData(stock_item_id, companyId,prod,option[0], session);
		
		// totalorderhdrDateData=
		// productiondao.getProductionItemTotal(startdate[0],enddate[0],option[0],session);
		
		JsonArray orderdataList = new JsonArray();
		orderdataList.add(orderhdrDateData);
		// orderdataList.add(totalorderhdrDateData);
		
		try
		{
			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("STOCK DISPOSAL REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
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
		if(prod.getPdfExcel().equals("pdf"))
			return new ModelAndView("disposalReportView", "disposalReportView", orderdataList);
		else
			return new ModelAndView("disposalReportExcelView", "disposalReportView", orderdataList);
	}
	
	@RequestMapping(value = "/Disposal Month Report")
	public ModelAndView printMonthReport(@ModelAttribute DisposalReportModel disposal , HttpServletRequest request ,
			Model model, HttpServletResponse response , HttpSession session) throws Exception 
	{
		CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		String[] startdate = request.getParameterValues("startdate");
    	String[] depId = request.getParameterValues("department_id");
		String department_id = depId[0];
		String year=request.getParameterValues("year")[0];
		String transType=request.getParameterValues("trans_type")[0];
		String departmentName=request.getParameterValues("department_name")[0];
		
		disposal.setStartdate(startdate[0]);
		disposal.setDeptId(department_id);
		disposal.setTransType(transType);
		disposal.setYear(year);
		disposal.setDeptName(departmentName);
		
		final DisposalReportDao disposalDao = new DisposalReportDao(getCurrentContext());
		
		JsonArray prodBomDateData = null;
		prodBomDateData = disposalDao.getDisposalMonthData(disposal, session);
		
		JsonArray orderdataList = new JsonArray();
		orderdataList.add(prodBomDateData);
		try {
			
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("DISPOSAL MONTH REPORT");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			// report.setIs_customer(option[0]);
			model.addAttribute("reportName", report);
			model.addAttribute("disposalModel", disposal);
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}
		if(disposal.getPdfExcel().equals("pdf"))
			return new ModelAndView("disposalMonthClassView", "disposalMonthClassView", orderdataList);
		else
			return new ModelAndView("disposalMonthlyReportExcelView", "disposalMonthClassView", orderdataList);
	}
}
