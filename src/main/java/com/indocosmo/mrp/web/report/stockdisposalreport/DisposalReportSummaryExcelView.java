package com.indocosmo.mrp.web.report.stockdisposalreport;

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
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;

 
public class DisposalReportSummaryExcelView extends AbstractExcelView{

	Integer decimalPlace;
	String dateFormat;

	public String getDateWithSystemFormat(String date) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat =dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Font mainheadFont = workbook.createFont();
		mainheadFont.setFontName("Liberation Sans");;
		mainheadFont.setBoldweight((short) 14);
		mainheadFont.setFontHeightInPoints((short) 16);
		mainheadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		mainheadFont.setUnderline(Font.U_SINGLE);
		
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font header_font = workbook.createFont();
		header_font.setFontName("Liberation Sans");
		header_font.setFontHeightInPoints((short) 10);
		header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				
		CellStyle subHeader_style = workbook.createCellStyle();
		subHeader_style.setAlignment(CellStyle.ALIGN_LEFT);
		subHeader_style.setFont(header_font);
		setBorders(subHeader_style);
		
		CellStyle table_header_style = workbook.createCellStyle();
		table_header_style.setAlignment(CellStyle.ALIGN_CENTER);
		table_header_style.setFont(header_font);
		setBorders(table_header_style);
		
		//table head
		CellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(CellStyle.ALIGN_CENTER);
		header_style.setFont(header_font);
		
		//normal style
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setFontHeightInPoints((short) 9);
		
		CellStyle cell_style = workbook.createCellStyle();
		cell_style.setAlignment(CellStyle.ALIGN_LEFT);
		setBorders(cell_style);
		cell_style.setFont(font);
		
		//number representation style
		CellStyle decimal_cell_style = workbook.createCellStyle();
		decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorders(decimal_cell_style);
		decimal_cell_style.setFont(font);
		
		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		
		int row_count = 0;

		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);
		
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);
		
		DisposalReportModel prod = (DisposalReportModel)model.get("prod");

		JsonArray orderhdrData = (JsonArray)model.get("disposalReportView");
		JsonArray orderhdrDateData = (JsonArray) orderhdrData.get(0);
		if(orderhdrDateData.size()!=0){
			
			String subHeader = "BETWEEN " + getDateWithSystemFormat(prod.getStartdate()) + " AND "
						+ getDateWithSystemFormat(prod.getEnddate()) + "";
			
			HSSFRow rowSubHeader = sheet.createRow(row_count++);
			sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,0,3));
			HSSFCell cellSubHeader = rowSubHeader.createCell(0);
			cellSubHeader.setCellValue(subHeader);
			cellSubHeader.setCellStyle(header_style);
			
			JsonObject jsonObject = (JsonObject) orderhdrDateData.get(0);
			String department = jsonObject.get("department_name").getAsString();
			//first reason in jsonArray
			String reason = jsonObject.get("disposalreason").getAsString();
			
			HSSFRow rowDepartment = sheet.createRow(row_count++);
			
			HSSFCell cellDepartment = rowDepartment.createCell(0);
			cellDepartment.setCellValue(department);
			cellDepartment.setCellStyle(header_style);
			sheet.addMergedRegion(new CellRangeAddress(row_count-1, row_count-1, 0, 3));
			
			HSSFRow rowReason = sheet.createRow(row_count++);

			CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count-1, row_count-1 ,0 ,3);
			sheet.addMergedRegion(cellRangeAddress);
			//add borders for merged region
			HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
			HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
			HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
			HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
			
			HSSFCell cellReason = rowReason.createCell(0);
			cellReason.setCellValue(jsonObject.get("disposalreason").getAsString());
			cellReason.setCellStyle(subHeader_style);
			
			HSSFRow rowTableHeader = sheet.createRow(row_count++);
			
			HSSFCell cellSerial = rowTableHeader.createCell(0);
			cellSerial.setCellValue("#");
			cellSerial.setCellStyle(table_header_style);
			
			HSSFCell cellItem = rowTableHeader.createCell(1);
			cellItem.setCellValue("ITEM NAME");
			cellItem.setCellStyle(table_header_style);
			
			HSSFCell cellDisposeDate = rowTableHeader.createCell(2);
			cellDisposeDate.setCellValue("DISPOSAL DATE");
			cellDisposeDate.setCellStyle(table_header_style);
			
			HSSFCell cellDisposeQty = rowTableHeader.createCell(3);
			cellDisposeQty.setCellValue("DISPOSAL QTY");
			cellDisposeQty.setCellStyle(table_header_style);
			
			int sl_no = 1;
			for (int i = 0; i < orderhdrDateData.size(); i++) 
			{
				JsonObject json = (JsonObject) orderhdrDateData.get(i);
				//checking if reason already exists or not
				if(!((json.get("disposalreason").getAsString()).equals(reason))){
					
					sl_no = 1;
					HSSFRow rowReasonRep = sheet.createRow(row_count++);

					CellRangeAddress cellRangeAddressRep = new CellRangeAddress(row_count-1, row_count-1, 0, 3);
					sheet.addMergedRegion(cellRangeAddressRep);
					//add borders for merged region
					HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddressRep, sheet, workbook);
					HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddressRep, sheet, workbook);
					HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddressRep, sheet, workbook);
					HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddressRep, sheet, workbook);
					
					HSSFCell cellReasonRep = rowReasonRep.createCell(0);
					cellReasonRep.setCellValue(json.get("disposalreason").getAsString());
					cellReasonRep.setCellStyle(subHeader_style);
					reason = json.get("disposalreason").getAsString();
					
					HSSFRow rowTableHeaderRep = sheet.createRow(row_count++);
					
					HSSFCell cellSerialRep = rowTableHeaderRep.createCell(0);
					cellSerialRep.setCellValue("#");
					cellSerialRep.setCellStyle(table_header_style);
					
					HSSFCell cellItemRep = rowTableHeaderRep.createCell(1);
					cellItemRep.setCellValue("ITEM NAME");
					cellItemRep.setCellStyle(table_header_style);
					
					HSSFCell cellDisposeDateRep = rowTableHeaderRep.createCell(2);
					cellDisposeDateRep.setCellValue("DISPOSAL DATE");
					cellDisposeDateRep.setCellStyle(table_header_style);
					
					HSSFCell cellDisposeQtyRep = rowTableHeaderRep.createCell(3);
					cellDisposeQtyRep.setCellValue("DISPOSAL QTY");
					cellDisposeQtyRep.setCellStyle(table_header_style);
				}
				HSSFRow rowData = sheet.createRow(row_count++);
				
				reason=json.get("disposalreason").getAsString();
				HSSFCell cellSerialData = rowData.createCell(0);
				cellSerialData.setCellValue(sl_no++);
				cellSerialData.setCellStyle(decimal_cell_style);
				
				HSSFCell cellItemData = rowData.createCell(1);
				cellItemData.setCellValue(json.get("stock_item_name").getAsString());
				cellItemData.setCellStyle(cell_style);
				
				HSSFCell cellDateData = rowData.createCell(2);
				cellDateData.setCellValue(getDateWithSystemFormat(json.get("disposal_date").getAsString()));
				cellDateData.setCellStyle(cell_style);
				
				HSSFCell cellQtyData = rowData.createCell(3);
				cellQtyData.setCellStyle(decimal_cell_style);
				cellQtyData.setCellValue(json.get("disposeqty").getAsDouble());
			}
				
		}
		else{
			HSSFRow rowNoData = sheet.createRow(row_count++);
			sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,0,3));
			
			HSSFCell cellNoData = rowNoData.createCell(0);
			cellNoData.setCellValue("No Data Available");
		}
	}
	
	public void setBorders(CellStyle cellStyle){

		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
	}
}
