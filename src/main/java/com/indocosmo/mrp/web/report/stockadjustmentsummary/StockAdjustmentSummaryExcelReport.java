package com.indocosmo.mrp.web.report.stockadjustmentsummary;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;

public class StockAdjustmentSummaryExcelReport extends AbstractExcelView{

	String dateFormat;
	Integer decimalPlace;

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}
	public String getRateWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);		
		return bd.toString();
	}	

	public String getDateWithSystemFormat(String date) throws Exception {
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//report header font
		Font mainheadFont = workbook.createFont();
		mainheadFont.setFontName("Liberation Sans");;
		mainheadFont.setBoldweight((short) 14);
		mainheadFont.setFontHeightInPoints((short) 16);
		mainheadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		mainheadFont.setUnderline(Font.U_SINGLE);

		//header font
		Font header_font = workbook.createFont();
		header_font.setFontName("Liberation Sans");
		header_font.setFontHeightInPoints((short) 10);
		header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//header font
		Font sub_header_font = workbook.createFont();
		sub_header_font.setFontName("Liberation Sans");
		sub_header_font.setFontHeightInPoints((short) 11);
		sub_header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//common font style
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setFontHeightInPoints((short) 9);

		//normal style
		CellStyle cell_style = workbook.createCellStyle();
		cell_style.setAlignment(CellStyle.ALIGN_LEFT);
		//setBorders(cell_style);
		cell_style.setFont(font);

		//decimal alignment style
		CellStyle decimal_cell_style = workbook.createCellStyle();
		decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		//setBorders(decimal_cell_style);
		decimal_cell_style.setFont(font);
		decimal_cell_style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

		//table header style
		CellStyle table_header_style = workbook.createCellStyle();
		table_header_style.setAlignment(CellStyle.ALIGN_CENTER);
		table_header_style.setFont(header_font);
		//setBorders(table_header_style);

		//header style
		CellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(CellStyle.ALIGN_CENTER);
		header_style.setFont(sub_header_font);

		//department title style
		CellStyle sub_header_style = workbook.createCellStyle();
		sub_header_style.setAlignment(CellStyle.ALIGN_LEFT);
		sub_header_style.setFont(header_font);
		//setBorders(sub_header_style);

		JsonArray stockAdjustmentArray = (JsonArray) model.get("stockAdjustmentData");
		StockAdjustmentReport stockAdjustmentReport = (StockAdjustmentReport) model.get("stockAdjustmentReport");
		Report  report = (Report) model.get("reportName");
		dateFormat = report.getDateFormat();
		decimalPlace = report.getDecimalPlace();

		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4500);

		//style applied for report header
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);

		int row_count = 0;
		sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);

		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);

		if (stockAdjustmentArray.size() != 0) {

			HashMap< String, List<Object> > StockadjustmentDataMap = new HashMap<String, List<Object>>();
			for (int i = 0; i < stockAdjustmentArray.size(); i++) 
			{
				JsonObject adjustData = (JsonObject) stockAdjustmentArray.get(i);
				if (StockadjustmentDataMap.containsKey(adjustData.get("department_name").getAsString())){

					List<Object> jsonList= StockadjustmentDataMap.get(adjustData.get("department_name").getAsString());
					jsonList.add(adjustData);
					StockadjustmentDataMap.put(adjustData.get("department_name").getAsString(), jsonList);

				}else{

					List<Object> jsonList=new  ArrayList<Object>();
					jsonList.add(adjustData);
					StockadjustmentDataMap.put(adjustData.get("department_name").getAsString(), jsonList);

				}
			}

			String subHeadString = "BETWEEN " + getDateWithSystemFormat(stockAdjustmentReport.getStartdate()) + " AND " 
					+ getDateWithSystemFormat(stockAdjustmentReport.getEnddate()) + "";

			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
			HSSFRow row_subHeader = sheet.createRow(row_count++);

			HSSFCell cell_subHeader = row_subHeader.createCell(0);
			cell_subHeader.setCellValue(subHeadString);
			cell_subHeader.setCellStyle(header_style);

			Set set = StockadjustmentDataMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iterator.next();
				String departMentName = (String) entry.getKey();

				sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
				HSSFRow row_department = sheet.createRow(row_count++);

				HSSFCell cell_department = row_department.createCell(0);
				cell_department.setCellValue(departMentName);
				cell_department.setCellStyle(sub_header_style);

				HSSFRow row_tableHead = sheet.createRow(row_count++);

				HSSFCell cell_dateHead = row_tableHead.createCell(0);
				cell_dateHead.setCellValue("DATE");
				cell_dateHead.setCellStyle(table_header_style);

				HSSFCell cell_itemNameHead = row_tableHead.createCell(1);
				cell_itemNameHead.setCellValue("ITEM NAME");
				cell_itemNameHead.setCellStyle(table_header_style);

				HSSFCell cell_systemStockHead = row_tableHead.createCell(2);
				cell_systemStockHead.setCellValue("SYSTEM STOCK");
				cell_systemStockHead.setCellStyle(table_header_style);

				HSSFCell cell_actualStockHead = row_tableHead.createCell(3);
				cell_actualStockHead.setCellValue("ACTUAL STOCK");
				cell_actualStockHead.setCellStyle(table_header_style);

				HSSFCell cell_varianceHead = row_tableHead.createCell(4);
				cell_varianceHead.setCellValue("VARIANCE");
				cell_varianceHead.setCellStyle(table_header_style);

				JsonObject json = (JsonObject) stockAdjustmentArray.get(0);
				String stock_adjustment_date = json.get("adjust_date").getAsString();
				int dateRepeat = 0;
				List<Object> jsonList = (List<Object>) entry.getValue();
				for (int i = 0; i < jsonList.size(); i++) {

					JsonObject stockAdjustData = (JsonObject) jsonList.get(i);
					HSSFRow row_tableData = sheet.createRow(row_count++);

					/* remove repeating adjust date */
					if(!stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString())){
						dateRepeat = 0;
						stock_adjustment_date = stockAdjustData.get("adjust_date").getAsString();
					}
					HSSFCell cell_adjustDate = row_tableData.createCell(0);
					cell_adjustDate.setCellStyle(cell_style);
					if(stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString()) && dateRepeat == 1){
						cell_adjustDate.setCellValue("");
					}
					else{
						if(stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString()) && dateRepeat == 0){
							cell_adjustDate.setCellValue(getDateWithSystemFormat(stockAdjustData.get("adjust_date").getAsString()));
							dateRepeat = 1;
						}
					}
					
					HSSFCell cell_itemName = row_tableData.createCell(1);
					cell_itemName.setCellValue(stockAdjustData.get("stock_item_name").getAsString());
					cell_itemName.setCellStyle(cell_style);
					
					HSSFCell cell_systemStock = row_tableData.createCell(2);
					cell_systemStock.setCellValue(stockAdjustData.get("system_qty").getAsString());
					cell_systemStock.setCellStyle(decimal_cell_style);
					
					HSSFCell cell_actualStock = row_tableData.createCell(3);
					cell_actualStock.setCellValue(stockAdjustData.get("actual_qty").getAsString());
					cell_actualStock.setCellStyle(decimal_cell_style);
					
					HSSFCell cell_variance = row_tableData.createCell(4);
					cell_variance.setCellValue(stockAdjustData.get("diff_qty").getAsString());
					cell_variance.setCellStyle(decimal_cell_style);
				}

			}	
		}else{
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
			HSSFRow row_subHeader = sheet.createRow(row_count++);

			HSSFCell cell_subHeader = row_subHeader.createCell(0);
			cell_subHeader.setCellValue("No item Available");
			cell_subHeader.setCellStyle(header_style);
		}
		
		Date currentDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = simpleDateFormat.format(currentDate);
		
		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(report.getReportName().toLowerCase().replaceAll("\\s","")+stringToday3+".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
}
