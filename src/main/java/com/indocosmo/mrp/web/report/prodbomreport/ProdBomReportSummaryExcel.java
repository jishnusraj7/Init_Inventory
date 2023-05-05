package com.indocosmo.mrp.web.report.prodbomreport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;

public class ProdBomReportSummaryExcel extends AbstractExcelView{
	
	String companyName;
	
	String dateFormat;
	
	Integer decimalPlace;
	
	String currency;
	
	
	public String getDateWithSystemFormat(String date) throws Exception {

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}
	
	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JsonArray orderhdrDateData1 = (JsonArray) model.get("prodBomSummaryExcelView");
		ProdBomReportModel prod = (ProdBomReportModel) model.get("prodbom");
		Report  report =   (Report) model.get("reportName");
		dateFormat = report.getDateFormat();
		decimalPlace=report.getDecimalPlace();
		
		JsonArray orderhdrDateData = (JsonArray) orderhdrDateData1.get(0);
		
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

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle contentCellData = workbook.createCellStyle();
		contentCellData.setFont(contentFont);
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
		
		if (orderhdrDateData.size() != 0) {
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			CellStyle orderdateCellStyle = workbook.createCellStyle();
			
			String orderdate = "BETWEEN " + getDateWithSystemFormat(prod.getStartdate()) + " AND " + getDateWithSystemFormat(prod.getEnddate()) + "";
			
			JsonObject bomData = (JsonObject) orderhdrDateData.get(0);
			
			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

			HSSFRow addressRow = sheet.createRow(1);
			HSSFCell Addcell = addressRow.createCell(0);
			addressRow.setHeightInPoints(50);
			Addcell.setCellValue(report.getCompanyAddress());
			Addcell.setCellStyle(tophdrCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

			HSSFRow nameRow = sheet.createRow(2);
			nameRow.setHeightInPoints(35);
			HSSFCell cellname = nameRow.createCell(0);
			cellname.setCellValue(report.getReportName());
			cellname.setCellStyle(rptNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

			// Merges the cells
			CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 6);
			sheet.addMergedRegion(merge1);

			HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);

			HSSFRow tableheading = sheet.createRow(3);
			tableheading.setHeightInPoints(25);


			HSSFCell cell_subHeader = tableheading.createCell(0);
			cell_subHeader.setCellValue(orderdate);
			
			orderdateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			orderdateCellStyle.setFont(reprName);
			cell_subHeader.setCellStyle(orderdateCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
			
			
			HSSFRow tableheading1 = sheet.createRow(4);
			HSSFCell cell2 = tableheading1.createCell(0);
			cell2.setCellValue("SI#");
			cell2.setCellStyle(headerCellStyle);
			
			HSSFCell cell_itemNameHead = tableheading1.createCell(1);
			cell_itemNameHead.setCellValue("ITEM");
			cell_itemNameHead.setCellStyle(headerCellStyle);
			
			HSSFCell cell_deliveryDate = tableheading1.createCell(2);
			cell_deliveryDate.setCellValue("Txn Date");
			cell_deliveryDate.setCellStyle(headerCellStyle);
			
			HSSFCell cell_balanceqty = tableheading1.createCell(3);
			cell_balanceqty.setCellValue("Opening");
			cell_balanceqty.setCellStyle(headerCellStyle);
			
			HSSFCell cell_orderqty = tableheading1.createCell(4);
			cell_orderqty.setCellValue("In Qty");
			cell_orderqty.setCellStyle(headerCellStyle);
			
			HSSFCell cell_totalqty = tableheading1.createCell(5);
			cell_totalqty.setCellValue("Out Qty");
			cell_totalqty.setCellStyle(headerCellStyle);
			
			HSSFCell cell_issuedqty = tableheading1.createCell(6);
			cell_issuedqty.setCellValue("Balance");
			cell_issuedqty.setCellStyle(headerCellStyle);
			
			int frt = 0;
			int count = 0;
			String stockitemname = "0";
			int flagName=0;
			int rowCount =5;
			for (int i = 0; i < orderhdrDateData.size(); i++){
				
				int f = 0;
				frt = 0;
				
				HSSFRow DataRow = sheet.createRow(rowCount);
				if (i == 0) {
					frt = 1;
				}
				if(!stockitemname.equals(((JsonObject)orderhdrDateData.get(i)).get("stock_item_id").getAsString())){
					flagName=1;
					count++;
				}
				String sino = "" + count;
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				if(flagName==1){
					HSSFCell cell_sino = DataRow.createCell(0);
					cell_sino.setCellValue(sino);
					cell_sino.setCellStyle(contentCellStyleDept);

				}
				else{
					HSSFCell cell_sino = DataRow.createCell(0);
					cell_sino.setCellValue("");
					cell_sino.setCellStyle(contentCellStyleDept);
				}
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_CENTER);
				
				if(flagName==1){
					HSSFCell cell_stkName = DataRow.createCell(1);
					cell_stkName.setCellValue(orderdata.get("stock_item_name").getAsString());
					cell_stkName.setCellStyle(contentCellStyleDept);
				}
				else{
					HSSFCell cell_stkName = DataRow.createCell(1);
					cell_stkName.setCellValue("");
					cell_stkName.setCellStyle(contentCellStyleDept);
				}
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				
				HSSFCell cell_taxnDate = DataRow.createCell(2);
				cell_taxnDate.setCellValue(getDateWithSystemFormat(orderdata.get("txn_date").getAsString()));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				cell_taxnDate.setCellStyle(contentCellStyleDept);
				
				
				HSSFCell cell_opening = DataRow.createCell(3);
				cell_opening.setCellValue((orderdata.get("opening") != null && orderdata.get("opening").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("opening").getAsDouble()) : "");
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				cell_opening.setCellStyle(contentCellStyleDept);
				
				HSSFCell cell_inqty = DataRow.createCell(4);
				cell_inqty.setCellValue((orderdata.get("inqty") != null && orderdata.get("inqty").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("inqty").getAsDouble()) : "");
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				cell_inqty.setCellStyle(contentCellStyleDept);
				
				HSSFCell cell_bom_qty = DataRow.createCell(5);
				cell_bom_qty.setCellValue((orderdata.get("bom_qty") != null && orderdata.get("bom_qty").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("bom_qty").getAsDouble()) : "");
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				cell_bom_qty.setCellStyle(contentCellStyleDept);
				
				HSSFCell cell_diff_qty = DataRow.createCell(6);
				cell_diff_qty.setCellValue(getNumberWithDecimal(orderdata.get("diff_qty").getAsDouble()));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);
				cell_diff_qty.setCellStyle(contentCellStyleDept);
				
				stockitemname = ((JsonObject)orderhdrDateData.get(i)).get("stock_item_id").getAsString();
				flagName=0;
				rowCount++;
			}
			
		}
		
		else {

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
		
		
		Date currentDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = simpleDateFormat.format(currentDate);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(report.getReportName().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);
		
	}

}
