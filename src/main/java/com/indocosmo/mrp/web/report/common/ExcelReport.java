package com.indocosmo.mrp.web.report.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExcelReport extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JsonArray reportDataList = (JsonArray) model.get("reportData");

		JsonArray reportData = (JsonArray) reportDataList.get(0);
		JsonObject coloumnData = (JsonObject) reportDataList.get(1);
		String coloumnlistStr = coloumnData.get("ColoumnList").getAsString();
		String reportname = coloumnData.get("reportname").getAsString();
		List<String> coloumnList = Arrays.asList(coloumnlistStr.split(","));

		HSSFSheet sheet = workbook.createSheet(reportname);
		for (int i = 0; i <= coloumnList.size(); i++) {
			sheet.setColumnWidth(i, 7000);

		}

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//headerData
		HSSFRow header = sheet.createRow(0);

		int counterheader = 0;
		for (String coloumn : coloumnList) {

			HSSFCell cell = header.createCell(counterheader);
			cell.setCellValue(coloumn.toUpperCase());
			cell.setCellStyle(style);
			counterheader++;

		}
//rowData
		int coloumnrrow = 0;
		int coloumnDatarow = 1;
		for (int i = 0; i < reportData.size(); i++) {
			JsonObject json = (JsonObject) reportData.get(i);
			HSSFRow row = sheet.createRow(coloumnDatarow);
			coloumnDatarow++;
			coloumnrrow = 0;
			for (String coloumn : coloumnList) {
				row.createCell(coloumnrrow).setCellValue(json.get(coloumn).getAsString());
				coloumnrrow++;
			}

		}

		   Date today1 = new Date();
		   SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		    String stringToday3 = sdf3.format(today1);
		   
		    
		    
		
		String headerResponse = "attachment;filename=";
	    headerResponse = headerResponse.concat(reportname.toLowerCase().replaceAll("\\s","")+stringToday3+".xls");
	    response.addHeader("Content-disposition", headerResponse);
	}

}
