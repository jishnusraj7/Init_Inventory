package com.indocosmo.mrp.web.report.departmentwisereport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;
/*
 * @gana 160320
 */
public class DepartmentWiseStockExcelReport extends AbstractExcelView{

	Integer decimalPlace;

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DepartmentWiseReport departmentWiseReport = (DepartmentWiseReport) model.get("listInvoice");

		Report report = (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

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
		
		Font tophdrDate = workbook.createFont();
		tophdrDate.setFontHeightInPoints((short) 9);
		tophdrDate.setColor(IndexedColors.BLACK.getIndex());
		tophdrDate.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle dateCellStyle1 = workbook.createCellStyle();
		dateCellStyle1.setFont(tophdrDate);
		dateCellStyle1.setWrapText(true);
		dateCellStyle1.setAlignment(CellStyle.ALIGN_LEFT);

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

		CellStyle contentCellStyleUnit = workbook.createCellStyle();
		contentCellStyleUnit.setFont(contentFont);
		contentCellStyleUnit.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

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

		List<DepartmentWiseReport> departmentStockList=departmentWiseReport.getDepartmentWiseReport();
		if(!departmentStockList.isEmpty()) {

			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 9000);
			sheet.setColumnWidth(2, 3000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 3000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			
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

			HSSFRow headerRow = sheet.createRow(3);
			nameRow.setHeightInPoints(40);
			HSSFCell cellDate = headerRow.createCell(0);
			cellDate.setCellValue(departmentWiseReport.getItem_category_name());
			cellDate.setCellStyle(dateCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		//	HSSFRow headerRow = sheet.createRow(3);

			/*HSSFCell cellDate = headerRow.createCell(0);
			headerRow.setHeightInPoints(35);
			cellDate.setCellValue(departmentStockList.get(0).getStockDate());
			cellDate.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));*/
			// Merges the cells
			CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 6);
			sheet.addMergedRegion(merge1);

			HSSFRow tableheading = sheet.createRow(4);
			tableheading.setHeightInPoints(25);
			HSSFCell cellSi = tableheading.createCell(0);
			cellSi.setCellStyle(subheaderCellStyle);
			cellSi.setCellValue("SI");

			HSSFCell cellcode = tableheading.createCell(1);
			cellcode.setCellStyle(subheaderCellStyle);
			cellcode.setCellValue("Item");

			HSSFCell cellOpening = tableheading.createCell(2);
			cellOpening.setCellStyle(subheaderCellStyle);
			cellOpening.setCellValue("Opening Stock");

			HSSFCell cellPurchase = tableheading.createCell(3);
			cellPurchase.setCellStyle(subheaderCellStyle);
			cellPurchase.setCellValue("Purchase");

			HSSFCell cellTotal = tableheading.createCell(4);
			cellTotal.setCellStyle(subheaderCellStyle);
			cellTotal.setCellValue("Total");

			HSSFCell cellIssue = tableheading.createCell(5);
			cellIssue.setCellStyle(subheaderCellStyle);
			cellIssue.setCellValue("Issue");

			HSSFCell cellClosing = tableheading.createCell(6);
			cellClosing.setCellStyle(subheaderCellStyle);
			cellClosing.setCellValue("Closing Stock");

			String sino = "";
			int count = 0;
			for(int i=0;i<departmentStockList.size();i++) {

				sheet.addMergedRegion(merge1);
				CellRangeAddress merge2 = new CellRangeAddress(i, i, 0, 6);
				sheet.addMergedRegion(merge1);
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge2, sheet, workbook);

				HSSFRow tableheading1 = sheet.createRow(5+i);
				tableheading1.setHeightInPoints(25);
				//i++;
				count = count + 1;
				sino = "" + count;

				HSSFCell cellSi1 = tableheading1.createCell(0);
				cellSi1.setCellStyle(contentCellStyle2);
				cellSi1.setCellValue(sino);

				HSSFCell cellName = tableheading1.createCell(1);
				cellName.setCellStyle(contentCellStyle);
				cellName.setCellValue(departmentStockList.get(i).getStockItemName());

				HSSFCell cellOpeningStock = tableheading1.createCell(2);
				cellOpeningStock.setCellStyle(contentCellStyle1);
				cellOpeningStock.setCellValue(getNumberWithDecimal(departmentStockList.get(i).getOpeningStock()));

				HSSFCell cellStockIn = tableheading1.createCell(3);
				cellStockIn.setCellStyle(contentCellStyle1);
				cellStockIn.setCellValue(getNumberWithDecimal(departmentStockList.get(i).getStockIn()));

				double total=departmentStockList.get(i).getOpeningStock()+departmentStockList.get(i).getStockIn();
				HSSFCell cellTotalStock = tableheading1.createCell(4);
				cellTotalStock.setCellStyle(contentCellStyle1);
				cellTotalStock.setCellValue(getNumberWithDecimal(total));

				HSSFCell cellStockOut = tableheading1.createCell(5);
				cellStockOut.setCellStyle(contentCellStyle1);
				cellStockOut.setCellValue(getNumberWithDecimal(departmentStockList.get(i).getStockOut()));

				double closing=total-departmentStockList.get(i).getStockOut();
				HSSFCell cellClosingStock = tableheading1.createCell(6);
				cellClosingStock.setCellStyle(contentCellStyle1);
				cellClosingStock.setCellValue(getNumberWithDecimal(closing));


			}
		}else {
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

			HSSFRow tableData = sheet.createRow(3);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		}
	}

}
