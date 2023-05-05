package com.indocosmo.mrp.web.report.currentstock.controller;

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
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.dao.ItemStockDao;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.currentstock.service.ItemStockService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value = "/itemstock")
public class ItemStockController extends ViewController<ItemStock,ItemStockDao,ItemStockService>{
	public static final Logger logger=Logger.getLogger(StockDisposalController.class);
	@Override
	public ItemStockService getService() {

		return new ItemStockService(getCurrentContext());
	}

	@RequestMapping(value = "currentstock")
	public String getitemstock(@ModelAttribute ItemStock itemstock,Model model)
			throws Exception {

		model.addAttribute("poStatus", purchaseordersType.values());
		model.addAttribute("stockinStatus", stockInStatus.values());
		model.addAttribute("Report",true);
		return "report/form";
	}



	@RequestMapping(value="currentStock")

	public void getcurrentStock(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{

		String[] itemData = request.getParameterValues("itemsid");
		String[] departmentid = request.getParameterValues("departmentid");

		final ItemStockDao itemstock=new ItemStockDao(getCurrentContext());

		JsonArray stockItmBatchData1 = itemstock.getCurrentStock(itemData[0],departmentid[0]);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stockItmBatchData1", stockItmBatchData1);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}




	@RequestMapping(value="/Stock Report")
	public ModelAndView PrintCurrentstock(@ModelAttribute ItemStock itemstock,Model model,HttpSession session,HttpServletResponse response) throws Exception {	

		try {

			final ItemStockService itemstockService=new ItemStockService(getCurrentContext());
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);


			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

			itemstock.setItemstock(itemstockService.getList(itemstock));

			Report report=new Report();
			report.setReportName("STOCK REPORT");
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

			return new ModelAndView("currentStockView", "listcurrentStock",itemstock);

		} catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}			


	}



	@RequestMapping(value="/Stock Report Excel")
	public ModelAndView PrintExcelCurrentstock(@ModelAttribute ItemStock itemstock,Model model,HttpSession session,
			HttpServletResponse response) throws Exception 
	{	
		try {

			final ItemStockService itemstockService=new ItemStockService(getCurrentContext());
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

			itemstock.setItemstock(itemstockService.getList(itemstock));

			Report report=new Report();
			report.setReportName("STOCK REPORT");
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

			return new ModelAndView("excelCurrentStockView", "listcurrentStock",itemstock);

		} catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}			


	}





}
