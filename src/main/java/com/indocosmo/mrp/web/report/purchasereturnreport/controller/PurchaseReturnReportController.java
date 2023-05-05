package com.indocosmo.mrp.web.report.purchasereturnreport.controller;

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
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.purchasereturnreport.dao.PurchaseReturnReportDao;
import com.indocosmo.mrp.web.report.purchasereturnreport.model.PurchaseReturnReport;
import com.indocosmo.mrp.web.report.purchasereturnreport.service.PurchaseReturnReportService;
import com.indocosmo.mrp.web.stock.purchasereturn.dao.PurchaseReturnDao;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.service.PurchaseReturnDetailService;
import com.indocosmo.mrp.web.stock.purchasereturn.service.PurchaseReturnService;

/*@gana 01042020*/

@Controller
@RequestMapping(value="/purchaseReturnreport")
public class PurchaseReturnReportController extends ViewController<PurchaseReturnReport, PurchaseReturnReportDao, PurchaseReturnReportService>{

	private final static int IS_PDF=1;

	@Override
	public PurchaseReturnReportService getService() {
		return new PurchaseReturnReportService(getCurrentContext());
	}

	@RequestMapping(value = "purchasereturn")
	public String getitemstock(@ModelAttribute PurchaseReturnReport purchaseReturnReport, Model model) throws Exception {
		model.addAttribute("Report", true);
		return "report/form";
	}
	@RequestMapping(value="/PurchaseReturn Report")
	public ModelAndView printInvoice(@ModelAttribute PurchaseReturnReport purchaseReturn , Model model , HttpServletResponse response ,
			HttpSession session,HttpServletRequest request) throws Exception {

		final PurchaseReturnReportService purchaseReturnService = new PurchaseReturnReportService(getCurrentContext());
		final PurchaseReturnDetailService purchaseReturnDetailService = new PurchaseReturnDetailService(getCurrentContext());
		final CompanyProfile companyProfile = (CompanyProfile) session
				.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		Integer combine_pur=(Integer) session.getAttribute("combineMode");
		final String dateFormat = currentDateFormat.getSystemDateFormat();
		PurchaseReturnReport purchaseReturnReport=new PurchaseReturnReport();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(purchaseReturn.getStartDate());
		Date endDate=formatter.parse(purchaseReturn.getEndDate());
		purchaseReturnReport.setStartDate(currentDateFormat.getDateWithSystemFormat(startDate));
		purchaseReturnReport.setEndDate(currentDateFormat.getDateWithSystemFormat(endDate));
		Report report = new Report();
		report.setReportName("Purchase Return Report");
		report.setCompanyName(companyProfile.getCompany_name());
		report.setCompanyAddress(companyProfile.getAddress());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		report.setCombine_mode(combine_pur);
		report.setReportType(1);
		model.addAttribute("reportName", report);

		purchaseReturnReport.setPurchaseReturnReportData(purchaseReturnService.getStockReturn(purchaseReturn));
		if(purchaseReturn.getOption()==IS_PDF) {
			return new ModelAndView("purchaseReturnView", "purchaseReturnView",purchaseReturnReport);
		}else {
			return new ModelAndView("purchaseReturnExcelView", "purchaseReturnExcelView",purchaseReturnReport);
		}
	}


}
