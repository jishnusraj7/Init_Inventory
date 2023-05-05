package com.indocosmo.mrp.web.report.stockexcisereport.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockexcisereport.dao.StockExciseReportDao;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;
import com.indocosmo.mrp.web.report.stockexcisereport.service.StockExciseReportService;

/*
 * 
 *  Done by anandu on 21-01-2020 
 *  
 */

@Controller
@RequestMapping(value = "/stockexcisereport")
public class StockExciseReportController
		extends ViewController<StockExciseReport, StockExciseReportDao, StockExciseReportService> {

	@Override
	public StockExciseReportService getService() {

		return new StockExciseReportService(getCurrentContext());
	}

	@RequestMapping(value = "/Stock Excise")
	public ModelAndView PrintInvoice(@ModelAttribute StockExciseReport stockExciseReport, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		try {
			final StockExciseReportService stockExciseReportService = new StockExciseReportService(getCurrentContext());

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();

			Integer option = stockExciseReport.getOption();

			Report report = new Report();
			report.setDocType(option);

			if (stockExciseReport.getOption() == 1) {
				report.setReportName("STOCK EXCISE REPORT");
			}

			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);

			stockExciseReport.setStockExcise(stockExciseReportService.getReportResult(stockExciseReport));
			/*
		List<StockExciseReport> setStockExcise=new ArrayList<StockExciseReport>();
			
			//stockExciseReport.setStockExcise(stockExciseReportService.getReportResult(stockExciseReport));
			
			
			
			try{
				for (StockExciseReport stockexcisereport:stockExciseReportService.getReportResult(stockExciseReport)){
					
					stockexcisereport.setBtlSize(stockExciseReportService.getBTLSize(stockexcisereport.getUomcode()));
					setStockExcise.add(stockexcisereport);
					
					//stockExciseReportService.getBTLSize(stockexcisereport.getUomcode());
				}
				
			}catch (Exception e){
				e.printStackTrace();
			}
			stockExciseReport.setStockExcise(setStockExcise);*/
			
			

			Date startDate1 = formatter.parse(stockExciseReport.getStartdate());
			Date endDate = formatter.parse(stockExciseReport.getEnddate());
			int category = stockExciseReport.getCategory();
			String superClass = stockExciseReport.getSuperClass();
			stockExciseReport.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stockExciseReport.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			stockExciseReport.setCategory(category);
			stockExciseReport.setSuperClass(superClass);

			return new ModelAndView("stockExciseView", "exciseInvoice", stockExciseReport);
		} catch (Exception e) {

			throw new CustomException();

		}
	}

	@RequestMapping(value = "/Stock Excise Excel")
	public ModelAndView excelInvoice(@ModelAttribute StockExciseReport stockExciseReport, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		try {
			final StockExciseReportService stockExciseReportService = new StockExciseReportService(getCurrentContext());

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();

			Integer option = stockExciseReport.getOption();

			Report report = new Report();

			report.setDocType(option);

			if (stockExciseReport.getOption() == 1) {
				report.setReportName("STOCK EXCISE REPORT");
			}

			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);

			stockExciseReport.setStockExcise(stockExciseReportService.getReportResult(stockExciseReport));

			Date startDate1 = formatter.parse(stockExciseReport.getStartdate());
			Date endDate = formatter.parse(stockExciseReport.getEnddate());
			int category = stockExciseReport.getCategory();
			String superClass = stockExciseReport.getSuperClass();
			stockExciseReport.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stockExciseReport.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			stockExciseReport.setCategory(category);
			stockExciseReport.setSuperClass(superClass);

			return new ModelAndView("stockExciseViewExcel", "exciseInvoice", stockExciseReport);
		} catch (Exception e) {
			throw new CustomException();
		}
	}
}