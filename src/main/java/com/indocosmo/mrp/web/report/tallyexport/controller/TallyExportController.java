package com.indocosmo.mrp.web.report.tallyexport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.exception.controller.ExceptionController;
import com.indocosmo.mrp.web.core.nodata.controller.NoDataAvailable;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;
import com.indocosmo.mrp.web.report.tallyexport.TallyExportDesign;
import com.indocosmo.mrp.web.report.tallyexport.dao.TallyExportDaoImp;
import com.indocosmo.mrp.web.report.tallyexport.model.TallyExport;
import com.indocosmo.mrp.web.report.tallyexport.service.TallyExportServiceImp;
import javax.servlet.ServletContext;
/*
 * 
 * @gana 06042020
 */
@Controller
@RequestMapping(value="/tallyExportReport")
public class TallyExportController extends ViewController<TallyExport, TallyExportDaoImp, TallyExportServiceImp>{

	@Override
	public TallyExportServiceImp getService() {
		return new TallyExportServiceImp(getCurrentContext());
	}

	@RequestMapping(value = "tallyexport")
	public String getitemstock(@ModelAttribute TallyExport tallyExport, Model model) throws Exception {
		model.addAttribute("Report", true);
		return "report/form";
	}



	@RequestMapping(value="/tallyPurchase",method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> PrintTally(@ModelAttribute TallyExport tallyExport, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		final TallyExport tallyExportModel=new TallyExport();
		TallyExportDesign tallyExportDesign=new TallyExportDesign();
		String tallyClass="";
		final TallyExportServiceImp tallyExportService=new TallyExportServiceImp(getCurrentContext());
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = null;
		Properties prop = new Properties();
		input = loader.getResourceAsStream("/export.properties");
		prop.load(input);
		File createFold=new File(prop.getProperty("tallyExportPath"));
		// if the directory does not exist, create it
		if(!createFold.exists()) {
			 createFold.getParentFile().mkdirs();
		     createFold.mkdir();
		}
		tallyExportModel.setTallyExport(tallyExportService.getTallyData(tallyExport));
		tallyClass=tallyExportDesign.getTallyExport(tallyExportModel,prop);
		if(tallyClass!=null) {
			
			File file = new File(tallyClass);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			final HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, file.getName());
			headers.add("Access_Control_Expose_Headers", HttpHeaders.CONTENT_DISPOSITION);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, file.getName()).headers(headers)
					.contentType(MediaType.TEXT_PLAIN).contentLength(file.length()).body(resource);
		}else {
		}
		return null;
	}
}
