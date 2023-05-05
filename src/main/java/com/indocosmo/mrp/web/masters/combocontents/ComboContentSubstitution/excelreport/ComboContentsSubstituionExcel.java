package com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.excelreport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.masters.areacode.model.AreaCode;

public class ComboContentsSubstituionExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model , HSSFWorkbook workbook , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		AreaCode supplier = (AreaCode) model.get("invoiceData");
		HSSFSheet sheet = workbook.createSheet("Area Code Report");
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		// for font dtyle
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle = workbook.createCellStyle();
		HSSFFont hSSFFont = workbook.createFont();
		hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
		hSSFFont.setFontHeightInPoints((short) 10);
		hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		hSSFFont.setColor(HSSFColor.BLACK.index);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFont(hSSFFont);
		
		HSSFRow header = sheet.createRow(0);
		
		HSSFCell cell0 = header.createCell(0);
		cell0.setCellValue("CODE");
		cell0.setCellStyle(cellStyle);
		
		HSSFCell cell1 = header.createCell(1);
		cell1.setCellValue("NAME");
		cell1.setCellStyle(cellStyle);
		
		HSSFCell cell2 = header.createCell(2);
		cell2.setCellValue("DESCRIPTION");
		cell2.setCellStyle(cellStyle);

		
		int counter = 1;
		for (AreaCode departmentDta : supplier.getAreaExcelData()) {
			
			HSSFRow row = sheet.createRow(counter);
			row.createCell(0).setCellValue(departmentDta.getCode());
			row.createCell(1).setCellValue(departmentDta.getName());
			row.createCell(2).setCellValue(departmentDta.getDescription());
			

			counter++;
		}
		
	}
	
}
