package com.indocosmo.mrp.web.report.stockoutreport.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockoutreport.dao.StockoutReportDao;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;
import com.indocosmo.mrp.web.report.stockoutreport.service.StockoutReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;


@Controller
@RequestMapping(value="/stockoutreport")
public class StockoutReportController extends ViewController<StockoutReportModel, StockoutReportDao, StockoutReportService> {
	public static final Logger logger=Logger.getLogger(StockDisposalController.class);

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public StockoutReportService getService() {

		return new StockoutReportService(getCurrentContext());
	}

	/**
	 * @param stockout
	 * @param model
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/Stockout Report")
	public ModelAndView PrintInvoice(@ModelAttribute StockoutReportModel stockout,Model model,
			HttpServletResponse response,HttpSession session) throws Exception 
	{
		try{

			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			StockoutReportService stockoutReportService=new StockoutReportService(getCurrentContext());

			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Report report=new Report();

			if(stockout.getOption()==0)
			{
				report.setReportName("STOCK OUT REPORT SUMMARY");
			}
			else
			{
				report.setReportName("STOCK OUT REPORT DETAIL");
			}

			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setCompanyAddress(company.getAddress());
			StockoutReportModel stkout=new StockoutReportModel();
			stkout.setStockoutreportData(stockoutReportService.getreportDetails(stockout));
			Date startDate1 = formatter.parse(stockout.getStartdate());
			Date endDate=formatter.parse(stockout.getEnddate());
			stkout.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stkout.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			stkout.setOption(stockout.getOption());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			report.setCompanyName(company.getCompany_name());
			//report.setCompanyName("French Toast");
			//report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);

			for (StockoutReportModel stockoutdtl : stkout.getStockoutreportData()) 
			{
				Date stkDate = formatter.parse(stockoutdtl.getStock_transfer_date());
				stockoutdtl.setStock_transfer_date(currentDateFormat.getDateWithSystemFormat(stkDate));
			}
			return new ModelAndView("stockoutSummaryView", "stockoutSummaryView",stkout);
		} 
		catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}		
	}
	
	@RequestMapping(value="/Stockout Report Excel")
	public ModelAndView stockOutExcel(@ModelAttribute StockoutReportModel stockout,Model model,
			HttpServletResponse response,HttpSession session) throws Exception 
	{
		try{

			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			StockoutReportService stockoutReportService=new StockoutReportService(getCurrentContext());

			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Report report=new Report();

			if(stockout.getOption()==0)
			{
				report.setReportName("STOCK OUT REPORT SUMMARY");
			}
			else
			{
				report.setReportName("STOCK OUT REPORT DETAIL");
			}

			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			StockoutReportModel stkout=new StockoutReportModel();
			stkout.setStockoutreportData(stockoutReportService.getreportDetails(stockout));
			Date startDate1 = formatter.parse(stockout.getStartdate());
			Date endDate=formatter.parse(stockout.getEnddate());
			stkout.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stkout.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			stkout.setOption(stockout.getOption());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);

			for (StockoutReportModel stockoutdtl : stkout.getStockoutreportData()) 
			{
				Date stkDate = formatter.parse(stockoutdtl.getStock_transfer_date());
				stockoutdtl.setStock_transfer_date(currentDateFormat.getDateWithSystemFormat(stkDate));
			}
			return new ModelAndView("stockoutSummaryExcel", "stockoutSummaryView",stkout);
		} 
		catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}		
	}

}
