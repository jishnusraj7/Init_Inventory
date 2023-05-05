package com.indocosmo.mrp.web.report.purchaseorderreport.controller;

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
import com.indocosmo.mrp.web.report.purchaseorderreport.dao.PurchaseOrderReportDao;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;
import com.indocosmo.mrp.web.report.purchaseorderreport.service.PurchaseOrderReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value="/purchaseorderreport")
public class PurchaseOrderReportController extends ViewController<PurchaseOrderReportModel,PurchaseOrderReportDao,PurchaseOrderReportService>{

	public static final Logger logger=Logger.getLogger(StockDisposalController.class);


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public PurchaseOrderReportService getService() {

		return new PurchaseOrderReportService(getCurrentContext());
	}

	/**
	 * @param puchaseorder
	 * @param model
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/Purchase Order Report")
	public ModelAndView PrintInvoice(@ModelAttribute PurchaseOrderReportModel puchaseorder,Model model,HttpServletResponse response,HttpSession session) throws Exception {	


		try{

			final PurchaseOrderReportService purchaseorderhdrService=new PurchaseOrderReportService(getCurrentContext());
			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Report report=new Report();

			report.setReportName("PURCHASE REPORT SUMMARY");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			PurchaseOrderReportModel po=new PurchaseOrderReportModel();
			po.setPurchasreportData(purchaseorderhdrService.getreportDetails(puchaseorder));
			Date startDate1 = formatter.parse(puchaseorder.getStartdate());
			Date endDate=formatter.parse(puchaseorder.getEnddate());
			puchaseorder.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			puchaseorder.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			po.setStartdate(puchaseorder.getStartdate());
			po.setEnddate(puchaseorder.getEnddate());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);

			model.addAttribute("reportName", report);
			model.addAttribute("puchaseorder", puchaseorder);

			for (PurchaseOrderReportModel po_dtl : po.getPurchasreportData()) {

				Date poDate = formatter.parse(po_dtl.getPo_date());
				po_dtl.setPo_date(currentDateFormat.getDateWithSystemFormat(poDate));

			}
			
			return new ModelAndView("poSummaryView", "poSummaryView", po);
		
			/*
			 * if(puchaseorder.getPdfExcel().equals("pdf")) {
			 * 
			 * System.out.println(puchaseorder.getPdfExcel()); return new
			 * ModelAndView("poSummaryView", "poSummaryView", po); }
			 * 
			 * else { return new ModelAndView("poSummaryExcelView", "poSummaryExcelView",
			 * po); }
			 */


			//return new ModelAndView("poSummaryView", "poSummaryView",po);
		} catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}
		

	}

}
