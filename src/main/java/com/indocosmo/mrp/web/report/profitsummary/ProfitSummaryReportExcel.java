package com.indocosmo.mrp.web.report.profitsummary;

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
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;

public class ProfitSummaryReportExcel extends AbstractExcelView {

	String companyName;

	String dateFormat;

	Integer decimalPlace;

	String currency;

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	public String getRateWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

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

		JsonArray orderhdrDateData1 = (JsonArray) model.get("profitSummaryViewExcelView");
		ProfitSummaryModel profit = (ProfitSummaryModel) model.get("profitSum");
		Report report = (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		// dateFormat = report.getDateFormat();

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
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 6000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			CellStyle profitdateCellStyle = workbook.createCellStyle();
			String orderdate = "BETWEEN " + getDateWithSystemFormat(profit.getStartdate()) + " AND "
					+ getDateWithSystemFormat(profit.getEnddate()) + "";

			

			if (report.getDocType() == 1) {
				
				HSSFRow companyRow = sheet.createRow(0);
				HSSFCell companyCell = companyRow.createCell(0);
				companyRow.setHeightInPoints(18);
				companyCell.setCellValue(report.getCompanyName());
				tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				companyCell.setCellStyle(tophdrCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

				HSSFRow addressRow = sheet.createRow(1);
				HSSFCell Addcell = addressRow.createCell(0);
				addressRow.setHeightInPoints(50);
				Addcell.setCellValue(report.getCompanyAddress());
				Addcell.setCellStyle(tophdrCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

				HSSFRow nameRow = sheet.createRow(2);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue(report.getReportName());
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));

				// Merges the cells
				CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 7);
				sheet.addMergedRegion(merge1);

				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);

				HSSFRow tableheading = sheet.createRow(3);
				tableheading.setHeightInPoints(25);

				HSSFCell cell_subHeader = tableheading.createCell(0);
				cell_subHeader.setCellValue(orderdate);

				profitdateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				profitdateCellStyle.setFont(tophdr);
				cell_subHeader.setCellStyle(tableHeadingFontCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
				
				
				HSSFRow tableheading1 = sheet.createRow(4);
				HSSFCell cell2 = tableheading1.createCell(0);
				cell2.setCellValue("SI#");
				cell2.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_shop = tableheading1.createCell(1);
				cell_shop.setCellValue("SHOP");
				cell_shop.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_material = tableheading1.createCell(2);
				cell_material.setCellValue("MATERIAL");
				cell_material.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_labour = tableheading1.createCell(3);
				cell_labour.setCellValue("LABOUR");
				cell_labour.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_other = tableheading1.createCell(4);
				cell_other.setCellValue("OTHER");
				cell_other.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_totalCost = tableheading1.createCell(5);
				cell_totalCost.setCellValue("TOTAL COST");
				cell_totalCost.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_sales = tableheading1.createCell(6);
				cell_sales.setCellValue("SALES");
				cell_sales.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_profit = tableheading1.createCell(7);
				cell_profit.setCellValue("PROFIT");
				cell_profit.setCellStyle(tableHeadingFontCellStyle);

				Double mateTotal = 0.0;
				Double labTotal = 0.0;
				Double othTotal = 0.0;
				Double totcostTotal = 0.0;
				Double salesTotal = 0.0;
				Double profitTotal = 0.0;

				int frt = 0;
				int count = 0;
				String stockitemname = "0";
				int flagName = 0;
				int rowCount = 5;

				for (int i = 0; i < orderhdrDateData.size(); i++) {

					int f = 0;
					frt = 0;
					HSSFRow DataRow = sheet.createRow(rowCount);
					if (i == 0) {
						frt = 1;
					}

					count++;
					String sino = "" + count;
					JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);

					HSSFCell cell_sino = DataRow.createCell(0);
					cell_sino.setCellValue(sino);
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_CENTER);
					cell_sino.setCellStyle(contentCellStyleDept);
					
					String shop_name1 = orderdata.get("shop_name").getAsString().toLowerCase();

					if (shop_name1.length() != 0) {
						shop_name1 = shop_name1.substring(0, 1).toUpperCase() + shop_name1.substring(1);
					}
					HSSFCell cell_stkName = DataRow.createCell(1);
					cell_stkName.setCellValue(shop_name1);
					cell_stkName.setCellStyle(contentCellStyleDept);
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_LEFT);

					HSSFCell cell_material_cost = DataRow.createCell(2);
					cell_material_cost.setCellValue((orderdata.get("material_cost") != null
							&& orderdata.get("material_cost").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("material_cost").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_material_cost.setCellStyle(contentCellStyleDept);

					mateTotal += orderdata.get("material_cost").getAsDouble();

					HSSFCell cell_labour_cost = DataRow.createCell(3);
					cell_labour_cost.setCellValue((orderdata.get("labour_cost") != null
							&& orderdata.get("labour_cost").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("labour_cost").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_labour_cost.setCellStyle(contentCellStyleDept);

					labTotal += orderdata.get("labour_cost").getAsDouble();

					HSSFCell cell_other_cost = DataRow.createCell(4);
					cell_other_cost.setCellValue((orderdata.get("other_cost") != null
							&& orderdata.get("other_cost").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("other_cost").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_other_cost.setCellStyle(contentCellStyleDept);

					othTotal += orderdata.get("other_cost").getAsDouble();

					HSSFCell cell_total_cost = DataRow.createCell(5);
					cell_total_cost.setCellValue((orderdata.get("total_cost") != null
							&& orderdata.get("total_cost").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("total_cost").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_total_cost.setCellStyle(contentCellStyleDept);

					totcostTotal += orderdata.get("total_cost").getAsDouble();

					HSSFCell cell_total_sales_amount = DataRow.createCell(6);
					cell_total_sales_amount.setCellValue((orderdata.get("total_sales_amount") != null
							&& orderdata.get("total_sales_amount").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("total_sales_amount").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_total_sales_amount.setCellStyle(contentCellStyleDept);

					salesTotal += orderdata.get("total_sales_amount").getAsDouble();

					HSSFCell cell_profitValue = DataRow.createCell(7);
					cell_profitValue.setCellValue(
							(orderdata.get("profit") != null && orderdata.get("profit").getAsString().length() != 0)
									? getRateWithDecimal(orderdata.get("profit").getAsDouble()) : "");
					contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_profitValue.setCellStyle(contentCellStyleDept);

					profitTotal += orderdata.get("profit").getAsDouble();

					flagName = 0;
					rowCount++;

				}

				HSSFRow totalRow = sheet.createRow(rowCount++);
				HSSFCell totalCell = totalRow.createCell(0);
				totalRow.setHeightInPoints(18);
				totalCell.setCellValue("TOTAL:");
				tophdrCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
				totalCell.setCellStyle(tophdrCellStyle1);
				System.out.println(rowCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount-1, rowCount-1, 0, 1));

				HSSFCell cell_mateTotal = totalRow.createCell(2);
				cell_mateTotal.setCellValue(getRateWithDecimal(Double.parseDouble(mateTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_mateTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_labTotal = totalRow.createCell(3);
				cell_labTotal.setCellValue(getRateWithDecimal(Double.parseDouble(labTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_labTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_othTotal = totalRow.createCell(4);
				cell_othTotal.setCellValue(getRateWithDecimal(Double.parseDouble(othTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_othTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_totcostTotal = totalRow.createCell(5);
				cell_totcostTotal.setCellValue(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_totcostTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_salesTotal = totalRow.createCell(6);
				cell_salesTotal.setCellValue(getRateWithDecimal(Double.parseDouble(salesTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_salesTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_profitTotal = totalRow.createCell(7);
				cell_profitTotal.setCellValue(getRateWithDecimal(Double.parseDouble(profitTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_profitTotal.setCellStyle(contentCellStyleDept);

			}

			else if (report.getDocType() == 2) {
				
				
				HSSFRow companyRow = sheet.createRow(0);
				HSSFCell companyCell = companyRow.createCell(0);
				companyRow.setHeightInPoints(18);
				companyCell.setCellValue(report.getCompanyName());
				tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				companyCell.setCellStyle(tophdrCellStyle);
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
				cell_subHeader.setCellValue(orderdate);

				profitdateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				profitdateCellStyle.setFont(reprName);
				cell_subHeader.setCellStyle(profitdateCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));
				
				
				String proddate;
				String stkName;

				JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
				proddate = orderdata5.get("prod_date").getAsString();
				stkName = orderdata5.get("stock_item_name").getAsString();

				HSSFRow dateheadingRow = sheet.createRow(4);
				HSSFCell cell_Date= dateheadingRow.createCell(0);
				cell_Date.setCellValue(getDateWithSystemFormat(orderdata5.get("prod_date").getAsString()));
				sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));
				tableHeadingDateFontCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				tableHeadingDateFontCellStyle.setBorderTop(CellStyle.BORDER_THIN);
				tableHeadingDateFontCellStyle.setBorderRight(CellStyle.BORDER_THIN);
				tableHeadingDateFontCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cell_Date.setCellStyle(tableHeadingDateFontCellStyle);
				
				
				HSSFRow tableheading2 = sheet.createRow(5);
				HSSFCell cell2 = tableheading2.createCell(0);
				cell2.setCellValue("SI#");
				cell2.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_shop = tableheading2.createCell(1);
				cell_shop.setCellValue("ITEM");
				cell_shop.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_material = tableheading2.createCell(2);
				cell_material.setCellValue("MATERIAL");
				cell_material.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_labour = tableheading2.createCell(3);
				cell_labour.setCellValue("LABOUR");
				cell_labour.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_other = tableheading2.createCell(4);
				cell_other.setCellValue("OTHER");
				cell_other.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_totalCost = tableheading2.createCell(5);
				cell_totalCost.setCellValue("TOTAL COST");
				cell_totalCost.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_sales = tableheading2.createCell(6);
				cell_sales.setCellValue("SALES");
				cell_sales.setCellStyle(tableHeadingFontCellStyle);
				
				HSSFCell cell_damage = tableheading2.createCell(7);
				cell_damage.setCellValue("DAMAGE");
				cell_damage.setCellStyle(tableHeadingFontCellStyle);

				HSSFCell cell_profit = tableheading2.createCell(8);
				cell_profit.setCellValue("PROFIT");
				cell_profit.setCellStyle(tableHeadingFontCellStyle);
				
				Double mateTotal = 0.0;
				Double labTotal=0.0;
				Double othTotal=0.0;
				Double totcostTotal=0.0;
				Double salesTotal=0.0;
				Double damageTotal=0.0;
				Double profitTotal=0.0;

				String sino="";
				int frt = 0;
				int count = 0;
				String stockitemname = "0";
				int flagName = 0;
				int rowCount = 6;

				for (int i = 0; i < orderhdrDateData.size(); i++){
					int f=0;
					frt=0;
					JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
					if(i==0)
					{
						frt=1;
					}
					count++;
					sino=""+count;
					if(!((orderdata.get("prod_date").getAsString()).equals(proddate))){
						HSSFRow totalRow = sheet.createRow(rowCount);
						HSSFCell totalCell = totalRow.createCell(0);
						totalRow.setHeightInPoints(18);
						totalCell.setCellValue("TOTAL:");
						tableHeadingFontCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
						tableHeadingFontCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
						tableHeadingFontCellStyle.setBorderTop(CellStyle.BORDER_THIN);
						tableHeadingFontCellStyle.setBorderRight(CellStyle.BORDER_THIN);
						tableHeadingFontCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
						totalCell.setCellStyle(tableHeadingFontCellStyle);
						System.out.println(rowCount);
						

						
						HSSFCell cell_mateTotal = totalRow.createCell(2);
						cell_mateTotal.setCellValue(getRateWithDecimal(Double.parseDouble(mateTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_mateTotal.setCellStyle(contentCellStyleDept);

						HSSFCell cell_labTotal = totalRow.createCell(3);
						cell_labTotal.setCellValue(getRateWithDecimal(Double.parseDouble(labTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_labTotal.setCellStyle(contentCellStyleDept);

						HSSFCell cell_othTotal = totalRow.createCell(4);
						cell_othTotal.setCellValue(getRateWithDecimal(Double.parseDouble(othTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_othTotal.setCellStyle(contentCellStyleDept);

						HSSFCell cell_totcostTotal = totalRow.createCell(5);
						cell_totcostTotal.setCellValue(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_totcostTotal.setCellStyle(contentCellStyleDept);

						HSSFCell cell_salesTotal = totalRow.createCell(6);
						cell_salesTotal.setCellValue(getRateWithDecimal(Double.parseDouble(salesTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_salesTotal.setCellStyle(contentCellStyleDept);
						
						HSSFCell cell_damageTotal = totalRow.createCell(7);
						cell_damageTotal.setCellValue(getRateWithDecimal(Double.parseDouble(damageTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_damageTotal.setCellStyle(contentCellStyleDept);

						HSSFCell cell_profitTotal = totalRow.createCell(8);
						cell_profitTotal.setCellValue(getRateWithDecimal(Double.parseDouble(profitTotal.toString())));
						contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
						cell_profitTotal.setCellStyle(contentCellStyleDept);
						
						f=1;
						rowCount = rowCount+1;
						HSSFRow dateheadingRow1 = sheet.createRow(rowCount);
						HSSFCell cell_Date1= dateheadingRow1.createCell(0);
						cell_Date1.setCellValue(getDateWithSystemFormat(orderdata.get("prod_date").getAsString()));
						sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 8));
						tableHeadingDateFontCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
						tableHeadingDateFontCellStyle.setBorderTop(CellStyle.BORDER_THIN);
						tableHeadingDateFontCellStyle.setBorderRight(CellStyle.BORDER_THIN);
						tableHeadingDateFontCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
						cell_Date1.setCellStyle(tableHeadingDateFontCellStyle);
						
						
						
						proddate=orderdata.get("prod_date").getAsString();
						
						rowCount = rowCount+1;
						HSSFRow tableheading3 = sheet.createRow(rowCount);
						HSSFCell cell3 = tableheading3.createCell(0);
						cell3.setCellValue("SI#");
						cell3.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_shop1 = tableheading3.createCell(1);
						cell_shop1.setCellValue("ITEM");
						cell_shop1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_material1 = tableheading3.createCell(2);
						cell_material1.setCellValue("MATERIAL");
						cell_material1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_labour1 = tableheading3.createCell(3);
						cell_labour1.setCellValue("LABOUR");
						cell_labour1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_other1 = tableheading3.createCell(4);
						cell_other1.setCellValue("Other");
						cell_other1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_totalCost1 = tableheading3.createCell(5);
						cell_totalCost1.setCellValue("TOTAL COST");
						cell_totalCost1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_sales1 = tableheading3.createCell(6);
						cell_sales1.setCellValue("SALES");
						cell_sales1.setCellStyle(tableHeadingFontCellStyle);
						
						HSSFCell cell_damage1 = tableheading3.createCell(7);
						cell_damage1.setCellValue("DAMAGE");
						cell_damage1.setCellStyle(tableHeadingFontCellStyle);

						HSSFCell cell_profit1 = tableheading3.createCell(8);
						cell_profit1.setCellValue("PROFIT");
						cell_profit1.setCellStyle(tableHeadingFontCellStyle);
						
						
						mateTotal = 0.0;
						   labTotal=0.0;
						   othTotal=0.0;
						   totcostTotal=0.0;
						   salesTotal=0.0;
						   damageTotal=0.0;
						   profitTotal=0.0;
						  
						   rowCount = rowCount+1; 
					}
					
					if(f==1)
					{
						count=0;
						sino="";
						count++;
						sino=""+count;
					}
					
					HSSFRow tableheading3 = sheet.createRow(rowCount);
					HSSFCell cell_sino = tableheading3.createCell(0);
					cell_sino.setCellValue(sino);
					contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
					cell_sino.setCellStyle(contentCellStyle2);
					
					HSSFCell cell_stock_item_name = tableheading3.createCell(1);
					cell_stock_item_name.setCellValue(orderdata.get("stock_item_name").getAsString());
					contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cell_stock_item_name.setCellStyle(contentCellStyle);
					
					HSSFCell cell_material_cost = tableheading3.createCell(2);
					cell_material_cost.setCellValue((orderdata.get("material_cost") != null && orderdata.get("material_cost").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("material_cost").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_material_cost.setCellStyle(contentCellStyle2);
					
					mateTotal+=orderdata.get("material_cost").getAsDouble();
					
					HSSFCell cell_labour_cost = tableheading3.createCell(3);
					cell_labour_cost.setCellValue((orderdata.get("labour_cost") != null && orderdata.get("labour_cost").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("labour_cost").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_labour_cost.setCellStyle(contentCellStyle2);
					
					labTotal+=orderdata.get("labour_cost").getAsDouble();
					
					HSSFCell cell_other_cost = tableheading3.createCell(4);
					cell_other_cost.setCellValue((orderdata.get("other_cost") != null && orderdata.get("other_cost").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("other_cost").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_other_cost.setCellStyle(contentCellStyle2);
					
					othTotal+=orderdata.get("other_cost").getAsDouble();
					
					HSSFCell cell_total_cost = tableheading3.createCell(5);
					cell_total_cost.setCellValue((orderdata.get("total_cost") != null && orderdata.get("total_cost").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("total_cost").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_total_cost.setCellStyle(contentCellStyle2);
					
					totcostTotal+=orderdata.get("total_cost").getAsDouble();
					
					HSSFCell cell_total_sales_amount = tableheading3.createCell(6);
					cell_total_sales_amount.setCellValue((orderdata.get("total_sales_amount") != null && orderdata.get("total_sales_amount").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("total_sales_amount").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_total_sales_amount.setCellStyle(contentCellStyle2);
					
					salesTotal+=orderdata.get("total_sales_amount").getAsDouble();
					
					HSSFCell cell_damage_cost = tableheading3.createCell(7);
					cell_damage_cost.setCellValue((orderdata.get("damage_cost") != null && orderdata.get("damage_cost").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("damage_cost").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_damage_cost.setCellStyle(contentCellStyle2);
					
					damageTotal+=orderdata.get("damage_cost").getAsDouble();
					
					HSSFCell cell_profit1 = tableheading3.createCell(8);
					cell_profit1.setCellValue((orderdata.get("profit") != null && orderdata.get("profit").getAsString()
							.length() != 0) ? getRateWithDecimal(orderdata.get("profit").getAsDouble()) : "");
					contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
					cell_profit1.setCellStyle(contentCellStyle2);
					
					profitTotal+=orderdata.get("profit").getAsDouble();
					
					rowCount++;

				}
				
				HSSFRow totalRow1 = sheet.createRow(rowCount++);
				HSSFCell totalCell = totalRow1.createCell(0);
				totalRow1.setHeightInPoints(18);
				totalCell.setCellValue("TOTAL:");
				tableHeadingFontCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
				totalCell.setCellStyle(tableHeadingFontCellStyle);
				System.out.println(rowCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount-1, rowCount-1, 0, 1));

				
				HSSFCell cell_mateTotal = totalRow1.createCell(2);
				cell_mateTotal.setCellValue(getRateWithDecimal(Double.parseDouble(mateTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_mateTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_labTotal = totalRow1.createCell(3);
				cell_labTotal.setCellValue(getRateWithDecimal(Double.parseDouble(labTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_labTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_othTotal = totalRow1.createCell(4);
				cell_othTotal.setCellValue(getRateWithDecimal(Double.parseDouble(othTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_othTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_totcostTotal = totalRow1.createCell(5);
				cell_totcostTotal.setCellValue(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_totcostTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_salesTotal = totalRow1.createCell(6);
				cell_salesTotal.setCellValue(getRateWithDecimal(Double.parseDouble(salesTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_salesTotal.setCellStyle(contentCellStyleDept);
				
				HSSFCell cell_damageTotal = totalRow1.createCell(7);
				cell_damageTotal.setCellValue(getRateWithDecimal(Double.parseDouble(damageTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_damageTotal.setCellStyle(contentCellStyleDept);

				HSSFCell cell_profitTotal = totalRow1.createCell(8);
				cell_profitTotal.setCellValue(getRateWithDecimal(Double.parseDouble(profitTotal.toString())));
				contentCellStyleDept.setAlignment(CellStyle.ALIGN_RIGHT);
				cell_profitTotal.setCellStyle(contentCellStyleDept);
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

		Date currentDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyy"); // without
																				// separators
		String stringToday3 = simpleDateFormat.format(currentDate);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(report.getReportName().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);

	}

	public double round(double value, int places) {

		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
