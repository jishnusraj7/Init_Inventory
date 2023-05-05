package com.indocosmo.mrp.web.report.bomanalysisreport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.indocosmo.mrp.web.report.bomanalysisreport.dao.BomAnalysisReportDao;
import com.indocosmo.mrp.web.report.bomanalysisreport.model.BomAnalysisReportModel;
import com.indocosmo.mrp.web.report.bomanalysisreport.service.BomAnalysisReportService;
import com.indocosmo.mrp.web.report.common.model.Report;

@Controller
@RequestMapping(value = "/bomanalysisreport")
public class BomAnalysisReportController extends ViewController<BomAnalysisReportModel, BomAnalysisReportDao, BomAnalysisReportService> {

	
	@Override
	public BomAnalysisReportService getService() {
	
		// TODO Auto-generated method stub
		return new BomAnalysisReportService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute BomAnalysisReportModel prod , HttpSession session , Model model)
			throws Exception {
	
		model.addAttribute("Report", true);
		
		return "/report/production/list";
	}
	
	
	@RequestMapping(value = "/bomanalysisReport")
	public ModelAndView PrintInvoice(@ModelAttribute BomAnalysisReportModel bomanalysis , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception 
	{
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		bomanalysis.setStartdate(startdate);
		bomanalysis.setEnddate(enddate);

		final BomAnalysisReportDao productiondao = new BomAnalysisReportDao(getCurrentContext());

		JsonArray itemData = null;
		itemData = productiondao.getItemData(startdate, enddate);
		//option[0], session);
		try {

			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("BOM ANALYSIS REPORT SUMMARY");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			model.addAttribute("bomanalysis", bomanalysis);

		}
		catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		if(bomanalysis.getPdfExcel().equals("pdf")){
			return new ModelAndView("bomanalysisSummaryView", "bomanalysisSummaryView", itemData);
		}
			
		else{
			return new ModelAndView("bomanalysisSummaryExcelView", "bomanalysisSummaryExcelView", itemData);
		}

	}
}