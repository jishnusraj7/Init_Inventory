package com.indocosmo.mrp.web.report.stockadjustmentsummary.controller;

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
import com.indocosmo.mrp.web.report.stockadjustmentsummary.dao.StockAdjustmentReportDao;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.service.StockAdjustmentReportService;

@Controller
@RequestMapping("/stockadjustmentreport")
public class StockAdjustmentReportController extends ViewController<StockAdjustmentReport, StockAdjustmentReportDao, StockAdjustmentReportService>{

	public static final Logger logger = Logger.getLogger(StockAdjustmentReport.class);
	
	@Override
	public StockAdjustmentReportService getService() {
		
		return new StockAdjustmentReportService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute StockAdjustmentReport stockAdjustment , HttpSession session , Model model) throws Exception {
		
		model.addAttribute("Report",true);		
		return "/report/production/list";
	}
	
	@RequestMapping(value = "/StockTakingSummary")
	public ModelAndView getReport(@ModelAttribute StockAdjustmentReport stockAdjustment , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception
	{
		stockAdjustment.setStartdate(request.getParameter("startdate"));
		stockAdjustment.setEnddate(request.getParameter("enddate"));
		stockAdjustment.setPdfexcel(request.getParameter("pdfexcel"));
		
		final StockAdjustmentReportDao stockAdjustmentReportDao = new StockAdjustmentReportDao(getCurrentContext());
		
		JsonArray StockAdjustmentArray = null;
		StockAdjustmentArray = stockAdjustmentReportDao.getStockAdjustmentDetails(stockAdjustment);
				
		try 
		{
			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("STOCK TAKING SUMMARY");
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
			model.addAttribute("stockAdjustmentReport", stockAdjustment);
			
		}
		catch (Exception e) {
			
			logger.error("Method: Stock taking report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();			
		}
		if(stockAdjustment.getPdfexcel().equals("pdf"))
			return new ModelAndView("StockAdjustmentReportView", "stockAdjustmentData", StockAdjustmentArray);
		else
			return new ModelAndView("StockAdjustmentReportExcelView", "stockAdjustmentData", StockAdjustmentArray);
	}

}
