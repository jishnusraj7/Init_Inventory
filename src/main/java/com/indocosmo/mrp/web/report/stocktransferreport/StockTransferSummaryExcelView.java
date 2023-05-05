package com.indocosmo.mrp.web.report.stocktransferreport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stocktransferreport.model.StockTransferReportModel;

public class StockTransferSummaryExcelView extends AbstractExcelView {
	
	
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

	String dateFormat;
	Integer decimalPlace;

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

		//header font
		Font sub_header_font = workbook.createFont();
		sub_header_font.setFontName("Liberation Sans");
		sub_header_font.setFontHeightInPoints((short) 11);
		sub_header_font.setBoldweight(Font.BOLDWEIGHT_BOLD);

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
		CellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(CellStyle.ALIGN_CENTER);
		header_style.setFont(sub_header_font);

		//department title style
		CellStyle sub_header_style = workbook.createCellStyle();
		sub_header_style.setAlignment(CellStyle.ALIGN_LEFT);
		sub_header_style.setFont(header_font);
		setBorders(sub_header_style);

		JsonArray itemData=(JsonArray)model.get("stocktransferSummaryView");

		StockTransferReportModel stocktransfer = (StockTransferReportModel)model.get("stocktransfer");
		Report  report = (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		boolean is_ExternalTransfer = false;

		int row_count = 0;
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);

		//style applied for report header
		CellStyle mainHeader_cell_style = workbook.createCellStyle();
		mainHeader_cell_style.setFont(mainheadFont);
		mainHeader_cell_style.setAlignment(CellStyle.ALIGN_CENTER);

		sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);

		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(mainHeader_cell_style);

		if(itemData.size()!=0)
		{
			HashMap< String, List<Object> > itemDataMap = new HashMap<String, List<Object>>();
			for (int i = 0; i < itemData.size(); i++) 
			{
				JsonObject jsonObject = (JsonObject) itemData.get(i);
				if(jsonObject.get("transfer_type").getAsInt() == 0)
				{
					is_ExternalTransfer = true;
					if (itemDataMap.containsKey(jsonObject.get("dest_company_name").getAsString())){

						List<Object> jsonList = itemDataMap.get(jsonObject.get("dest_company_name").getAsString());
						jsonList.add(jsonObject);
						itemDataMap.put(jsonObject.get("dest_company_name").getAsString(), jsonList);

					}
					else{

						List<Object> jsonList = new  ArrayList<Object>();
						jsonList.add(jsonObject);
						itemDataMap.put(jsonObject.get("dest_company_name").getAsString(), jsonList);

					}
				}
				else{

					if (itemDataMap.containsKey(jsonObject.get("dest_department_name").getAsString())){

						List<Object> jsonList= itemDataMap.get(jsonObject.get("dest_department_name").getAsString());
						jsonList.add(jsonObject);
						itemDataMap.put(jsonObject.get("dest_department_name").getAsString(), jsonList);
					}
					else{

						List<Object> jsonList=new  ArrayList<Object>();
						jsonList.add(jsonObject);
						itemDataMap.put(jsonObject.get("dest_department_name").getAsString(), jsonList);
					}
				}
			}

			String sub_header = "";
			if(!is_ExternalTransfer){

				sub_header = "Internal Stock Transfer Between " + getDateWithSystemFormat(stocktransfer.getStartdate())
						+ " AND "+ getDateWithSystemFormat(stocktransfer.getEnddate()) + "";
			}
			else{

				sub_header = "External Stock Transfer Between " + getDateWithSystemFormat(stocktransfer.getStartdate())
						+ " AND "+ getDateWithSystemFormat(stocktransfer.getEnddate()) + "";
			}
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
			HSSFRow row_subHeader = sheet.createRow(row_count++);

			HSSFCell cell_subHeader = row_subHeader.createCell(0);
			cell_subHeader.setCellValue(sub_header);
			cell_subHeader.setCellStyle(header_style);

			//Company name (if external transfer) or department name (if internal transfer)
			Set set = itemDataMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iterator.next();
				String departMent_OR_Company = (String) entry.getKey();

				CellRangeAddress cellRangeAddress = new CellRangeAddress(row_count, row_count, 0, 4);
				sheet.addMergedRegion(cellRangeAddress);
				HSSFRow row_department = sheet.createRow(row_count++);
				//add borders for merged region
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, workbook);

				HSSFCell cell_department = row_department.createCell(0);
				cell_department.setCellValue(departMent_OR_Company);
				cell_department.setCellStyle(sub_header_style);

				HSSFRow row_tableHead = sheet.createRow(row_count++);

				HSSFCell cell_head0 = row_tableHead.createCell(0);
				cell_head0.setCellValue("DATE");
				cell_head0.setCellStyle(table_header_style);

				HSSFCell cell_head1 = row_tableHead.createCell(1);
				cell_head1.setCellValue("ITEM NAME");
				cell_head1.setCellStyle(table_header_style);

				HSSFCell cell_head2 = row_tableHead.createCell(2);
				cell_head2.setCellValue("QTY");
				cell_head2.setCellStyle(table_header_style);

				HSSFCell cell_head3 = row_tableHead.createCell(3);
				cell_head3.setCellValue("UNIT PRICE");
				cell_head3.setCellStyle(table_header_style);

				HSSFCell cell_head4 = row_tableHead.createCell(4);
				cell_head4.setCellValue("AMOUNT");
				cell_head4.setCellStyle(table_header_style);

				List<Object> jsonList =(List<Object>) entry.getValue();
				for (int i = 0; i < jsonList.size(); i++) {

					JsonObject trasferData = (JsonObject) itemData.get(i);

					HSSFRow row_table = sheet.createRow(row_count++);

					HSSFCell cell_transfer_date = row_table.createCell(0);
					cell_transfer_date.setCellValue(getDateWithSystemFormat(trasferData.get("stock_transfer_date").getAsString()));
					cell_transfer_date.setCellStyle(cell_style);

					HSSFCell cell_item_name = row_table.createCell(1);
					cell_item_name.setCellValue(trasferData.get("stock_item_name").getAsString());
					cell_item_name.setCellStyle(cell_style);

					HSSFCell cell_item_qty = row_table.createCell(2);
					cell_item_qty.setCellValue(getNumberWithDecimal(trasferData.get("issued_qty").getAsDouble()));
					cell_item_qty.setCellStyle(decimal_cell_style);

					HSSFCell cell_unit_price = row_table.createCell(3);
					cell_unit_price.setCellValue(getRateWithDecimal(trasferData.get("cost_price").getAsDouble()));
					cell_unit_price.setCellStyle(decimal_cell_style);

					HSSFCell cell_amount = row_table.createCell(4);
					cell_amount.setCellValue(getRateWithDecimal(trasferData.get("amount").getAsDouble()));
					cell_amount.setCellStyle(decimal_cell_style);
				}
			}
		}
		else{
			sheet.addMergedRegion(new CellRangeAddress(row_count, row_count, 0, 4));
			HSSFRow row_subHeader = sheet.createRow(row_count++);

			HSSFCell cell_subHeader = row_subHeader.createCell(0);
			cell_subHeader.setCellValue("No item Available");
			cell_subHeader.setCellStyle(header_style);
		}

		Date current_date = new Date();
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMMyy"); // without separators
		String str_current_date = date_format.format(current_date);

		String headerResponse = "attachment;filename=";
		String report_name = "";
		if(is_ExternalTransfer)
			report_name = "externalstocktransferreport";
		else
			report_name = "internalstocktransferreport";
		headerResponse = headerResponse.concat(report_name +str_current_date+ ".xls");
		response.addHeader("Content-disposition", headerResponse);
	}

	public String getDateWithSystemFormat(String date) throws Exception 
	{
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}

	public void setBorders(CellStyle cellStyle){

		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
	}

}