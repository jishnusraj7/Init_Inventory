package com.indocosmo.mrp.web.production.bomanalysis;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

public class BomAnalysisExcelReport extends AbstractExcelView{

	String dateFormat;
	Integer decimalPlace;

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	public String getDateWithSystemFormat(String date) throws Exception 
	{
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}


	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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

		//department style
		CellStyle dep_cell_style = workbook.createCellStyle();
		dep_cell_style.setAlignment(CellStyle.ALIGN_LEFT);
		//setBorders(cell_style);
		dep_cell_style.setFont(header_font);

		//sl no style
		CellStyle serial_cell_style = workbook.createCellStyle();
		serial_cell_style.setAlignment(CellStyle.ALIGN_CENTER);
		//setBorders(cell_style);
		serial_cell_style.setFont(font);

		//decimal alignment style
		CellStyle decimal_cell_style = workbook.createCellStyle();
		decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		//setBorders(decimal_cell_style);
		decimal_cell_style.setFont(font);
		decimal_cell_style.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#.#"));

		//table header style
		CellStyle table_header_style = workbook.createCellStyle();
		table_header_style.setAlignment(CellStyle.ALIGN_CENTER);
		table_header_style.setFont(header_font);
		//setBorders(table_header_style);

		//sub header style
		CellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(CellStyle.ALIGN_CENTER);
		header_style.setFont(sub_header_font);

		//department title style
		CellStyle sub_header_style = workbook.createCellStyle();
		sub_header_style.setAlignment(CellStyle.ALIGN_LEFT);
		sub_header_style.setFont(header_font);
		//setBorders(sub_header_style);

		JsonArray itemData = (JsonArray)model.get("bomanalysisView");
		Report  report =   (Report) model.get("reportName");
		String fromDate = (String) model.get("startDate");
		String toDate = (String) model.get("endDate");

		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();

		int row_count = 0;
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 1000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4500);
		sheet.setColumnWidth(5, 4500);
		sheet.setColumnWidth(6, 4500);
		/*sheet.setColumnWidth(7, 4500);
		sheet.setColumnWidth(8, 4000);*/


		//style applied for report header
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);

		sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 6));
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);

		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);

		String sub_header = "DATE From: "+getDateWithSystemFormat(fromDate) +" To: "+getDateWithSystemFormat(toDate);	

		sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 6));
		HSSFRow row_subHeader = sheet.createRow(row_count++);

		HSSFCell cell_subHeader = row_subHeader.createCell(0);
		cell_subHeader.setCellValue(sub_header);
		cell_subHeader.setCellStyle(header_style);

		sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 6));
		HSSFRow rowDepartment = sheet.createRow(row_count++);

		HSSFCell colDepartment = rowDepartment.createCell(0);
		JsonObject itemDataObject = (JsonObject) itemData.get(0);
		colDepartment.setCellValue(itemDataObject.get("department_name").getAsString());
		colDepartment.setCellStyle(dep_cell_style);

		HSSFRow row_tableHead = sheet.createRow(row_count++);

		HSSFCell cell_head_sl_no = row_tableHead.createCell(0);
		cell_head_sl_no.setCellValue("#");
		cell_head_sl_no.setCellStyle(table_header_style);

		HSSFCell cell_head_itemName = row_tableHead.createCell(1);
		cell_head_itemName.setCellValue("ITEM NAME");
		cell_head_itemName.setCellStyle(table_header_style);

		HSSFCell cell_head_opening = row_tableHead.createCell(2);
		cell_head_opening.setCellValue("OPENING STOCK");
		cell_head_opening.setCellStyle(table_header_style);

		HSSFCell cell_head_stockin = row_tableHead.createCell(3);
		cell_head_stockin.setCellValue("STOCK IN");
		cell_head_stockin.setCellStyle(table_header_style);

		HSSFCell cell_head_totalStock = row_tableHead.createCell(4);
		cell_head_totalStock.setCellValue("TOTAL STOCK");
		cell_head_totalStock.setCellStyle(table_header_style);

		HSSFCell cell_head_stockOutBom = row_tableHead.createCell(5);
		cell_head_stockOutBom.setCellValue("STOCK OUT (BOM)");
		cell_head_stockOutBom.setCellStyle(table_header_style);

		HSSFCell cell_head_closing = row_tableHead.createCell(6);
		cell_head_closing.setCellValue("CLOSING STOCK");
		cell_head_closing.setCellStyle(table_header_style);

		/*HSSFCell cell_head_actual = row_tableHead.createCell(7);
		cell_head_actual.setCellValue("ACTUAL STOCK");
		cell_head_actual.setCellStyle(table_header_style);

		HSSFCell cell_head_variation = row_tableHead.createCell(8);
		cell_head_variation.setCellValue("VARIATION");
		cell_head_variation.setCellStyle(table_header_style);*/

		String sino = "";	
		int count = 0;
		for (int i = 0; i < itemData.size(); i++) {

			JsonObject BOMdata = (JsonObject) itemData.get(i);
			count++;
			sino = ""+count;

			HSSFRow row_tableData = sheet.createRow(row_count++);

			HSSFCell cell_sl_no = row_tableData.createCell(0);
			cell_sl_no.setCellValue(sino);
			cell_sl_no.setCellStyle(serial_cell_style);

			HSSFCell cell_itemName = row_tableData.createCell(1);
			cell_itemName.setCellValue(BOMdata.get("stock_item_name").getAsString());
			cell_itemName.setCellStyle(cell_style);

			HSSFCell cell_opening = row_tableData.createCell(2);
			cell_opening.setCellValue(getNumberWithDecimal(BOMdata.get("opening_stock").getAsDouble()));
			cell_opening.setCellStyle(decimal_cell_style);

			HSSFCell cell_stockin = row_tableData.createCell(3);
			cell_stockin.setCellValue(getNumberWithDecimal(BOMdata.get("stock_in").getAsDouble()));
			cell_stockin.setCellStyle(decimal_cell_style);

			HSSFCell cell_totalStock = row_tableData.createCell(4);
			cell_totalStock.setCellValue(getNumberWithDecimal(BOMdata.get("total").getAsDouble()));
			cell_totalStock.setCellStyle(decimal_cell_style);

			HSSFCell cell_stockOutBom = row_tableData.createCell(5);
			cell_stockOutBom.setCellValue(getNumberWithDecimal(BOMdata.get("bom_consumption").getAsDouble()));
			cell_stockOutBom.setCellStyle(decimal_cell_style);

			HSSFCell cell_closing = row_tableData.createCell(6);
			cell_closing.setCellValue(getNumberWithDecimal(BOMdata.get("closing_stock").getAsDouble()));
			cell_closing.setCellStyle(decimal_cell_style);

			/*HSSFCell cell_actual_amount = row_tableData.createCell(7);
			cell_actual_amount.setCellValue(getNumberWithDecimal(BOMdata.get("actual_stock").getAsDouble()));
			cell_actual_amount.setCellStyle(decimal_cell_style);

			Double variation = BOMdata.get("closing_stock").getAsDouble() - BOMdata.get("actual_stock").getAsDouble();

			HSSFCell cell_variation = row_tableData.createCell(8);
			cell_variation.setCellValue(getNumberWithDecimal(variation));
			cell_variation.setCellStyle(decimal_cell_style);*/
		}

		Date current_date = new Date();
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMMyy"); // without separators
		String str_current_date = date_format.format(current_date);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(report.getReportName().toLowerCase().replaceAll("\\s","")
				+ str_current_date + ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
}
