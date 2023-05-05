package com.indocosmo.mrp.web.report.stockinreport;

import java.math.BigDecimal;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;

public class StockinExcelReport extends AbstractExcelView{

	Integer decimalPlace;
	
	public String getRateWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}
	public String getNumberWithDecimal(Double value){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		//report header font
		Font mainheadFont = workbook.createFont();
		mainheadFont.setFontName("Liberation Sans");;
		mainheadFont.setBoldweight((short) 14);
		mainheadFont.setFontHeightInPoints((short) 16);
		mainheadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		mainheadFont.setUnderline(Font.U_SINGLE);

		//header font
		Font header_font = workbook.createFont();
		header_font.setFontName("Liberation Sans");
		header_font.setFontHeightInPoints((short) 10);
		header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//sub header font
		Font sub_header_font = workbook.createFont();
		sub_header_font.setFontName("Liberation Sans");
		sub_header_font.setFontHeightInPoints((short) 11);
		sub_header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//style applied for report header
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);

		//common font style
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setFontHeightInPoints((short) 9);

		//normal style
		CellStyle cell_style = workbook.createCellStyle();
		cell_style.setAlignment(CellStyle.ALIGN_LEFT);
		setBorders(cell_style);
		cell_style.setFont(font);

		//decimal alignment style
		CellStyle decimal_cell_style = workbook.createCellStyle();
		decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorders(decimal_cell_style);
		decimal_cell_style.setFont(font);
		decimal_cell_style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

		//table header style
		CellStyle table_header_style = workbook.createCellStyle();
		table_header_style.setAlignment(CellStyle.ALIGN_CENTER);
		table_header_style.setFont(header_font);
		setBorders(table_header_style);

		//sub header style
		CellStyle subheader_style = workbook.createCellStyle();
		subheader_style.setAlignment(CellStyle.ALIGN_CENTER);
		subheader_style.setFont(sub_header_font);

		//department title style
		CellStyle title_style = workbook.createCellStyle();
		title_style.setAlignment(CellStyle.ALIGN_LEFT);
		title_style.setFont(header_font);
		setBorders(title_style);

		//total style
		CellStyle total_label_style = workbook.createCellStyle();
		total_label_style.setAlignment(CellStyle.ALIGN_RIGHT);
		total_label_style.setFont(header_font);
		setBorders(total_label_style);
		
		//decimal alignment style
		Font bold_font = workbook.createFont();
		bold_font.setFontName("Liberation Sans");
		bold_font.setFontHeightInPoints((short) 9);
		bold_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		CellStyle bold_decimal_cell_style = workbook.createCellStyle();
		bold_decimal_cell_style.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorders(bold_decimal_cell_style);
		bold_decimal_cell_style.setFont(bold_font);
		bold_decimal_cell_style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
				
		StockinReportModel  stockList =   (StockinReportModel) model.get("stockinSummaryView");
		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();

		int row_count = 0;
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4500);
		sheet.setColumnWidth(5, 4500);

		if(stockList.getOption() == 1)
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 5));//detailed report 
		else
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 7));// summary report
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);

		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);

		String subheader = "BETWEEN  " + stockList.getStartdate() + "  AND  " + stockList.getEnddate();
		if(stockList.getOption() == 1)
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 5));//detailed report 
		else
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 7));// summary report
		HSSFRow row_subHeader = sheet.createRow(row_count++);

		HSSFCell cell_subHeader = row_subHeader.createCell(0);
		cell_subHeader.setCellValue(subheader);
		cell_subHeader.setCellStyle(subheader_style);

		int count = 0;
		String grno = "";
		String item_category = "";
		if( !stockList.getPurchasreportData().isEmpty() )
		{
			grno = stockList.getPurchasreportData().get(0).getGrn_no();
			//detailed report
			if(stockList.getOption() == 1)
			{
				Double total_amount = 0.0;
				Double grand_total = 0.0;
				int is_new_GRN = 0;
				int is_new_category = 0;
				item_category = stockList.getPurchasreportData().get(0).getItm_cat_name();

				for (StockinReportModel stockindtl : stockList.getPurchasreportData()) 
				{
					grand_total += stockindtl.getAmount();
					if(!(stockindtl.getItm_cat_name().matches(item_category)))
					{
						is_new_category = 0; 
						item_category = stockindtl.getItm_cat_name();
					}
					//when new GRN number arrives find total of current GRN number 
					if(!(stockindtl.getGrn_no()).equals(grno))
					{
						is_new_GRN =0;
						count = 0;
						grno = stockindtl.getGrn_no();

						CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 4);
						sheet.addMergedRegion(cellRangeAddress);
						HSSFRow row_subtotal = sheet.createRow(row_count++);
						
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);

						HSSFCell cell_subtotal_head = row_subtotal.createCell(0);
						cell_subtotal_head.setCellValue("Total :");
						cell_subtotal_head.setCellStyle(total_label_style);

						HSSFCell cell_subtotal = row_subtotal.createCell(5);
						cell_subtotal.setCellValue(getRateWithDecimal(total_amount));
						cell_subtotal.setCellStyle(bold_decimal_cell_style);
						
						//make an empty row before a new GRN number
						CellRangeAddress empty_cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 5);
						sheet.addMergedRegion(empty_cellRangeAddress);
						HSSFRow row_empty = sheet.createRow(row_count++);
						
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, empty_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, empty_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, empty_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, empty_cellRangeAddress, sheet, workbook);

						HSSFCell cell_emptyrow = row_empty.createCell(0);
						cell_emptyrow.setCellStyle(total_label_style);
					}

					if(stockindtl.getGrn_no() == grno && is_new_GRN == 0)
					{
						is_new_GRN = 1;
						count = 0;
						total_amount = 0.0; //make total 0 when new GRN number assigned
						//table title with date and GRN number
						HSSFRow row_main_title = sheet.createRow(row_count++);

						HSSFCell cell_date_title = row_main_title.createCell(0);
						cell_date_title.setCellValue("Date");
						cell_date_title.setCellStyle(table_header_style);

						HSSFCell cell_date = row_main_title.createCell(1);
						cell_date.setCellValue(stockindtl.getReceived_date());
						cell_date.setCellStyle(cell_style);
						
						HSSFCell blank_cell1 = row_main_title.createCell(2);
						blank_cell1.setCellStyle(cell_style);
						
						HSSFCell blank_cell2 = row_main_title.createCell(3);
						blank_cell2.setCellStyle(cell_style);

						HSSFCell cell_gr_title = row_main_title.createCell(4);
						cell_gr_title.setCellValue("GRNO");
						cell_gr_title.setCellStyle(table_header_style);

						HSSFCell cell_gr_no = row_main_title.createCell(5);
						cell_gr_no.setCellValue(stockindtl.getGrn_no());
						cell_gr_no.setCellStyle(cell_style);
						//table title with supplier name
						HSSFRow row_sub_title = sheet.createRow(row_count++);

						HSSFCell cell_supplier_title = row_sub_title.createCell(0);
						cell_supplier_title.setCellValue("SUPPLIER");
						cell_supplier_title.setCellStyle(table_header_style);

						HSSFCell cell_supplier = row_sub_title.createCell(1);
						cell_supplier.setCellValue(stockindtl.getSupplier_name());
						cell_supplier.setCellStyle(cell_style);
						
						HSSFCell sub_title_blank_cell1 = row_sub_title.createCell(2);
						sub_title_blank_cell1.setCellStyle(cell_style);
						
						HSSFCell sub_title_blank_cell2 = row_sub_title.createCell(3);
						sub_title_blank_cell2.setCellStyle(cell_style);
						
						HSSFCell sub_title_blank_cell3 = row_sub_title.createCell(4);
						sub_title_blank_cell3.setCellStyle(cell_style);
						
						HSSFCell sub_title_blank_cell4 = row_sub_title.createCell(5);
						sub_title_blank_cell4.setCellStyle(cell_style);
						//table head
						HSSFRow row_table_head = sheet.createRow(row_count++);

						HSSFCell cell_serial_head = row_table_head.createCell(0);
						cell_serial_head.setCellValue("#");
						cell_serial_head.setCellStyle(table_header_style);

						HSSFCell cell_item_head = row_table_head.createCell(1);
						cell_item_head.setCellValue("Item Name");
						cell_item_head.setCellStyle(table_header_style);

						HSSFCell cell_unit_head = row_table_head.createCell(2);
						cell_unit_head.setCellValue("Unit");
						cell_unit_head.setCellStyle(table_header_style);

						HSSFCell cell_qty_head = row_table_head.createCell(3);
						cell_qty_head.setCellValue("Qty");
						cell_qty_head.setCellStyle(table_header_style);

						HSSFCell cell_rate_head = row_table_head.createCell(4);
						cell_rate_head.setCellValue("Rate");
						cell_rate_head.setCellStyle(table_header_style);

						HSSFCell cell_amount_head = row_table_head.createCell(5);
						cell_amount_head.setCellValue("Amount");
						cell_amount_head.setCellStyle(table_header_style);
						is_new_category = 0;
					}
					if(!(stockindtl.getItm_cat_name().matches(item_category)))
					{
						is_new_category = 1;
						item_category = stockindtl.getItm_cat_name();
					}
					//row for new category name 
					if(stockindtl.getItm_cat_name().matches(item_category) && is_new_category == 0)
					{
						CellRangeAddress category_cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 5);
						sheet.addMergedRegion(category_cellRangeAddress);
						HSSFRow row_category_name = sheet.createRow(row_count++);
						
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, category_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, category_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, category_cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, category_cellRangeAddress, sheet, workbook);

						HSSFCell cell_category = row_category_name.createCell(0);
						cell_category.setCellValue(stockindtl.getItm_cat_name());
						cell_category.setCellStyle(cell_style);						
						is_new_category = 1;
					}
					//table data
					count++;
					HSSFRow row_table = sheet.createRow(row_count++);

					HSSFCell cell_serial_no = row_table.createCell(0);
					cell_serial_no.setCellValue("" + count);
					cell_serial_no.setCellStyle(cell_style);

					HSSFCell cell_item_name = row_table.createCell(1);
					cell_item_name.setCellValue(stockindtl.getStock_item_name());
					cell_item_name.setCellStyle(cell_style);

					HSSFCell cell_unit = row_table.createCell(2);
					cell_unit.setCellValue(stockindtl.getUomname());
					cell_unit.setCellStyle(cell_style);

					HSSFCell cell_qty = row_table.createCell(3);
					cell_qty.setCellValue(getNumberWithDecimal(stockindtl.getReceived_qty()));
					cell_qty.setCellStyle(decimal_cell_style);

					HSSFCell cell_unit_price = row_table.createCell(4);
					cell_unit_price.setCellValue(getRateWithDecimal(stockindtl.getUnit_price()));
					cell_unit_price.setCellStyle(decimal_cell_style);

					HSSFCell cell_amount = row_table.createCell(5);
					cell_amount.setCellValue(getRateWithDecimal(stockindtl.getAmount()));
					cell_amount.setCellStyle(decimal_cell_style);

					total_amount = total_amount + stockindtl.getAmount();
				}
				// total amount of last GRN number
				CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 4);
				sheet.addMergedRegion(cellRangeAddress);
				HSSFRow row_subtotal = sheet.createRow(row_count++);
				
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);

				HSSFCell cell_subtotal_head = row_subtotal.createCell(0);
				cell_subtotal_head.setCellValue("Total :");
				cell_subtotal_head.setCellStyle(total_label_style);

				HSSFCell cell_subtotal = row_subtotal.createCell(5);
				cell_subtotal.setCellValue(getRateWithDecimal(total_amount));
				cell_subtotal.setCellStyle(bold_decimal_cell_style);
				
				//grand total of stock in
				CellRangeAddress grandtotl_cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 4);
				sheet.addMergedRegion(grandtotl_cellRangeAddress);
				HSSFRow row_grandtotal = sheet.createRow(row_count++);
				
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);

				HSSFCell cell_grandtotal_head = row_grandtotal.createCell(0);
				cell_grandtotal_head.setCellValue("Grand Total :");
				cell_grandtotal_head.setCellStyle(total_label_style);
				
				HSSFCell cell_grandtotal = row_grandtotal.createCell(5);
				cell_grandtotal.setCellValue(getRateWithDecimal(grand_total));
				cell_grandtotal.setCellStyle(bold_decimal_cell_style);
			}
			//summarized report
			else if( stockList.getOption() == 0 )
			{
				//reset column widths
				sheet.setColumnWidth(0, 2000);
				sheet.setColumnWidth(5, 10000);
				sheet.setColumnWidth(6, 4500);
				sheet.setColumnWidth(7, 4500);
				String stock_item_id = "0";
				//table head
				HSSFRow row_table_head = sheet.createRow(row_count++);

				HSSFCell cell_serial_head = row_table_head.createCell(0);
				cell_serial_head.setCellValue("#");
				cell_serial_head.setCellStyle(table_header_style);

				HSSFCell cell_item_head = row_table_head.createCell(1);
				cell_item_head.setCellValue("Item Name");
				cell_item_head.setCellStyle(table_header_style);

				HSSFCell cell_unit_head = row_table_head.createCell(2);
				cell_unit_head.setCellValue("Unit");
				cell_unit_head.setCellStyle(table_header_style);

				HSSFCell cell_date_head = row_table_head.createCell(3);
				cell_date_head.setCellValue("Date");
				cell_date_head.setCellStyle(table_header_style);

				HSSFCell cell_reference_head = row_table_head.createCell(4);
				cell_reference_head.setCellValue("REF. NO");
				cell_reference_head.setCellStyle(table_header_style);

				HSSFCell cell_supplier_head = row_table_head.createCell(5);
				cell_supplier_head.setCellValue("SUPPLIER");
				cell_supplier_head.setCellStyle(table_header_style);
				
				HSSFCell cell_stockin_head = row_table_head.createCell(6);
				cell_stockin_head.setCellValue("Stock In");
				cell_stockin_head.setCellStyle(table_header_style);
				
				HSSFCell cell_value_head = row_table_head.createCell(7);
				cell_value_head.setCellValue("Value");
				cell_value_head.setCellStyle(table_header_style);
				
				int is_new_stockitem = 0;
				Double stockin_total = 0.0;
				Double value_total = 0.0;
				Double stockin_grandtotal = 0.0;
				Double value_grandtotal = 0.0;

				if(!stockList.getPurchasreportData().isEmpty())
				{
					stock_item_id = stockList.getPurchasreportData().get(0).getStock_item_id();
				}
				int si_no = 0;
				for (StockinReportModel stockindtl : stockList.getPurchasreportData()) 
				{			
					stockin_grandtotal += stockindtl.getReceived_qty();
					value_grandtotal += (stockindtl.getReceived_qty() * stockindtl.getUnit_price());
					if( !(stockindtl.getStock_item_id() == stock_item_id) )
					{
						is_new_stockitem = 0;
						stock_item_id = stockindtl.getStock_item_id();
						
						CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 5);
						sheet.addMergedRegion(cellRangeAddress);
						HSSFRow row_subtotal = sheet.createRow(row_count++);
						
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);

						HSSFCell cell_subtotal_head = row_subtotal.createCell(0);
						cell_subtotal_head.setCellValue("Total :");
						cell_subtotal_head.setCellStyle(total_label_style);

						HSSFCell cell_stockin_subtotal = row_subtotal.createCell(6);
						cell_stockin_subtotal.setCellValue(getNumberWithDecimal(stockin_total));
						cell_stockin_subtotal.setCellStyle(bold_decimal_cell_style);
						
						HSSFCell cell_value_subtotal = row_subtotal.createCell(7);
						cell_value_subtotal.setCellValue(getRateWithDecimal(value_total));
						cell_value_subtotal.setCellStyle(bold_decimal_cell_style);
						
						stockin_total = 0.00;
						value_total = 0.00;
					}
					
					HSSFRow row_table = sheet.createRow(row_count++);
					if(is_new_stockitem == 0)
					{
						si_no++;
						HSSFCell cell_serial = row_table.createCell(0);
						cell_serial.setCellValue("" + si_no);
						cell_serial.setCellStyle(cell_style);
						
						HSSFCell cell_item = row_table.createCell(1);
						cell_item.setCellValue(stockindtl.getStock_item_name());
						cell_item.setCellStyle(cell_style);
						
						HSSFCell cell_unit = row_table.createCell(2);
						cell_unit.setCellValue(stockindtl.getUomname());
						cell_unit.setCellStyle(cell_style);
					}
					
					HSSFCell cell_date = row_table.createCell(3);
					cell_date.setCellValue(stockindtl.getReceived_date());
					cell_date.setCellStyle(cell_style);
					
					HSSFCell cell_ref = row_table.createCell(4);
					cell_ref.setCellValue(stockindtl.getGrn_no());
					cell_ref.setCellStyle(cell_style);
					
					HSSFCell cell_supplier = row_table.createCell(5);
					cell_supplier.setCellValue(stockindtl.getSupplier_name());
					cell_supplier.setCellStyle(cell_style);
					
					HSSFCell cell_stockin = row_table.createCell(6);
					cell_stockin.setCellValue(getNumberWithDecimal(stockindtl.getReceived_qty()));
					cell_stockin.setCellStyle(decimal_cell_style);
					
					HSSFCell cell_value = row_table.createCell(7);
					cell_value.setCellValue(getRateWithDecimal(stockindtl.getReceived_qty() * stockindtl.getUnit_price()));
					cell_value.setCellStyle(decimal_cell_style);
					
					stockin_total = stockin_total + stockindtl.getReceived_qty();
					value_total = value_total + (stockindtl.getReceived_qty() * stockindtl.getUnit_price());
				}
				// total amount of last item
				CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 5);
				sheet.addMergedRegion(cellRangeAddress);
				HSSFRow row_subtotal = sheet.createRow(row_count++);
				
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);

				HSSFCell cell_subtotal_head = row_subtotal.createCell(0);
				cell_subtotal_head.setCellValue("Total :");
				cell_subtotal_head.setCellStyle(total_label_style);

				HSSFCell cell_stockin_subtotal = row_subtotal.createCell(6);
				cell_stockin_subtotal.setCellValue(getNumberWithDecimal(stockin_total));
				cell_stockin_subtotal.setCellStyle(bold_decimal_cell_style);
				
				HSSFCell cell_value_subtotal = row_subtotal.createCell(7);
				cell_value_subtotal.setCellValue(getRateWithDecimal(value_total));
				cell_value_subtotal.setCellStyle(bold_decimal_cell_style);
				
				//grand total of stock in
				CellRangeAddress grandtotl_cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 5);
				sheet.addMergedRegion(grandtotl_cellRangeAddress);
				HSSFRow row_grandtotal = sheet.createRow(row_count++);
				
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, grandtotl_cellRangeAddress, sheet, workbook);

				HSSFCell cell_grandtotal_head = row_grandtotal.createCell(0);
				cell_grandtotal_head.setCellValue("Grand Total :");
				cell_grandtotal_head.setCellStyle(total_label_style);
				
				HSSFCell cell_stockin_grandtotal = row_grandtotal.createCell(6);
				cell_stockin_grandtotal.setCellValue(getNumberWithDecimal(stockin_grandtotal));
				cell_stockin_grandtotal.setCellStyle(bold_decimal_cell_style);
				
				HSSFCell cell_value_grandtotal = row_grandtotal.createCell(7);
				cell_value_grandtotal.setCellValue(getRateWithDecimal(value_grandtotal));
				cell_value_grandtotal.setCellStyle(bold_decimal_cell_style);
			}
		}
		else{ // no data available
			if(stockList.getOption() == 1)
				sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 5));
			else
				sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 7));
			HSSFRow row_nodata = sheet.createRow(row_count++);

			HSSFCell cell_nodata = row_nodata.createCell(0);
			cell_nodata.setCellValue("NO DATA AVAILABLE");
			cell_nodata.setCellStyle(subheader_style);
		}
		//set generated filename
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
