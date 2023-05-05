package com.indocosmo.mrp.web.stock.stocksummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.stocksummary.model.StockSummary;

/*
 * 
 *  @Author Gana
 */
public class StockSummaryReportExcelView extends AbstractExcelView{

	Integer decimalPlace;
	
	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//StockSummary stockSummary= (StockSummary) model.get("stockSummaryData");
		JsonArray stockSummaryData = (JsonArray)model.get("stockSummaryDataExcel");
		Report  report =  (Report) model.get("reportName");
		String fromDate = (String) model.get("fromDate");
		String toDate = (String) model.get("toDate");
		String department_name = (String) model.get("department");
		decimalPlace = report.getDecimalPlace();

		HSSFSheet sheet = workbook.createSheet(report.getReportName());

		Font headerFont = workbook.createFont();
		// headerFont.setBoldweight((short) 14);;
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

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
		tophdrCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font reprName=workbook.createFont();
		reprName.setFontName("Liberation Sans");;
		reprName.setBoldweight((short) 14);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);


		Font contentFont=workbook.createFont();
		contentFont.setFontName("Liberation Sans");;
		// contentFont.setBoldweight((short) 9);
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);



		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyle2= workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle contentCellData= workbook.createCellStyle();
		contentCellData.setFont(contentFont);
		contentCellData.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellData.setBorderTop(CellStyle.BORDER_THIN);
		contentCellData.setBorderRight(CellStyle.BORDER_THIN);
		contentCellData.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellData.setAlignment(CellStyle.ALIGN_RIGHT);

		Font headcontentFont=workbook.createFont();

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
		
		int count = 0;
		if(stockSummaryData.size()!=0) {

			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 3800);
			sheet.setColumnWidth(9, 3000);

			
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
			
			HSSFRow departmentRow = sheet.createRow(3);
			HSSFCell departmentCell = departmentRow.createCell(0);
			departmentRow.setHeightInPoints(23);
			departmentCell.setCellValue(department_name);
			departmentCell.setCellStyle(contentCellStyleDept);
			sheet.addMergedRegion( new CellRangeAddress(3, 3, 0, 10));

			// Create a Row
			HSSFRow headerRow = sheet.createRow(4);

			HSSFCell cell1 = headerRow.createCell(0);
			headerRow.setHeightInPoints(18);
			//cell1.setCellValue(depat_name);
			cell1.setCellStyle(headerCellStyle);

			// Merges the cells
			CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 10);
			sheet.addMergedRegion(merge1);

			HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);

			HSSFRow tableheading = sheet.createRow(4);
			tableheading.setHeightInPoints(25);
			HSSFCell cellSi = tableheading.createCell(0);
			cellSi.setCellStyle(subheaderCellStyle);
			cellSi.setCellValue("SI");

			HSSFCell cellItem = tableheading.createCell(1);
			cellItem.setCellStyle(subheaderCellStyle);
			cellItem.setCellValue("ITEM");

			HSSFCell cellName = tableheading.createCell(2);
			cellName.setCellStyle(subheaderCellStyle);
			cellName.setCellValue("Item Name");

			HSSFCell cellOpening = tableheading.createCell(3);
			cellOpening.setCellStyle(subheaderCellStyle);
			cellOpening.setCellValue("OPENING");

			HSSFCell cellStockIn = tableheading.createCell(4);
			cellStockIn.setCellStyle(subheaderCellStyle);
			cellStockIn.setCellValue("STOCK IN");

			HSSFCell cellStockOut = tableheading.createCell(5);
			cellStockOut.setCellStyle(subheaderCellStyle);
			cellStockOut.setCellValue("STOCK OUT");

			HSSFCell cellProduction = tableheading.createCell(6);
			cellProduction.setCellStyle(subheaderCellStyle);
			cellProduction.setCellValue("PRODUCTION");

			HSSFCell cellBom = tableheading.createCell(2);
			cellBom.setCellStyle(subheaderCellStyle);
			cellBom.setCellValue("BOM OUT");

			HSSFCell cellTransfer = tableheading.createCell(7);
			cellTransfer.setCellStyle(subheaderCellStyle);
			cellTransfer.setCellValue("TRANSFER");

			HSSFCell cellAdjustment = tableheading.createCell(8);
			cellAdjustment.setCellStyle(subheaderCellStyle);
			cellAdjustment.setCellValue("ADJUSTMENT");

			HSSFCell cellDisposal = tableheading.createCell(9);
			cellDisposal.setCellStyle(subheaderCellStyle);
			cellDisposal.setCellValue("DISPOSAL");

			HSSFCell cellClosing = tableheading.createCell(10);
			cellClosing.setCellStyle(subheaderCellStyle);
			cellClosing.setCellValue("CLOSING");

			int sino=1;
			for (int i = 0; i <stockSummaryData.size(); i++) {

				JsonObject stockSummary = (JsonObject) stockSummaryData.get(i);

				
				//int count=0;
				int toatalcnt=0;

				//int i=5;

				HSSFRow tableheading1 = sheet.createRow(5+i);				
				HSSFCell cellSi1 = tableheading1.createCell(0);
				cellSi1.setCellStyle(contentCellStyle2);
				cellSi1.setCellValue(sino);


				HSSFCell cellItemName = tableheading1.createCell(1);
				cellItemName.setCellStyle(contentCellStyle2);
				cellItemName.setCellValue(stockSummary.get("stock_item_name").getAsString());


				HSSFCell cellOpeningStock = tableheading1.createCell(2);
				cellOpeningStock.setCellStyle(contentCellData);
				cellOpeningStock.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("opening_stock").getAsString())));


				HSSFCell cellStockin = tableheading1.createCell(3);
				cellStockin.setCellStyle(contentCellData);
				cellStockin.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_in").getAsString())));


				HSSFCell cellStockout = tableheading1.createCell(4);
				cellStockout.setCellStyle(contentCellData);
				cellStockout.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_out").getAsString())));


				HSSFCell cellStockProduction = tableheading1.createCell(5);
				cellStockProduction.setCellStyle(contentCellData);
				cellStockProduction.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("production").getAsString())));


				HSSFCell cellStockBom = tableheading1.createCell(6);
				cellStockBom.setCellStyle(contentCellData);
				cellStockBom.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_out_BOM").getAsString())));


				HSSFCell cellStockTransfer = tableheading1.createCell(7);
				cellStockTransfer.setCellStyle(contentCellData);
				cellStockTransfer.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_transfer").getAsString())));


				HSSFCell cellStockAdjustment = tableheading1.createCell(8);
				cellStockAdjustment.setCellStyle(contentCellData);
				cellStockAdjustment.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_adjustment").getAsString())));


				HSSFCell cellStockDisposal = tableheading1.createCell(9);
				cellStockDisposal.setCellStyle(contentCellData);
				cellStockDisposal.setCellValue(getNumberWithDecimal(Double.parseDouble(stockSummary.get("stock_disposal").getAsString())));

                
				HSSFCell cellClosingStock = tableheading1.createCell(10);
				cellClosingStock.setCellStyle(contentCellData);
				cellClosingStock.setCellValue(getNumberWithDecimal(getCurrentStock(stockSummary)));
				count=count+1;
				sino=sino+1;
			}
		}else {

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
	
	private Double getCurrentStock(JsonObject stockSummary) {
		
		double currentStock = 0.0;
		double total_stock_in = stockSummary.get("opening_stock").getAsDouble() + stockSummary.get("stock_in").getAsDouble() 
				+ stockSummary.get("production").getAsDouble() + stockSummary.get("stock_adjustment").getAsDouble();
		
		double total_stock_out = stockSummary.get("stock_out").getAsDouble() + stockSummary.get("stock_out_BOM").getAsDouble()
				+ stockSummary.get("stock_transfer").getAsDouble() + stockSummary.get("stock_disposal").getAsDouble();
		
		currentStock = total_stock_in - total_stock_out;
		return currentStock;
}



}
