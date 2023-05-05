package com.indocosmo.mrp.web.report.bomanalysisreport;

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
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.bomanalysisreport.model.BomAnalysisReportModel;
import com.indocosmo.mrp.web.report.common.model.Report;

public class BomAnalysisReportSummaryExcel extends AbstractExcelView{
	
	
	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;
	
	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	
	public String getDateWithSystemFormat(String date) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat =dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);


		return dateWithSystemFormat;
	}
	

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonArray itemData=(JsonArray)model.get("bomanalysisSummaryExcelView");
		BomAnalysisReportModel bomanalysis =(BomAnalysisReportModel)model.get("bomanalysis");
		Report  report =   (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);


		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		Font tophdr1 = workbook.createFont();
		tophdr1.setFontHeightInPoints((short)12);
		tophdr1.setColor(IndexedColors.BLACK.getIndex());
		tophdr1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle1 = workbook.createCellStyle();
		tophdrCellStyle1.setFont(tophdr1);
		tophdrCellStyle1.setWrapText(true);
		tophdrCellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(tophdr.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		Font reprName = workbook.createFont();
		reprName.setFontName("Liberation Sans");
		
		reprName.setBoldweight((short) 14);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");
		
		// contentFont.setBoldweight((short) 9);
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setWrapText(true);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setWrapText(true);
		contentCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setWrapText(true);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle contentCellData = workbook.createCellStyle();
		contentCellData.setFont(contentFont);
		contentCellData.setWrapText(true);
		contentCellData.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellData.setBorderTop(CellStyle.BORDER_THIN);
		contentCellData.setBorderRight(CellStyle.BORDER_THIN);
		contentCellData.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellData.setAlignment(CellStyle.ALIGN_RIGHT);

		Font headcontentFont = workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(headcontentFont.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

		CellStyle contentCellStyleDept = workbook.createCellStyle();
		contentCellStyleDept.setFont(headcontentFont);
		contentCellStyleDept.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyleDept.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyleDept.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
		
		
		Font tableHeadingFont = workbook.createFont();
		tableHeadingFont.setBoldweight((short) 10);
		tableHeadingFont.setFontHeightInPoints((short) 9);
		tableHeadingFont.setColor(IndexedColors.BLACK.getIndex());
		tableHeadingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tableHeadingFontCellStyle = workbook.createCellStyle();
		tableHeadingFontCellStyle.setFont(tableHeadingFont);
		tableHeadingFontCellStyle.setWrapText(true);
		tableHeadingFontCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		tableHeadingFontCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		tableHeadingFontCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		tableHeadingFontCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		tableHeadingFontCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		if(itemData.size()!=0){
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			
			JsonObject json = (JsonObject) itemData.get(0);
			String proddate="BETWEEN "+getDateWithSystemFormat(bomanalysis.getStartdate())+" AND "
					+getDateWithSystemFormat(bomanalysis.getEnddate())+"";
			
			CellStyle orderdateCellStyle = workbook.createCellStyle();
			
			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			HSSFRow addressRow = sheet.createRow(1);
			HSSFCell Addcell = addressRow.createCell(0);
			addressRow.setHeightInPoints(50);
			Addcell.setCellValue(report.getCompanyAddress());
			Addcell.setCellStyle(tophdrCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

			HSSFRow nameRow = sheet.createRow(2);
			nameRow.setHeightInPoints(35);
			HSSFCell cellname = nameRow.createCell(0);
			cellname.setCellValue(report.getReportName());
			cellname.setCellStyle(rptNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));

			// Merges the cells
			CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 8);
			sheet.addMergedRegion(merge1);

			HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);

			HSSFRow tableheading = sheet.createRow(3);
			tableheading.setHeightInPoints(25);
			HSSFCell cell_subHeader = tableheading.createCell(0);
			cell_subHeader.setCellValue(proddate);
			orderdateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			orderdateCellStyle.setFont(reprName);
			cell_subHeader.setCellStyle(orderdateCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));
			
			HSSFRow tableheading1 = sheet.createRow(4);
			HSSFCell cell2 = tableheading1.createCell(0);
			cell2.setCellValue("#");
			cell2.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_itemNameHead = tableheading1.createCell(1);
			cell_itemNameHead.setCellValue("ITEM NAME");
			cell_itemNameHead.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_openingStock = tableheading1.createCell(2);
			cell_openingStock.setCellValue("OPENING STOCK");
			cell_openingStock.setCellStyle(tableHeadingFontCellStyle);
			
			
			HSSFCell cell_stockin = tableheading1.createCell(3);
			cell_stockin.setCellValue("STOCK IN");
			cell_stockin.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_totalStock = tableheading1.createCell(4);
			cell_totalStock.setCellValue("TOTAL STOCK");
			cell_totalStock.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_stockOutBom = tableheading1.createCell(5);
			cell_stockOutBom.setCellValue("STOCK OUT(BOM)");
			cell_stockOutBom.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_closingStock = tableheading1.createCell(6);
			cell_closingStock.setCellValue("CLOSING STOCK");
			cell_closingStock.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_actualStock = tableheading1.createCell(7);
			cell_actualStock.setCellValue("ACTUAL STOCK");
			cell_actualStock.setCellStyle(tableHeadingFontCellStyle);
			
			HSSFCell cell_variation = tableheading1.createCell(8);
			cell_variation.setCellValue("VARIATION");
			cell_variation.setCellStyle(tableHeadingFontCellStyle);
			
			String sino="";	
			int count=0,rowCount = 5;
			
			for (int i = 0; i < itemData.size(); i++){
				JsonObject orderdata = (JsonObject) itemData.get(i);
				count++;
				sino=""+count;
				
				HSSFRow DataRow = sheet.createRow(rowCount);
				HSSFCell cell_sino = DataRow.createCell(0);
				cell_sino.setCellValue(sino);
				contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				cell_sino.setCellStyle(contentCellStyle);
				
				HSSFCell cell_stkName = DataRow.createCell(1);
				cell_stkName.setCellValue(orderdata.get("stock_item_name").getAsString());
				contentCellStyle1.setAlignment(CellStyle.ALIGN_LEFT);
				cell_stkName.setCellStyle(contentCellStyle1);
				
				HSSFCell cell_opening_stock = DataRow.createCell(2);
				cell_opening_stock.setCellValue(getNumberWithDecimal(orderdata.get("opening_stock").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_opening_stock.setCellStyle(contentCellStyle2);
				
				HSSFCell cell_stock_in = DataRow.createCell(3);
				cell_stock_in.setCellValue(getNumberWithDecimal(orderdata.get("stock_in").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_stock_in.setCellStyle(contentCellStyle2);
				
				HSSFCell cell_total = DataRow.createCell(4);
				cell_total.setCellValue(getNumberWithDecimal(orderdata.get("total").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_total.setCellStyle(contentCellStyle2);
				
				HSSFCell cell_bom_consumption = DataRow.createCell(5);
				cell_bom_consumption.setCellValue(getNumberWithDecimal(orderdata.get("bom_consumption").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_bom_consumption.setCellStyle(contentCellStyle2);
				
				HSSFCell cell_closing_stock = DataRow.createCell(6);
				cell_closing_stock.setCellValue(getNumberWithDecimal(orderdata.get("closing_stock").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_closing_stock.setCellStyle(contentCellStyle2);
				
				HSSFCell cell_actual_stock = DataRow.createCell(7);
				cell_actual_stock.setCellValue(getNumberWithDecimal(orderdata.get("actual_stock").getAsDouble()));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_actual_stock.setCellStyle(contentCellStyle2);
				
				Double diff=orderdata.get("closing_stock").getAsDouble()-orderdata.get("actual_stock").getAsDouble();
				
				HSSFCell cell_diff = DataRow.createCell(8);
				cell_diff.setCellValue(getNumberWithDecimal(diff));
				contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_diff.setCellStyle(contentCellStyle2);
				
				rowCount++;
			}
		}
		else{
			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion( new CellRangeAddress(0, 0, 0, 10));

			HSSFRow addressRow = sheet.createRow(1);
			HSSFCell Addcell = addressRow.createCell(0);
			addressRow.setHeightInPoints(50);
			Addcell.setCellValue(report.getCompanyAddress());
			Addcell.setCellStyle(tophdrCellStyle);		
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow nameRow = sheet.createRow(2);
			nameRow.setHeightInPoints(35);
			HSSFCell cellname = nameRow.createCell(0);
			cellname.setCellValue(report.getReportName());
			cellname.setCellStyle(rptNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress( 2, 2,0,10));

			HSSFRow tableData = sheet.createRow(3);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));
		}
	}

}
