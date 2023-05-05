package com.indocosmo.mrp.web.report.stockregisterreport;

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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;

public class StockRegisterReportDesignExcel extends AbstractExcelView {
	
    String companyName;
    
    String addrsss;
	
	String dateFormat;
	
	Integer decimalPlace;
	
	String currency;
	
	Integer docType;
	
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

	@Override
	protected void buildExcelDocument(Map<String, Object> model , HSSFWorkbook workbook , HttpServletRequest request ,
			HttpServletResponse response) throws Exception 
	{
		
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
		tophdr.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight((short) 12);;
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font contentFont=workbook.createFont();
		contentFont.setBoldweight((short) 12);
		contentFont.setFontHeightInPoints((short) 12);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentCellStyle.setFont(contentFont);

		Font headcontentFont=workbook.createFont();
		headcontentFont.setFontHeightInPoints((short) 12);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setFont(headcontentFont);
		
		CellStyle deptCellStyle = workbook.createCellStyle();
		deptCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		deptCellStyle.setFont(headcontentFont);
		
		
		
		Font reprName=workbook.createFont();
		reprName.setFontName("Liberation Sans");;
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

		StockRegisterReport stockRegister = (StockRegisterReport) model.get("listInvoice");

		HSSFSheet sheet = workbook.createSheet(report.getReportName());

		String dateTo = "";
		String subHeader = "";
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 10000);
		sheet.setColumnWidth(2, 3000);

		if (stockRegister.getOption() == 1) 
		{
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,7));
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,7));
			sheet.addMergedRegion(new CellRangeAddress(3,3,0,7));
			sheet.addMergedRegion(new CellRangeAddress(4,4,0,7));
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			subHeader = "BETWEEN  " + stockRegister.getStartdate() + "  AND  " + stockRegister.getEnddate();
		}
		else {
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,10));
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,10));
			sheet.addMergedRegion(new CellRangeAddress(3,3,0,10));
			sheet.addMergedRegion(new CellRangeAddress(4,4,0,10));
			
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 4000);
			sheet.setColumnWidth(8, 4000);
			sheet.setColumnWidth(9, 4000);
			sheet.setColumnWidth(10,4000);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date yrrdate = new Date();
			yrrdate = df.parse(stockRegister.getStartdate());
			
			LocalDate yrdate = yrrdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = yrdate.getYear();
			
			
			
			SimpleDateFormat monthName = new SimpleDateFormat("MM");
			Date date2 = monthName.parse(stockRegister.getEnddate());
			SimpleDateFormat month = new SimpleDateFormat("MMMM");
			
			dateTo = month.format(date2);
			
			
			
			
			subHeader = dateTo.toUpperCase() + " " + year;
		}
		
		
		HSSFRow companyRow = sheet.createRow(0);
		HSSFCell companyCell = companyRow.createCell(0);
		companyRow.setHeightInPoints(18);
		companyCell.setCellValue(report.getCompanyName());
		companyCell.setCellStyle(tophdrCellStyle1);

		HSSFRow addressRow = sheet.createRow(1);
		HSSFCell Addcell = addressRow.createCell(0);
		addressRow.setHeightInPoints(50);
		Addcell.setCellValue(report.getCompanyAddress());
		Addcell.setCellStyle(tophdrCellStyle);		
		
		int row_count = 2;
		HSSFRow nameRow = sheet.createRow(row_count++);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(rptNameCellStyle);
		
		HSSFRow rowSubHeader = sheet.createRow(row_count++);

		HSSFCell cellSubHeader = rowSubHeader.createCell(0);
		cellSubHeader.setCellValue(subHeader);
		cellSubHeader.setCellStyle(subheaderCellStyle);
		
		
		HSSFRow rowDepartment = sheet.createRow(row_count++);

		HSSFCell cellDepartment = rowDepartment.createCell(0);
		cellDepartment.setCellValue(stockRegister.getDepartment_name());
		cellDepartment.setCellStyle(deptCellStyle);

		if(stockRegister.getStockRegister().isEmpty())
		{
			if (stockRegister.getOption() == 1) 
			{
				sheet.addMergedRegion(new CellRangeAddress(5,5,0,7));
			}else{
				sheet.addMergedRegion(new CellRangeAddress(5,5,0,10));
			}
			HSSFRow emptyRow = sheet.createRow(row_count++);
			HSSFCell cellNoData = emptyRow.createCell(0);
			cellNoData.setCellValue("No data available");
			cellNoData.setCellStyle(subheaderCellStyle);

		}
		else{
			int col_count = 0;
			HSSFRow rowTableHeader = sheet.createRow(row_count++);
			
			HSSFCell cellNumber = rowTableHeader.createCell(col_count++);
			cellNumber.setCellValue("SI#");
			cellNumber.setCellStyle(subheaderCellStyle);

			
			HSSFCell cellItem = rowTableHeader.createCell(col_count++);
			cellItem.setCellValue("Item");
			cellItem.setCellStyle(subheaderCellStyle);

			
			HSSFCell cellUnit = rowTableHeader.createCell(col_count++);
			cellUnit.setCellValue("Unit");
			cellUnit.setCellStyle(subheaderCellStyle);

			
			if (stockRegister.getOption() == 1) {
				HSSFCell cellDate = rowTableHeader.createCell(col_count++);
				cellDate.setCellValue("Date");
				cellDate.setCellStyle(subheaderCellStyle);

			}
			
			HSSFCell cellOpening = rowTableHeader.createCell(col_count++);
			cellOpening.setCellValue("Opening");
			cellOpening.setCellStyle(subheaderCellStyle);

			
			if(stockRegister.getOption() != 1){
				HSSFCell cellValue = rowTableHeader.createCell(col_count++);
				cellValue.setCellValue("Value");
				cellValue.setCellStyle(subheaderCellStyle);

			}
			
			HSSFCell cellStockin = rowTableHeader.createCell(col_count++);
			cellStockin.setCellValue("Stock In");
			cellStockin.setCellStyle(subheaderCellStyle);

			
			if(stockRegister.getOption() != 1){
				HSSFCell cellValue = rowTableHeader.createCell(col_count++);
				cellValue.setCellValue("Value");
				cellValue.setCellStyle(subheaderCellStyle);

			}
			
			HSSFCell cellStockout = rowTableHeader.createCell(col_count++);
			cellStockout.setCellValue("Stock Out");
			cellStockout.setCellStyle(subheaderCellStyle);

			
			if(stockRegister.getOption() != 1){
				HSSFCell cellValue = rowTableHeader.createCell(col_count++);
				cellValue.setCellValue("Value");
				cellValue.setCellStyle(subheaderCellStyle);

			}
			
			HSSFCell cellbalance = rowTableHeader.createCell(col_count++);
			cellbalance.setCellValue("Balance");
			cellbalance.setCellStyle(subheaderCellStyle);

			
			if(stockRegister.getOption() != 1){
				HSSFCell cellValue = rowTableHeader.createCell(col_count++);
				cellValue.setCellValue("Value");
				cellValue.setCellStyle(subheaderCellStyle);

			}
		
		
			String stockitemname = "0";
	
			int f = 0;
			int firstRowOpening = 0;
			int firstRow = 0;
			if (!stockRegister.getStockRegister().isEmpty()) {
				stockitemname = stockRegister.getStockRegister().get(0).getStock_item_id();
			}
	
			Double balance = 0.0;
			Double total = 0.0;
			Double openingValue = 0.0;
			Double inqtyValue = 0.0;
			Double outqtyValue = 0.0;
			Double balanceValue = 0.0;
			Double stockintotal = 0.0;
			Double stockouttotal = 0.0;
			Double totalstockinItem = 0.0;
			Double totalstockoutItem = 0.0;
			Double curropen = 0.0;
			int count = 1;
			for (StockRegisterReport stkreg : stockRegister.getStockRegister()) {
	
				HSSFRow rowTableData1 =null;
				if (stockRegister.getOption() == 1) {
	
					if (!(stkreg.getStock_item_id().equals(stockitemname)))
					{
						f = 0;
						firstRowOpening = 0;
						stockitemname = stkreg.getStock_item_id();
	
					}
	
					if ((stkreg.getStock_item_id().equals(stockitemname)) && f == 1) {
	
						HSSFRow rowTableData = sheet.createRow(row_count++);
	
						HSSFCell cellNumberData = rowTableData.createCell(0);
						cellNumberData.setCellValue("");
	
						HSSFCell cellItemData = rowTableData.createCell(1);
						cellItemData.setCellValue("");
	
						HSSFCell cellUnitData = rowTableData.createCell(2);
						cellUnitData.setCellValue("");
					}
					else {
						if ((stkreg.getStock_item_id().equals(stockitemname)) && f == 0)
	
						{
							if (firstRow == 0) {
								firstRow = 1;
							}
							else if (firstRow == 1) { 
								HSSFRow rowTableData = sheet.createRow(row_count++);
								
								sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,0,3));
								HSSFCell cellTotal = rowTableData.createCell(0);
								cellTotal.setCellStyle(contentCellStyle2);
								cellTotal.setCellValue("Total :");
								
	
								HSSFCell cellTotalQty = rowTableData.createCell(4);
								cellTotalQty.setCellStyle(contentCellStyle2);
								cellTotalQty.setCellValue(getNumberWithDecimal(Double.parseDouble(curropen.toString())));
	
								HSSFCell cellTotalStockin = rowTableData.createCell(5);
								cellTotalStockin.setCellStyle(contentCellStyle2);
								cellTotalStockin.setCellValue(getNumberWithDecimal(Double.parseDouble(totalstockinItem.toString())));
	
								HSSFCell cellTotalStockout = rowTableData.createCell(6);
								cellTotalStockout.setCellStyle(contentCellStyle2);
								cellTotalStockout.setCellValue(getNumberWithDecimal(Double.parseDouble(totalstockoutItem.toString())));
	
								HSSFCell cellTotalbalance = rowTableData.createCell(7);
								cellTotalbalance.setCellStyle(contentCellStyle2);
								cellTotalbalance.setCellValue(getNumberWithDecimal(Double.parseDouble(balance.toString())));
								curropen = 0.0;
								totalstockoutItem = 0.0;
								totalstockinItem = 0.0;
							}
							rowTableData1 = sheet.createRow(row_count++);
	
							HSSFCell cellNumberData = rowTableData1.createCell(0);
							cellNumberData.setCellStyle(contentCellStyle);
							cellNumberData.setCellValue(count++);
	
							HSSFCell cellItemData = rowTableData1.createCell(1);
							cellItemData.setCellStyle(contentCellStyle);
							cellItemData.setCellValue(stkreg.getStockitemName());
	
							HSSFCell cellUnitData = rowTableData1.createCell(2);
							cellUnitData.setCellStyle(contentCellStyle);
							cellUnitData.setCellValue(stkreg.getUomcode());
						}
					}
				}
				else{
					rowTableData1 = sheet.createRow(row_count++);
	
					HSSFCell cellNumberData = rowTableData1.createCell(0);
					cellNumberData.setCellStyle(contentCellStyle);
					cellNumberData.setCellValue(count++);
	
					HSSFCell cellItemData = rowTableData1.createCell(1);
					cellItemData.setCellStyle(contentCellStyle);
					cellItemData.setCellValue(stkreg.getStockitemName());
	
					HSSFCell cellUnitData = rowTableData1.createCell(2);
					cellUnitData.setCellStyle(contentCellStyle);
					cellUnitData.setCellValue(stkreg.getUomcode());
				}
				
				
				
				
				
				
				String open = stkreg.getOpening();
				String inqty = stkreg.getInQty();
				String outqty = stkreg.getOutQty();
				if (open == null || open == "") {
					open = "0.0";
				}
				if (inqty == null || inqty == "") {
					inqty = "0.0";
				}
				if (outqty == null || outqty == "") {
					outqty = "0.0";
				}
				
				if (stockRegister.getOption() == 1) {
					
					HSSFCell cellDateData = rowTableData1.createCell(3);
					cellDateData.setCellStyle(contentCellStyle);
					cellDateData.setCellValue(stkreg.getTxnDate());
					if (stkreg.getStock_item_id().equals(stockitemname) && firstRowOpening == 1) {
						
						
						HSSFCell cell = rowTableData1.createCell(4);
						cell.setCellValue("");
					}
					else if (stkreg.getStock_item_id().equals(stockitemname) && firstRowOpening == 0)
					
					{
						firstRowOpening = 1;
						curropen = Double.parseDouble(open.toString());
						HSSFCell cell = rowTableData1.createCell(4);
						cell.setCellStyle(contentCellStyle1);
						cell.setCellValue(getNumberWithDecimal(Double.parseDouble(open.toString())));
						
					}
				}
				else {
					HSSFCell cellDateData = rowTableData1.createCell(3);
					cellDateData.setCellStyle(contentCellStyle1);
					cellDateData.setCellValue(getNumberWithDecimal(Double.parseDouble(open.toString())));
				}
				
				stockintotal += Double.parseDouble(inqty);
				stockouttotal += Double.parseDouble(outqty);
				balance = (Double.parseDouble(open) + Double.parseDouble(inqty)) - (Double.parseDouble(outqty));
				total = total + balance;
				
				
				
				if (stockRegister.getOption() != 1) {
					HSSFCell cellDateData = rowTableData1.createCell(4);
					cellDateData.setCellStyle(contentCellStyle1);
					cellDateData.setCellValue(getRateWithDecimal(
							Double.parseDouble(open) * Double.parseDouble(stkreg.getCost_price())));
				}
				openingValue = openingValue + Double.parseDouble(open) * Double.parseDouble(stkreg.getCost_price());
				totalstockinItem += Double.parseDouble(inqty.toString());
				totalstockoutItem += Double.parseDouble(outqty.toString());
				
				HSSFCell cellDateData = rowTableData1.createCell(5);
				cellDateData.setCellStyle(contentCellStyle1);
				cellDateData.setCellValue(getNumberWithDecimal(Double.parseDouble(inqty.toString())));
				HSSFCell  out;
				if (stockRegister.getOption() != 1) {
					HSSFCell cell = rowTableData1.createCell(6);
					cell.setCellStyle(contentCellStyle1);
					cell.setCellValue(getRateWithDecimal(
							Double.parseDouble(inqty) * Double.parseDouble(stkreg.getCost_price())).toString());
					out = rowTableData1.createCell(7);
					
				} else{
					out = rowTableData1.createCell(6);
				}
				
				inqtyValue = inqtyValue + Double.parseDouble(inqty) * Double.parseDouble(stkreg.getCost_price());
				out.setCellStyle(contentCellStyle1);
				out.setCellValue(getNumberWithDecimal(Double.parseDouble(outqty.toString())));
				outqtyValue = outqtyValue + Double.parseDouble(outqty) * Double.parseDouble(stkreg.getCost_price());
				HSSFCell  balanceCell;
				if (stockRegister.getOption() != 1) {
					HSSFCell cell = rowTableData1.createCell(8);
					cell.setCellStyle(contentCellStyle1);
					cell.setCellValue(getRateWithDecimal(
							Double.parseDouble(outqty) * Double.parseDouble(stkreg.getCost_price())).toString());
					balanceCell = rowTableData1.createCell(9);
				}else{
					balanceCell = rowTableData1.createCell(7);
				}
				balanceValue = balanceValue + Double.parseDouble(balance.toString())
						* Double.parseDouble(stkreg.getCost_price());
				balanceCell.setCellStyle(contentCellStyle1);
				balanceCell.setCellValue(getNumberWithDecimal(Double.parseDouble(balance.toString())));
				if (stockRegister.getOption() != 1) {
					HSSFCell cell = rowTableData1.createCell(10);
					cell.setCellStyle(contentCellStyle1);
					cell.setCellValue(getRateWithDecimal(
							Double.parseDouble(balance.toString()) * Double.parseDouble(stkreg.getCost_price()))
							.toString());
				}	
			}
			
			HSSFRow row =sheet.createRow(row_count++);;
			
			if (stockRegister.getOption() == 1) {
				
			}
			else {
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,0,2));
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,3,4));
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,5,6));
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,7,8));
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,9,10));
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(contentCellStyle2);
				cell.setCellValue("Total:");
				
				HSSFCell cell0 = row.createCell(3);
				cell0.setCellStyle(contentCellStyle2);
				cell0.setCellValue(getRateWithDecimal(Double.parseDouble(openingValue.toString())));
				HSSFCell cell1 = row.createCell(5);
				cell1.setCellStyle(contentCellStyle2);
				cell1.setCellValue(getRateWithDecimal(Double.parseDouble(inqtyValue.toString())));
				HSSFCell cell2 = row.createCell(7);
				cell2.setCellStyle(contentCellStyle2);
				cell2.setCellValue(getRateWithDecimal(Double.parseDouble(outqtyValue.toString())));
				HSSFCell cell3 = row.createCell(9);
				cell3.setCellStyle(contentCellStyle2);
				cell3.setCellValue(getRateWithDecimal(Double.parseDouble(balanceValue.toString())));
				
				
			}
			
			if (stockRegister.getOption() == 1) {
				sheet.addMergedRegion(new CellRangeAddress(row_count-1,row_count-1,0,3));
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(contentCellStyle2);
				cell.setCellValue("Total:");
	
				HSSFCell cell0 = row.createCell(4);
				cell0.setCellStyle(contentCellStyle2);
				cell0.setCellValue(getNumberWithDecimal(Double.parseDouble(curropen.toString())));
				HSSFCell cell1 = row.createCell(5);
				cell1.setCellStyle(contentCellStyle2);
				cell1.setCellValue(getNumberWithDecimal(Double.parseDouble(totalstockinItem.toString())));
				HSSFCell cell2 = row.createCell(6);
				cell2.setCellStyle(contentCellStyle2);
				cell2.setCellValue(getNumberWithDecimal(Double.parseDouble(totalstockoutItem.toString())));
				HSSFCell cell3 = row.createCell(7);
				cell3.setCellStyle(contentCellStyle2);
				cell3.setCellValue(getNumberWithDecimal(Double.parseDouble(balance.toString())));
			}
		}
		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = sdf3.format(today1);

		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(report.getReportName().toLowerCase().replaceAll("\\s","")+stringToday3+".xls");
		response.addHeader("Content-disposition", headerResponse);
	}
}
