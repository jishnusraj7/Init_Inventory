package com.indocosmo.mrp.web.report.purchasereturnreport;

import java.math.BigDecimal;
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
import com.indocosmo.mrp.web.report.purchasereturnreport.model.PurchaseReturnReport;

/*
 * 
 * @gana 03042020
 */
public class PurchaseReturnExcelReport extends AbstractExcelView{

	Integer decimalPlace;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PurchaseReturnReport  returnStockList =   (PurchaseReturnReport) model.get("purchaseReturnExcelView");
		Report  report =   (Report) model.get("reportName");
		
		decimalPlace = report.getDecimalPlace();
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		int count=1;
		String siNo=null;
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

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(Font.BOLDWEIGHT_BOLD);
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
		
		contentFont.setFontHeightInPoints((short) 8);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);

		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);
		
		Font headcontentFont = workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle subheaderCellStyle1 = workbook.createCellStyle();
		subheaderCellStyle1.setFont(headcontentFont);
		subheaderCellStyle1.setWrapText(true);
		CellRangeAddress cellRangeAddress=new CellRangeAddress(0, 0, 0, 6);;

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 6000);
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

		String Header1 = "BETWEEN  " + returnStockList.getStartDate() + "  AND  " + returnStockList.getEndDate();
	    
		HSSFRow rangeRaw = sheet.createRow(3);
		rangeRaw.setHeightInPoints(18);
		HSSFCell cellRange = rangeRaw.createCell(0);
		cellRange.setCellValue(Header1);
		cellRange.setCellStyle(tophdrCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		
		if (returnStockList.getPurchaseReturnReportData().isEmpty()) {
			HSSFRow tableData = sheet.createRow(4);
			tableData.setHeightInPoints(25);
			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));
		}else {
			
			HSSFRow tableheading3 = sheet.createRow(4);
			tableheading3.setHeightInPoints(25);
			HSSFCell si = tableheading3.createCell(0);
			si.setCellStyle(subheaderCellStyle);
			si.setCellValue("SI#");

			HSSFCell name = tableheading3.createCell(1);
			name.setCellStyle(subheaderCellStyle);
			name.setCellValue("Item Name");

			HSSFCell unit = tableheading3.createCell(2);
			unit.setCellStyle(subheaderCellStyle);
			unit.setCellValue("Unit");

			HSSFCell retDate = tableheading3.createCell(3);
			retDate.setCellStyle(subheaderCellStyle);
			retDate.setCellValue("Return Date");

			HSSFCell supplier = tableheading3.createCell(4);
			supplier.setCellStyle(subheaderCellStyle);
			supplier.setCellValue("Supplier");

			HSSFCell refNo = tableheading3.createCell(5);
			refNo.setCellStyle(subheaderCellStyle);
			refNo.setCellValue("Ref No");
			
			HSSFCell retQty = tableheading3.createCell(6);
			retQty.setCellStyle(subheaderCellStyle);
			retQty.setCellValue("Return Qty");
			
			for (PurchaseReturnReport purchaseReturnReportData : returnStockList.getPurchaseReturnReportData()) {

				//count = count + 1;
				siNo = "" + count;
				
				HSSFRow tableheading1 = sheet.createRow(4+count);
				
				HSSFCell cellSi1 = tableheading1.createCell(0);
				cellSi1.setCellStyle(contentCellStyle);
				cellSi1.setCellValue(siNo);

				HSSFCell cellName = tableheading1.createCell(1);
				cellName.setCellStyle(contentCellStyle);
				cellName.setCellValue(purchaseReturnReportData.getStock_item_name());
				
				HSSFCell cellUomCode = tableheading1.createCell(2);
				cellUomCode.setCellStyle(contentCellStyle);
				cellUomCode.setCellValue(purchaseReturnReportData.getUomcode());
				
				HSSFCell cellReturnDate = tableheading1.createCell(3);
				cellReturnDate.setCellStyle(contentCellStyle);
				cellReturnDate.setCellValue(purchaseReturnReportData.getReturn_date());
				
				HSSFCell cellSupplierName = tableheading1.createCell(4);
				cellSupplierName.setCellStyle(contentCellStyle);
				cellSupplierName.setCellValue(purchaseReturnReportData.getSupplier_name());
				
				HSSFCell cellReturnNo = tableheading1.createCell(5);
				cellReturnNo.setCellStyle(contentCellStyle);
				cellReturnNo.setCellValue(purchaseReturnReportData.getReturn_no());
				
				HSSFCell cellReturnQty = tableheading1.createCell(6);
				cellReturnQty.setCellStyle(contentCellStyle2);
				cellReturnQty.setCellValue(getNumberWithDecimal(purchaseReturnReportData.getReturn_qty()));
				count++;
			}
		}
	}
	

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

}
