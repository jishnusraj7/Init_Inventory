package com.indocosmo.mrp.web.report.productionreport;

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
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;

public class ProdBalanceReportSummaryExcel extends AbstractExcelView {

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

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);

		return dateWithSystemFormat;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JsonArray orderhdrDateData1 = (JsonArray) model.get("prodBalSummaryViewExcelView");
		String Company_name = (String) model.get("companyId");

		Report report = (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		ProductionReportModel prod = (ProductionReportModel) model.get("prod");

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
		tophdr1.setFontHeightInPoints((short) 12);
		tophdr1.setColor(IndexedColors.BLACK.getIndex());
		tophdr1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle1 = workbook.createCellStyle();
		tophdrCellStyle1.setFont(tophdr1);
		tophdrCellStyle1.setWrapText(true);
		tophdrCellStyle1.setAlignment(CellStyle.ALIGN_CENTER);

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font tableHeadingDateFont = workbook.createFont();
		tableHeadingDateFont.setBoldweight((short) 10);
		tableHeadingDateFont.setFontHeightInPoints((short) 9);
		tableHeadingDateFont.setColor(IndexedColors.BLACK.getIndex());
		tableHeadingDateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tableHeadingDateFontCellStyle = workbook.createCellStyle();
		tableHeadingDateFontCellStyle.setFont(tableHeadingDateFont);
		tableHeadingDateFontCellStyle.setWrapText(true);
		tableHeadingDateFontCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		tableHeadingDateFontCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		tableHeadingDateFontCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		tableHeadingDateFontCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		tableHeadingDateFontCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

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
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setWrapText(true);
		contentCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setWrapText(true);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle slNoAndDateCellStyle2 = workbook.createCellStyle();
		slNoAndDateCellStyle2.setFont(contentFont);
		slNoAndDateCellStyle2.setWrapText(true);
		slNoAndDateCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		slNoAndDateCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		slNoAndDateCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		slNoAndDateCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		slNoAndDateCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

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
		contentCellStyleDept.setWrapText(true);
		contentCellStyleDept.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyleDept.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyleDept.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);

		if (orderhdrDateData.size() != 0) {
			sheet.setColumnWidth(0, 4000);
			sheet.setColumnWidth(1, 12000);
			sheet.setColumnWidth(2, 9000);
			CellStyle prodBalanceDateCellStyle = workbook.createCellStyle();

			String orderdate = "DATE: " + getDateWithSystemFormat(prod.getStartdate()) + "";

			String shop_code1 = "";
			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

			HSSFRow addressRow = sheet.createRow(1);
			HSSFCell Addcell = addressRow.createCell(0);
			addressRow.setHeightInPoints(50);
			Addcell.setCellValue(report.getCompanyAddress());
			Addcell.setCellStyle(tophdrCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

			HSSFRow nameRow = sheet.createRow(2);
			nameRow.setHeightInPoints(35);
			HSSFCell cellname = nameRow.createCell(0);
			cellname.setCellValue(report.getReportName());
			cellname.setCellStyle(rptNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));

			HSSFRow departmentRow = sheet.createRow(3);
			HSSFCell departmentCell = departmentRow.createCell(0);
			departmentRow.setHeightInPoints(23);
			departmentCell.setCellValue(Company_name);
			departmentCell.setCellStyle(contentCellStyleDept);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));

			// Merges the cells
			CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 2);
			sheet.addMergedRegion(merge1);

			HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
			HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);

			HSSFRow tableheading = sheet.createRow(3);
			tableheading.setHeightInPoints(25);

			HSSFCell cell_subHeader = tableheading.createCell(0);
			cell_subHeader.setCellValue(orderdate);

			prodBalanceDateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cell_subHeader.setCellStyle(prodBalanceDateCellStyle);
			if (report.getIs_customer().equals("2")) {
				HSSFCell cell_dateHead = tableheading.createCell(1);
				cell_dateHead.setCellValue("SHOP ORDERS");
				cell_dateHead.setCellStyle(headerCellStyle);
			} else if (report.getIs_customer().equals("1")) {
				HSSFCell cell_dateHead = tableheading.createCell(1);
				cell_dateHead.setCellValue("CUSTOMERS ORDERS");
				CellStyle cell_dateHeadCellStyle = workbook.createCellStyle();
				cell_dateHeadCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_dateHead.setCellStyle(headerCellStyle);
			}

			JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
			shop_code1 = orderdata5.get("orderfrom").getAsString();

			HSSFRow orderfromtableheading = sheet.createRow(4);

			HSSFCell cell_orderfrom = orderfromtableheading.createCell(0);
			cell_orderfrom.setCellValue(orderdata5.get("orderfrom").getAsString());
			cell_orderfrom.setCellStyle(contentCellStyleDept);

			HSSFRow tableheading1 = sheet.createRow(5);
			HSSFCell cell2 = tableheading1.createCell(0);
			cell2.setCellValue("#");
			cell2.setCellStyle(tableHeadingFontCellStyle);

			HSSFCell cell_itemNameHead = tableheading1.createCell(1);
			cell_itemNameHead.setCellValue("ITEM NAME");
			cell_itemNameHead.setCellStyle(tableHeadingFontCellStyle);

			HSSFCell cell_deliveryDate = tableheading1.createCell(2);
			cell_deliveryDate.setCellValue("BALANCE QTY");
			cell_deliveryDate.setCellStyle(tableHeadingFontCellStyle);

			int frt = 0;

			String sino = "";
			int count = 0;
			int rowCount = 6;
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				HSSFRow DataRow = sheet.createRow(rowCount);
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				if (!((orderdata.get("orderfrom").getAsString()).equals(shop_code1))) {
					HSSFCell cell_orderfrom1 = DataRow.createCell(0);
					cell_orderfrom1.setCellValue(orderdata.get("orderfrom").getAsString());

					shop_code1 = orderdata.get("orderfrom").getAsString();
					count = 0;

					rowCount++;
					DataRow = sheet.createRow(rowCount);
					HSSFCell cell3 = DataRow.createCell(0);
					cell3.setCellValue("#");
					cell3.setCellStyle(tableHeadingFontCellStyle);

					HSSFCell cell_itemNameHead1 = DataRow.createCell(1);
					cell_itemNameHead1.setCellValue("ITEM NAME");
					cell_itemNameHead1.setCellStyle(tableHeadingFontCellStyle);

					HSSFCell cell_deliveryDate1 = DataRow.createCell(2);
					cell_deliveryDate1.setCellValue("BALANCE QTY");
					cell_deliveryDate1.setCellStyle(tableHeadingFontCellStyle);
				}

				count++;
				sino = "" + count;

				HSSFCell cell_sino = DataRow.createCell(0);
				cell_sino.setCellValue(sino);
				cell_sino.setCellStyle(slNoAndDateCellStyle2);

				HSSFCell cell_stkName = DataRow.createCell(1);
				cell_stkName.setCellValue(orderdata.get("stock_item_name").getAsString());
				contentCellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
				cell_stkName.setCellStyle(contentCellStyle2);

				HSSFCell cell_blnceqty = DataRow.createCell(2);
				cell_blnceqty.setCellValue(getNumberWithDecimal(orderdata.get("balanceqty").getAsDouble()));
				cell_blnceqty.setCellStyle(contentCellData);

				rowCount++;
			}
		}
		// for no items available
		else {

			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

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
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));

			HSSFRow tableData = sheet.createRow(3);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));

		}

		Date currentDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyy"); // without
																				// separators
		String stringToday3 = simpleDateFormat.format(currentDate);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(report.getReportName().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

}
