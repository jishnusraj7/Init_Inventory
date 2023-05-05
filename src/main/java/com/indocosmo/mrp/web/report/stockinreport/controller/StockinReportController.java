package com.indocosmo.mrp.web.report.stockinreport.controller;

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
import com.indocosmo.mrp.web.report.stockinreport.dao.StockinReportDao;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;
import com.indocosmo.mrp.web.report.stockinreport.service.StockinReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value = "/stockinreport")
public class StockinReportController extends ViewController<StockinReportModel, StockinReportDao, StockinReportService> {
	
	public static final Logger logger = Logger.getLogger(StockDisposalController.class);
	
	@Override
	public StockinReportService getService() {
	
		return new StockinReportService(getCurrentContext());
	}
	
	@RequestMapping(value = "/Stockin Report")
	public ModelAndView PrintInvoice(@ModelAttribute StockinReportModel puchaseorder , Model model ,
			HttpServletResponse response , HttpSession session) throws Exception {
	
		try {
			
			final StockinReportService stockinhdrService = new StockinReportService(getCurrentContext());
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			
			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			
			if (puchaseorder.getOption() == 0) {
				
				report.setReportName("ITEM WISE STOCK IN REPORT");
			}
			else {
				
				report.setReportName("DATE WISE STOCK IN REPORT");
			}
			
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			StockinReportModel po = new StockinReportModel();
			po.setPurchasreportData(stockinhdrService.getreportDetails(puchaseorder));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date startDate1 = formatter.parse(puchaseorder.getStartdate());
			Date endDate = formatter.parse(puchaseorder.getEnddate());
			puchaseorder.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			puchaseorder.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			po.setStartdate(puchaseorder.getStartdate());
			po.setEnddate(puchaseorder.getEnddate());
			po.setOption(puchaseorder.getOption());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			
			model.addAttribute("reportName", report);
			
			for (StockinReportModel stockindtl : po.getPurchasreportData()) {
				
				Date stkDate = formatter.parse(stockindtl.getReceived_date());
				stockindtl.setReceived_date(currentDateFormat.getDateWithSystemFormat(stkDate));
			}
			if(puchaseorder.getPdfExcel().equals("pdf"))
				return new ModelAndView("stockinSummaryView", "stockinSummaryView", po);
			else
				return new ModelAndView("StockInExcelReportView", "stockinSummaryView", po);
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}
	}
}
