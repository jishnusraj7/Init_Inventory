package com.indocosmo.mrp.web.report.nonmoving;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.nonmoving.model.NonMovingStock;

public class NonMovingExcelReportBuilder extends AbstractExcelView{

	Integer decimalPlace;
	String dateFormat;
	public String getNumberWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		NonMovingStock itemstock =   (NonMovingStock) model.get("listcurrentStock");
		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		
		dateFormat = report.getDateFormat();
		DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat(dateFormat);
		toFormat.setLenient(false);
		Date date = fromFormat.parse(itemstock.getEnddate());
		System.out.println(toFormat.format(date));
		String end_date=toFormat.format(date);
		String mainHeader = "WITHIN "+ itemstock.getDays() +" DAYS AS ON  " + end_date +"";
		
		//DecimalFormat decimalFormat = new DecimalFormat("##.00");
		
		Font mainheadFont = workbook.createFont();
		mainheadFont.setFontName("Liberation Sans");;
		mainheadFont.setBoldweight((short) 14);
		mainheadFont.setFontHeightInPoints((short) 16);
		mainheadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		mainheadFont.setUnderline(Font.U_SINGLE);
		
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setFontHeightInPoints((short) 9);
		
		//decimal data style
		CellStyle decimal_cell_style = workbook.createCellStyle();
		decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorders(decimal_cell_style);
		decimal_cell_style.setFont(font);
		decimal_cell_style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
		
		//normal style
		CellStyle cell_style = workbook.createCellStyle();
		cell_style.setAlignment(CellStyle.ALIGN_LEFT);
		setBorders(cell_style);
		cell_style.setFont(font);
		
		//head style
		Font header_font = workbook.createFont();
		header_font.setFontName("Liberation Sans");
		header_font.setFontHeightInPoints((short) 10);
		header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				
		CellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(CellStyle.ALIGN_CENTER);
		header_style.setFont(header_font);
		
		//table head
		CellStyle table_header_style = workbook.createCellStyle();
		table_header_style.setAlignment(CellStyle.ALIGN_CENTER);
		table_header_style.setFont(header_font);
		setBorders(table_header_style);
		
		//category style
		CellStyle category_style = workbook.createCellStyle();
		category_style.setAlignment(CellStyle.ALIGN_LEFT);
		category_style.setFont(font);
		
		int row_count = 0;

		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 4500);
		
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);
		
		HSSFRow tableHeader = sheet.createRow(row_count++);
		tableHeader.setHeightInPoints( 25);

		sheet.addMergedRegion(new CellRangeAddress(1,1,0,3));
		HSSFCell dateCell = tableHeader.createCell(0);
		dateCell.setCellValue(mainHeader);
		dateCell.setCellStyle(header_style);
		
		if(itemstock.getItem_category_id() != 0)
		{
			HSSFRow itemCategoryRow = sheet.createRow(row_count++);
			itemCategoryRow.setHeightInPoints(15);
			
			HSSFCell itemCategoryLable = itemCategoryRow.createCell(0);
			itemCategoryLable.setCellValue("Item Category :");
			itemCategoryLable.setCellStyle(header_style);

			sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,1,3));
			HSSFCell itemCategory = itemCategoryRow.createCell(1);
			itemCategory.setCellValue(itemstock.getCategoryName());
			itemCategory.setCellStyle(category_style);
		}
		HSSFRow rowTableHead = sheet.createRow(row_count++);
		
		HSSFCell cellNumber = rowTableHead.createCell(0);
		cellNumber.setCellValue("SI#");
		cellNumber.setCellStyle(table_header_style);
		
		HSSFCell cellItemCode = rowTableHead.createCell(1);
		cellItemCode.setCellValue("ITEM CODE");
		cellItemCode.setCellStyle(table_header_style);
		
		HSSFCell cellItemName = rowTableHead.createCell(2);
		cellItemName.setCellValue("ITEM NAME");
		cellItemName.setCellStyle(table_header_style);
		
		HSSFCell cellCurrectStock = rowTableHead.createCell(3);
		cellCurrectStock.setCellValue("CURRENT STOCK");
		cellCurrectStock.setCellStyle(table_header_style);
		
		int serial_no = 0;
		for (NonMovingStock nonMovingStock : itemstock.getItemstock()) 
		{
			serial_no = serial_no + 1;
			HSSFRow rowTableData = sheet.createRow(row_count++);
			
			HSSFCell cellNumberData = rowTableData.createCell(0);
			cellNumberData.setCellStyle(cell_style);
			cellNumberData.setCellValue("" + serial_no);
			
			HSSFCell cellCodeData = rowTableData.createCell(1);
			cellCodeData.setCellStyle(cell_style);
			cellCodeData.setCellValue(nonMovingStock.getCode());
			
			HSSFCell cellItemNameData = rowTableData.createCell(2);
			cellItemNameData.setCellStyle(cell_style);
			cellItemNameData.setCellValue(nonMovingStock.getName());
			
			HSSFCell cellStockData = rowTableData.createCell(3);
			cellStockData.setCellType(Cell.CELL_TYPE_NUMERIC);
			cellStockData.setCellStyle(decimal_cell_style);
			cellStockData.setCellValue(getNumberWithDecimal(nonMovingStock.getCurrent_stock()));
		}
		
		Date current_date = new Date();
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMMyy"); // without separators
		String str_current_date = date_format.format(current_date);
		
		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(report.getReportName().toLowerCase().replaceAll("\\s","") +str_current_date+ ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
	
	
	public void setBorders(CellStyle cellStyle){
		
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
	}
}