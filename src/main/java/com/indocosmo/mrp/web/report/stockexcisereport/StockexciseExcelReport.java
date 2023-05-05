package com.indocosmo.mrp.web.report.stockexcisereport;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;

/*
 * 
 *  Done by anandu on 21-01-2020
 *   
 */

public class StockexciseExcelReport extends AbstractExcelView {

	String companyName;

	String addrsss;

	String dateFormat;

	Integer decimalPlace;

	String currency;

//	Integer docType;

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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

		Font headerFont = workbook.createFont();
		headerFont.setBoldweight((short) 12);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font contentFont = workbook.createFont();
		contentFont.setBoldweight((short) 7);
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentCellStyle.setFont(contentFont);

		Font headcontentFont = workbook.createFont();
		headcontentFont.setFontHeightInPoints((short) 12);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setFont(headcontentFont);
		
		Font TotalFont = workbook.createFont();
		TotalFont.setFontHeightInPoints((short) 9);
		TotalFont.setColor(IndexedColors.BLACK.getIndex());
		TotalFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle cellTotalStyle = workbook.createCellStyle();
		cellTotalStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		cellTotalStyle.setFont(TotalFont);

		CellStyle deptCellStyle = workbook.createCellStyle();
		deptCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		deptCellStyle.setFont(headcontentFont);

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

		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(headcontentFont);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);

		Report report = (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		addrsss = report.getCompanyAddress();

		StockExciseReport stockExcise = (StockExciseReport) model.get("exciseInvoice");

		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		String subHeader = "";
		sheet.setColumnWidth(0, 1300);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 2300);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 2300);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 2300);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 2300);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(10, 2300);
		sheet.setColumnWidth(11, 3000);
		if (stockExcise.getOption() == 1) {
			subHeader = "BETWEEN  " + stockExcise.getStartdate() + "  AND  " + stockExcise.getEnddate();

		} 
	/*	else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date yrrdate = new Date();
			yrrdate = df.parse(stockExcise.getStartdate());

			LocalDate yrdate = yrrdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = yrdate.getYear();

			SimpleDateFormat monthName = new SimpleDateFormat("MM");
			Date date2 = monthName.parse(stockExcise.getEnddate());
			SimpleDateFormat month = new SimpleDateFormat("MMMM");
			String dateTo = "";
			dateTo = month.format(date2);

			subHeader = dateTo.toUpperCase() + " " + year;
		}*/

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		HSSFRow companyRow = sheet.createRow(0);
		HSSFCell companyCell = companyRow.createCell(0);
		companyRow.setHeightInPoints(18);
		companyCell.setCellValue(report.getCompanyName());
		companyCell.setCellStyle(tophdrCellStyle1);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
		HSSFRow addressRow = sheet.createRow(1);
		HSSFCell Addcell = addressRow.createCell(0);
		addressRow.setHeightInPoints(50);
		Addcell.setCellValue(report.getCompanyAddress());
		Addcell.setCellStyle(tophdrCellStyle);

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 11));
		HSSFRow nameRow = sheet.createRow(2);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(rptNameCellStyle);

		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 11));
		HSSFRow rowSubHeader = sheet.createRow(3);
		HSSFCell cellSubHeader = rowSubHeader.createCell(0);
		cellSubHeader.setCellValue(subHeader);
		cellSubHeader.setCellStyle(subheaderCellStyle);

		if (stockExcise.getStockExcise().isEmpty()) {
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 11));
			HSSFRow emptyRow = sheet.createRow(4);
			HSSFCell cellNoData = emptyRow.createCell(0);
			cellNoData.setCellValue("No data available");
			cellNoData.setCellStyle(subheaderCellStyle);

		} else {
			HSSFRow rowTableHeader = sheet.createRow(4);

			sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0));
			HSSFCell cellNumber = rowTableHeader.createCell(0);
			cellNumber.setCellValue("SI#");
			cellNumber.setCellStyle(subheaderCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(4, 5, 1, 1));
			HSSFCell cellItem = rowTableHeader.createCell(1);
			cellItem.setCellValue("Name");
			cellItem.setCellStyle(subheaderCellStyle);

			HSSFCell cellOpStock = rowTableHeader.createCell(2);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));
			cellOpStock.setCellValue("Opening Stock");
			cellOpStock.setCellStyle(subheaderCellStyle);

			HSSFCell cellOpStockTemp = rowTableHeader.createCell(3);
			cellOpStockTemp.setCellValue("");
			cellOpStockTemp.setCellStyle(subheaderCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 5));
			HSSFCell cellPurchase = rowTableHeader.createCell(4);
			cellPurchase.setCellValue("Purchase");
			cellPurchase.setCellStyle(subheaderCellStyle);

			HSSFCell cellPurchaseTemp = rowTableHeader.createCell(5);
			cellPurchaseTemp.setCellValue("");
			cellPurchaseTemp.setCellStyle(subheaderCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 7));
			HSSFCell cellTotalPurchase = rowTableHeader.createCell(6);
			cellTotalPurchase.setCellValue("Total");
			cellTotalPurchase.setCellStyle(subheaderCellStyle);

			HSSFCell cellTotalPurchaseTemp = rowTableHeader.createCell(7);
			cellTotalPurchaseTemp.setCellValue("");
			cellTotalPurchaseTemp.setCellStyle(subheaderCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(4, 4, 8, 9));
			HSSFCell cellSales = rowTableHeader.createCell(8);
			cellSales.setCellValue("Sales");
			cellSales.setCellStyle(subheaderCellStyle);

			HSSFCell cellSalesTemp = rowTableHeader.createCell(9);
			cellSalesTemp.setCellValue("");
			cellSalesTemp.setCellStyle(subheaderCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(4, 4, 10, 11));
			HSSFCell cellClStock = rowTableHeader.createCell(10);
			cellClStock.setCellValue("Closing Stock");
			cellClStock.setCellStyle(subheaderCellStyle);

			HSSFCell cellClStockTemp = rowTableHeader.createCell(11);
			cellClStockTemp.setCellValue("");
			cellClStockTemp.setCellStyle(subheaderCellStyle);

			HSSFRow rowTableSubHeader = sheet.createRow(5);

			HSSFCell cellTemp = rowTableSubHeader.createCell(2);
			cellTemp.setCellValue("");
			cellTemp.setCellStyle(subheaderCellStyle);

			HSSFCell cellTemp1 = rowTableSubHeader.createCell(2);
			cellTemp1.setCellValue("");
			cellTemp1.setCellStyle(subheaderCellStyle);

			HSSFCell cellUnit1 = rowTableSubHeader.createCell(2);
			cellUnit1.setCellValue("Btl");
			cellUnit1.setCellStyle(subheaderCellStyle);

			HSSFCell cellDate = rowTableSubHeader.createCell(3);
			cellDate.setCellValue("Ltr");
			cellDate.setCellStyle(subheaderCellStyle);

			HSSFCell cellOpening = rowTableSubHeader.createCell(4);
			cellOpening.setCellValue("Btl");
			cellOpening.setCellStyle(subheaderCellStyle);

			HSSFCell cellValue = rowTableSubHeader.createCell(5);
			cellValue.setCellValue("Ltr");
			cellValue.setCellStyle(subheaderCellStyle);

			HSSFCell cellStockin = rowTableSubHeader.createCell(6);
			cellStockin.setCellValue("Btl");
			cellStockin.setCellStyle(subheaderCellStyle);

			HSSFCell cellQuant = rowTableSubHeader.createCell(7);
			cellQuant.setCellValue("Ltr");
			cellQuant.setCellStyle(subheaderCellStyle);

			HSSFCell cellStockout = rowTableSubHeader.createCell(8);
			cellStockout.setCellValue("Btl");
			cellStockout.setCellStyle(subheaderCellStyle);

			HSSFCell cellQuantity = rowTableSubHeader.createCell(9);
			cellQuantity.setCellValue("Ltr");
			cellQuantity.setCellStyle(subheaderCellStyle);

			HSSFCell cellbalance = rowTableSubHeader.createCell(10);
			cellbalance.setCellValue("Btl");
			cellbalance.setCellStyle(subheaderCellStyle);

			HSSFCell cellTotQuant = rowTableSubHeader.createCell(11);
			cellTotQuant.setCellValue("Ltr");
			cellTotQuant.setCellStyle(subheaderCellStyle);

			int count = 0;
			int row_count = 6;
			
			int temp1=0, temp2=0, temp3=0, temp4=0, temp5=0, temp6=0, temp7=0, temp8=0, temp9=0, temp10=0;
			BigDecimal sum, sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9;
			sum  = BigDecimal.valueOf(temp1);
			sum1  = BigDecimal.valueOf(temp2); 
			sum2  = BigDecimal.valueOf(temp3); 
			sum3  = BigDecimal.valueOf(temp4); 
			sum4  = BigDecimal.valueOf(temp5); 
			sum5  = BigDecimal.valueOf(temp6);
			sum6  = BigDecimal.valueOf(temp7);
			sum7  = BigDecimal.valueOf(temp8);
			sum8  = BigDecimal.valueOf(temp9);
			sum9  = BigDecimal.valueOf(temp10);
			
			for (StockExciseReport stkreg : stockExcise.getStockExcise()) {

				HSSFRow rowTableData = sheet.createRow(row_count++);
				count++;

				HSSFCell cellTotal = rowTableData.createCell(0);
				cellTotal.setCellStyle(contentCellStyle1);
				cellTotal.setCellValue(count);

				HSSFCell cellName = rowTableData.createCell(1);
				cellName.setCellStyle(contentCellStyle1);
				cellName.setCellValue(stkreg.getStockitemName());

				HSSFCell cellTotalQty = rowTableData.createCell(2);
				cellTotalQty.setCellStyle(contentCellStyle1);
				BigDecimal opening = new BigDecimal(stkreg.getOpening());
				opening = opening.setScale(2, BigDecimal.ROUND_DOWN);
				cellTotalQty.setCellValue(opening.toString());

				HSSFCell cellTotalStockin = rowTableData.createCell(3);
				cellTotalStockin.setCellStyle(contentCellStyle1);
				BigDecimal openingUnit = new BigDecimal(stkreg.getOpening_unit());
				openingUnit = openingUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cellTotalStockin.setCellValue(openingUnit.toString());

				HSSFCell cellTotalStockout = rowTableData.createCell(4);
				cellTotalStockout.setCellStyle(contentCellStyle1);
				BigDecimal Inqty = new BigDecimal(stkreg.getInQty());
				Inqty = Inqty.setScale(2, BigDecimal.ROUND_DOWN);
				cellTotalStockout.setCellValue(Inqty.toString());

				HSSFCell cellTotalbalance = rowTableData.createCell(5);
				cellTotalbalance.setCellStyle(contentCellStyle1);
				BigDecimal InqtyUnit = new BigDecimal(stkreg.getInqty_unit());
				InqtyUnit = InqtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cellTotalbalance.setCellValue(InqtyUnit.toString());

				BigDecimal total;
				total = Inqty.add(opening);
				total = total.setScale(2, BigDecimal.ROUND_DOWN);
				HSSFCell cellNumberData = rowTableData.createCell(6);
				cellNumberData.setCellStyle(contentCellStyle1);
				cellNumberData.setCellValue(total.toString());

				BigDecimal totalUnit;
				totalUnit = InqtyUnit.add(openingUnit);
				totalUnit = totalUnit.setScale(3, BigDecimal.ROUND_DOWN);
				HSSFCell cellItemData = rowTableData.createCell(7);
				cellItemData.setCellStyle(contentCellStyle1);
				cellItemData.setCellValue(totalUnit.toString());

				HSSFCell cellUnitData = rowTableData.createCell(8);
				cellUnitData.setCellStyle(contentCellStyle1);
				BigDecimal Outqty = new BigDecimal(stkreg.getOutQty());
				Outqty = Outqty.setScale(2, BigDecimal.ROUND_DOWN);
				cellUnitData.setCellValue(Outqty.toString());

				HSSFCell cellUnitData1 = rowTableData.createCell(9);
				cellUnitData1.setCellStyle(contentCellStyle1);
				BigDecimal OutqtyUnit = new BigDecimal(stkreg.getOutqty_unit());
				OutqtyUnit = OutqtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cellUnitData1.setCellValue(OutqtyUnit.toString());

				BigDecimal CLStock;
				CLStock = total.subtract(Outqty);
				CLStock = CLStock.setScale(2, BigDecimal.ROUND_DOWN);
				HSSFCell cellUnitData2 = rowTableData.createCell(10);
				cellUnitData2.setCellStyle(contentCellStyle1);
				cellUnitData2.setCellValue(CLStock.toString());

				BigDecimal CLStockUnit;
				CLStockUnit = totalUnit.subtract(OutqtyUnit);
				CLStockUnit = CLStockUnit.setScale(3, BigDecimal.ROUND_DOWN);
				HSSFCell cellUnitData3 = rowTableData.createCell(11);
				cellUnitData3.setCellStyle(contentCellStyle1);
				cellUnitData3.setCellValue(CLStockUnit.toString());
				
				
				sum= sum.add(opening);
				sum1 = sum1.add(openingUnit);
				sum2= sum2.add(Inqty);
				sum3= sum3.add(InqtyUnit);
				sum4= sum4.add(total);
				sum5= sum5.add(totalUnit);
				sum6= sum6.add(Outqty);
				sum7= sum7.add(OutqtyUnit);
				sum8= sum8.add(CLStock);
				sum9= sum9.add(CLStockUnit);

			}
			
			HSSFRow rowTableData = sheet.createRow(row_count++);
			count++;
			
			HSSFCell cellTotal = rowTableData.createCell(0);
			cellTotal.setCellStyle(contentCellStyle1);
			cellTotal.setCellValue("");

			HSSFCell cellName = rowTableData.createCell(1);
			cellName.setCellStyle(cellTotalStyle);
			cellName.setCellValue("Total");

			HSSFCell cellTotalQty = rowTableData.createCell(2);
			cellTotalQty.setCellStyle(cellTotalStyle);
			sum = sum.setScale(2, BigDecimal.ROUND_DOWN);
			cellTotalQty.setCellValue(sum.toString());

			HSSFCell cellTotalStockin = rowTableData.createCell(3);
			cellTotalStockin.setCellStyle(cellTotalStyle);
			sum1 = sum1.setScale(3, BigDecimal.ROUND_DOWN);
			cellTotalStockin.setCellValue(sum1.toString());

			HSSFCell cellTotalStockout = rowTableData.createCell(4);
			cellTotalStockout.setCellStyle(cellTotalStyle);
			sum2 = sum2.setScale(2, BigDecimal.ROUND_DOWN);
			cellTotalStockout.setCellValue(sum2.toString());

			HSSFCell cellTotalbalance = rowTableData.createCell(5);
			cellTotalbalance.setCellStyle(cellTotalStyle);
			sum3 = sum3.setScale(3, BigDecimal.ROUND_DOWN);
			cellTotalbalance.setCellValue(sum3.toString());

	
			sum4 = sum4.setScale(2, BigDecimal.ROUND_DOWN);
			HSSFCell cellNumberData = rowTableData.createCell(6);
			cellNumberData.setCellStyle(cellTotalStyle);
			cellNumberData.setCellValue(sum4.toString());


			sum5 = sum5.setScale(3, BigDecimal.ROUND_DOWN);
			HSSFCell cellItemData = rowTableData.createCell(7);
			cellItemData.setCellStyle(cellTotalStyle);
			cellItemData.setCellValue(sum5.toString());

			HSSFCell cellUnitData = rowTableData.createCell(8);
			cellUnitData.setCellStyle(cellTotalStyle);
			sum6 = sum6.setScale(2, BigDecimal.ROUND_DOWN);
			cellUnitData.setCellValue(sum6.toString());

			HSSFCell cellUnitData1 = rowTableData.createCell(9);
			cellUnitData1.setCellStyle(cellTotalStyle);
			sum7 = sum7.setScale(3, BigDecimal.ROUND_DOWN);
			cellUnitData1.setCellValue(sum7.toString());

			
			sum8 = sum8.setScale(2, BigDecimal.ROUND_DOWN);
			HSSFCell cellUnitData2 = rowTableData.createCell(10);
			cellUnitData2.setCellStyle(cellTotalStyle);
			cellUnitData2.setCellValue(sum8.toString());

			
			sum9 = sum9.setScale(3, BigDecimal.ROUND_DOWN);
			HSSFCell cellUnitData3 = rowTableData.createCell(11);
			cellUnitData3.setCellStyle(cellTotalStyle);
			cellUnitData3.setCellValue(sum9.toString());
		
			
		}

		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); 
		String stringToday3 = sdf3.format(today1);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse
				.concat(report.getReportName().toLowerCase().replaceAll("\\s", "") + stringToday3 + ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
}
